package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.List;

public class Favorite extends AbstractAction {

  public Favorite(
      final Input input, final ActionInputData actionInputData,
      final Writer fileWriter, final JSONArray arrayResult) {
    super(input, actionInputData, fileWriter, arrayResult);
  }

  public StringBuilder executeCommand() {
    StringBuilder message = new StringBuilder();
    String username = super.getActionInputData().getUsername();
    List<UserInputData> usersList = super.getInput().getUsers();
    if (!usersList.isEmpty()) {
      for (UserInputData userInputData : usersList) {
        if (userInputData.getUsername().compareTo(username) == 0) {
          if (userInputData.getHistory().get(super.getActionInputData().getTitle()) == null) {

            message.append("error -> ");
            message.append(super.getActionInputData().getTitle());
            message.append(" is not seen");
            return message;
          } else {
            for (String favorite : userInputData.getFavoriteMovies()) {
              if (favorite.compareTo(super.getActionInputData().getTitle()) == 0) {
                message.append("error -> ");
                message.append(super.getActionInputData().getTitle());
                message.append(" is already in favourite list");
                return message;
              }
            }
            userInputData.getFavoriteMovies().add(super.getActionInputData().getTitle());
            message.append("success -> ");
            message.append(super.getActionInputData().getTitle());
            message.append(" was added as favourite");
          }
        }
      }
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
