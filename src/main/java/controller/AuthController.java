package controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import auth.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Handles the Cognito login callback, exchanges the authorization code
 * for tokens, validates the ID token, and stores the user's email
 * in the session.
 */
@WebServlet(urlPatterns = {"/auth"})
public class AuthController extends HttpServlet {
    Properties cognito;
    Jwks jwks;

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void init() throws ServletException {
        super.init();
        cognito = (Properties) getServletContext().getAttribute("cognitoProperties");
        loadJwks();
    }

    /**
     * Gets the auth code from the request and exchanges it for a token
     * containing user information.
     *
     * @param req  the servlet request
     * @param resp the servlet response
     * @throws ServletException if the servlet encounters an error
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String authCode = req.getParameter("code");
        String email;

        if (authCode == null) {
            req.setAttribute("errorMessage", "The login response was missing an authorization code.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        } else {
            HttpRequest tokenRequest = buildAuthRequest(authCode);
            try {
                TokenResponse tokenResponse = getCognitoToken(tokenRequest);
                email = validateCognitoToken(tokenResponse);
                req.getSession().setAttribute("email", email);
                resp.sendRedirect(req.getContextPath() + "/user");
            } catch (IOException e) {
                logger.error("Error getting or validating the token", e);
                req.setAttribute("errorMessage", "Unable to complete login.");
                req.getRequestDispatcher("/error.jsp").forward(req, resp);
            } catch (InterruptedException e) {
                logger.error("Error getting token from Cognito OAuth URL", e);
                req.setAttribute("errorMessage", "Unable to complete login.");
                req.getRequestDispatcher("/error.jsp").forward(req, resp);
            }
        }
    }

    /**
     * Sends the request for a token to Cognito and maps the response.
     *
     * @param tokenRequest the request sent to Cognito's oauth2/token endpoint
     * @return the response from the oauth2/token endpoint, including the
     *         ID token, access token, and refresh token
     * @throws IOException if the request cannot be sent
     * @throws InterruptedException if the request is interrupted
     */
    private TokenResponse getCognitoToken(HttpRequest tokenRequest) throws IOException, InterruptedException {

        ObjectMapper mapper = new ObjectMapper();
        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = client.send(tokenRequest, HttpResponse.BodyHandlers.ofString());

        logger.debug("Response headers: {}", response.headers());

        return mapper.readValue(response.body(), TokenResponse.class);
    }

    /**
     * Reads the Cognito ID token, finds the correct public key, verifies the
     * token's signature and issuer, and returns the email claim.
     *
     * @param tokenResponse the response containing the Cognito ID token
     * @return the email extracted from the validated ID token
     * @throws IOException if the token cannot be parsed or the matching
     *                     public key cannot be built
     */
    private String validateCognitoToken(TokenResponse tokenResponse) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CognitoTokenHeader tokenHeader = mapper.readValue(CognitoJWTParser.getHeader(tokenResponse.getIdToken()).toString(), CognitoTokenHeader.class);
        
        String tokenKeyId = tokenHeader.getKid();
        Jwk matchingKey = findMatchingJwk(tokenKeyId);
        RSAPublicKey publicKey = buildPublicKey(matchingKey);


        if (!"RS256".equals(tokenHeader.getAlg())) {
            logger.error("Invalid algorithm: {}", tokenHeader.getAlg());
            throw new IOException("Only RS256 tokens are accepted");
        }

        Algorithm algorithm = Algorithm.RSA256(publicKey, null);

        String iss = String.format("https://cognito-idp.%s.amazonaws.com/%s", cognito.getProperty("region"), cognito.getProperty("poolId"));

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(iss)
                .withClaim("token_use", "id")
                .build();

        DecodedJWT jwt = verifier.verify(tokenResponse.getIdToken());

        return jwt.getClaim("email").asString();
    }

    /**
     * Finds the public JSON Web Key whose key ID matches the key ID
     * contained in the Cognito token header.
     *
     * @param tokenKeyId the key ID from the Cognito token header
     * @return the matching JSON Web Key
     * @throws IOException if the JWKS was not loaded, the token has no key ID,
     *                     or no matching key is found
     */
    private Jwk findMatchingJwk(String tokenKeyId) throws IOException {
        if (jwks == null || jwks.getKeys() == null) {
            throw new IOException("JWKS was not loaded");
        }

        if (tokenKeyId == null) {
            throw new IOException("Token did not contain a kid value");
        }

        for (Jwk jwk : jwks.getKeys()) {
            if (tokenKeyId.equals(jwk.getKid())) {
                return jwk;
            }
        }

        throw new IOException(
                "No JWKS key matched token kid: " + tokenKeyId
        );
    }

    /**
     * Builds an RSA public key from the modulus and exponent contained
     * in a Cognito JSON Web Key.
     *
     * @param matchingKey the JSON Web Key containing the RSA key values
     * @return the RSA public key used to verify the token signature
     * @throws IOException if the key is null, missing its modulus or exponent,
     *                     or cannot be converted into an RSA public key
     */
    private RSAPublicKey buildPublicKey(Jwk matchingKey) throws IOException {
        if (matchingKey == null) {
            throw new IOException("Cannot build public key from null JWK");
        }

        if (matchingKey.getN() == null || matchingKey.getE() == null) {
            throw new IOException("JWK is missing modulus or exponent");
        }

        BigInteger modulus = new BigInteger(1, org.apache.commons.codec.binary.Base64.decodeBase64(matchingKey.getN()));
        BigInteger exponent = new BigInteger(1, org.apache.commons.codec.binary.Base64.decodeBase64(matchingKey.getE()));

        try {
            return (RSAPublicKey) KeyFactory
                    .getInstance("RSA")
                    .generatePublic(new RSAPublicKeySpec(modulus, exponent));

        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new IOException("Could not build RSA public key from JWK", e);
        }
    }

    /** Create the auth url and use it to build the request.
     *
     * @param authCode auth code received from Cognito as part of the login process
     * @return the constructed oauth request
     */
    private HttpRequest buildAuthRequest(String authCode) {
        String clientCredentials = cognito.getProperty("client.id") + ":" + cognito.getProperty("client.secret");

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("grant_type", "authorization_code");
        parameters.put("code", authCode);
        parameters.put("redirect_uri", cognito.getProperty("redirectURL"));

        String form = parameters
                .keySet()
                .stream()
                .map(key -> key + "=" + URLEncoder.encode(parameters.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        String encoding = Base64.getEncoder().encodeToString(clientCredentials.getBytes(StandardCharsets.UTF_8));

        return HttpRequest.newBuilder()
                .uri(URI.create(cognito.getProperty("oauthURL")))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", "Basic " + encoding)
                .POST(HttpRequest.BodyPublishers.ofString(form))
                .build();
    }

    /**
     * Downloads the JSON Web Key Set from the Cognito user pool and converts
     * the response into Jwks and Jwk objects for token validation.
     *
     * @throws ServletException if the JWKS cannot be downloaded, parsed, or loaded successfully
     */
    private void loadJwks() throws ServletException {
        try {
            ObjectMapper mapper = new ObjectMapper();

            String jwksUrl = String.format(
                    "https://cognito-idp.%s.amazonaws.com/%s/.well-known/jwks.json",
                    cognito.getProperty("region"),
                    cognito.getProperty("poolId")
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(jwksUrl))
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                            .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new IOException("JWKS request failed with HTTP status " + response.statusCode());
            }

            jwks = mapper.readValue(response.body(), Jwks.class);

            logger.debug("JWKS is loaded");

        } catch (IOException e) {
            logger.error("Unable to load Cognito JWKS", e);
            throw new ServletException("Unable to load Cognito JWKS", e);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Loading Cognito JWKS was interrupted", e);
            throw new ServletException("Loading Cognito JWKS was interrupted", e);
        }
    }
}

