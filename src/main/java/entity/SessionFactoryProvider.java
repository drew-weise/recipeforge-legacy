package entity;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This file provides a SessionFactory for use with DAOs using Hibernate
 *
 * @author paulawaite
 * @version 3.0
 */
public class SessionFactoryProvider {

    private static SessionFactory sessionFactory;
    private static StandardServiceRegistry registry;

    /**
     * Create session factory.
     */
    public static void createSessionFactory() {

        Properties databaseProperties = new Properties();

        try (InputStream inputStream = SessionFactoryProvider.class
                .getClassLoader()
                .getResourceAsStream("database.properties")) {

            if (inputStream == null) {
                throw new IllegalStateException(
                        "database.properties could not be found on the classpath");
            }

            databaseProperties.load(inputStream);
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Unable to load database.properties", exception);
        }

        // Create registry
        registry = new StandardServiceRegistryBuilder()
                .configure()
                .applySetting("hibernate.connection.driver_class",
                        databaseProperties.getProperty("driver"))
                .applySetting("hibernate.connection.url",
                        databaseProperties.getProperty("url"))
                .applySetting("hibernate.connection.username",
                        databaseProperties.getProperty("username"))
                .applySetting("hibernate.connection.password",
                        databaseProperties.getProperty("password"))
                .build();

        // Create MetadataSources
        MetadataSources sources = new MetadataSources(registry);

        // Create Metadata
        Metadata metadata = sources.getMetadataBuilder().build();

        // Create SessionFactory
        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }

    /**
     * Gets session factory.
     *
     * @return the session factory
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            createSessionFactory();
        }
        return sessionFactory;

    }
}
