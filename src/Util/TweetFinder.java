package Util;

import Model.Tweet;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;

import java.util.ArrayList;

public class TweetFinder {

    private final static String consumerKey = "j5g7cn5rC4FVtrKicKcAL2ZvE";
    private final static String consumerSecret = "o7lNQsu9lpzKBB36ATIReTiHbAdocYb72LrgwAcB5l6Kz7X47M";
    private final static String accessToken = "833923929366478852-GFUr6eWpcL8sQElBn3w2X4lLIhdHyCb";
    private final static String accessTokenSecret = "dE5qghxbZbD77NRC7gaSxpI6MdMEWBXjvg31WFHkVXb8m";

    private static ConfigurationBuilder getConfigurationBuilder(){
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);
        configurationBuilder.setJSONStoreEnabled(true);

        return configurationBuilder;
    }

    public static ArrayList<Tweet> getArrayAsJson(Query query){
        TwitterFactory twitterFactory = new TwitterFactory(getConfigurationBuilder().build());
        Twitter twitter = twitterFactory.getInstance();

        QueryResult queryResult = null;
        ArrayList<Tweet> tweetresult = new ArrayList<>();
        try {
            queryResult = twitter.search(query);
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        if(queryResult != null) {
            for (Status status : queryResult.getTweets()) {
                String json = DataObjectFactory.getRawJSON(status);
                Tweet tweet = new Tweet(json);
                tweetresult.add(tweet);
            }
        }else System.out.println("La Ricerca non ha trovato alcun Tweet!");

        return tweetresult;
    }
}
