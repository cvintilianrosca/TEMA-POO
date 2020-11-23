package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import sortingstategies.SortingStrategyFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QueryUserRatings extends AbstractAction {

  public QueryUserRatings(
      final Input input, final ActionInputData actionInputData,
      final Writer fileWriter, final JSONArray arrayResult) {
    super(input, actionInputData, fileWriter, arrayResult);
  }
  /**
   * Function that computes the user rating query,
   * build the message with the list and returns it
   *
   * <p>DO NOT MODIFY
   */
  public StringBuilder executeCommand() {
    HashMap<String, Integer> usersList = new HashMap<>();
    StringBuilder message = new StringBuilder();

    for (UserInputData user : super.getInput().getUsers()) {
      int counter = 0;
      for (Map.Entry<String, Float> ignored : user.getMovieRatings().entrySet()) {
        counter++;
      }
      counter += user.getShowRatingsArrayList().size();
      if (counter != 0) {
        usersList.put(user.getUsername(), counter);
      }
    }

    HashMap<String, Integer> sortedMap;
    if (super.getActionInputData().getSortType().compareTo("asc") == 0) {
      sortedMap = SortingStrategyFactory.createStrategy("asc").sortHashMap(usersList);
    } else {
      sortedMap = SortingStrategyFactory.createStrategy("desc").sortHashMap(usersList);
    }

    ArrayList<Map.Entry<String, Integer>> auxList = new ArrayList<>(sortedMap.entrySet());

    if (super.getActionInputData().getSortType().compareTo("asc") == 0) {
      auxList = SortingStrategyFactory.createStrategy("asc").sortForInteger(auxList);

    } else {

      auxList = SortingStrategyFactory.createStrategy("desc").sortForInteger(auxList);
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
