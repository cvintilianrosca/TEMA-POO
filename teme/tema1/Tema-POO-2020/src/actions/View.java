package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.List;

public class View extends AbstractAction {

  public View(
      final Input input, final ActionInputData actionInputData,
      final Writer fileWriter, final JSONArray arrayResult) {
    super(input, actionInputData, fileWriter, arrayResult);
  }
  /**
   * Function that compute the command View, modifies the database,
   * build the result message and returns it
   *
   * <p>DO NOT MODIFY
   */
  public StringBuilder executeCommand() {
    StringBuilder message = new StringBuilder();
    String username = super.getActionInputData().getUsername();
    List<UserInputData> usersList = super.getInput().getUsers();
    for (UserInputData user : usersList) {
      if (user.getUsername().compareTo(username) == 0) {
        if (user.getHistory().get(super.getActionInputData().getTitle()) != null) {
          int views = user.getHistory().get(super.getActionInputData().getTitle());
          views += 1;
          user.getHistory().put(super.getActionInputData().getTitle(), views);
          message.append("success -> ");
          message.append(super.getActionInputData().getTitle());
          message.append(" was viewed with total views of ");
          message.append(views);
        } else {
          user.getHistory().put(super.getActionInputData().getTitle(), 1);
          message.append("success -> ");
          message.append(super.getActionInputData().getTitle());
          message.append(" was viewed with total views of 1");
        }
      }
    }
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
