import Model.Tweet;
import Util.TwitterQuery;
import Util.TweetDBManager;
import Util.TweetFinder;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

public class mainTest {
    private static String urlServerPostgreSql = "127.0.0.1:5432";
    private static String dbName = "postgres";
    private static String user = "postgres";
    private static String password = "postgres";

    public static void main(String [ ] args){
        initTweetTable();
        populateTweets();
        //viewTableTweet();
    }

    private static void initTweetTable(){
        Connection connection = TweetDBManager.getConnectionDB(TweetDBManager.POSTGRESQL_DRIVER, urlServerPostgreSql, dbName, user, password);

        TweetDBManager.createTableDefaultWithJson(connection, Tweet.TABLE_NAME);

        TweetDBManager.closeConnection(connection);
    }

    private static void populateTweets(){
        String keyword = "";
        //String keyword = "earthquake";

        ArrayList<String> tag = new ArrayList<>();
        tag.add("earthquake");
        tag.add("distruption");
        tag.add("Powerful");
        tag.add("strong");
        tag.add("big");
        tag.add("Felt");
        tag.add("Evacuation");
        tag.add("break");
        tag.add("Landslides");
        tag.add("Fear");

        ArrayList<String> frasi = new ArrayList<>();
        frasi.add("Major Damages");
        frasi.add("Felt enough");
        frasi.add("felt strongly");
        frasi.add("Cracks on roads");
        frasi.add("Minor Damages");
        frasi.add("General alert");
        frasi.add("emergence situation");

        int[] dataInizio = {10, 04, 2018};
        int[] dataFine = {12, 04, 2018};

        boolean onlyTweetGeolocated = true;

        ArrayList<Tweet> tweets = TweetFinder.getArrayAsJson(TwitterQuery.queryIntervalloTemporaleConParoleChiavi(dataInizio[0], dataInizio[1], dataInizio[2], dataFine[0], dataFine[1], dataFine[2], tag, frasi, keyword), onlyTweetGeolocated);

        Connection connection = TweetDBManager.getConnectionDB(TweetDBManager.POSTGRESQL_DRIVER, urlServerPostgreSql, dbName, user, password);

        ArrayList<String> jsonToInsert = new ArrayList<>();
        for (Tweet oneTweet : tweets){
            jsonToInsert.add(oneTweet.getTweetAsJson());
        }
        TweetDBManager.insertJson(connection, Tweet.TABLE_NAME, jsonToInsert);

        TweetDBManager.closeConnection(connection);
    }

    private static void viewTableTweet(){
        Connection connection = TweetDBManager.getConnectionDB(TweetDBManager.POSTGRESQL_DRIVER, urlServerPostgreSql, dbName, user, password);

        ArrayList<Tweet> tweets = TweetDBManager.viewTableTweets(connection, Tweet.TABLE_NAME);

        for (Tweet tweet : tweets) System.out.println(tweet.toString());

        TweetDBManager.closeConnection(connection);
    }
}
