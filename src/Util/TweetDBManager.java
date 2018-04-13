package Util;

import java.sql.*;

public class TweetDBManager {
    public final static String POSTGRESQL_DRIVER = "jdbc:postgresql://";

    public static Connection getConnectionDB(String serverDriver, String urlServerPostgreSql, String dbName, String user, String password){
        System.out.println("-------- PostgreSQL "
                + "JDBC Connection Testing ------------");

        try {

            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your PostgreSQL JDBC Driver? "
                    + "Include in your library path!");
            e.printStackTrace();
            return null;

        }

        System.out.println("PostgreSQL JDBC Driver Registered!");

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(serverDriver + urlServerPostgreSql + "/" + dbName, user, password);

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return null;

        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }

        return connection;
    }

    public static boolean createTableDefault(Connection connection, String nameTable){
        Statement statement = null;

        String query = "create table " + nameTable + " (" +
                "id serial not null," +
                "primary key (id)" +
                ");";

        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return  true;
    }

    public static boolean closeConnection(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
