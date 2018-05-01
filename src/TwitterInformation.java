import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import twitter4j.IDs;
import twitter4j.PagableResponseList;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterInformation {
	ConfigurationBuilder cb;
	Twitter twitter;
	TwitterFactory tf;
	
	//Twitter twitter;

	
	private List<Long> recommendations;
	
	
	public TwitterInformation(long id) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("Joy1KOjXNlE1qt6JL81WeVPdz")
		  .setOAuthConsumerSecret("9COvRqcw2aX4S0sAIF9m88hAZGZPmhG427MDHDrsJRATe0pww8")
		  .setOAuthAccessToken("990434071632404480-aLQltQ1715JuXKaDRLKTd9cGQDESTrG")
		  .setOAuthAccessTokenSecret("bvJdvDHIitQQqbiHy0ZVwcaw9zWIkbmdLxZJCzyNYIOPo");

    tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
    
		List<Long> friends = getFriends(twitter, id);
		List<Long> friendsFollowers = getFriendsFollowers(friends, twitter);
		friendsFollowers.removeAll(friends);
		this.recommendations = friendsFollowers;
	}
	
	public List<Long> getRecommendations() {
		return this.recommendations;
	}
	
	private List<Long> getFriends(Twitter twitter, long id) {

		 List<Long> friends = new ArrayList<>();
		 long[] friendsArr = new long[10];
         
		try { 
            long cursor = -1;
            //PagableResponseList<User> pagableFollowings;
            IDs ids;
            do {
                ids = twitter.getFriendsIDs(id, cursor);
                friendsArr = ids.getIDs();
                /*
                for (User user : ids) {
                    friends.add(user.getId()); // ArrayList<User>
                }
                */
                for (int i = 0; i < friendsArr.length; i++) {
        				friends.add(friendsArr[i]);
        			}
            } while ((cursor = ids.getNextCursor()) != 0);

        } catch (TwitterException e) {
            e.printStackTrace();
        }
		
		
		return friends;
	}
	
	private List<Long> getFriendsFollowers(List<Long> friends, Twitter twitter) {
		List<Long> followers = new ArrayList<>();
		
		Set<Long> set = new HashSet<>();
		
		for (Long username : friends) {
			long cursor = -1;
			long[] tempids;
			
			while(cursor != 0) {
	            try {
	                IDs temp = twitter.friendsFollowers().getFollowersIDs(username, cursor);
	                cursor = temp.getNextCursor();
	                tempids = temp.getIDs();
	            } catch (TwitterException e) {
	                e.printStackTrace();
	                return null;
	            }

	            if(tempids != null) {
	                for (long id : tempids) {
	                    set.add(id);
	                }
	            }
	        }
		}
		
		followers.addAll(set);
		
		return followers;
	}
	

	
	/*
	private List<User> getFollowers(Twitter twitter) {
         List<User> listFollowers = new ArrayList<>();
         
		try {
            // get followers
            long cursor = -1;
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
	}*/
	
	public ArrayList<String> getTweets(String user, int tweetNumber) throws TwitterException {
	    List statuses = new ArrayList();
	    ArrayList<String> tweets = new ArrayList<String>();
	    int pageno = 1;
	    int count = 0;
	    System.out.println("Gathering " + user + "'s tweets...");
	    while (pageno <= tweetNumber/100) {
	        try {
	            int size = statuses.size();
	            Paging page = new Paging(pageno++, 100);
	            statuses.addAll(twitter.getUserTimeline(user, page));
	            count += twitter.getUserTimeline(user, page).size();
	            System.out.println("Gathered " + count + " tweets");
	            for (Status status: twitter.getUserTimeline(user, page)) {
	                tweets.add(status.getText());
	            }
	            if (statuses.size() == size)
	                break;
	        }catch(TwitterException e) {
	            e.printStackTrace();
	        }
	    }
	    return tweets;     
		}
	
	public String getTimeline(String user) {
	    String timeline = "";
		try {
            List<Status> statuses;
            statuses = twitter.getUserTimeline(user);

            System.out.println("Showing @" + user + "'s user timeline.");
            for (Status status : statuses) {
                timeline += status.getText();
            	//System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            }
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
        return timeline;    
	}
}