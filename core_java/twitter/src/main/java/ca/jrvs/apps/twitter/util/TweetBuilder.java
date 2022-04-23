package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;

public class TweetBuilder {

    public static Tweet buildTweet (String message, double longitude, double latitude){
        Tweet tweet = new Tweet();
        Coordinates coordinates = new Coordinates();
        coordinates.setCoordinates(new double[]{longitude, latitude});
        coordinates.setType("point");
        tweet.setText(message);
        tweet.setCoordinates(coordinates);
        return tweet;
    }
}
