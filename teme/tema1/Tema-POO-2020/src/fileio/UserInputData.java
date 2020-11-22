package fileio;

import java.util.*;

/**
 * Information about an user, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class UserInputData {
    /**
     * User's username
     */
    private final String username;
    /**
     * Subscription Type
     */
    private final String subscriptionType;
    /**
     * The history of the movies seen
     */
    private final Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    private final ArrayList<String> favoriteMovies;
    private final ArrayList<showRatings> showRatingsArrayList= new ArrayList<>();

    public  class showRatings{
        private String Title;
        private Map <Integer, Float> seasonRatings=new HashMap<Integer, Float>();

        public showRatings(String Title, int season, float rating){
            this.Title=Title;
            this.seasonRatings.put(season,rating);

        }

     public void putSeasonRatings(int season, float seasonRatings){
          this.seasonRatings.put(season,seasonRatings);
        }
        public Map<Integer, Float> getSeasonRatings() {
            return seasonRatings;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getTitle() {
            return Title;
        }
    }

    private final Map<String, Float> movieRatings =new HashMap<String, Float>();

    public UserInputData(final String username, final String subscriptionType,
                         final Map<String, Integer> history,
                         final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public Map<String, Float> getMovieRatings() {
        return movieRatings;
    }

    public ArrayList<showRatings> getShowRatingsArrayList() {
        return showRatingsArrayList;
    }

    @Override
    public String toString() {
        return "UserInputData{" + "username='"
                + username + '\'' + ", subscriptionType='"
                + subscriptionType + '\'' + ", history="
                + history + ", favoriteMovies="
                + favoriteMovies + '}';
    }
}
