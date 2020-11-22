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

  private final ArrayList<showRatings> showRatingsArrayList = new ArrayList<>();
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

  public ArrayList<showRatings> getShowRatingsArrayList() {
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

  public static class showRatings {
    private String title;
    private final Map<Integer, Float> seasonRatings = new HashMap<>();

    public showRatings(String title, final int season, final float rating) {
      this.title = title;
      this.seasonRatings.put(season, rating);
    }

    public Map<Integer, Float> getSeasonRatings() {
      return seasonRatings;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }
  }
}
