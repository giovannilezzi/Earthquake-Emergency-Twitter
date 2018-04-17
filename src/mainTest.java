import Model.Tweet;
import Util.TwitterQuery;
import Util.TweetDBManager;
import Util.TweetFinder;

import java.sql.Connection;
import java.util.ArrayList;

public class mainTest {
    private static String urlServerPostgreSql = "127.0.0.1:5433";
    private static String dbName = "postgres";
    private static String user = "postgres";
    private static String password = "Giovanni93";

    public static void main(String [ ] args){
        initTweetTable();
        populateTweets();
        viewTableTweet();
    }

    private static void initTweetTable(){
        Connection connection = TweetDBManager.getConnectionDB(TweetDBManager.POSTGRESQL_DRIVER, urlServerPostgreSql, dbName, user, password);

        TweetDBManager.createTableDefaultWithJson(connection, Tweet.TABLE_NAME);

        TweetDBManager.closeConnection(connection);
    }

    private static void populateTweets(){
        ArrayList<String> Tag = new ArrayList<>();
        /*
        Tag.add("distruption");
        Tag.add("Powerful");
        Tag.add("strong");
        Tag.add("big");
        Tag.add("Felt");
        Tag.add("Evacuation");
        Tag.add("break");
        Tag.add("Landslides");
        Tag.add("Fear");

        ArrayList<String> frasi = new ArrayList<>();
        frasi.add("major damages");
        frasi.add("Felt enough");
        frasi.add("felt strongly");
        frasi.add("Cracks on roads");
        frasi.add("Minor damages");
        frasi.add("Major Damages");
        frasi.add("General alert");
        frasi.add("emergence situation");
        */
        ArrayList<String> frasi = new ArrayList<>();

        ArrayList<Tweet> tweets = TweetFinder.getArrayAsJson(TwitterQuery.queryIntervalloTemporaleConParoleChiavi(10, 04, 2018, 17, 04, 2018, Tag, frasi, "earthquake"));



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
