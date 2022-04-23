package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@org.springframework.stereotype.Service
public class TwitterService implements Service {

    private CrdDao crdDao;
    private static final int CHARACTER_LIMIT = 140;
    private static final double LON_MIN = -180;
    private static final double LON_MAX = 180;
    private static final double LAT_MIN = -90;
    private static final double LAT_MAX = 90;


    @Autowired
    public TwitterService(CrdDao crdDao) {
        this.crdDao = crdDao;
    }

    @Override
    public Tweet postTweet(Tweet tweet) throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        validatePostTweet(tweet);
        return (Tweet) crdDao.create(tweet);
    }

    @Override
    public Tweet showTweet(String id, String[] fields) throws OAuthMessageSignerException, OAuthExpectationFailedException, URISyntaxException, IOException, OAuthCommunicationException {
        if (fields == null){
            return (Tweet) crdDao.findById(id);
        }
        validateShowTweet(id, fields);
        Tweet tweet =  (Tweet) crdDao.findById(id);
        List<String> fieldsAsList = Arrays.asList(fields);

        if (!fieldsAsList.contains("created_at")){
            tweet.setCreated_at(null);
        }
        if (!fieldsAsList.contains("id")){
            tweet.setId(null);
        }
        if (!fieldsAsList.contains("id_str")){
            tweet.setId_str(null);
        }
        if (!fieldsAsList.contains("text")){
            tweet.setText(null);
        }
        if (!fieldsAsList.contains("entities")){
            tweet.setEntities(null);
        }
        if (!fieldsAsList.contains("coordinates")){
            tweet.setCoordinates(null);
        }
        if (!fieldsAsList.contains("retweet_count")){
            tweet.setRetweet_count(null);
        }
        if (!fieldsAsList.contains("favorite_count")){
            tweet.setFavorite_count(null);
        }
        if (!fieldsAsList.contains("favorited")){
            tweet.setFavorited(null);
        }
        if (!fieldsAsList.contains("retweeted")){
            tweet.setRetweeted(null);
        }

        return tweet;
    }

    @Override
    public List<Tweet> deleteTweets(String[] ids) throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        List<Tweet> deletedTweets = new ArrayList<>();
        for (String id : ids) {
            if (!id.matches("[0-9]+")) {
                throw new RuntimeException("ERROR: Invalid ID " + id);
            }
            deletedTweets.add((Tweet) crdDao.deleteById(id));
        }
        return deletedTweets;
    }

    private void validatePostTweet(Tweet tweet){
        String message = tweet.getText();
        if (message.length() > 140){
            throw new RuntimeException("ERROR: Number of characters in the Tweet exceeds 140 characters");
        }

        if (tweet.getCoordinates() != null){
            Coordinates coordinates = tweet.getCoordinates();
            double lon = coordinates.getCoordinates()[0];
            double lat = coordinates.getCoordinates()[1];
            if (lon > LON_MAX || lon < LON_MIN || lat > LAT_MAX || lat < LAT_MIN){
                throw new RuntimeException("ERROR: Tweet coordinates out of range");
            }
        }
    }

    private void validateShowTweet(String id, String[] fields){
        try {
            Double.parseDouble(id);
        }catch (NumberFormatException e){
            throw new RuntimeException("ERROR: Invalid ID",e);
        }

        String[] validFields = {"created_at", "id", "id_str", "text", "entities", "coordinates",
                "retweet_count", "favorite_count", "favorited", "retweeted"};
        List<String> validFieldsAsList = Arrays.asList(validFields);
        if (fields != null){
            Arrays.stream(fields).forEach(field -> {
                if (!validFieldsAsList.contains(field)){
                    throw new RuntimeException("ERROR: Invalid field " + field);
                }
            });
        }
    }
}


