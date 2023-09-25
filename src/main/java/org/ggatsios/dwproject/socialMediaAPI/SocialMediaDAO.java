package org.ggatsios.dwproject.socialMediaAPI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SocialMediaDAO {
	
	    // Database connection properties
	    private final String jdbcUrl = "jdbc:postgresql://localhost:5432/postgres";
	    private final String jdbcUser = "postgres";
	    private final String jdbcPassword = "230Ros670";
	    private Connection connection;
	    
	    public SocialMediaDAO() {
	        // Initialize the database connection when the DAO is created
	        connectToDatabase();
	    }
	    
	    private void connectToDatabase() {
	        try {
	            // Load the PostgreSQL JDBC driver
	            Class.forName("org.postgresql.Driver");

	            // Establish the database connection
	            connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
	        } catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	            //Error handling
	            throw new RuntimeException("Failed to connect to the database", e);
	        }
	    }
	    
	    public void close() {
	        try {
	            // Closes the database connection when the DAO is no longer needed
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            // Handles any exceptions that may occur when closing the connection
	            e.printStackTrace();
	            throw new RuntimeException("An error has occured!", e);
	        }
	    }
	    
	 // CRUD operations for Users
	    public Users getUserById(long id) {
	        Users user = null;
	        try {
	            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
	            preparedStatement.setLong(1, id);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            if (resultSet.next()) {
	                user = new Users(
	                    resultSet.getString("email"),
	                    resultSet.getString("password"),
	                    resultSet.getString("planType")
	                );
	                user.setId(resultSet.getLong("id"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new RuntimeException("An error has occured!", e); 
	        }
	        return user;
	    }
	    
	    public List<Users> getAllUsers() {
	        List<Users> userList = new ArrayList<>();
	        try {
	            Statement statement = connection.createStatement();
	            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
	            while (resultSet.next()) {
	                Users user = new Users(
	                    resultSet.getString("email"),
	                    resultSet.getString("password"),
	                    resultSet.getString("planType")
	                );
	                user.setId(resultSet.getLong("id"));
	                userList.add(user);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new RuntimeException("An error has occured!", e);
	        }
	        return userList;
	    }
	    
	    public void addUser(Users user) {
	        try {
	            PreparedStatement preparedStatement = connection.prepareStatement(
	                "INSERT INTO users (email, password, planType) VALUES (?, ?, ?)",
	                Statement.RETURN_GENERATED_KEYS
	            );
	            preparedStatement.setString(1, user.getEmail());
	            preparedStatement.setString(2, user.getPassword());
	            preparedStatement.setString(3, user.getPlan());
	            int affectedRows = preparedStatement.executeUpdate();
	            if (affectedRows == 0) {
	            	throw new RuntimeException("User insertion failed");
	            }
	            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                user.setId(generatedKeys.getLong(1));
	            } else {
	            	throw new RuntimeException("User insertion failed to return generated keys");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new RuntimeException("An error has occured!", e);
	        }
	    }
	    
	 // CRUD operations for Posts
	    public Posts getPostById(long id) {
	        Posts post = null;
	        try {
	            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM posts WHERE id = ?");
	            preparedStatement.setLong(1, id);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            if (resultSet.next()) {
	                post = new Posts(
	                    resultSet.getLong("userId"),
	                    resultSet.getString("text")
	                );
	                post.setId(resultSet.getLong("id"));
	                post.setCreatedDate(resultSet.getDate("createdDate")); // Fetching createdDate
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new RuntimeException("An error has occured!", e); 
	        }
	        return post;
	    }
	    
	    public List<Posts> getAllPosts() {
	        List<Posts> postList = new ArrayList<>();
	        try {
	            Statement statement = connection.createStatement();
	            ResultSet resultSet = statement.executeQuery("SELECT * FROM posts");
	            while (resultSet.next()) {
	                Posts post = new Posts(
	                    resultSet.getLong("userId"),
	                    resultSet.getString("text")
	                );
	                post.setId(resultSet.getLong("id"));
	                post.setCreatedDate(resultSet.getDate("createdDate")); // Fetching createdDate
	                postList.add(post);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new RuntimeException("An error has occured!", e); 
	        }
	        return postList;
	    }
	    
	    public void addPost(Posts post) {
	        try {
	            PreparedStatement preparedStatement = connection.prepareStatement(
	                "INSERT INTO posts (userId, text, createdDate) VALUES (?, ?, ?)",
	                Statement.RETURN_GENERATED_KEYS
	            );
	            preparedStatement.setLong(1, post.getUserId());
	            preparedStatement.setString(2, post.getText());
	            preparedStatement.setDate(3, post.getCreatedDate());
	            int affectedRows = preparedStatement.executeUpdate();
	            if (affectedRows == 0) {
	            	throw new RuntimeException("Post insertion failed");
	            }
	            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                post.setId(generatedKeys.getLong(1));
	            } else {
	            	throw new RuntimeException("Post insertion failed to return generated keys");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new RuntimeException("An error has occured!", e);
	        }
	    }
	    
	    public Comments getCommentById(long id) {
	        Comments comment = null;
	        try {
	            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM comments WHERE id = ?");
	            preparedStatement.setLong(1, id);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            if (resultSet.next()) {
	                comment = new Comments(
	                    resultSet.getLong("postId"),
	                    resultSet.getLong("userId"),
	                    resultSet.getString("text")
	                );
	                comment.setId(resultSet.getLong("id"));
	                comment.setCreatedDate(resultSet.getDate("createdDate")); // Fetching createdDate
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new RuntimeException("An error has occured!", e);
	        }
	        return comment;
	    }
	    
	    public List<Comments> getAllComments() {
	        List<Comments> commentList = new ArrayList<>();
	        try {
	            Statement statement = connection.createStatement();
	            ResultSet resultSet = statement.executeQuery("SELECT * FROM comments");
	            while (resultSet.next()) {
	                Comments comment = new Comments(
	                    resultSet.getLong("postId"),
	                    resultSet.getLong("userId"),
	                    resultSet.getString("text")
	                );
	                comment.setId(resultSet.getLong("id"));
	                comment.setCreatedDate(resultSet.getDate("createdDate")); // Fetching createdDate
	                commentList.add(comment);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new RuntimeException("An error has occured!", e); 
	        }
	        return commentList;
	    }
	    
	    public void addComment(Comments comment) {
	        try {
	            PreparedStatement preparedStatement = connection.prepareStatement(
	                "INSERT INTO comments (postId, userId, text) VALUES (?, ?, ?)",
	                Statement.RETURN_GENERATED_KEYS
	            );
	            preparedStatement.setLong(1, comment.getPostId());
	            preparedStatement.setLong(2, comment.getUserId());
	            preparedStatement.setString(3, comment.getText());
	            int affectedRows = preparedStatement.executeUpdate();
	            if (affectedRows == 0) {
	            	throw new RuntimeException("Comment insertion failed");
	            }
	            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                comment.setId(generatedKeys.getLong(1));
	            } else {
	            	throw new RuntimeException("Comment insertion failed to return generated keys");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new RuntimeException("An error has occured!", e); 
	        }
	    }
	    
	 // CRUD operations for Followers
	    public Followers getFollowerById(long id) {
	        Followers follower = null;
	        try {
	            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM followers WHERE id = ?");
	            preparedStatement.setLong(1, id);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            if (resultSet.next()) {
	                follower = new Followers(
	                    resultSet.getLong("followerUserId"),
	                    resultSet.getLong("followingUserId")
	                );
	                follower.setId(resultSet.getLong("id"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new RuntimeException("An error has occured!", e); 
	        }
	        return follower;
	    }

	    public List<Followers> getAllFollowers() {
	        List<Followers> followerList = new ArrayList<>();
	        try {
	            Statement statement = connection.createStatement();
	            ResultSet resultSet = statement.executeQuery("SELECT * FROM followers");
	            while (resultSet.next()) {
	                Followers follower = new Followers(
	                    resultSet.getLong("followerUserId"),
	                    resultSet.getLong("followingUserId")
	                );
	                follower.setId(resultSet.getLong("id"));
	                followerList.add(follower);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new RuntimeException("An error has occured!", e); 
	        }
	        return followerList;
	    }

	    public void addFollower(Followers follower) {
	        try {
	            PreparedStatement preparedStatement = connection.prepareStatement(
	                "INSERT INTO followers (followerUserId, followingUserId) VALUES (?, ?)",
	                Statement.RETURN_GENERATED_KEYS
	            );
	            preparedStatement.setLong(1, follower.getFollowerUserId());
	            preparedStatement.setLong(2, follower.getFollowingUserId());
	            int affectedRows = preparedStatement.executeUpdate();
	            if (affectedRows == 0) {
	            	throw new RuntimeException("Follower insertion failed");
	            }
	            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                follower.setId(generatedKeys.getLong(1));
	            } else {
	            	throw new RuntimeException("Follower insertion failed to return generated keys");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new RuntimeException("An error has occured!", e);
	        }
	    }


}
