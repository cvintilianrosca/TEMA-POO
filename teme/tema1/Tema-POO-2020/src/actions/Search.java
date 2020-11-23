package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Search extends AbstractAction {

  private HashMap<String, Double> listMovies = new HashMap<>();

  public Search(
      final Input input,
      final ActionInputData actionInputData,
      final Writer fileWriter,
      final JSONArray arrayResult) {
    super(input, actionInputData, fileWriter, arrayResult);
  }
  /**
   * Function that compute the command Search, build the result message and returns it
   *
   * <p>DO NOT MODIFY
   */
  public StringBuilder executeCommand() {
    StringBuilder message = new StringBuilder();
    String username = super.getActionInputData().getUsername();
    boolean flag = false;
    for (UserInputData user : super.getInput().getUsers()) {
      if (user.getUsername().compareTo(username) == 0) {
        for (MovieInputData movie : super.getInput().getMovies()) {
          for (String genre : movie.getGenres()) {
            if (genre.compareTo(super.getActionInputData().getGenre()) == 0) {
              flag = true;
              if (user.getHistory().get(movie.getTitle()) == null) {
                listMovies.put(movie.getTitle(), movie.getTotalRating());
              }
            }
          }
        }

        for (SerialInputData serial : super.getInput().getSerials()) {
          for (String genre : serial.getGenres()) {
            if (genre.compareTo(super.getActionInputData().getGenre()) == 0) {
              flag = true;
              if (user.getHistory().get(serial.getTitle()) == null) {
                listMovies.put(serial.getTitle(), serial.getTotalRatings());
              }
            }
          }
        }
      }
    }
    if (!flag || listMovies.isEmpty()) {
      message.append("SearchRecommendation cannot be applied!");
      return message;
    }
    ArrayList<String> finalList = new ArrayList<>();

    for (Map.Entry<String, Double> entry : listMovies.entrySet()) {
      finalList.add(entry.getKey());
    }
    Collections.sort(finalList);
    message.append("SearchRecommendation result: [");
    flag = false;
    for (String element : finalList) {
      message.append(element).append(", ");
      flag = true;
    }
    if (flag) {
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
