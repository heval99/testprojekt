package databaselayer;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;

public class DBConnection {   
    // Constants for database connection
    private static final String SERVER_ADDRESS = "jdbc:sqlserver://localhost:1433";
    private static final String DATABASE_NAME = ";databaseName=PayStation";
    private static final String USERNAME = ";user=sa";
    private static final String PASSWORD = ";password=secret";
    private static final String SECURITY = ";encrypt=false;trustServerCertificate=true";

    private DatabaseMetaData dma;
    private static Connection con;
    private static DBConnection instance = null;

    // Private constructor to enforce singleton pattern
    private DBConnection() {
        String url = SERVER_ADDRESS + DATABASE_NAME + USERNAME + PASSWORD + SECURITY;
        System.out.println("Attempting to connect to: " + url);

        try {
            // Load SQL Server JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Driver loaded successfully");
          
            // Establish database connection
            con = DriverManager.getConnection(url);
            con.setAutoCommit(true);
            dma = con.getMetaData(); 

            System.out.println("Connected to: " + dma.getURL());
            System.out.println("Driver: " + dma.getDriverName());
            System.out.println("Database: " + dma.getDatabaseProductName());

        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
        }
    }

    // Closes the database connection safely
    public static void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                instance = null;
                System.out.println("Database connection closed.");
            }
        } catch (Exception e) {
            System.err.println("Error closing database: " + e.getMessage());
        }
    }

    // Returns the singleton instance of the database connection
    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    // Returns the active connection object
    public Connection getDBcon() {
        return con;
    }

    // Checks if the instance is null
    public static boolean instanceIsNull() {
        return (instance == null);
    }

    // Checks if the database connection is open
    public static boolean getOpenStatus() {
        try {
            return (con != null && !con.isClosed());
        } catch (Exception e) {
            return false;
        }
    }
}
