
package api;

import entity.User;
import persistence.GenericDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/user")
public class UserResource {

    private final GenericDao<User> userDao =
            new GenericDao<>(User.class);

    @POST
    @Path("/sync")
    @Produces(MediaType.APPLICATION_JSON)
    public Response syncUser(
            @Context HttpServletRequest request) {

        String email = getSessionEmail(request);

        if (email == null || email.isBlank()) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("User is not logged in")
                    .build();
        }

        User user = findOrCreateUser(email);

        Map<String, Object> userResponse = Map.of(
                "userId", user.getUserId(),
                "displayName", user.getDisplayName(),
                "email", user.getEmail()
        );

        return Response.ok(userResponse).build();
    }

    private String getSessionEmail(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

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
}
