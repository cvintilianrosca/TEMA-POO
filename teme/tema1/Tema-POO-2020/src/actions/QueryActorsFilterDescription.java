package actions;

import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QueryActorsFilterDescription extends AbstractAction {

  public QueryActorsFilterDescription(
      final Input input,
      final ActionInputData actionInputData,
      final Writer fileWriter,
      final JSONArray arrayResult) {
    super(input, actionInputData, fileWriter, arrayResult);
  }
  /**
   * Function that computes the Actor filterDescription query, build the message with the list and
   * returns it
   *
   * <p>DO NOT MODIFY
   */
  public StringBuilder executeCommand() {
    StringBuilder message = new StringBuilder();
    List<String> listFilterDescription = super.getActionInputData().getFilters().get(2);
    List<String> listActors = new ArrayList<>();
    StringBuilder aux = new StringBuilder();
    for (ActorInputData actor : super.getInput().getActors()) {
      boolean flag = true;

      for (String filter : listFilterDescription) {
        String aux1 = filter + " ";
        String description = actor.getCareerDescription().toLowerCase();
        if (!description.contains(aux1)) {
          if (!description.contains(filter + ",")) {
            if (!description.contains(filter + ".")) {
              flag = false;
            }
          }
        }
      }
      if (flag) {
        listActors.add(actor.getName());
      }
    }

    if (super.getActionInputData().getSortType().compareTo("asc") == 0) {
      Collections.sort(listActors);
    }
    if (super.getActionInputData().getSortType().compareTo("desc") == 0) {
      listActors.sort(Collections.reverseOrder());
    }
    message.append("Query result: [");

    for (String actor : listActors) {
      message.append(actor).append(", ");
    }

    if (!listActors.isEmpty()) {
      message.deleteCharAt(message.length() - 1).deleteCharAt(message.length() - 1);
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
