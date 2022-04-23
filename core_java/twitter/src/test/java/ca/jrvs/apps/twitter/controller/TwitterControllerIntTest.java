package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.*;

public class TwitterControllerIntTest {

    private TwitterDao twitterDao;
    private TwitterService twitterService;
    private TwitterController twitterController;

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
        twitterController = new TwitterController(twitterService);
    }

    @Test
    public void postTweet() throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        //test arguments
        double lon = 10.2;
        double lat = 50.5;
        String command = "post";
        String text = "Twitter API test";

        String coord = lat + ":" + lon;
        String[] args = {command, text, coord};
        Tweet tweet = twitterController.postTweet(args);

        //verify results
        assertEquals(text, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getCoordinates().length);
        assertEquals(lon, tweet.getCoordinates().getCoordinates()[0], 0.0);
        assertEquals(lat, tweet.getCoordinates().getCoordinates()[1], 0.0);
    }

    @Test
    public void showTweet() throws OAuthMessageSignerException, OAuthExpectationFailedException, URISyntaxException, IOException, OAuthCommunicationException {
        //test arguments
        String command = "show";
        String id = "1465779434334609416";
        String fields = "created_at,id,id_str,text,retweeted";
        String text = "test_1";

        String[] args = {command, id, fields};
        Tweet tweet = twitterController.showTweet(args);

        //verify results
        assertEquals(text, tweet.getText());
        assertEquals(id, tweet.getId_str());

    }

    @Test
    public void deleteTweet() throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        //test arguments
        String ids = "1465779434334609416,1465774822575722497";
        String text_1 = "test_1";
        String text_2 = "test_2";
        String command = "delete";
        String[] args = {command,ids};


        List<Tweet> tweets = twitterController.deleteTweet(args);

        //verify results
        assertEquals(text_1, tweets.get(0).getText());
        assertEquals(text_2, tweets.get(1).getText());
    }
}