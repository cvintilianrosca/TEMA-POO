package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import sortingstategies.SortingStrategyFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryVideoFavoriteMovie extends AbstractAction {

  public QueryVideoFavoriteMovie(
      final Input input, final ActionInputData actionInputData,
      final Writer fileWriter, final JSONArray arrayResult) {
    super(input, actionInputData, fileWriter, arrayResult);
  }
  /**
   * Function that computes the Video favorite query,
   * build the message with the list and returns it
   *
   * <p>DO NOT MODIFY
   */
  public StringBuilder executeCommand() {
    StringBuilder message = new StringBuilder();
    HashMap<String, Integer> listMovies = new HashMap<>();
    List<String> years = super.getActionInputData().getFilters().get(0);
    List<String> genres = super.getActionInputData().getFilters().get(1);

    for (MovieInputData movie : super.getInput().getMovies()) {
      boolean yearFlag = true;
      boolean genresFlag = false;
      if (years.get(0) != null) {
        for (String year : years) {

          if (year != null) {
            if (year.compareTo(String.valueOf(movie.getYear())) != 0) {
              yearFlag = false;
            }
          }
        }
      }
      if (genres.get(0) == null) {
        genresFlag = true;
      } else {
        for (String genre : genres) {
          for (String movieGenre : movie.getGenres()) {
            if (genre != null) {
              if (genre.compareTo(movieGenre) == 0) {
                genresFlag = true;
              }
            }
          }
        }
      }
      if (genresFlag && yearFlag) {
        listMovies.put(movie.getTitle(), 0);
      }
    }

    for (UserInputData user : super.getInput().getUsers()) {
      for (String favoriteMovie : user.getFavoriteMovies()) {
        if (listMovies.get(favoriteMovie) != null) {
          listMovies.
                  put(favoriteMovie, listMovies.get(favoriteMovie) + 1);
        }
      }
    }

    HashMap<String, Integer> sortedMap;
    if (super.getActionInputData().getSortType().compareTo("asc") == 0) {
      sortedMap = SortingStrategyFactory.createStrategy("asc").sortHashMap(listMovies);
    } else {
      sortedMap = SortingStrategyFactory.createStrategy("desc").sortHashMap(listMovies);
    }

    ArrayList<Map.Entry<String, Integer>> auxList = new ArrayList<>(sortedMap.entrySet());
    if (super.getActionInputData().getSortType().compareTo("asc") == 0) {
      auxList = SortingStrategyFactory.createStrategy("asc").bubbleSortForInteger(auxList);
    } else {
      auxList = SortingStrategyFactory.createStrategy("desc").bubbleSortForInteger(auxList);
    }

    message.append("Query result: [");
    int i = 0;
    boolean added = false;
    for (Map.Entry<String, Integer> entry : auxList) {
      if (i < super.getActionInputData().getNumber()) {
        message.append(entry.getKey());
        message.append(", ");
        added = true;
      }
      i++;
    }

    if (added) {
      message.deleteCharAt(message.length() - 1);
      message.deleteCharAt(message.length() - 1);
    }
    message.append("]");
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
