package controller;

import entity.User;
import persistence.GenericDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/displayUsers")
public class DisplayUsers extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        GenericDao<User> userDao = new GenericDao<>(User.class);

        req.setAttribute("users", userDao.getAll());

        RequestDispatcher dispatcher =
                req.getRequestDispatcher("/index.jsp");

        dispatcher.forward(req, resp);
    }
}