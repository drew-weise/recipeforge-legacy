package persistence;

import Util.Database;
import entity.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Ingredient dao test.
 */
class IngredientDaoTest {

    private GenericDao<Ingredient> ingredientDao;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        ingredientDao = new GenericDao<>(Ingredient.class);

        Database database = Database.getInstance();
        database.runSQL("CleanDB.sql");
    }

    /**
     * Gets by id success.
     */
    @Test
    void getByIdSuccess() {
        Ingredient ingredient = ingredientDao.getById(1);

        assertNotNull(ingredient);
        assertEquals("Flour", ingredient.getIngredientName());
    }

    /**
     * Insert success.
     */
    @Test
    void insertSuccess() {
        Ingredient ingredient = new Ingredient("Butter");

        int insertedIngredientId = ingredientDao.insert(ingredient);

        assertTrue(insertedIngredientId > 0);

        Ingredient insertedIngredient = ingredientDao.getById(insertedIngredientId);

        assertNotNull(insertedIngredient);
        assertEquals("Butter", insertedIngredient.getIngredientName());
    }

    /**
     * Update success.
     */
    @Test
    void updateSuccess() {
        Ingredient ingredientToUpdate = ingredientDao.getById(1);

        ingredientToUpdate.setIngredientName("Whole Wheat Flour");
        ingredientDao.update(ingredientToUpdate);

        Ingredient updatedIngredient = ingredientDao.getById(1);

        assertEquals("Whole Wheat Flour", updatedIngredient.getIngredientName());
    }

    /**
     * Delete success.
     */
    @Test
    void deleteSuccess() {
        Ingredient ingredientToDelete = ingredientDao.getById(1);
        int ingredientId = ingredientToDelete.getIngredientId();

        assertNotNull(ingredientDao.getById(ingredientId));

        ingredientDao.delete(ingredientToDelete);

        assertNull(ingredientDao.getById(ingredientId));
    }

    /**
     * Gets all success.
     */
    @Test
    void getAllSuccess() {
        List<Ingredient> ingredients = ingredientDao.getAll();

        assertNotNull(ingredients);
        assertEquals(16, ingredients.size());
    }

    /**
     * Gets by property equal success.
     */
    @Test
    void getByPropertyEqualSuccess() {
        List<Ingredient> ingredients = ingredientDao.getByPropertyEqual("ingredientName", "Flour");

        assertEquals(1, ingredients.size());
        assertEquals("Flour", ingredients.get(0).getIngredientName());
    }

    /**
     * Gets by property like success.
     */
    @Test
    void getByPropertyLikeSuccess() {
        List<Ingredient> ingredients =
                ingredientDao.getByPropertyLike("ingredientName", "oil");

        assertEquals(1, ingredients.size());
        assertEquals("Olive Oil", ingredients.get(0).getIngredientName());
    }
}