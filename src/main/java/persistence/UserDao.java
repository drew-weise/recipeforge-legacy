package persistence;

import Util.SessionFactoryProvider;
import entity.User;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.util.List;

/**
 * Provides database operations for User entities.
 *
 */
public class UserDao {

    private final Logger logger = LogManager.getLogger(this.getClass());
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    /**
     * Retrieves a user by their database ID.
     *
     * @param id the user's database ID
     * @return the matching user, or null if no user was found
     */
    public User getById(int id) {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class, id);
        session.close();
        return user;
    }

    /**
     * Updates an existing user in the database.
     *
     * @param user the user to be updated
     */
    public void update(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.merge(user);

        transaction.commit();
        session.close();
    }

    /**
     * Inserts a new user into the database.
     *
     * @param user the user to be inserted
     * @return the generated database ID
     */
    public int insert(User user) {
        int id = 0;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.persist(user);

        transaction.commit();
        id = user.getUserId();
        session.close();

        return id;
    }

    /**
     * Deletes a user from the database.
     *
     * @param user the user to be deleted
     */
    public void delete(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.delete(user);

        transaction.commit();
        session.close();
    }

    /**
     * Retrieves all users from the database.
     *
     * @return a list containing all users
     */
    public List<User> getAll() {
        try (Session session = sessionFactory.openSession()) {
            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);

            query.select(root);

            List<User> users =
                    session.createSelectionQuery(query).getResultList();

            logger.debug("The list of users {}", users);
            return users;
        }
    }

    /**
     * Retrieves users whose specified property exactly matches a value.
     *
     * <p>Property names must be Java entity property names, such as
     * {@code displayName}, rather than database column names such as
     * {@code display_name}.</p>
     *
     * @param propertyName the User property to search
     * @param value the exact value to match
     * @return a list of users matching the property value
     */
    public List<User> getByPropertyEqual(String propertyName, String value) {
        Session session = sessionFactory.openSession();

        logger.debug("Searching for user with {} = {}", propertyName, value);

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);

        query.select(root)
                .where(builder.equal(root.get(propertyName), value));

        List<User> users =
                session.createSelectionQuery(query).getResultList();

        session.close();
        return users;
    }

    /**
     * Retrieves users whose specified property contains a value.
     *
     * <p>The search uses a SQL {@code LIKE} comparison and automatically
     * searches for the value anywhere within the property.</p>
     *
     * @param propertyName the User property to search
     * @param value the partial value to search for
     * @return a list of users matching the partial value
     */
    public List<User> getByPropertyLike(String propertyName, String value) {
        Session session = sessionFactory.openSession();

        logger.debug("Searching for user with {} LIKE {}", propertyName, value);

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Expression<String> propertyPath = root.get(propertyName);

        query.where(builder.like(propertyPath, "%" + value + "%"));

        List<User> users =
                session.createQuery(query).getResultList();

        session.close();
        return users;
    }
}