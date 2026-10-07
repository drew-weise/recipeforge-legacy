package persistence;

import Util.Database;
import entity.Ingredient;
import entity.Recipe;
import entity.RecipeIngredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Recipe ingredient dao test.
 */
class RecipeIngredientDaoTest {

    private GenericDao<RecipeIngredient> recipeIngredientDao;
    private GenericDao<Recipe> recipeDao;
    private GenericDao<Ingredient> ingredientDao;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        recipeIngredientDao = new GenericDao<>(RecipeIngredient.class);
        recipeDao = new GenericDao<>(Recipe.class);
        ingredientDao = new GenericDao<>(Ingredient.class);

        Database database = Database.getInstance();
        database.runSQL("CleanDB.sql");
    }

    /**
     * Insert success.
     */
    @Test
    void insertSuccess() {
        Recipe recipe = recipeDao.getById(1);
        Ingredient ingredient = ingredientDao.getById(4);

        RecipeIngredient recipeIngredient = new RecipeIngredient(recipe, ingredient, "2 cups");

        int insertedRecipeIngredientId = recipeIngredientDao.insert(recipeIngredient);

        assertTrue(insertedRecipeIngredientId > 0);

        RecipeIngredient insertedRecipeIngredient = recipeIngredientDao.getById(insertedRecipeIngredientId);

        assertNotNull(insertedRecipeIngredient);
        assertEquals("2 cups", insertedRecipeIngredient.getAmount());

        assertEquals(1, insertedRecipeIngredient.getRecipe().getRecipeId());

        assertEquals(4, insertedRecipeIngredient.getIngredient().getIngredientId());
    }

    /**
     * Update success.
     */
    @Test
    void updateSuccess() {
        Recipe recipe = recipeDao.getById(1);
        Ingredient ingredient = ingredientDao.getById(4);

        RecipeIngredient recipeIngredient = new RecipeIngredient(recipe, ingredient, "2 cups");

        int insertedRecipeIngredientId = recipeIngredientDao.insert(recipeIngredient);

        RecipeIngredient recipeIngredientToUpdate = recipeIngredientDao.getById(insertedRecipeIngredientId);

        recipeIngredientToUpdate.setAmount("3 cups");
        recipeIngredientDao.update(recipeIngredientToUpdate);

        RecipeIngredient updatedRecipeIngredient = recipeIngredientDao.getById(insertedRecipeIngredientId);

        assertNotNull(updatedRecipeIngredient);
        assertEquals("3 cups", updatedRecipeIngredient.getAmount());
    }

    /**
     * Delete success.
     */
    @Test
    void deleteSuccess() {
        Recipe recipe = recipeDao.getById(1);
        Ingredient ingredient = ingredientDao.getById(4);

        RecipeIngredient recipeIngredient = new RecipeIngredient(recipe, ingredient, "2 cups");

        int insertedRecipeIngredientId = recipeIngredientDao.insert(recipeIngredient);

        RecipeIngredient recipeIngredientToDelete = recipeIngredientDao.getById(insertedRecipeIngredientId);

        assertNotNull(recipeIngredientToDelete);

        recipeIngredientDao.delete(recipeIngredientToDelete);

        assertNull(recipeIngredientDao.getById(insertedRecipeIngredientId));
    }

    /**
     * Gets by id success.
     */
    @Test
    void getByIdSuccess() {
        RecipeIngredient recipeIngredient = recipeIngredientDao.getById(1);

        assertNotNull(recipeIngredient);
        assertEquals("2 cups", recipeIngredient.getAmount());
        assertEquals(1, recipeIngredient.getRecipe().getRecipeId());
        assertEquals(1, recipeIngredient.getIngredient().getIngredientId());
    }

    /**
     * Gets all success.
     */
    @Test
    void getAllSuccess() {
        List<RecipeIngredient> recipeIngredients =
                recipeIngredientDao.getAll();

        assertNotNull(recipeIngredients);
        assertEquals(19, recipeIngredients.size());
    }

    /**
     * Gets by property equal success.
     */
    @Test
    void getByPropertyEqualSuccess() {
        List<RecipeIngredient> recipeIngredients =
                recipeIngredientDao.getByPropertyEqual("amount", "2 cups");

        assertEquals(1, recipeIngredients.size());
        assertEquals("2 cups", recipeIngredients.get(0).getAmount());
    }

    /**
     * Gets by property like success.
     */
    @Test
    void getByPropertyLikeSuccess() {
        List<RecipeIngredient> recipeIngredients =
                recipeIngredientDao.getByPropertyLike("amount", "cup");

        assertEquals(5, recipeIngredients.size());
    }
}