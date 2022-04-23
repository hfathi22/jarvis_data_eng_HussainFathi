package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonParser;
import ca.jrvs.apps.twitter.util.TweetBuilder;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.isNotNull;

import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import java.io.IOException;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;


import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {

    @Mock
    HttpHelper mockHelper;

    @InjectMocks
    TwitterDao twitterDao;

    private String tweetJsonStr = "{\n"
            + "   \"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
            + "   \"id\":1097607853932564480,\n"
            + "   \"id_str\":\"1097607853932564480\",\n"
            + "   \"text\":\"test with loc223\",\n"
            + "   \"entities\":{\n"
            + "      \"hashtags\":[],"
            + "      \"user_mentions\":[]"
            + "   },\n"
            + "   \"coordinates\":null,\n"
            + "   \"retweet_count\":0,\n"
            + "   \"favorite_count\":0,\n"
            + "   \"favorited\":false,\n"
            + "   \"retweeted\":false\n"
            + "}";

    @Test
    public void postTweet() throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        String hashTag = "#APITest";
        String text = "@Twitter Create Test " + hashTag;
        Double lon = 10d;
        Double lat = 10.5d;

        //Exception is expected to be thrown here
        when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
        try {
            twitterDao.create(TweetBuilder.buildTweet(text, lon, lat));
            fail();
        }catch (RuntimeException e){
            assertTrue(true);
        }

        //mock httpPost
        when(mockHelper.httpPost(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(twitterDao);
        Tweet expectedTweet = JsonParser.toObjectFromJson(tweetJsonStr, Tweet.class);
        //mock parse response body
        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
        Tweet tweet = spyDao.create(TweetBuilder.buildTweet(text, lon, lat));
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
    }

    @Test
    public void showTweet() throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        String id = "146479174174426728";
        when(mockHelper.httpGet(isNotNull())).thenThrow(new RuntimeException("mock"));
        try {
            twitterDao.findById(id);
            fail();
        }catch (RuntimeException e){
            assertTrue(true);
        }

        //mock httpGet
        when(mockHelper.httpGet(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(twitterDao);
        Tweet expectedTweet = JsonParser.toObjectFromJson(tweetJsonStr, Tweet.class);
        //mock parseResponseBody
        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(),anyInt());
        Tweet tweet = spyDao.findById(id);
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
    }

    @Test
    public void deleteById() throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        String id = "146479174174426728";
        when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
        try {
            twitterDao.deleteById(id);
            fail();
        }catch (RuntimeException e){
            assertTrue(true);
        }

        //mock httpPost
        when(mockHelper.httpPost(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(twitterDao);
        Tweet expectedTweet = JsonParser.toObjectFromJson(tweetJsonStr, Tweet.class);
        //mock parseResponseBody
        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(),anyInt());
        Tweet tweet = spyDao.deleteById(id);
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
    }
}