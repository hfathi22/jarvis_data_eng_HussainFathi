package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetBuilder;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.*;

public class TwitterServiceIntTest {

    private TwitterDao twitterDao;
    private TwitterService twitterService;

    @Before
    public void setUp(){
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        //Setup dependency
        HttpHelper helper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        twitterDao = new TwitterDao(helper);
        twitterService = new TwitterService(twitterDao);
    }

    @Test
    public void postTweet() throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        String hashTag = "#APITest";
        String text = "@Twitter Create Test_2 " + hashTag;
        Double lon = 10d;
        Double lat = 10.5d;
        Tweet postTweet = TweetBuilder.buildTweet(text, lon, lat);
        Tweet tweet = twitterService.postTweet(postTweet);

        //Verifying results
        assertEquals(text, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getCoordinates().length);
        assertEquals(lon, tweet.getCoordinates().getCoordinates()[0], 0.0);
        assertEquals(lat, tweet.getCoordinates().getCoordinates()[1], 0.0);
        assertTrue(hashTag.contains(tweet.getEntities().getHashtags()[0].getText()));
    }

    @Test
    public void showTweet() throws OAuthMessageSignerException, OAuthExpectationFailedException, URISyntaxException, IOException, OAuthCommunicationException {
        //Expected response
        Double lon = 10d;
        Double lat = 10.5d;
        String hashTag = "#APITest";
        String text = "@Twitter Create Test " + hashTag;

        //Tweet ID to find
        String id = "1465183695988531201";

        String[] fields = {"created_at", "id", "id_str", "text", "entities", "coordinates",
                "retweet_count", "favorite_count", "favorited", "retweeted"};

        Tweet tweet = twitterService.showTweet(id,fields);

        //Verifying results
        assertEquals(text, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getCoordinates().length);
        assertEquals(lon, tweet.getCoordinates().getCoordinates()[0], 0.0);
        assertEquals(lat, tweet.getCoordinates().getCoordinates()[1], 0.0);
        assertTrue(hashTag.contains(tweet.getEntities().getHashtags()[0].getText()));

    }

    @Test
    public void deleteTweets() throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {

        String[] ids = {"1465183540203634690", "1465183558310539266"};
        String[] texts = {"test_1", "test_2"};
        Tweet[] tweetsArr = twitterService.deleteTweets(ids).toArray(new Tweet[0]);


        for (int i=0; i<tweetsArr.length; i++){
            assertEquals(texts[i], tweetsArr[i].getText());
            assertNull(tweetsArr[i].getCoordinates());
            assertEquals(2, tweetsArr[i].getCoordinates().getCoordinates().length);
        }
    }
}