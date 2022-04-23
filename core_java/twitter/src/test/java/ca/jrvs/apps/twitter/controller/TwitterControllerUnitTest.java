package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {

    @Mock
    Service service;

    @InjectMocks
    TwitterController twitterController;

    @Test
    public void postTweet() throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        //test arguments
        double lon = 10.2;
        double lat = 50.5;
        String command = "post";
        String text = "Twitter API test";
        String coord = lat + ":" + lon;

        when(service.postTweet(any())).thenReturn(new Tweet());

        //Test#1: No exception expected
        String[] args = {command, text, coord};
        Tweet tweet = twitterController.postTweet(args);
        assertNotNull(tweet);

        //Test#2: Exception expected
        String[] argsException = {command, coord};
        try {
            twitterController.postTweet(argsException);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void showTweet() throws OAuthMessageSignerException, OAuthExpectationFailedException, URISyntaxException, IOException, OAuthCommunicationException {
        String command = "show";
        String id = "1465779434334609416";
        String fields = "created_at,id,id_str,text,retweeted";

        when(service.showTweet(any(), any())).thenReturn(new Tweet());

        //Test#1: No exception expected
        String[] args = {command, id, fields};
        Tweet tweet = twitterController.showTweet(args);
        assertNotNull(tweet);

        //Test#2: Exception expected
        String[] argsException = {command, id, fields, fields};
        try {
            twitterController.showTweet(argsException);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void deleteTweet() throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        String command = "delete";
        String ids = "123456,4590642";

        when(service.deleteTweets(any())).thenReturn(new ArrayList<Tweet>());

        //Test#1: No exception expected
        String[] args = {command, ids};
        List tweets = twitterController.deleteTweet(args);
        assertNotNull(tweets);

        //Test#2: Exception expected
        String[] argsException = {command, ids, ids};
        try {
            twitterController.deleteTweet(argsException);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(true);
        }
    }
}
