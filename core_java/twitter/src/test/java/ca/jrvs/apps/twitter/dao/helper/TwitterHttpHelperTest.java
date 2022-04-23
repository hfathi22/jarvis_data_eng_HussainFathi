package ca.jrvs.apps.twitter.dao.helper;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class TwitterHttpHelperTest {

    @Test
    public void httpPost() throws URISyntaxException, OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        String text = "HTTP_POST_test";
        URI uri = new URI("https://api.twitter.com/1.1/statuses/update.json?status=" + text);
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");
        TwitterHttpHelper helper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
        HttpResponse response = helper.httpPost(uri);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    @Test
    public void httpGet() throws URISyntaxException, OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        String id = "1463629183129735170";
        URI uri = new URI("https://api.twitter.com/1.1/statuses/show.json?id=" + id);
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");
        TwitterHttpHelper helper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
        HttpResponse response = helper.httpGet(uri);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }
}