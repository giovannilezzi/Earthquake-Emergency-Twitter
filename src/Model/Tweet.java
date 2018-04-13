package Model;

public class Tweet {

    public final static String TABLE_NAME = "tweets";
    public final static String ID_COLUMN_NAME = "id";
    public final static String JSON_COLUMN_NAME = "json_field";

    private String tweetAsJson;

    public Tweet(String tweetAsJson) {
        this.tweetAsJson = tweetAsJson;
    }

    public String getTweetAsJson() {
        return tweetAsJson;
    }

    public void setTweetAsJson(String tweetAsJson) {
        this.tweetAsJson = tweetAsJson;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "tweetAsJson='" + tweetAsJson + '\'' +
                '}';
    }
}
