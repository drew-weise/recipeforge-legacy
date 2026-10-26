package persistence;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class OpenRouterTest {

    public static void main(String[] args) throws Exception {

        String apiKey = System.getenv("OPENROUTER_API_KEY");

        if (apiKey == null || apiKey.length() == 0) {
            throw new IllegalStateException(
                    "OPENROUTER_API_KEY is not configured");
        }

        String jsonRequest =
                "{\"model\":\"openrouter/free\","
                        + "\"messages\":["
                        + "{\"role\":\"user\","
                        + "\"content\":\"Create a simple recipe using chicken, rice, onion, and garlic.\"}"
                        + "]}";

        Client client = ClientBuilder.newClient();

        Response response = client
                .target("https://openrouter.ai/api/v1/chat/completions")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + apiKey)
                .post(Entity.entity(jsonRequest, MediaType.APPLICATION_JSON));

        try {System.out.println("HTTP status: " + response.getStatus());

            String responseJson =
                    response.readEntity(String.class);

            if (response.getStatus() < 200 || response.getStatus() >= 300) {

                System.out.println(responseJson);
                return;
            }

            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode root = objectMapper.readTree(responseJson);

            String answer = root.at("/choices/0/message/content").asText();

            System.out.println();
            System.out.println("Generated recipe:");
            System.out.println(answer);

        } finally {
            response.close();
            client.close();
        }
    }
}