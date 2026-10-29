package controller;

import entity.User;
import persistence.GenericDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/user")
public class UserController extends HttpServlet {

    private final GenericDao<User> userDao = new GenericDao<>(User.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = (String) request.getSession().getAttribute("email");

        if (email == null || email.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/logIn");
            return;
        }

        List<User> matchingUsers = userDao.getByPropertyEqual("email", email);

        User user;

        if (matchingUsers.isEmpty()) {

            // First login: create the local RecipeForge user.
            user = new User(email, email);

            userDao.insert(user);
        } else {

            // Returning user: use the existing database record.
            user = matchingUsers.get(0);
        }

        // Make the database user available to user.jsp.
        request.setAttribute("user", user);

        request.getRequestDispatcher("/user.jsp").forward(request, response);
    }
}