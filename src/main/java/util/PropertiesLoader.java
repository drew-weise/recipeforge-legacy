package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This interface contains a default method that can be used anywhere a Properties
 * object is needed to be loaded.
 *
 * @author Eric Knapp
 */
public interface PropertiesLoader {

    Logger logger = LogManager.getLogger(PropertiesLoader.class);

    /**
     * This default method will load a properties file into a Properties instance
     * and return it.
     *
     * @param propertiesFilePath a path to a file on the Java classpath
     * @return a populated Properties instance, or an empty Properties instance if
     * the file path was not found
     */
    default Properties loadProperties(String propertiesFilePath) {
        Properties properties = new Properties();

        try (InputStream input = this.getClass().getResourceAsStream(propertiesFilePath)) {
            if (input == null) {
                logger.error("Could not find properties file: " + propertiesFilePath);
                return properties;
            }
            properties.load(input);
        } catch (IOException e) {
            logger.error("Could not load properties file: " + propertiesFilePath, e);
        }

        return properties;
    }
}