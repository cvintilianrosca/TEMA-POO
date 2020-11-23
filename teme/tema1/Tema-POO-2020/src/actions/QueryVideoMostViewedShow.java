package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.SerialInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import sortingstategies.SortingStrategyFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryVideoMostViewedShow extends AbstractAction {

  public QueryVideoMostViewedShow(
     final Input input, final ActionInputData actionInputData,
     final Writer fileWriter, final JSONArray arrayResult) {
    super(input, actionInputData, fileWriter, arrayResult);
  }
  /**
   * Function that computes the Show mostViewed query,
   * build the message with the list and returns it
   *
   * <p>DO NOT MODIFY
   */
  public StringBuilder executeCommand() {

    StringBuilder message = new StringBuilder();
    List<String> years = super.getActionInputData().getFilters().get(0);
    List<String> genres = super.getActionInputData().getFilters().get(1);
    HashMap<String, Integer> listShows = new HashMap<>();

    for (SerialInputData show : super.getInput().getSerials()) {
      boolean yearFlag = true;
      boolean genresFlag = false;

      if (!(years.isEmpty())) {
        for (String year : years) {

          if (year != null) {
            if (year.compareTo(String.valueOf(show.getYear())) != 0) {
              yearFlag = false;
            }
          }
        }
      }

      if (!genres.isEmpty()) {
        for (String genre : genres) {
          for (String movieGenre : show.getGenres()) {
            if (genre != null) {
              if (genre.compareTo(movieGenre) == 0) {
                genresFlag = true;
                //                            System.out.println(movie.getTitle());
                break;
              }
            }
          }
        }
      }

      if (genresFlag && yearFlag) {
        if (!show.getSeasons().isEmpty()) {
          listShows.put(show.getTitle(), 0);
          // System.out.println(show.getTitle());
        }
      }
    }

    for (UserInputData user : super.getInput().getUsers()) {
      for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
        if (listShows.get(entry.getKey()) != null) {
          listShows.put(entry.getKey(), listShows.get(entry.getKey()) + entry.getValue());
        }
      }
    }

    HashMap<String, Integer> sortedMap;
    if (super.getActionInputData().getSortType().compareTo("asc") == 0) {
      sortedMap = SortingStrategyFactory.createStrategy("asc").sortHashMap(listShows);
    } else {
      sortedMap = SortingStrategyFactory.createStrategy("desc").sortHashMap(listShows);
    }

    ArrayList<Map.Entry<String, Integer>> auxList = new ArrayList<>();
    for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
      if (entry.getValue() != 0) {
        auxList.add(entry);
      }
    }
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
