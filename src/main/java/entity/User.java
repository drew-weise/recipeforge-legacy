package entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
/**
 * Represents a user account in RecipeForge.
 * @author dweise
 */
public class User {

    /** The user's database userId. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;
    /** The user's displayName. */

    @Column(name = "display_name", nullable = false)
    private String displayName;

    /** The user's email address. */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<Recipe> recipes = new ArrayList<>();

    /** Required by Hibernate. */
    public User() {
    }

    /** Creates a user with the given account information. */
    public User(String displayName, String email) {
        this.displayName = displayName;
        this.email = email;
    }

    /** @return the user's ID */
    public int getUserId() {
        return userId;
    }

    /** @return the user's recipes */
    public List<Recipe> getRecipes() {return recipes;}

    /** Sets the user's recipes */
    public void setRecipes(List<Recipe> recipes) {this.recipes = recipes;}

    /** @return the user's displayName */
    public String getDisplayName() {
        return displayName;
    }

    /** Sets the user's displayName. */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /** @return the user's email */
    public String getEmail() {
        return email;
    }

    /** Sets the user's email. */
    public void setEmail(String email) {
        this.email = email;
    }
}