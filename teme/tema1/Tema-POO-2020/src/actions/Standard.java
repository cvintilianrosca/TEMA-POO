package actions;

import fileio.*;
import org.json.simple.JSONArray;

import java.io.IOException;

public class Standard extends AbstractAction {

  public Standard(
      Input input, ActionInputData actionInputData, Writer fileWriter, JSONArray arrayResult) {
    super(input, actionInputData, fileWriter, arrayResult);
  }

  public StringBuilder executeCommand() {
    StringBuilder message = new StringBuilder();
    String username = super.getActionInputData().getUsername();
    boolean flag = false;
    for (UserInputData user : super.getInput().getUsers()) {
      if (user.getUsername().compareTo(username) == 0) {

        for (MovieInputData movie : super.getInput().getMovies()) {
          if (user.getHistory().get(movie.getTitle()) == null) {
            message.append("StandardRecommendation result: ");
            message.append(movie.getTitle());
            flag = true;
            return message;
          }
        }
      }
    }
    if (flag == false) {
      message.append("StandardRecommendation cannot be applied!");
    }
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
