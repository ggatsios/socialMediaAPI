package org.ggatsios.dwproject.socialMediaAPI;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.mindrot.jbcrypt.BCrypt;

@Path("/users")
public class AuthResource {
    private final String jdbcUrl = "jdbc:postgresql://localhost:5432/postgres";
    private final String jdbcUser = "postgres";
    private final String jdbcPassword = "230Ros670";

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(Users newUser) {
        // Hash the user's password before storing it
        String hashedPassword = hashPassword(newUser.getPassword());

        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);

            // Insert the user into the database
            String insertUserQuery = "INSERT INTO users (email, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertUserQuery);
            preparedStatement.setString(1, newUser.getEmail());
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.executeUpdate();

            // Close the connection
            connection.close();

            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUser(Users user) {
        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);

            // Retrieve the user from the database by email
            String selectUserQuery = "SELECT * FROM users WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectUserQuery);
            preparedStatement.setString(1, user.getEmail());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() && checkPassword(user.getPassword(), resultSet.getString("password"))) {
                // Authentication successful
                return Response.status(Response.Status.OK).build();
            }

            // Close the connection
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Authentication failed
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    private String hashPassword(String plainPassword) {
        // Generate a salt
        String salt = BCrypt.gensalt();

        // Hash the password with the salt
        return BCrypt.hashpw(plainPassword, salt);
    }

    private boolean checkPassword(String plainPassword, String hashedPassword) {
        // Check if the provided plain password matches the stored hashed password
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
