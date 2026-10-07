//package persistence;
//
//import Util.Database;
//import entity.Ingredient;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class IngredientDaoTest {
//
//    private GenericDao<Ingredient> ingredientDao;
//
//    @BeforeEach
//    void setUp() {
//        ingredientDao = new GenericDao<>(Ingredient.class);
//
//        Database database = Database.getInstance();
//        database.runSQL("CleanDB.sql");
//    }
//
//    @Test
//    void getByIdSuccess() {
//        Ingredient ingredient = ingredientDao.getById(1);
//
//        assertNotNull(ingredient);
//        assertEquals("Flour", ingredient.getIngredientName());
//    }
//
//    @Test
//    void insertSuccess() {
//        Ingredient ingredient = new Ingredient("Butter");
//
//        int insertedIngredientId = ingredientDao.insert(ingredient);
//
//        assertTrue(insertedIngredientId > 0);
//
//        Ingredient insertedIngredient =
//                ingredientDao.getById(insertedIngredientId);
//
//        assertNotNull(insertedIngredient);
//        assertEquals("Butter", insertedIngredient.getIngredientName());
//    }
//
//    @Test
//    void updateSuccess() {
//        Ingredient ingredientToUpdate = ingredientDao.getById(1);
//
//        ingredientToUpdate.setIngredientName("Whole Wheat Flour");
//        ingredientDao.update(ingredientToUpdate);
//
//        Ingredient updatedIngredient = ingredientDao.getById(1);
//
//        assertEquals(
//                "Whole Wheat Flour",
//                updatedIngredient.getIngredientName()
//        );
//    }
//
//    @Test
//    void deleteSuccess() {
//        Ingredient ingredientToDelete = ingredientDao.getById(1);
//        int ingredientId = ingredientToDelete.getIngredientId();
//
//        assertNotNull(ingredientDao.getById(ingredientId));
//
//        ingredientDao.delete(ingredientToDelete);
//
//        assertNull(ingredientDao.getById(ingredientId));
//    }
//
//    @Test
//    void getAllSuccess() {
//        List<Ingredient> ingredients = ingredientDao.getAll();
//
//        assertNotNull(ingredients);
//        assertEquals(16, ingredients.size());
//    }
//
//    @Test
//    void getByPropertyEqualSuccess() {
//        List<Ingredient> ingredients =
//                ingredientDao.getByPropertyEqual("ingredientName", "Flour");
//
//        assertEquals(1, ingredients.size());
//        assertEquals("Flour", ingredients.get(0).getIngredientName());
//    }
//}