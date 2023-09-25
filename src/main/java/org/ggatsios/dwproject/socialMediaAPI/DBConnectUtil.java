package org.ggatsios.dwproject.socialMediaAPI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Utility class managing the database connection
public class DBConnectUtil {
	private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
	private static final String USERNAME = "postgres";
	private static final String PASSWORD = "230Ros670";
	
	public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }
}
