import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;

import twitter4j.*;

public class Recommender {

	private List<User> userList;
	private PriorityQueue<CosineUser> possibleSuggestions; 
	TwitterInformation tInfo;

	public Recommender(String userName) {
		tInfo = new TwitterInformation(userName);
		userList = new ArrayList<User>();		
		ArrayList<Document> allStatuses = new ArrayList<Document>();
		possibleSuggestions = new PriorityQueue<CosineUser>(Collections.reverseOrder());
		
		userList = tInfo.getRecommendations();
		
		User mainUser = null;
		try {
			mainUser = tInfo.newTwitterInstance().showUser(userName);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
		Document query = new Document(mainUser.getId(), tInfo.getTimeline(mainUser.getId()));
		allStatuses.add(query);
		userList.add(mainUser);
		
		int j = 0;
		for (User user : userList) {
			if (j < 899) {
				String timeLineText = tInfo.getTimeline(user.getId());
				allStatuses.add(new Document(user.getId(), timeLineText));
				j++;
			}
			else {
				break;
			}
		}
		
		Corpus corpus = new Corpus(allStatuses);
		VectorSpaceModel vectorSpace = new VectorSpaceModel(corpus);
		
		for (int i = 0; i < userList.size(); i++) {
			possibleSuggestions.add(new CosineUser(userList.get(i), 
					vectorSpace.cosineSimilarity(query, allStatuses.get(i))));
		}
	}
	
	public List<String> getRecommendedUsers(int recs) {
		List<String> recUsers = new ArrayList<String>();
		for (int i = 0; i < recs + 1; i++) {
			if (!possibleSuggestions.isEmpty()) {
				recUsers.add(possibleSuggestions.remove().getScreenName());
			}
			else {
				break;
			}
		}
		return recUsers;
	}
	
	private class CosineUser implements User {

		private static final long serialVersionUID = 1L;
		private User user;
		private Double dfIdf;
		
		public CosineUser(User u, double rating) {
			user = u;
			dfIdf = rating;
		}
		
		@Override
		public int compareTo(User o) {
			if (o instanceof CosineUser) {
				return dfIdf.compareTo(((CosineUser) o).getDfIdf());
			}
			else {
				return user.compareTo(o);
			}
		}

		public double getDfIdf() {
			return dfIdf;
		}
		
		@Override
		public int getAccessLevel() {
			return user.getAccessLevel();
		}

		@Override
		public RateLimitStatus getRateLimitStatus() {
			return user.getRateLimitStatus();
		}

		@Override
		public String getBiggerProfileImageURL() {
			return user.getBiggerProfileImageURL();
		}

		@Override
		public String getBiggerProfileImageURLHttps() {
			return user.getBiggerProfileImageURLHttps();
		}

		@Override
		public Date getCreatedAt() {
			return user.getCreatedAt();
		}

		@Override
		public String getDescription() {
			return user.getDescription();
		}

		@Override
		public URLEntity[] getDescriptionURLEntities() {
			return user.getDescriptionURLEntities();
		}

		@Override
		public int getFavouritesCount() {
			return user.getFavouritesCount();
		}

		@Override
		public int getFollowersCount() {
			return user.getFollowersCount();
		}

		@Override
		public int getFriendsCount() {
			return user.getFriendsCount();
		}

		@Override
		public long getId() {
			return user.getId();
		}

		@Override
		public String getLang() {
			return user.getLang();
		}

		@Override
		public int getListedCount() {
			return user.getListedCount();
		}

		@Override
		public String getLocation() {
			return user.getLocation();
		}

		@Override
		public String getMiniProfileImageURL() {
			return user.getMiniProfileImageURL();
		}

		@Override
		public String getMiniProfileImageURLHttps() {
			return user.getMiniProfileImageURLHttps();
		}

		@Override
		public String getName() {
			return user.getName();
		}

		@Override
		public String getOriginalProfileImageURL() {
			return user.getOriginalProfileImageURL();
		}

		@Override
		public String getOriginalProfileImageURLHttps() {
			return user.getOriginalProfileImageURLHttps();
		}

		@Override
		public String getProfileBackgroundColor() {
			return user.getProfileBackgroundColor();
		}

		@Override
		public String getProfileBackgroundImageURL() {
			return user.getProfileBackgroundImageURL();
		}

		@Override
		public String getProfileBackgroundImageUrlHttps() {
			return user.getProfileBackgroundImageUrlHttps();
		}

		@Override
		public String getProfileBannerIPadRetinaURL() {
			return user.getProfileBannerIPadRetinaURL();
		}

		@Override
		public String getProfileBannerIPadURL() {
			return user.getProfileBannerIPadURL();
		}

		@Override
		public String getProfileBannerMobileRetinaURL() {
			return user.getProfileBannerMobileRetinaURL();
		}

		@Override
		public String getProfileBannerMobileURL() {
			return user.getProfileBannerMobileURL();
		}

		@Override
		public String getProfileBannerRetinaURL() {
			return user.getProfileBannerRetinaURL();
		}

		@Override
		public String getProfileBannerURL() {
			return user.getProfileBannerURL();
		}

		@Override
		public String getProfileImageURL() {
			return user.getProfileImageURL();
		}

		@Override
		public String getProfileImageURLHttps() {
			return user.getProfileImageURLHttps();
		}

		@Override
		public String getProfileLinkColor() {
			return user.getProfileLinkColor();
		}

		@Override
		public String getProfileSidebarBorderColor() {
			return user.getProfileSidebarBorderColor();
		}

		@Override
		public String getProfileSidebarFillColor() {
			return user.getProfileSidebarFillColor();
		}

		@Override
		public String getProfileTextColor() {
			return user.getProfileTextColor();
		}

		@Override
		public String getScreenName() {
			return user.getScreenName();
		}

		@Override
		public Status getStatus() {
			return user.getStatus();
		}

		@Override
		public int getStatusesCount() {
			return user.getStatusesCount();
		}

		@Override
		public String getTimeZone() {
			return user.getTimeZone();
		}

		@Override
		public String getURL() {
			return user.getURL();
		}

		@Override
		public URLEntity getURLEntity() {
			return user.getURLEntity();
		}

		@Override
		public int getUtcOffset() {
			return user.getUtcOffset();
		}

		@Override
		public String[] getWithheldInCountries() {
			return user.getWithheldInCountries();
		}
	

		@Override
		public boolean isContributorsEnabled() {
			return user.isContributorsEnabled();
		}

		@Override
		public boolean isDefaultProfile() {
			return user.isDefaultProfile();
		}

		@Override
		public boolean isDefaultProfileImage() {
			return user.isDefaultProfileImage();
		}

		@Override
		public boolean isFollowRequestSent() {
			return user.isFollowRequestSent();
		}

		@Override
		public boolean isGeoEnabled() {
			return user.isGeoEnabled();
		}

		@Override
		public boolean isProfileBackgroundTiled() {
			return user.isProfileBackgroundTiled();
		}

		@Override
		public boolean isProfileUseBackgroundImage() {
			return user.isProfileUseBackgroundImage();
		}

		@Override
		public boolean isProtected() {
			return user.isProtected();
		}

		@Override
		public boolean isShowAllInlineMedia() {
			return user.isShowAllInlineMedia();
		}

		@Override
		public boolean isTranslator() {
			return user.isTranslator();
		}

		@Override
		public boolean isVerified() {
			return user.isVerified();
		}
		
	}
}
