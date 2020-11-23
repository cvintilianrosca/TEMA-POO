package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;

public class Standard extends AbstractAction {

  public Standard(
      final Input input,
      final ActionInputData actionInputData,
      final Writer fileWriter,
      final JSONArray arrayResult) {
    super(input, actionInputData, fileWriter, arrayResult);
  }
  /**
   * Function that compute the command Standard, build the result message and returns it
   *
   * <p>DO NOT MODIFY
   */
  public StringBuilder executeCommand() {
    StringBuilder message = new StringBuilder();
    String username = super.getActionInputData().getUsername();
    for (UserInputData user : super.getInput().getUsers()) {
      if (user.getUsername().compareTo(username) == 0) {

        for (MovieInputData movie : super.getInput().getMovies()) {
          if (user.getHistory().get(movie.getTitle()) == null) {
            message.append("StandardRecommendation result: ");
            message.append(movie.getTitle());
            return message;
          }
        }
      }
    }
    message.append("StandardRecommendation cannot be applied!");
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
