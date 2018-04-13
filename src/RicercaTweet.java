import Model.Tweet;
import Util.QueryCriteria;
import Util.TweetDBManager;
import Util.TweetFinder;

import java.sql.Connection;
import java.util.ArrayList;

public class RicercaTweet {

    public static void main(String [ ] args){
        ArrayList<String> Tag = new ArrayList<>();
        Tag.add("Lazio");
        Tag.add("EuropaLeague");
        //ArrayList<Tweet> tweets = TweetFinder.getArrayAsJson(QueryCriteria.queryA(12, 04, 2018, 13, 04, 2018, Tag));
        //System.out.println(tweets.get(0).getTweetAsJson());

        Connection connection = TweetDBManager.getConnectionDB(TweetDBManager.POSTGRESQL_DRIVER, "127.0.0.1:5432", "postgres", "postgres", "postgres");

        System.out.println(TweetDBManager.createTableDefault(connection, "TweetsB"));

        TweetDBManager.closeConnection(connection);
    }
}
