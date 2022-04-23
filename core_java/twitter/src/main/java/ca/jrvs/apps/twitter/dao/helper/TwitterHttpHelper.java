package ca.jrvs.apps.twitter.dao.helper;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

@SuppressWarnings("Duplicates")
@Component
public class TwitterHttpHelper implements HttpHelper {

    private OAuthConsumer consumer;
    private HttpClient httpClient;

    /**
     * Constructor
     * Setup keys and tokens required to connect to the Twitter APIs
     */
    public TwitterHttpHelper (String consumerKey, String consumerSecret, String accessToken,
        String tokenSecret){
        consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(accessToken, tokenSecret);
        httpClient = HttpClientBuilder.create().build();
    }

    /**
     * Constructor
     */
    public TwitterHttpHelper(){
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(accessToken, tokenSecret);
        httpClient = HttpClientBuilder.create().build();
    }

    /**
     * Sends a POST request to the Twitter API
     * @param uri Twitter endpoint uri
     * @return HTTP response
     */
    @Override
    public HttpResponse httpPost(URI uri) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, IOException {
        HttpPost request = new HttpPost(uri);
        consumer.sign(request);
        HttpResponse response = httpClient.execute(request);
        return response;
    }

    /**
     * Sends a GET request to the Twitter API
     * @param uri Twitter endpoint uri
     * @return HTTP response
     */
    @Override
    public HttpResponse httpGet(URI uri) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, IOException {
        HttpGet request = new HttpGet(uri);
        consumer.sign(request);
        HttpResponse response = httpClient.execute(request);
        return response;
    }
}
