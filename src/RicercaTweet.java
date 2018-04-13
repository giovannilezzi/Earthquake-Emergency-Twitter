import Model.Tweet;
import Util.TwitterQuery;
import Util.TweetDBManager;
import Util.TweetFinder;

import java.sql.Connection;
import java.util.ArrayList;

public class RicercaTweet {

    public static void main(String [ ] args){
        initTweetTable();
        populateTweets();
        viewTableTweet();
    }

    private static void initTweetTable(){
        Connection connection = TweetDBManager.getConnectionDB(TweetDBManager.POSTGRESQL_DRIVER, "127.0.0.1:5432", "postgres", "postgres", "postgres");

        TweetDBManager.createTableDefaultWithJson(connection, Tweet.TABLE_NAME);

        TweetDBManager.closeConnection(connection);
    }

    private static void populateTweets(){
        ArrayList<String> Tag = new ArrayList<>();
        Tag.add("Lazio");
        Tag.add("EuropaLeague");
        ArrayList<Tweet> tweets = TweetFinder.getArrayAsJson(TwitterQuery.queryIntervalloTemporaleConParoleChiavi(12, 04, 2018, 13, 04, 2018, Tag));

        Connection connection = TweetDBManager.getConnectionDB(TweetDBManager.POSTGRESQL_DRIVER, "127.0.0.1:5432", "postgres", "postgres", "postgres");

        ArrayList<String> jsonToInsert = new ArrayList<>();
        for (Tweet oneTweet : tweets){
            jsonToInsert.add(oneTweet.getTweetAsJson());
        }
        TweetDBManager.insertJson(connection, Tweet.TABLE_NAME, jsonToInsert);

        TweetDBManager.closeConnection(connection);
    }

    private static void viewTableTweet(){
        Connection connection = TweetDBManager.getConnectionDB(TweetDBManager.POSTGRESQL_DRIVER, "127.0.0.1:5432", "postgres", "postgres", "postgres");

        ArrayList<Tweet> tweets = TweetDBManager.viewTableTweets(connection, Tweet.TABLE_NAME);

        for (Tweet tweet : tweets) System.out.println(tweet.toString());

        TweetDBManager.closeConnection(connection);
    }
}
