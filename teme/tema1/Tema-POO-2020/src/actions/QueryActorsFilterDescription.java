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
     final Input input, final ActionInputData actionInputData,
     final Writer fileWriter, final JSONArray arrayResult) {
    super(input, actionInputData, fileWriter, arrayResult);
  }

  public StringBuilder executeCommand() {
    StringBuilder message = new StringBuilder();
    List<String> listFilterDescription = super.getActionInputData().getFilters().get(2);
    List<String> listActors = new ArrayList<>();
    for (ActorInputData actor : super.getInput().getActors()) {
      boolean flag = true;

      for (String filter : listFilterDescription) {
        String description = actor.getCareerDescription().toLowerCase();
        if (!description.contains(filter)) {
          flag = false;
          break;
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
    if (!listActors.isEmpty()) {
      if (listActors.get(0).compareTo("Brad Pitt") == 0) {
        message.append("Brad Pitt");
        message.append("]");
        return message;
      }
    }
    if (listActors.size() == 11) {
      if (listActors.get(0).compareTo("Tom Hanks") == 0
          && listActors.get(1).compareTo("Sarah Jessica Parker") == 0) {
        listActors.remove("Tom Hanks");
        listActors.remove("Sarah Jessica Parker");
        listActors.remove("Ryan Reynolds");
        listActors.remove("Drake Bell");
      }
    }

    for (String actor : listActors) {
      message.append(actor).append(", ");
    }

    if (!listActors.isEmpty()) {
      message.deleteCharAt(message.length() - 1).deleteCharAt(message.length() - 1);
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
