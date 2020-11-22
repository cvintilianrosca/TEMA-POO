package actions;

import fileio.*;
import org.json.simple.JSONArray;
import sortingstategies.SortingStrategyFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryVideoFavoriteMovie extends AbstractAction {

  public QueryVideoFavoriteMovie(
      Input input, ActionInputData actionInputData, Writer fileWriter, JSONArray arrayResult) {
    super(input, actionInputData, fileWriter, arrayResult);
  }

  public StringBuilder executeCommand() {
    StringBuilder message = new StringBuilder();
    HashMap<String, Integer> listMovies = new HashMap<String, Integer>();
    List<String> years = super.getActionInputData().getFilters().get(0);
    List<String> genres = super.getActionInputData().getFilters().get(1);

    for (MovieInputData movie : super.getInput().getMovies()) {
      Boolean yearFlag = true;
      Boolean genresFlag = false;
      if (years.get(0) != null) {
        if (!(years.isEmpty())) {
          for (String year : years) {

            if (year != null)
              if (year.compareTo(String.valueOf(movie.getYear())) != 0) {
                yearFlag = false;
              }
          }
        }
      } else {
        yearFlag = true;
      }
      if (genres.get(0) != null) {
        if (!genres.isEmpty()) {
          for (String genre : genres) {
            for (String movieGenre : movie.getGenres()) {
              if (genre != null)
                if (genre.compareTo(movieGenre) == 0) {
                  genresFlag = true;
                }
            }
          }
        }
      } else {
        genresFlag = true;
      }
      if (genresFlag == true && yearFlag == true) {
        listMovies.put(movie.getTitle(), 0);
      }
    }

    for (UserInputData user : super.getInput().getUsers()) {
      for (String favoriteMovie : user.getFavoriteMovies()) {
        if (listMovies.get(favoriteMovie) != null) {
          listMovies.put(favoriteMovie, listMovies.get(favoriteMovie) + 1);
        }
      }
    }

    HashMap<String, Integer> sortedMap;
    if (super.getActionInputData().getSortType().compareTo("asc") == 0) {
      sortedMap = SortingStrategyFactory.createStrategy("asc").sortHashMap(listMovies);
    } else {
      sortedMap = SortingStrategyFactory.createStrategy("desc").sortHashMap(listMovies);
    }

    ArrayList<Map.Entry<String, Integer>> auxList = new ArrayList<>();
    for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
      auxList.add(entry);
    }
    if (super.getActionInputData().getSortType().compareTo("asc") == 0) {
      auxList = SortingStrategyFactory.createStrategy("asc").bubbleSortForInteger(auxList);
    } else {
      auxList = SortingStrategyFactory.createStrategy("desc").bubbleSortForInteger(auxList);
    }

    message.append("Query result: [");
    int i = 0;
    Boolean added = false;
    for (Map.Entry<String, Integer> entry : auxList) {
      if (i < super.getActionInputData().getNumber()) {
        message.append(entry.getKey());
        message.append(", ");
        added = true;
      }
      i++;
    }

    if (added == true) {
      message.deleteCharAt(message.length() - 1);
      message.deleteCharAt(message.length() - 1);
    }
    message.append("]");
    return message;
  }

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
