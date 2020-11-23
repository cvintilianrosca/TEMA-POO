package fileio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Information about an user, retrieved from parsing the input test files
 *
 * <p>DO NOT MODIFY
 */
public final class UserInputData {
  /** User's username */
  private final String username;
  /** Subscription Type */
  private final String subscriptionType;
  /** The history of the movies seen */
  private final Map<String, Integer> history;
  /** Movies added to favorites */
  private final ArrayList<String> favoriteMovies;

  private final ArrayList<ShowRatings> showRatingsArrayList = new ArrayList<>();
  private final Map<String, Float> movieRatings = new HashMap<>();

  public UserInputData(
      final String username,
      final String subscriptionType,
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

  public ArrayList<ShowRatings> getShowRatingsArrayList() {
    return showRatingsArrayList;
  }

  @Override
  public String toString() {
    return "UserInputData{"
        + "username='"
        + username
        + '\''
        + ", subscriptionType='"
        + subscriptionType
        + '\''
        + ", history="
        + history
        + ", favoriteMovies="
        + favoriteMovies
        + '}';
  }

  public static class ShowRatings {
    private final String title;
    private final Map<Integer, Float> seasonRatings = new HashMap<>();

    public ShowRatings(final String title, final int season, final float rating) {
      this.title = title;
      this.seasonRatings.put(season, rating);
    }
    /**
     * Function that returns season ratings
     * <p>DO NOT MODIFY
     */
    public Map<Integer, Float> getSeasonRatings() {
      return seasonRatings;
    }
    /**
     * Function that returns Title of movie
     * <p>DO NOT MODIFY
     */
    public String getTitle() {
      return title;
    }
  }
}
