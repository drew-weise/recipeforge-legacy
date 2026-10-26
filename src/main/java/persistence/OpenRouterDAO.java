package persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openrouter.ChatCompletionResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class OpenRouterDAO {

    public String getRecipe(String userRequest) throws Exception {

        String apiKey = System.getenv("OPENROUTER_API_KEY");

        if (apiKey == null || apiKey.length() == 0) {
            throw new IllegalStateException("OPENROUTER_API_KEY is not configured");
        }

        String jsonRequest =
                "{\"model\":\"openrouter/free\","
                        + "\"messages\":["
                        + "{\"role\":\"user\","
                        + "\"content\":\""
                        + userRequest
                        + "\"}"
                        + "]}";

        Client client = ClientBuilder.newClient();

        Response response = client
                .target("https://openrouter.ai/api/v1/chat/completions")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + apiKey)
                .post(Entity.entity(jsonRequest, MediaType.APPLICATION_JSON));

        try {
            String responseJson = response.readEntity(String.class);

            if (response.getStatus() < 200 || response.getStatus() >= 300) {

                throw new RuntimeException("OpenRouter request failed: " + response.getStatus() + " " + responseJson);
            }

            ObjectMapper objectMapper = new ObjectMapper();

            ChatCompletionResponse openRouterResponse =
                    objectMapper.readValue(responseJson, ChatCompletionResponse.class);

            return openRouterResponse
                    .getChoices()
                    .get(0)
                    .getMessage()
                    .getContent();

        } finally {
            response.close();
            client.close();
        }
    }
}