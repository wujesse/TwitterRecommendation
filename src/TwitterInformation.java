import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import twitter4j.PagableResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterInformation {
	
	//Twitter twitter;
	
	
	public TwitterInformation() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("Joy1KOjXNlE1qt6JL81WeVPdz")
		  .setOAuthConsumerSecret("9COvRqcw2aX4S0sAIF9m88hAZGZPmhG427MDHDrsJRATe0pww8")
		  .setOAuthAccessToken("990434071632404480-aLQltQ1715JuXKaDRLKTd9cGQDESTrG")
		  .setOAuthAccessTokenSecret("bvJdvDHIitQQqbiHy0ZVwcaw9zWIkbmdLxZJCzyNYIOPo");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		
		
	}
	
	private List<User> getFriends(Twitter twitter) {

		 List<User> listFriends = new LinkedList<>();
         List<User> listFollowers = new LinkedList<>();
         
		try { 
            // get friends
            long cursor = -1;
            PagableResponseList<User> pagableFollowings;
            
            
            do {
                pagableFollowings = twitter.getFriendsList(twitter.getId(), cursor);
                for (User user : pagableFollowings) {
                    listFriends.add(user); // ArrayList<User>
                }
            } while ((cursor = pagableFollowings.getNextCursor()) != 0);

            // get followers
            cursor = -1;
            PagableResponseList<User> pagableFollowers;
            do {
                pagableFollowers = twitter.getFollowersList(twitter.getId(), cursor);
                for (User user : pagableFollowers) {
                    listFollowers.add(user); // ArrayList<User>
                }
            } while ((cursor = pagableFollowers.getNextCursor()) != 0);

        } catch (TwitterException e) {
            e.printStackTrace();
        }
		
		return listFriends;
	}
	
	private List<User> getFollowers(Twitter twitter) {
		 List<User> listFriends = new LinkedList<>();
         List<User> listFollowers = new LinkedList<>();
         
		
		try {
            // get friends
            long cursor = -1;
            PagableResponseList<User> pagableFollowings;
            
           
            do {
                pagableFollowings = twitter.getFriendsList(twitter.getId(), cursor);
                for (User user : pagableFollowings) {
                    listFriends.add(user); // ArrayList<User>
                }
            } while ((cursor = pagableFollowings.getNextCursor()) != 0);

            // get followers
            cursor = -1;
            PagableResponseList<User> pagableFollowers;
            do {
                pagableFollowers = twitter.getFollowersList(twitter.getId(), cursor);
                for (User user : pagableFollowers) {
                    listFollowers.add(user); // ArrayList<User>
                }
            } while ((cursor = pagableFollowers.getNextCursor()) != 0);

        } catch (TwitterException e) {
            e.printStackTrace();
        }
		
		return listFollowers;
	}
	
	private List<String> getTimeLine(Twitter user) throws TwitterException {
		   // Twitter twitter = getTwitterinstance();
		     
		    return user.getHomeTimeline().stream()
		      .map(item -> item.getText())
		      .collect(Collectors.toList());
		    
		    /*
		     *  try {
	            List<Status> statuses;
	            String user; 
	            if (args.length == 1) {
	                user = args[0];
	                statuses = twitter.getUserTimeline(user);
	            } else {
	                user = twitter.verifyCredentials().getScreenName();
	                statuses = twitter.getUserTimeline();
	            }
	            System.out.println("Showing @" + user + "'s user timeline.");
	            System.out.println(getTimeLine(twitter));
	            for (Status status : statuses) {
	                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
	            }
	        } catch (TwitterException te) {
	            te.printStackTrace();
	            System.out.println("Failed to get timeline: " + te.getMessage());
	            System.exit(-1);
	        }
		     */
		}
}
