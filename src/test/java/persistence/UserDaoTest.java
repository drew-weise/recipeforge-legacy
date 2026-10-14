package persistence;

import entity.Recipe;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Database;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the CRUD and search operations provided by UserDao
 *
 */
class UserDaoTest {

    private GenericDao<User> userDao;
    private GenericDao<Recipe> recipeDao;

    /**
     * Resets the test database and creates a new UserDao
     * before each test.
     */
    @BeforeEach
    void setUp() {
        userDao = new GenericDao<>(User.class);
        recipeDao = new GenericDao<>(Recipe.class);

        Database database = Database.getInstance();
        database.runSQL("CleanDB.sql");
    }

    /**
     * Verifies that a user can be retrieved by their database ID.
     */
    @Test
    void getByIdSuccess() {
        User user = userDao.getById(1);

        assertNotNull(user);
        assertEquals("drew123", user.getDisplayName());
    }

    /**
     * Verifies that an existing user's display name can be updated
     * and retrieved with its new value.
     */
    @Test
    void updateSuccess() {
        User userToUpdate = userDao.getById(1);

        assertNotNull(userToUpdate);

        userToUpdate.setDisplayName("John");
        userDao.update(userToUpdate);

        User updatedUser = userDao.getById(1);

        assertNotNull(updatedUser);
        assertEquals("John", updatedUser.getDisplayName());
    }

    /**
     * Verifies that a new user can be inserted, assigned a generated ID,
     * and retrieved from the database.
     */
    @Test
    void insertSuccess() {
        User user = new User(
                "Johnny123",
                "Appleseed@gmail.com"
        );

        int insertUserId = userDao.insert(user);

        assertTrue(insertUserId > 0);

        User insertedUser = userDao.getById(insertUserId);

        assertNotNull(insertedUser);
        assertEquals("Johnny123", insertedUser.getDisplayName());
        assertEquals("Appleseed@gmail.com", insertedUser.getEmail());
    }

    /**
     * Verifies that an existing user can be deleted and can no longer
     * be retrieved by their database ID.
     */
    @Test
    void deleteSuccess() {
        User userToDelete = userDao.getById(1);

        assertNotNull(userToDelete);

        userDao.delete(userToDelete);

        assertNull(userDao.getById(1));
    }

    /**
     * Verifies that deleting a user also deletes all recipes
     * associated with that user.
     */
    @Test
    void deleteWithRecipesSuccess() {
        User userToDelete = userDao.getById(1);

        assertNotNull(userToDelete);

        List<Recipe> recipes = userToDelete.getRecipes();

        assertEquals(2, recipes.size());

        Recipe recipe1 = recipes.get(0);
        Recipe recipe2 = recipes.get(1);

        int recipeId1 = recipe1.getRecipeId();
        int recipeId2 = recipe2.getRecipeId();

        userDao.delete(userToDelete);

        assertNull(userDao.getById(1));

        assertNull(recipeDao.getById(recipeId1));
        assertNull(recipeDao.getById(recipeId2));
    }

    /**
     * Verifies that all users in the reset test database are returned.
     */
    @Test
    void getAllSuccess() {
        List<User> users = userDao.getAll();

        assertNotNull(users);
        assertEquals(2, users.size());
    }

    /**
     * Verifies that a user can be found using an exact display name match.
     */
    @Test
    void getByPropertyEqualSuccess() {
        List<User> users = userDao.getByPropertyEqual("displayName", "drew123");

        assertEquals(1, users.size());
        assertEquals("drew123", users.get(0).getDisplayName());
    }

    /**
     * Verifies that users can be found using a partial display name match.
     */
    @Test
    void getByPropertyLikeSuccess() {
        List<User> users = userDao.getByPropertyLike("displayName", "drew123");

        assertEquals(1, users.size());
        assertEquals("drew123", users.get(0).getDisplayName());
    }
}