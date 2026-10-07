USE recipeforge_test;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS recipe_ingredients;
DROP TABLE IF EXISTS recipes;
DROP TABLE IF EXISTS ingredients;
DROP TABLE IF EXISTS users;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE users (
                       user_id INT NOT NULL AUTO_INCREMENT,
                       display_name VARCHAR(50) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       password_hash VARCHAR(255) NOT NULL,
                       PRIMARY KEY (user_id),
                       UNIQUE KEY email (email)
) ENGINE = InnoDB;

INSERT INTO users (
    user_id,
    display_name,
    email,
    password_hash
)
VALUES
    (1, 'drew123', 'drew@example.com', 'test-password-hash-1'),
    (2, 'alex456', 'alex@example.com', 'test-password-hash-2');

CREATE TABLE recipes (
                         recipe_id INT NOT NULL AUTO_INCREMENT,
                         user_id INT NOT NULL,
                         title VARCHAR(255) NOT NULL,
                         description TEXT,
                         instructions TEXT,
                         servings INT,
                         PRIMARY KEY (recipe_id),
                         KEY user_id (user_id),
                         CONSTRAINT recipes_ibfk_1
                             FOREIGN KEY (user_id)
                                 REFERENCES users (user_id)
                                 ON DELETE CASCADE
) ENGINE = InnoDB;

INSERT INTO recipes (
    recipe_id,
    user_id,
    title,
    description,
    instructions,
    servings
)
VALUES
    (
        1,
        1,
        'Pancakes',
        'Simple breakfast pancakes.',
        'Mix the ingredients. Heat a pan. Pour the batter into the pan. Cook each pancake until golden brown on both sides.',
        4
    ),
    (
        2,
        1,
        'Garlic Pasta',
        'Quick pasta with garlic and olive oil.',
        'Boil the pasta. Cook the garlic in olive oil. Toss the pasta with the garlic and serve.',
        2
    ),
    (
        3,
        2,
        'Vegetable Soup',
        'A basic vegetable soup with potatoes and carrots.',
        'Chop the vegetables. Add the vegetables and broth to a pot. Simmer until the vegetables are tender.',
        6
    ),
    (
        4,
        2,
        'Chicken Tacos',
        'Seasoned chicken tacos with fresh toppings.',
        'Cook the seasoned chicken. Warm the tortillas. Add the chicken and toppings to each tortilla.',
        4
    );

    CREATE TABLE ingredients;