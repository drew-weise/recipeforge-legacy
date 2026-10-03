package persistence;

import Util.SessionFactoryProvider;
import entity.Recipe;
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
 * Provides database operations for Recipe entities.
 *
 */
public class RecipeDao {

    private final Logger logger = LogManager.getLogger(this.getClass());
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    /**
     * Retrieves a recipe by its database ID.
     *
     * @param id the recipe's database ID
     * @return the matching recipe, or null if no recipe was found
     */
    public Recipe getById(int id) {
        Session session = sessionFactory.openSession();
        Recipe recipe = session.get(Recipe.class, id);
        session.close();
        return recipe;
    }

    /**
     * Updates an existing recipe in the database.
     *
     * @param recipe the recipe to be updated
     */
    public void update(Recipe recipe) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.merge(recipe);

        transaction.commit();
        session.close();
    }

    /**
     * Inserts a new recipe into the database.
     *
     * @param recipe the recipe to be inserted
     * @return the generated database ID
     */
    public int insert(Recipe recipe) {
        int id = 0;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.persist(recipe);

        transaction.commit();
        id = recipe.getRecipeId();
        session.close();

        return id;
    }

    /**
     * Deletes a recipe from the database.
     *
     * @param recipe the recipe to be deleted
     */
    public void delete(Recipe recipe) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.delete(recipe);

        transaction.commit();
        session.close();
    }

    /**
     * Retrieves all recipes from the database.
     *
     * @return a list containing all recipes
     */
    public List<Recipe> getAll() {
        try (Session session = sessionFactory.openSession()) {
            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Recipe> query = builder.createQuery(Recipe.class);
            Root<Recipe> root = query.from(Recipe.class);

            query.select(root);

            List<Recipe> recipes =
                    session.createSelectionQuery(query).getResultList();

            logger.debug("The list of recipes {}", recipes);
            return recipes;
        }
    }

    /**
     * Retrieves recipes whose specified property exactly matches a value.
     *
     * <p>Property names must be Java entity property names, such as
     * {@code recipeTitle}, rather than database column names such as
     * {@code title}.</p>
     *
     * @param propertyName the Recipe property to search
     * @param value the exact value to match
     * @return a list of recipes matching the property value
     */
    public List<Recipe> getByPropertyEqual(String propertyName, String value) {
        Session session = sessionFactory.openSession();

        logger.debug("Searching for recipe with {} = {}", propertyName, value);

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Recipe> query = builder.createQuery(Recipe.class);
        Root<Recipe> root = query.from(Recipe.class);

        query.select(root)
                .where(builder.equal(root.get(propertyName), value));

        List<Recipe> recipes =
                session.createSelectionQuery(query).getResultList();

        session.close();
        return recipes;
    }

    /**
     * Retrieves recipes whose specified property contains a value.
     *
     * <p>The search uses a SQL {@code LIKE} comparison and searches
     * for the value anywhere within the property.</p>
     *
     * @param propertyName the Recipe property to search
     * @param value the partial value to search for
     * @return a list of recipes matching the partial value
     */
    public List<Recipe> getByPropertyLike(String propertyName, String value) {
        Session session = sessionFactory.openSession();

        logger.debug("Searching for recipe with {} LIKE {}", propertyName, value);

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Recipe> query = builder.createQuery(Recipe.class);
        Root<Recipe> root = query.from(Recipe.class);
        Expression<String> propertyPath = root.get(propertyName);

        query.where(builder.like(propertyPath, "%" + value + "%"));

        List<Recipe> recipes =
                session.createQuery(query).getResultList();

        session.close();
        return recipes;
    }
}