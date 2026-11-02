package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.User;
import persistence.GenericDao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/api/user/sync")
public class UserApiController extends HttpServlet {

    private final GenericDao<User> userDao =
            new GenericDao<>(User.class);

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        addCorsHeaders(response);

        String email = getSessionEmail(request);

        if (email == null || email.isBlank()) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "User is not logged in"
            );
            return;
        }

        User user = findOrCreateUser(email);

        sendUserResponse(response, user);
    }

    private String getSessionEmail(
            HttpServletRequest request) {

        HttpSession session =
                request.getSession(false);

        if (session == null) {
            return null;
        }

        return (String) session.getAttribute("email");
    }

    private User findOrCreateUser(String email) {

        List<User> matchingUsers =
                userDao.getByPropertyEqual("email", email);

        if (!matchingUsers.isEmpty()) {
            return matchingUsers.get(0);
        }

        User newUser = new User(email, email);
        userDao.insert(newUser);

        return newUser;
    }

    private void sendUserResponse(
            HttpServletResponse response,
            User user)
            throws IOException {

        Map<String, Object> userResponse = Map.of(
                "userId", user.getUserId(),
                "displayName", user.getDisplayName(),
                "email", user.getEmail()
        );

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        objectMapper.writeValue(
                response.getWriter(),
                userResponse
        );
    }

    private void addCorsHeaders(HttpServletResponse response) {

        response.setHeader(
                "Access-Control-Allow-Origin",
                "http://localhost:5173"
        );

        response.setHeader(
                "Access-Control-Allow-Credentials",
                "true"
        );
    }
}