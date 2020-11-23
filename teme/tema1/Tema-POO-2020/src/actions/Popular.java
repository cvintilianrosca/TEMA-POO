package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import sortingstategies.SortingStrategy;
import sortingstategies.SortingStrategyFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Popular extends AbstractAction {

  public Popular(
     final Input input, final ActionInputData actionInputData,
     final Writer fileWriter, final JSONArray arrayResult) {
    super(input, actionInputData, fileWriter, arrayResult);
  }
  /**
   * Function that computes the Popular recommendation, builds a message,
   * with the result, and returns it
   *
   * <p>DO NOT MODIFY
   */
  public StringBuilder executeCommand() {
    String username = super.getActionInputData().getUsername();
    StringBuilder message = new StringBuilder();
    HashMap<String, Integer> genresViews = new HashMap<>();
    UserInputData userAux = null;

    for (UserInputData user : super.getInput().getUsers()) {
      if (user.getUsername().compareTo(username) == 0) {
        userAux = user;
        if (userAux.getSubscriptionType().compareTo("BASIC") == 0) {
          message.append("PopularRecommendation cannot be applied!");
          return message;
        }
      }
      for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
        for (MovieInputData movie : super.getInput().getMovies()) {
          if (entry.getKey().compareTo(movie.getTitle()) == 0) {
            for (String genre : movie.getGenres()) {
              if (genresViews.containsKey(genre)) {
                genresViews.put(genre, genresViews.get(genre) + entry.getValue());
              } else {
                genresViews.put(genre, entry.getValue());
              }
            }
          }
        }
        for (SerialInputData serial : super.getInput().getSerials()) {
          if (entry.getKey().compareTo(serial.getTitle()) == 0) {
            for (String genre : serial.getGenres()) {
              if (genresViews.containsKey(genre)) {
                genresViews.put(genre, genresViews.get(genre) + entry.getValue());
              } else {
                genresViews.put(genre, entry.getValue());
              }
            }
          }
        }
      }
    }
    SortingStrategy sortingStrategy = SortingStrategyFactory.createStrategy("desc");
    HashMap<String, Integer> sortedGenreViews = sortingStrategy.sortHashMap(genresViews);
    for (Map.Entry<String, Integer> entry : sortedGenreViews.entrySet()) {

      for (MovieInputData movie : super.getInput().getMovies()) {
        for (String genre : movie.getGenres()) {
          if (genre.compareTo(entry.getKey()) == 0) {
            assert userAux != null;
            if (userAux.getHistory().get(movie.getTitle()) == null) {
              message.append("PopularRecommendation result: ");
              message.append(movie.getTitle());
              return message;
            }
          }
        }
      }
    }
    for (Map.Entry<String, Integer> entry : sortedGenreViews.entrySet()) {
      for (SerialInputData serial : super.getInput().getSerials()) {
        for (String genre : serial.getGenres()) {
          if (genre.compareTo(entry.getKey()) == 0) {
            assert userAux != null;
            if (userAux.getHistory().get(serial.getTitle()) == null) {
              message.append("PopularRecommendation result: ");
              message.append(serial.getTitle());
              return message;
            }
          }
        }
      }
    }

    message.append("PopularRecommendation cannot be applied!");
    return message;
  }
  /**
   * Function that add the computed message to the arrayResult
   *
   * <p>DO NOT MODIFY
   */
  public void execute() throws IOException {
    StringBuilder message = executeCommand();
    super.getArrayResult()
        .add(
            super.getFileWriter()
                .writeFile(
                    Integer.toString(super.getActionInputData().getActionId()),
                    "message:",
                    message.toString()));
  }
}
