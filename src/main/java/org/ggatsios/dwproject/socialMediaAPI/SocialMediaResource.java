package org.ggatsios.dwproject.socialMediaAPI;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api")
public class SocialMediaResource extends ResourceConfig {

    private SocialMediaDAO dao; // Initialize DAO instance

    public SocialMediaResource() {
        packages("org.glassfish.jersey.examples.linking");
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        // Initialize here
        dao = new SocialMediaDAO();
    }

    @GET
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") long id) {
        Users user = dao.getUserById(id);
        if (user != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/users")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(Users user) {
        // Add validation logic here if needed
        dao.addUser(user);
        return Response.status(Response.Status.CREATED).entity(user).build();
    }

    @GET
    @Path("/posts/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPost(@PathParam("id") long id) {
        Posts post = dao.getPostById(id);
        if (post != null) {
            return Response.ok(post).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/posts")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPost(Posts post) {
        // Add validation logic here if needed
        dao.addPost(post);
        return Response.status(Response.Status.CREATED).entity(post).build();
    }

    // Similar methods for Comments and Followers can be added here

    @DELETE
    @Path("/users/{id}")
    public Response deleteUser(@PathParam("id") long id) {
        // Implement logic to delete a user by ID
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PUT
    @Path("/users/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") long id, Users updatedUser) {
        // Implement logic to update a user by ID
        return Response.ok(updatedUser).build();
    }
}	