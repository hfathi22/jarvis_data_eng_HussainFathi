package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonParser;
import com.google.gdata.util.common.base.PercentEscaper;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;

@Repository
public class TwitterDao implements CrdDao<Tweet, String> {

    //URI constants
    private static final String API_BASE_URI = "https://api.twitter.com";
    private static final String POST_PATH = "/1.1/statuses/update.json";
    private static final String SHOW_PATH = "/1.1/statuses/show.json";
    private static final String DELETE_PATH = "/1.1/statuses/destroy.json";
    //URI symbols
    private static final String QUERY_SYM = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";

    //Response code
    private static final int HTTP_OK = 200;

    private HttpHelper httpHelper;

    @Autowired
    public TwitterDao(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

    @Override
    public Tweet create(Tweet entity) throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        URI uri;
        uri = getPostUri(entity);
        HttpResponse response = httpHelper.httpPost(uri);
        return parseResponseBody(response, HTTP_OK);
    }

    @Override
    public Tweet findById(String s) throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        URI uri = getFindUri(s);
        HttpResponse response = httpHelper.httpGet(uri);
        return parseResponseBody(response, HTTP_OK);
    }

    @Override
    public Tweet deleteById(String s) throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        URI uri = getDeleteUri(s);
        HttpResponse response = httpHelper.httpPost(uri);
        return parseResponseBody(response, HTTP_OK);
    }

    public URI getPostUri(Tweet tweet){
        URI uri;
        Coordinates coordinates = tweet.getCoordinates();
        PercentEscaper percentEscaper = new PercentEscaper("",false);
        String text = percentEscaper.escape(tweet.getText());
        try{
            if (coordinates != null){
                String longitude = Double.toString(coordinates.getCoordinates()[0]);
                String latitude = Double.toString(coordinates.getCoordinates()[1]);
                uri = new URI(API_BASE_URI + POST_PATH + QUERY_SYM + "status" + EQUAL + text
                    + AMPERSAND + "long" + EQUAL + longitude + AMPERSAND + "lat" + EQUAL + latitude);
            }
            else {
                uri = new URI(API_BASE_URI + POST_PATH + QUERY_SYM + "status" + EQUAL + text);
            }
            return uri;
        } catch (URISyntaxException e) {
            throw new RuntimeException("ERROR: Invalid URI", e);
        }
    }

    public Tweet parseResponseBody(HttpResponse response, int expectedStatusCode){
        Tweet tweet;
        int responseStatus = response.getStatusLine().getStatusCode();
        if (expectedStatusCode != responseStatus){
            throw new RuntimeException("ERROR: Unexpected HTTP status " + responseStatus);
        }

        if (response.getEntity() == null){
            throw new RuntimeException("ERROR: Empty response body");
        }

        //Convert response entity to Tweet object
        try {
            tweet = JsonParser.toObjectFromJson(EntityUtils.toString(response.getEntity()), Tweet.class);
        } catch (IOException e) {
            throw new RuntimeException("ERROR: Unable to convert response entity to Tweet Object", e);
        }

        return tweet;
    }

    private URI getDeleteUri(String s){
        try {
            return new URI(API_BASE_URI + DELETE_PATH + QUERY_SYM + "id" + EQUAL + s);
        } catch (URISyntaxException e) {
            throw new RuntimeException("ERROR: Unable to create delete by ID URI", e);
        }
    }

    private URI getFindUri(String s){
        try{
           return new URI(API_BASE_URI + SHOW_PATH + QUERY_SYM + "id" + EQUAL + s);
        } catch (URISyntaxException e) {
            throw new RuntimeException("ERROR: Unable to create find by ID URI", e);
        }
    }
}
