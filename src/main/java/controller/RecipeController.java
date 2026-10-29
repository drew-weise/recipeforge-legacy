package controller;

import entity.Ingredient;
import entity.Recipe;
import entity.User;
import persistence.GenericDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/recipe/*")
public class RecipeController extends HttpServlet {

    private final GenericDao<User> userDao = new GenericDao<>(User.class);
    private final GenericDao<Recipe> recipeDao = new GenericDao<>(Recipe.class);
    private final GenericDao<Ingredient> ingredientDao = new GenericDao<>(Ingredient.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = request.getPathInfo();

        if ("/new".equals(path)) {
            request.getRequestDispatcher("/recipeform.jsp").forward(request, response);
            return;
        }

        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String email = (String) request.getSession().getAttribute("email");

        if (email == null || email.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/logIn");
            return;
        }

        List<User> matchingUser = userDao.getByPropertyEqual("email", email);

        if (matchingUser.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No local user account was found");
            return;
        }

        User user = matchingUser.get(0);

        String recipeTitle = request.getParameter("title");
        String recipeDescription = request.getParameter("description");
        String recipeInstructions = request.getParameter("instructions");
        String servingsParameter = request.getParameter("servings");

        int recipeServings;

        try {
            recipeServings = Integer.parseInt(servingsParameter);
        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Servings must be a number"
            );
            return;
        }

        Recipe recipe = new Recipe(
                recipeTitle,
                recipeDescription,
                recipeInstructions,
                recipeServings,
                user
        );

        recipeDao.insert(recipe);

        response.sendRedirect(request.getContextPath() + "/user");
    }
}