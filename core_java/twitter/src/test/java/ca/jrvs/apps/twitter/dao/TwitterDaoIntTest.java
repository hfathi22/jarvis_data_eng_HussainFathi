package ca.jrvs.apps.twitter.dao;

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

import static org.junit.Assert.*;

public class TwitterDaoIntTest {

    private TwitterDao twitterDao;

    @Before
    public void setUp(){
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        //Setup dependency
        HttpHelper helper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        twitterDao = new TwitterDao(helper);
    }

    @Test
    public void create() throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        String hashTag = "#APITest";
        String text = "@Twitter Create Test " + hashTag;
        Double lon = 10d;
        Double lat = 10.5d;
        Tweet postTweet = TweetBuilder.buildTweet(text, lon, lat);
        Tweet tweet = twitterDao.create(postTweet);

        //Verifying results
        assertEquals(text, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getCoordinates().length);
        assertEquals(lon, tweet.getCoordinates().getCoordinates()[0], 0.0);
        assertEquals(lat, tweet.getCoordinates().getCoordinates()[1], 0.0);
        assertTrue(hashTag.contains(tweet.getEntities().getHashtags()[0].getText()));
    }

    @Test
    public void findById() throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        //Expected response
        Double lon = 10d;
        Double lat = 10.5d;
        String hashTag = "#APITest";
        String text = "@Twitter Create Test " + hashTag;

        //Tweet ID to find
        String id = "1464073716799885312";

        Tweet tweet = twitterDao.findById(id);

        assertEquals(text, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getCoordinates().length);
        assertEquals(lon, tweet.getCoordinates().getCoordinates()[0], 0.0);
        assertEquals(lat, tweet.getCoordinates().getCoordinates()[1], 0.0);
        assertTrue(hashTag.contains(tweet.getEntities().getHashtags()[0].getText()));
    }

    @Test
    public void deleteById() throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        //Expected response
        Double lon = 10d;
        Double lat = 10.5d;
        String hashTag = "#APITest";
        String text = "@Twitter Create Test " + hashTag;

        //Tweet ID to be deleted
        String id = "1464073716799885312";

        Tweet tweet = twitterDao.deleteById(id);

        assertEquals(text, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getCoordinates().length);
        assertEquals(lon, tweet.getCoordinates().getCoordinates()[0], 0.0);
        assertEquals(lat, tweet.getCoordinates().getCoordinates()[1], 0.0);
        assertTrue(hashTag.contains(tweet.getEntities().getHashtags()[0].getText()));
    }
}