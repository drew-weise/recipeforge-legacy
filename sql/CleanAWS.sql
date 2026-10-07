USE recipeforge;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS recipe_ingredient;
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


CREATE TABLE ingredients (
                             ingredient_id INT NOT NULL AUTO_INCREMENT,
                             ingredient VARCHAR(100) NOT NULL,
                             PRIMARY KEY (ingredient_id),
                             UNIQUE KEY ingredient_name (ingredient)
) ENGINE = InnoDB;


INSERT INTO ingredients (
    ingredient_id,
    ingredient
)
VALUES
    (1, 'Flour'),
    (2, 'Milk'),
    (3, 'Eggs'),
    (4, 'Garlic'),
    (5, 'Spaghetti'),
    (6, 'Olive Oil'),
    (7, 'Potatoes'),
    (8, 'Carrots'),
    (9, 'Chicken'),
    (10, 'Tortillas'),
    (11, 'Chicken Broth'),
    (12, 'Salt'),
    (13, 'Pepper'),
    (14, 'Taco Seasoning'),
    (15, 'Lettuce'),
    (16, 'Cheese');


CREATE TABLE recipe_ingredient (
                                   recipe_ingredient_id INT NOT NULL AUTO_INCREMENT,
                                   recipe_id INT NOT NULL,
                                   ingredient_id INT NOT NULL,
                                   amount VARCHAR(100) NOT NULL,

                                   PRIMARY KEY (recipe_ingredient_id),

                                   UNIQUE KEY recipe_ingredient_unique (recipe_id, ingredient_id),

                                   CONSTRAINT recipe_ingredient_ibfk_1
                                       FOREIGN KEY (recipe_id)
                                           REFERENCES recipes (recipe_id)
                                           ON DELETE CASCADE,

                                   CONSTRAINT recipe_ingredient_ibfk_2
                                       FOREIGN KEY (ingredient_id)
                                           REFERENCES ingredients (ingredient_id)
                                           ON DELETE CASCADE
) ENGINE = InnoDB;


INSERT INTO recipe_ingredient (
    recipe_id,
    ingredient_id,
    amount
)
VALUES
    (1, 1, '2 cups'),
    (1, 2, '1 1/2 cups'),
    (1, 3, '2 eggs'),
    (1, 12, '1/2 teaspoon'),

    (2, 4, '3 cloves'),
    (2, 5, '8 ounces'),
    (2, 6, '2 tablespoons'),
    (2, 12, '1/2 teaspoon'),
    (2, 13, '1/4 teaspoon'),

    (3, 7, '3 potatoes'),
    (3, 8, '2 carrots'),
    (3, 11, '4 cups'),
    (3, 12, '1 teaspoon'),
    (3, 13, '1/2 teaspoon'),

    (4, 9, '1 pound'),
    (4, 10, '8 tortillas'),
    (4, 14, '2 tablespoons'),
    (4, 15, '1 cup'),
    (4, 16, '1 cup');