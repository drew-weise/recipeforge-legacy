package persistence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class OpenRouterDAOTest {
    private static final Logger logger = LogManager.getLogger(OpenRouterDAOTest.class);
    private final OpenRouterDAO openRouterDAO = new OpenRouterDAO();


    @Test
    void getResponseSuccess() throws Exception {
        String userRequest = "I have chicken breast at home and need a recipe I can cook it with, please give me a recipe that is quick and easy with lots of flavor";
        String response = openRouterDAO.sendPrompt(userRequest);

        assertNotNull(response);
        assertFalse(response.isBlank());

        logger.debug("OpenRouter response: {}", response);
    }
}