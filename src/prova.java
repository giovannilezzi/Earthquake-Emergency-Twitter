import java.util.ArrayList;

public class prova {

    public static void main(String [ ] args){
        ArrayList<String> Tag = new ArrayList<>();
        Tag.add("Lazio");
        Tag.add("EuropaLeague");
        ArrayList<Tweet> tweets = TweetFinder.getArrayAsJson(QueryCriteria.queryA(12, 04, 2018, 13, 04, 2018, Tag));
        System.out.println(tweets.get(0).getTweetAsJson());

    }
}
