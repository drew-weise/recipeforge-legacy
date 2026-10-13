package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

@WebServlet(urlPatterns = {"/logIn"})

/**
 * Begins the authentication process using AWS Cognito
 */
public class LogIn extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }
    /**
     * Route to the aws-hosted cognito login page.
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Properties properties = (Properties) getServletContext().getAttribute("cognitoProperties");

        if (properties == null) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Cognito configuration is unavailable.");
            return;
        }

        String url = properties.getProperty("loginURL") + "?response_type=code&client_id=" + properties.getProperty("client.id") + "&redirect_uri=" + properties.getProperty("redirectURL");
        resp.sendRedirect(url);
    }
}
