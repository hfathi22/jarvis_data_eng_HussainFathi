package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.TweetBuilder;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
public class TwitterController implements Controller {

    private static final String COORD_SEP = ":";
    private static final String COMMA = ",";

    private Service service;

    @Autowired
    public TwitterController(Service service) {
        this.service = service;
    }

    @Override
    public Tweet postTweet(String[] args) throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        if (args.length != 3){
            throw new IllegalArgumentException("USAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
        }

        String text = args[1];
        String coord = args[2];
        String[] coordArray = coord.split(COORD_SEP);
        if (coordArray.length != 2){
            throw new IllegalArgumentException("Invalid location format\nUSAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
        }
        if (text.isEmpty()){
            throw new IllegalArgumentException("No text provided\nUSAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
        }

        Double lat;
        Double lon;
        try {
            lat = Double.parseDouble(coordArray[0]);
            lon = Double.parseDouble(coordArray[1]);
        }catch (NumberFormatException e){
            throw new IllegalArgumentException("Invalid location format\nUSAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"", e);
        }

        Tweet tweet = TweetBuilder.buildTweet(text, lon, lat);
        return service.postTweet(tweet);

    }

    @Override
    public Tweet showTweet(String[] args) throws OAuthMessageSignerException, OAuthExpectationFailedException, URISyntaxException, IOException, OAuthCommunicationException {
        if (args.length != 2 && args.length != 3){
            throw new IllegalArgumentException("USAGE: TwitterCLIApp show \"tweet_id\" \"tweet_fields\"");
        }
        String id = args[1];
        if (args.length == 2){
            return service.showTweet(id, null);
        }
        String fields = args[2];
        String[] fieldsArr = fields.split(COMMA);
        return service.showTweet(id, fieldsArr);
    }

    @Override
    public List<Tweet> deleteTweet(String[] args) throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        if (args.length != 2){
            throw new IllegalArgumentException("USAGE: TwitterCLIApp delete \"tweets_ids\"");
        }
        String[] ids = args[1].split(COMMA);
        return service.deleteTweets(ids);
    }
}
