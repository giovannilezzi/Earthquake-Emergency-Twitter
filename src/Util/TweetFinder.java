package Util;

import Model.Tweet;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;

import javax.xml.bind.SchemaOutputResolver;
import java.util.ArrayList;
import java.util.List;

public class TweetFinder {

    private final static String consumerKey = "j5g7cn5rC4FVtrKicKcAL2ZvE";
    private final static String consumerSecret = "o7lNQsu9lpzKBB36ATIReTiHbAdocYb72LrgwAcB5l6Kz7X47M";
    private final static String accessToken = "833923929366478852-GFUr6eWpcL8sQElBn3w2X4lLIhdHyCb";
    private final static String accessTokenSecret = "dE5qghxbZbD77NRC7gaSxpI6MdMEWBXjvg31WFHkVXb8m";

    private final static String[] indicatorTweer = {"|", "?"};
    private final static String progressSymbol = ".";

    private static ConfigurationBuilder getConfigurationBuilder(){
        System.out.println("------------ Twitter API Connection ------------\n");
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);
        configurationBuilder.setJSONStoreEnabled(true);

        return configurationBuilder;
    }

    public static ArrayList<Tweet> getArrayAsJson(Query query, boolean onlyTweetGeolocated){
        TwitterFactory twitterFactory = new TwitterFactory(getConfigurationBuilder().build());
        Twitter twitter = twitterFactory.getInstance();

        QueryResult queryResult = null;
        ArrayList<Tweet> tweetResult = new ArrayList<>();
        System.out.print("Ricerca Tweet");
        if (onlyTweetGeolocated) {
            System.out.println("\nRichiesta Tweet Geolocalizzati Attiva\n" +
                    "Simbolo Tweet \nGeolocalizzati:     " + indicatorTweer[0] +
                    "\nNON Geolocalizzati: " + indicatorTweer[1]);
        }
        executeQuery(tweetResult, twitter, queryResult, query, onlyTweetGeolocated);

        return tweetResult;
    }

    private static void executeQuery(ArrayList<Tweet> tweetResult, Twitter twitter, QueryResult queryResult,
                                     Query query, boolean onlyTweetGeolocated){
        try {
            queryResult = twitter.search(query);
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        if(queryResult != null) {
            List<Status> tweets = queryResult.getTweets();
            for (Status status : tweets) {
                /*
                                               onlyTweetGeolocated
                                                _________________
                                                |		|		|
                                                |	0	| 	1	|
                                        ________|_______|_______|
                                        |		|		|		|
                                        |	0	|	1	|	0 	|
                status.getGeoLocation()	|_______|_______|_______|
                                        |		|		|		|
                                        |	1	|	1	|	1	|
                                        |_______|_______|_______|

                L'Unico caso in cui non deve fare nulla è:
                    quando il tweet non contiene informazioni di geolocalizzazione &
                    si voglia tweet geolocalizzati

                In tutti gli altri casi invece si vorrà sempre salvare il tweet
                 */
                if (status.getGeoLocation() == null && onlyTweetGeolocated){}
                else{
                    if (status.getGeoLocation() != null) System.out.print(indicatorTweer[0]);
                    else System.out.print(indicatorTweer[1]);

                    String json = DataObjectFactory.getRawJSON(status);
                    Tweet tweet = new Tweet(json);
                    tweetResult.add(tweet);
                }
            }
            System.out.print(progressSymbol);

            if(queryResult.hasNext())
            {
                query = queryResult.nextQuery();
                executeQuery(tweetResult, twitter, queryResult, query, onlyTweetGeolocated);
            }else System.out.println("\n\nLa Ricerca dei Tweet è terminata! Trovati " +
                    tweetResult.size() + " tweets geolocalizzati\n");
        }else System.out.println("La Ricerca non ha trovato alcun Tweet!\n");
    }
}
