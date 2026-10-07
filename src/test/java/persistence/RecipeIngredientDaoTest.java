package persistence;

import Util.Database;
import entity.Ingredient;
import entity.Recipe;
import entity.RecipeIngredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeIngredientDaoTest {

    private GenericDao<RecipeIngredient> recipeIngredientDao;
    private GenericDao<Recipe> recipeDao;
    private GenericDao<Ingredient> ingredientDao;

    @BeforeEach
    void setUp() {
        recipeIngredientDao = new GenericDao<>(RecipeIngredient.class);
        recipeDao = new GenericDao<>(Recipe.class);
        ingredientDao = new GenericDao<>(Ingredient.class);

        Database database = Database.getInstance();
        database.runSQL("CleanDB.sql");
    }

    @Test
    void insertSuccess() {
        Recipe recipe = recipeDao.getById(1);
        Ingredient ingredient = ingredientDao.getById(1);

        RecipeIngredient recipeIngredient =
                new RecipeIngredient(recipe, ingredient, "2 cups");

        int insertedRecipeIngredientId =
                recipeIngredientDao.insert(recipeIngredient);

        assertTrue(insertedRecipeIngredientId > 0);

        RecipeIngredient insertedRecipeIngredient =
                recipeIngredientDao.getById(insertedRecipeIngredientId);

        assertNotNull(insertedRecipeIngredient);
        assertEquals("2 cups", insertedRecipeIngredient.getAmount());
        assertEquals(
                1,
                insertedRecipeIngredient.getRecipe().getRecipeId()
        );
        assertEquals(
                1,
                insertedRecipeIngredient.getIngredient().getIngredientId()
        );
    }

    @Test
    void updateSuccess() {
        Recipe recipe = recipeDao.getById(1);
        Ingredient ingredient = ingredientDao.getById(1);

        RecipeIngredient recipeIngredient =
                new RecipeIngredient(recipe, ingredient, "2 cups");

        int insertedRecipeIngredientId =
                recipeIngredientDao.insert(recipeIngredient);

        RecipeIngredient recipeIngredientToUpdate =
                recipeIngredientDao.getById(insertedRecipeIngredientId);

        recipeIngredientToUpdate.setAmount("3 cups");
        recipeIngredientDao.update(recipeIngredientToUpdate);

        RecipeIngredient updatedRecipeIngredient =
                recipeIngredientDao.getById(insertedRecipeIngredientId);

        assertEquals("3 cups", updatedRecipeIngredient.getAmount());
    }

    @Test
    void deleteSuccess() {
        Recipe recipe = recipeDao.getById(1);
        Ingredient ingredient = ingredientDao.getById(1);

        RecipeIngredient recipeIngredient =
                new RecipeIngredient(recipe, ingredient, "2 cups");

        int insertedRecipeIngredientId =
                recipeIngredientDao.insert(recipeIngredient);

        RecipeIngredient recipeIngredientToDelete =
                recipeIngredientDao.getById(insertedRecipeIngredientId);

        recipeIngredientDao.delete(recipeIngredientToDelete);

        assertNull(
                recipeIngredientDao.getById(insertedRecipeIngredientId)
        );
    }
}