package ca.jrvs.apps.twitter;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.util.JsonParser;
import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Locale;

@Component
public class TwitterCLIApp {

    private static final String USAGE = "USAGE: TwitterCLIApp post|show|delete [option]";
    private Controller controller;

    @Autowired
    public TwitterCLIApp(Controller controller){
        this.controller = controller;
    }

    public static void main(String[] args) throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, URISyntaxException, OAuthCommunicationException {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        HttpHelper helper = new TwitterHttpHelper(consumerKey,consumerSecret,accessToken,tokenSecret);
        CrdDao dao = new TwitterDao(helper);
        Service service = new TwitterService(dao);
        Controller controller = new TwitterController(service);
        TwitterCLIApp cliApp = new TwitterCLIApp(controller);

        //run the app
        cliApp.run(args);
    }

    public void run(String[] args) throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException, URISyntaxException {
        if (args.length == 0){
            throw new IllegalArgumentException(USAGE);
        }

        switch (args[0].toLowerCase()){
            case "post":
                printTweet(controller.postTweet(args));
                break;
            case "show":
                printTweet(controller.showTweet(args));
                break;
            case "delete":
                controller.deleteTweet(args).forEach(this::printTweet);
                break;
            default:
                throw new IllegalArgumentException(USAGE);
        }
    }

    private void printTweet (Tweet tweet){
        try {
            System.out.println(JsonParser.toJson(tweet,true,false));
        }catch (JsonProcessingException e){
            throw new RuntimeException("Error: Unable to convert Twee object to string",e);
        }
    }
}
