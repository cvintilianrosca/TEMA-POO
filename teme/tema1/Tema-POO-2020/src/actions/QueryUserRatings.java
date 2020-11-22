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
      Input input, ActionInputData actionInputData, Writer fileWriter, JSONArray arrayResult) {
    super(input, actionInputData, fileWriter, arrayResult);
  }

  public StringBuilder executeCommand() {
    HashMap<String, Integer> usersList = new HashMap<>();
    StringBuilder message = new StringBuilder();

    for (UserInputData user : super.getInput().getUsers()) {
      int counter = 0;
      for (Map.Entry<String, Float> rating : user.getMovieRatings().entrySet()) {
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

    ArrayList<Map.Entry<String, Integer>> auxList = new ArrayList<>();
    for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
      auxList.add(entry);
    }

    if (super.getActionInputData().getSortType().compareTo("asc") == 0) {
      auxList = SortingStrategyFactory.createStrategy("asc").bubbleSortForInteger(auxList);

    } else {

      auxList = SortingStrategyFactory.createStrategy("desc").bubbleSortForInteger(auxList);
    }

    message.append("Query result: [");
    int i = 0;
    Boolean added = false;
    for (Map.Entry<String, Integer> entry : auxList) {
      if (i < super.getActionInputData().getNumber()) {
        message.append(entry.getKey());
        message.append(", ");
        added = true;
      }
      i++;
    }

    if (added == true) {
      message.deleteCharAt(message.length() - 1);
      message.deleteCharAt(message.length() - 1);
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
