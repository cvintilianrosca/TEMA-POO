package fileio;

import java.util.ArrayList;

/**
 * Information about a movie, retrieved from parsing the input test files
 *
 * <p>DO NOT MODIFY
 */
public final class MovieInputData extends ShowInput {
  /** Duration in minutes of a season */
  private final int duration;

  private final ArrayList<Double> ratingList;

  public MovieInputData(
      final String title,
      final ArrayList<String> cast,
      final ArrayList<String> genres,
      final int year,
      final int duration) {
    super(title, year, cast, genres);
    this.duration = duration;
    this.ratingList = new ArrayList<>();
  }

  public int getDuration() {
    return duration;
  }
  /**
   * Function that returns a list with ratings
   *
   * <p>DO NOT MODIFY
   */
  public ArrayList<Double> getRatingList() {
    return ratingList;
  }
  /**
   * Function that computes the total rating of
   * movies (average)
   *
   * <p>DO NOT MODIFY
   */
  public double getTotalRating() {
    double sum = 0;
    for (double rating : ratingList) {
      sum += rating;
    }
    if (ratingList.size() != 0) {
      return sum / ratingList.size();
    }
    return sum;
  }

  @Override
  public String toString() {
    return "MovieInputData{"
        + "title= "
        + super.getTitle()
        + "year= "
        + super.getYear()
        + "duration= "
        + duration
        + "cast {"
        + super.getCast()
        + " }\n"
        + "genres {"
        + super.getGenres()
        + " }\n ";
  }
}
