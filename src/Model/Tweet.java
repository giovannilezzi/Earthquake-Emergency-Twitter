package Model;

public class Tweet {

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
}
