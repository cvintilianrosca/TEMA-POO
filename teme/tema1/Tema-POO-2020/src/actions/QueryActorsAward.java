package actions;

import actor.ActorsAwards;
import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONArray;
import sortingstategies.SortingStrategyFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryActorsAward extends AbstractAction {

  public QueryActorsAward(
      final Input input, final ActionInputData actionInputData,
      final Writer fileWriter, final JSONArray arrayResult) {
    super(input, actionInputData, fileWriter, arrayResult);
  }
  /**
   * Function that computes the Actor awards query,
   * build the message with the list and returns it
   *
   * <p>DO NOT MODIFY
   */
  public StringBuilder executeCommand() {
    final int n = 3;
    StringBuilder message = new StringBuilder();
    List<String> listAwardsInput = super.getActionInputData().getFilters().get(n);
    ArrayList<ActorsAwards> listAwards = new ArrayList<>();
    for (String award : listAwardsInput) {
      switch (award) {
        default -> listAwards.add(ActorsAwards.BEST_PERFORMANCE);
        case "BEST_PERFORMANCE" -> listAwards.add(ActorsAwards.BEST_PERFORMANCE);
        case "BEST_DIRECTOR" -> listAwards.add(ActorsAwards.BEST_DIRECTOR);
        case "PEOPLE_CHOICE_AWARD" -> listAwards.add(ActorsAwards.PEOPLE_CHOICE_AWARD);
        case "BEST_SUPPORTING_ACTOR" -> listAwards.add(ActorsAwards.BEST_SUPPORTING_ACTOR);
        case "BEST_SCREENPLAY" -> listAwards.add(ActorsAwards.BEST_SCREENPLAY);
      }
    }
    HashMap<String, Integer> listActorsAward = new HashMap<>();

    for (ActorInputData actor : super.getInput().getActors()) {
      boolean flag = true;

      for (ActorsAwards award : listAwards) {
        if (actor.getAwards().get(award) == null) {
          flag = false;
        }
      }
      if (flag) {
        int sum = 0;
        for (Map.Entry<ActorsAwards, Integer> actorAward : actor.getAwards().entrySet()) {
          sum += actorAward.getValue();
        }
        listActorsAward.put(actor.getName(), sum);
      }
    }

    HashMap<String, Integer> sortedlist;
    if (super.getActionInputData().getSortType().compareTo("desc") == 0) {
      sortedlist = SortingStrategyFactory.createStrategy("desc").sortHashMap(listActorsAward);
    } else {
      sortedlist = SortingStrategyFactory.createStrategy("asc").sortHashMap(listActorsAward);
    }

    ArrayList<Map.Entry<String, Integer>> auxList = new ArrayList<>(sortedlist.entrySet());

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
