package startup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.PropertiesLoader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.Properties;
import javax.servlet.annotation.WebServlet;

@WebServlet(
        loadOnStartup = 1,
        urlPatterns = {"/startup"}
)

public class CognitoPropertiesLoader extends HttpServlet implements PropertiesLoader {
    Properties properties;
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void init() throws ServletException {
        super.init();
        loadCognitoProperties();
    }

    private void loadCognitoProperties() {
        try {
            Properties properties = loadProperties("/cognito.properties");
            getServletContext().setAttribute("cognitoProperties", properties);
        } catch (Exception e) {
            logger.error("Error loading properties" + e.getMessage(), e);
        }
    }
}
