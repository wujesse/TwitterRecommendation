import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import twitter4j.PagableResponseList;
import twitter4j.Paging;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.api.FriendsFollowersResources;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterInformation {
	ConfigurationBuilder cb;
	Twitter twitter;
	TwitterFactory tf;
	FriendsFollowersResources ffResources;
	
	private List<User> recommendations;
	
	public TwitterInformation(String userName) {
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("Joy1KOjXNlE1qt6JL81WeVPdz")
		  .setOAuthConsumerSecret("9COvRqcw2aX4S0sAIF9m88hAZGZPmhG427MDHDrsJRATe0pww8")
		  .setOAuthAccessToken("990434071632404480-aLQltQ1715JuXKaDRLKTd9cGQDESTrG")
		  .setOAuthAccessTokenSecret("bvJdvDHIitQQqbiHy0ZVwcaw9zWIkbmdLxZJCzyNYIOPo");

		tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
		
		ffResources = twitter.friendsFollowers();
		long id = 0;
		try {
			id = twitter.showUser(userName).getId();
		} catch (TwitterException e) {
			System.out.println("username does not exist");
			System.exit(0);
		}
		
    
		List<User> friends = getFriends(twitter, id);
		List<User> friendsFollowers = getFriendsFollowers(friends, twitter);
		friendsFollowers.removeAll(friends);
		this.recommendations = friendsFollowers;
	}
	
	public Twitter newTwitterInstance() {
		return tf.getInstance();
	}
	
	public List<User> getRecommendations() {
		return this.recommendations;
	}
	
	private List<User> getFriends(Twitter twitter, long id) {
		List<User> friends = new ArrayList<User>();
		try {
			friends = ffResources.getFriendsList(id, -1);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return friends;
	}
	
	private List<User> getFriendsFollowers(List<User> friends, Twitter twitter) {
		List<User> followers = new ArrayList<>();
		Set<User> set = new HashSet<>();
		RateLimitStatus followersStatus = null;
		int limit = 0;
		try {
			followersStatus = twitter.getRateLimitStatus().get("/followers/list");
			limit = followersStatus.getRemaining();
		} catch (TwitterException e1) {
			e1.printStackTrace();
		}
		
		if (limit == 0) {
			System.out.println("\nYou have reached the limit for followersList calls from the Twitter API. Please "
				+ "wait " + followersStatus.getSecondsUntilReset() + " seconds for the limit to reset.\nExiting...");
			System.exit(1);
		}
		
		int i = 0;
		
		while (limit > i && friends.size() > i) {
			System.out.println("FollowerList Endpoints Used: " + i);
			long cursor = -1;
			PagableResponseList<User> followersList = null;
			//do {
				try {
					followersList = ffResources.getFollowersList(friends.get(i).getId(), cursor);
	                set.addAll(followersList);
	            } catch (TwitterException e) {
	                e.printStackTrace();
	                return null;
	            }
			//} while ((cursor = followersList.getNextCursor()) != 0);
            
            i++;
		}
		
		followers.addAll(set);
		return followers;
	}
	
	public ArrayList<String> getTweets(String user, int tweetNumber) throws TwitterException {
	    List statuses = new ArrayList();
	    ArrayList tweets = new ArrayList<String>();
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
	
	public String getTimeline(long id) {
	    StringBuilder timeline = new StringBuilder();
		try {
            List<Status> statuses;
            statuses = twitter.getUserTimeline(id);

            System.out.println("Getting" + id + "'s user timeline.");
            for (Status status : statuses) {
                timeline.append(status.getText());
            }
        } catch (TwitterException te) {
            
        }
        return timeline.toString();    
	}
}