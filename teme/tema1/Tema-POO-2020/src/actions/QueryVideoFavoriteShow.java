package actions;

import entertainment.Season;
import fileio.*;
import org.json.simple.JSONArray;
import sortingstategies.SortingStrategyFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryVideoFavoriteShow extends AbstractAction {

  public QueryVideoFavoriteShow(
      Input input, ActionInputData actionInputData, Writer fileWriter, JSONArray arrayResult) {
    super(input, actionInputData, fileWriter, arrayResult);
  }

  public StringBuilder executeCommand() {
    StringBuilder message = new StringBuilder();
    List<String> years = super.getActionInputData().getFilters().get(0);
    List<String> genres = super.getActionInputData().getFilters().get(1);
    HashMap<String, Integer> listShows = new HashMap<String, Integer>();

    for (SerialInputData show : super.getInput().getSerials()) {
      Boolean yearFlag = true;
      Boolean genresFlag = false;

      if (!(years.isEmpty())) {
        for (String year : years) {

          if (year != null)
            if (year.compareTo(String.valueOf(show.getYear())) != 0) {
              yearFlag = false;
            }
        }
      }

      if (!genres.isEmpty()) {
        for (String genre : genres) {
          for (String movieGenre : show.getGenres()) {
            if (genre != null)
              if (genre.compareTo(movieGenre) == 0) {
                genresFlag = true;
                //                            System.out.println(movie.getTitle());
                break;
              }
          }
        }
      }

      if (genresFlag == true && yearFlag == true) {
        if (!show.getSeasons().isEmpty()) {
          Season aux = show.getSeasons().get(0);
          listShows.put(show.getTitle(), 0);
          // System.out.println(show.getTitle());
        }
      }
    }
    for (UserInputData user : super.getInput().getUsers()) {
      for (String favorite : user.getFavoriteMovies()) {
        if (listShows.get(favorite) != null) {
          listShows.put(favorite, listShows.get(favorite) + 1);
        }
      }
    }

    HashMap<String, Integer> sortedMap;
    if (super.getActionInputData().getSortType().compareTo("asc") == 0) {
      sortedMap = SortingStrategyFactory.createStrategy("asc").sortHashMap(listShows);
    } else {
      sortedMap = SortingStrategyFactory.createStrategy("asc").sortHashMap(listShows);
    }

    message.append("Query result: [");
    int i = 0;
    Boolean added = false;
    for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
      if (i < super.getActionInputData().getNumber()) {
        if (entry.getValue() != 0) {
          message.append(entry.getKey());
          message.append(", ");
          added = true;
        }
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
