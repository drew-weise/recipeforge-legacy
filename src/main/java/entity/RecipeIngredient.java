package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recipe_ingredient")
/**
 * Represents a RecipeIngredient in RecipeForge.
 *
 * @author dweise
 */
public class RecipeIngredient {

    /** The recipe ingredient's database recipeIngredientId. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_ingredient_id")
    private int recipeIngredientId;

    /** The recipe ingredient's recipe. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    /** The recipe ingredient's ingredient. */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    /** The recipe ingredient's amount. */
    @Column(name = "amount", nullable = false)
    private String amount;

    /** Required by Hibernate. */
    public RecipeIngredient() {
    }

    /**
     * Creates a new RecipeIngredient.
     *
     * @param recipe     the recipe
     * @param ingredient the ingredient
     * @param amount     the amount
     */
    public RecipeIngredient(Recipe recipe, Ingredient ingredient, String amount) {
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.amount = amount;
    }

    /** @return the recipe ingredient id */
    public int getRecipeIngredientId() {return recipeIngredientId;}

    /** @return the recipe */
    public Recipe getRecipe() {return recipe;}

    /** Sets the recipe. */
    public void setRecipe(Recipe recipe) {this.recipe = recipe;}

    /** @return the ingredient */
    public Ingredient getIngredient() {return ingredient;}

    /** Sets the ingredient. */
    public void setIngredient(Ingredient ingredient) {this.ingredient = ingredient;}

    /** @return the amount */
    public String getAmount() {return amount;}

    /** Sets the amount. */
    public void setAmount(String amount) {this.amount = amount;}

    @Override
    public String toString() {
        return "RecipeIngredient{" +
                "recipeIngredientId=" + recipeIngredientId +
                ", recipe=" + recipe +
                ", ingredient=" + ingredient +
                ", amount='" + amount + '\'' +
                '}';
    }
}