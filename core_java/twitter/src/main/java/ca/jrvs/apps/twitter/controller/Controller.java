package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface Controller {

  /**
   * Parse user argument and post a tweet by calling service classes
   *
   * @param args
   * @return a posted tweet
   * @throws IllegalArgumentException if args are invalid
   */
  Tweet postTweet(String[] args) throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException;

  /**
   * Parse user argument and search a tweet by calling service classes
   *
   * @param args
   * @return a tweet
   * @throws IllegalArgumentException if args are invalid
   */
  Tweet showTweet(String[] args) throws OAuthMessageSignerException, OAuthExpectationFailedException, URISyntaxException, IOException, OAuthCommunicationException;

  /**
   * Parse user argument and delete tweets by calling service classes
   *
   * @param args
   * @return a list of deleted tweets
   * @throws IllegalArgumentException if args are invalid
   */
  List<Tweet> deleteTweet(String[] args) throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException;

}
