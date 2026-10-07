package persistence;

import entity.Ingredient;
import entity.Recipe;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Util.Database;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the CRUD and search operations provided by RecipeDao
 *
 */
class RecipeDaoTest {

    private GenericDao<Recipe> recipeDao;
    private GenericDao<User> userDao;
    private GenericDao<Ingredient> ingredientDao;

    /**
     * Resets the test database and creates a new RecipeDao/UserDao
     * before each test.
     */
    @BeforeEach
    void setUp() {

        recipeDao = new GenericDao<>(Recipe.class);
        userDao = new GenericDao<>(User.class);
        ingredientDao = new GenericDao<>(Ingredient.class);

        Database database = Database.getInstance();
        database.runSQL("CleanDB.sql");
    }

    /**
     * Verifies that a recipe can be retrieved by its database ID.
     */
    @Test
    void getByIdSuccess() {
        Recipe recipe = (Recipe)recipeDao.getById(1);

        assertNotNull(recipe);
        assertEquals("Pancakes", recipe.getRecipeTitle());
    }

    /**
     * Verifies that an existing recipe can be updated and retrieved
     * with its changed title.
     */
    @Test
    void updateSuccess() {
        Recipe recipeToUpdate = (Recipe)recipeDao.getById(1);

        recipeToUpdate.setRecipeTitle("Waffles");
        recipeDao.update(recipeToUpdate);

        Recipe updatedRecipe = (Recipe)recipeDao.getById(1);

        assertEquals("Waffles", updatedRecipe.getRecipeTitle());
    }

    /**
     * Verifies that a new recipe can be inserted, assigned a generated ID,
     * retrieved, and associated with the correct user.
     */
    @Test
    void insertSuccess() {
        User user = userDao.getById(1);

        Recipe recipe = new Recipe(
                "Avocado Toast",
                "Toast with Avocado Spread and an Egg",
                "Make some toast. Smash an avocado with a little salt and pepper, then spread on toast. Optionally sprinkle some everything bagel seasoning on top. Cook a sunny-side-up egg and place it on top of the avocado spread.",
                2,
                user
        );

        int insertedRecipeId = recipeDao.insert(recipe);

        assertTrue(insertedRecipeId > 0);

        Recipe insertedRecipe = recipeDao.getById(insertedRecipeId);

        assertNotNull(insertedRecipe);
        assertEquals("Avocado Toast", insertedRecipe.getRecipeTitle());
        assertEquals(
                "Toast with Avocado Spread and an Egg",
                insertedRecipe.getRecipeDescription()
        );
        assertEquals(2, insertedRecipe.getRecipeServings());
        assertNotNull(insertedRecipe.getUser());
        assertEquals(1, insertedRecipe.getUser().getUserId());
    }

    /**
     * Verifies that an existing recipe can be deleted and can no longer
     * be retrieved by its ID.
     */
    @Test
    void deleteSuccess() {
        User user = userDao.getById(1);
        assertNotNull(user);

        Recipe recipeToDelete = user.getRecipes().get(1);
        int recipeId = recipeToDelete.getRecipeId();

        assertNotNull(recipeDao.getById(recipeId));

        recipeDao.delete(recipeToDelete);

        assertNull(recipeDao.getById(recipeId));

        User updatedUser = userDao.getById(1);
        assertEquals(1, updatedUser.getRecipes().size());

        assertNotNull(user);

    }

    @Test
    void deleteRecipeKeepsIngredientsSuccess() {
        Recipe recipeToDelete = recipeDao.getById(1);
        Ingredient flour = ingredientDao.getById(1);

        assertNotNull(recipeToDelete);
        assertNotNull(flour);

        recipeDao.delete(recipeToDelete);

        assertNull(recipeDao.getById(1));

        Ingredient flourAfterRecipeDelete = ingredientDao.getById(1);

        assertNotNull(flourAfterRecipeDelete);
        assertEquals("Flour", flourAfterRecipeDelete.getIngredientName());
    }

    /**
     * Verifies that all recipes in the reset test database are returned.
     */
    @Test
    void getAllSuccess() {
        List<Recipe> recipes = recipeDao.getAll();

        assertNotNull(recipes);
        assertEquals(4, recipes.size());
    }

    /**
     * Verifies that an exact recipe title can be searched successfully.
     */
    @Test
    void getByPropertyEqual() {
        List<Recipe> recipes =
                recipeDao.getByPropertyEqual("recipeTitle", "Pancakes");

        assertEquals(1, recipes.size());
    }

    /**
     * Verifies that a recipe title can be searched using a partial match.
     */
    @Test
    void getByPropertyLike() {
        List<Recipe> recipes =
                recipeDao.getByPropertyLike("recipeTitle", "pan");

        assertEquals(1, recipes.size());
        assertEquals("Pancakes", recipes.get(0).getRecipeTitle());
    }
}