package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.SerialInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import sortingstategies.SortingStrategyFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryVideoRatingShow extends AbstractAction {

  public QueryVideoRatingShow(
      final Input input,
      final ActionInputData actionInputData,
      final Writer fileWriter,
      final JSONArray arrayResult) {
    super(input, actionInputData, fileWriter, arrayResult);
  }
  /**
   * Function that computes the Show MostRated query, build the message with the list and returns it
   *
   * <p>DO NOT MODIFY
   */
  public StringBuilder executeCommand() {

    StringBuilder message = new StringBuilder();
    List<String> years = super.getActionInputData().getFilters().get(0);
    List<String> genres = super.getActionInputData().getFilters().get(1);
    HashMap<String, Float> listShows = new HashMap<>();

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
                break;
              }
            }
          }
        }
      }

      if (genresFlag && yearFlag) {
        if (!show.getSeasons().isEmpty()) {
          listShows.put(show.getTitle(), (float) show.getTotalRatings());
        }
      }
    }

    HashMap<String, Float> sortedMap;
    if (super.getActionInputData().getSortType().compareTo("asc") == 0) {
      sortedMap = SortingStrategyFactory.createStrategy("asc").sortHashMap(listShows);
    } else {
      sortedMap = SortingStrategyFactory.createStrategy("desc").sortHashMap(listShows);
    }

    message.append("Query result: [");
    int i = 0;
    boolean added = false;
    for (Map.Entry<String, Float> entry : sortedMap.entrySet()) {
      if (i < super.getActionInputData().getNumber()) {
        if (entry.getValue() != 0.0) {
          message.append(entry.getKey());
          message.append(", ");
          added = true;
        }
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
