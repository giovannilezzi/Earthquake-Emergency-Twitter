package Util;

import Model.Tweet;
import org.postgresql.util.PGobject;
import java.sql.*;
import java.util.ArrayList;

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

        String query = PostgreSqlQuery.createTableDefault(nameTable);

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

    public static boolean createTableDefaultWithJson(Connection connection, String nameTable){
        Statement statement = null;

        String query = PostgreSqlQuery.createTableDefaultWithJson(nameTable);

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

    public static boolean insertJson(Connection connection, String nameTable, ArrayList<String> jsonToInsert){
        PreparedStatement preparedStatement = null;

        String query = PostgreSqlQuery.insertJson(nameTable);

        try {
            preparedStatement = connection.prepareStatement(query);

            int i = 1;
            for (String oneJson : jsonToInsert){
                PGobject jsonObject = new PGobject();
                jsonObject.setType("json");
                try {
                    jsonObject.setValue(oneJson);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                preparedStatement.setObject(1, i);
                preparedStatement.setObject(2, jsonObject);
                preparedStatement.executeUpdate();

                i++;
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }

        return false;
    }

    public static ArrayList<Tweet> viewTableTweets(Connection connection, String nameTable){
        Statement statement = null;

        String query  = PostgreSqlQuery.viewTable(nameTable);

        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        try {
            ArrayList<Tweet> tweets = new ArrayList<>();

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                Tweet oneTweet = new Tweet(resultSet.getString(Tweet.JSON_COLUMN_NAME));
                tweets.add(oneTweet);
            }
            return tweets;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
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
