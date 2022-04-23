package ca.jrvs.apps.twitter.spring;

import ca.jrvs.apps.twitter.TwitterCLIApp;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URISyntaxException;

@Configuration
@ComponentScan(value = "ca.jrvs.apps.twitter")
public class TwitterCLIComponentScan {

    public static void main(String[] args) throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, URISyntaxException, OAuthCommunicationException {
        //IoC container
        ApplicationContext context = new AnnotationConfigApplicationContext(TwitterCLIComponentScan.class);

        TwitterCLIApp app = context.getBean(TwitterCLIApp.class);
        app.run(args);
    }

}
