

import java.util.ArrayList;

import twitter4j.*;

public class TestClass {

	
	public static void main(String[] args) {
		System.out.println("tweet");
		System.out.println("Sammy tweet");
		
		TwitterInformation arena = new TwitterInformation();

		try {
			ArrayList<String> tweets = arena.getTweets("neiltyson", 500);
			System.out.println(tweets.get(1));
			
			String timeline = arena.getTimeline("neiltyson");
			System.out.println(timeline);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}