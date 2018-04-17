package Util;

public class PostgreSqlQuery {

    public static String createTableDefault(String nameTable){
        String query = "create table " + nameTable + " (" +
                "id serial not null," +
                "primary key (id)" +
                ");";

        return query;
    }

    public static String createTableDefaultWithJson(String nameTable){
        String query = "create table " + nameTable + " (" +
                "id serial not null," +
                "json_field json," +
                "primary key (id)" +
                ");";

        return query;
    }

    public static String viewTable(String nameTable){
        String query = "select * from " + nameTable;

        return query;
    }

    public static String insertJson(String nameTable){
        String query = "INSERT INTO " + nameTable + " VALUES (?, ?::JSON)";

        return query;
    }

    public static String deleteTable(String nameTable){
        String query = "Drop Table " + nameTable;

        return query;
    }
}
