package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetBuilder;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {

    @Mock
    CrdDao crdDao;

    @InjectMocks
    TwitterService twitterService;

    @Test
    public void postTweet() throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        when(crdDao.create(any())).thenReturn(new Tweet());
        double lon = 50;
        double lat = 50;

        //Test#1: No exception expected
        String text = "test";
        Tweet tweet = twitterService.postTweet(TweetBuilder.buildTweet(text, lon, lat));
        assertNotNull(tweet);

        //Test#2: Exception expected
        String textException = new String(new char[190]);
        try {
            twitterService.postTweet(TweetBuilder.buildTweet(textException, lon, lat));
            fail();
        }catch (RuntimeException e){
            assertTrue(true);
        }
    }

    @Test
    public void showTweet() throws OAuthMessageSignerException, OAuthExpectationFailedException, URISyntaxException, IOException, OAuthCommunicationException {

        when(crdDao.findById(any())).thenReturn(new Tweet());
        String id = "1234567";

        //Test#1: No exception expected
        String[] validFields = {"created_at", "id", "id_str", "text", "entities",
                "coordinates"};
        Tweet tweet = twitterService.showTweet(id, validFields);
        assertNotNull(tweet);

        //Test#2: Exception expected
        String[] invalidFields = {"created_at", "id", "id_string", "text", "entities",
                "coordinates"};
        try {
            twitterService.showTweet(id, invalidFields);
            fail();
        }catch (RuntimeException e){
            assertTrue(true);
        }
    }

    @Test
    public void deleteTweet() throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        when(crdDao.deleteById(any())).thenReturn(new Tweet());

        //Test#1: No exception expected
        String[] ids = {"123456", "654096"};
        List<Tweet> tweets = twitterService.deleteTweets(ids);
        assertNotNull(tweets);

        //Test#2: Exception expected
        String[] invalidIds = {"3289064", "456h6"};
        try {
            twitterService.deleteTweets(invalidIds);
            fail();
        }catch (RuntimeException e) {
            assertTrue(true);
        }

    }
}
