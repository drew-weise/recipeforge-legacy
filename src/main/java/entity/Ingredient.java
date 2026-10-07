package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ingredients")
/**
 * Represents an Ingredient in RecipeForge.
 *
 * @author dweise
 */
public class Ingredient {

    /** The ingredient's database ingredientId. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private int ingredientId;

    /** The ingredient's ingredientName. */
    @Column(name = "ingredient", nullable = false, unique = true)
    private String ingredientName;

    /** Required by Hibernate. */
    public Ingredient() {
    }

    /**
     * Creates a new Ingredient.
     *
     * @param ingredientName the ingredient name
     */
    public Ingredient(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    /** @return the ingredient id */
    public int getIngredientId() {return ingredientId;}

    /** @return the ingredient name */
    public String getIngredientName() {return ingredientName;}

    /** Sets the ingredient name. */
    public void setIngredientName(String ingredientName) {this.ingredientName = ingredientName;}

    @Override
    public String toString() {
        return "Ingredient{" +
                "ingredientId=" + ingredientId +
                ", ingredientName='" + ingredientName + '\'' +
                '}';
    }
}