package persistence;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class OpenRouterTest {

    public static void main(String[] args) {

        String apiKey = System.getenv("OPENROUTER_API_KEY");

        if (apiKey == null || apiKey.length() == 0) {
            throw new IllegalStateException(
                    "OPENROUTER_API_KEY is not configured"
            );
        }

        String jsonRequest =
                "{\"model\":\"openrouter/free\","
                        + "\"messages\":["
                        + "{\"role\":\"user\","
                        + "\"content\":\"Say hello in one sentence.\"}"
                        + "]}";

        Client client = ClientBuilder.newClient();

        Response response = client
                .target(
                        "https://openrouter.ai/api/v1/chat/completions"
                )
                .request(MediaType.APPLICATION_JSON)
                .header(
                        "Authorization",
                        "Bearer " + apiKey
                )
                .post(
                        Entity.entity(
                                jsonRequest,
                                MediaType.APPLICATION_JSON
                        )
                );

        try {
            System.out.println(
                    "HTTP status: " + response.getStatus()
            );

            System.out.println(
                    response.readEntity(String.class)
            );

        } finally {
            response.close();
            client.close();
        }
    }
}