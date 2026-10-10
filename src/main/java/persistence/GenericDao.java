package persistence;

import util.SessionFactoryProvider;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.util.List;

/**
 * Provides common CRUD and property-search operations for any Hibernate entity.
 *
 * @param <T> the type of entity managed by this DAO
 */
public class GenericDao<T> {

    private final Class<T> type;
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Creates a GenericDao for the specified entity type.
     *
     * @param type the entity class managed by this DAO
     */
    public GenericDao(Class<T> type) {
        this.type = type;
    }

    /**
     * Opens and returns a new Hibernate session.
     *
     * @return a new open Hibernate session
     */
    private Session getSession() {
        return SessionFactoryProvider.getSessionFactory().openSession();
    }

    /**
     * Retrieves an entity by its database ID.
     *
     * @param id the database ID of the entity
     * @return the matching entity, or {@code null} if no entity was found
     */
    public T getById(int id) {
        try (Session session = getSession()) {
            return session.get(type, id);
        }
    }

    /**
     * Deletes an entity from the database.
     *
     * @param entity the entity to delete
     */
    public void delete(T entity) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();

            session.delete(entity);

            transaction.commit();
        }
    }

    /**
     * Retrieves all entities of the managed type.
     *
     * @return a list containing all entities of the managed type
     */
    public List<T> getAll() {
        try (Session session = getSession()) {
            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(type);
            Root<T> root = query.from(type);

            query.select(root);

            List<T> entities =
                    session.createSelectionQuery(query).getResultList();

            logger.debug("The list of entities {}", entities);
            return entities;
        }
    }

    /**
     * Updates an existing entity in the database.
     *
     * @param entity the entity containing the updated values
     */
    public void update(T entity) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();

            session.merge(entity);

            transaction.commit();
        }
    }

    /**
     * Inserts a new entity into the database.
     *
     * @param entity the entity to insert
     * @return the generated database ID
     */
    public int insert(T entity) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();

            session.persist(entity);

            transaction.commit();

            return (Integer) session.getIdentifier(entity);
        }
    }

    /**
     * Finds entities whose property exactly matches the specified value.
     *
     * @param propertyName the Java property to search
     * @param value the exact value to match
     * @return a list of matching entities
     */
    public List<T> getByPropertyEqual(String propertyName, String value) {
        try (Session session = getSession()) {
            logger.debug(
                    "Searching for entity with {} = {}",
                    propertyName,
                    value
            );

            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(type);
            Root<T> root = query.from(type);

            query.select(root)
                    .where(builder.equal(root.get(propertyName), value));

            return session.createSelectionQuery(query).getResultList();
        }
    }

    /**
     * Finds entities whose string property contains the specified value.
     *
     * @param propertyName the Java string property to search
     * @param value the partial value to search for
     * @return a list of matching entities
     */
    public List<T> getByPropertyLike(String propertyName, String value) {
        try (Session session = getSession()) {
            logger.debug(
                    "Searching for entity with {} LIKE {}",
                    propertyName,
                    value
            );

            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(type);
            Root<T> root = query.from(type);
            Expression<String> propertyPath = root.get(propertyName);

            query.where(
                    builder.like(propertyPath, "%" + value + "%")
            );

            return session.createSelectionQuery(query).getResultList();
        }
    }
}