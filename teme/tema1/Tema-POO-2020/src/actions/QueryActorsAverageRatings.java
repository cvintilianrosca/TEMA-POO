package actions;

import fileio.*;
import org.json.simple.JSONArray;
import sortingstategies.SortingStrategyFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QueryActorsAverageRatings extends AbstractAction {

  public QueryActorsAverageRatings(
      Input input, ActionInputData actionInputData, Writer fileWriter, JSONArray arrayResult) {
    super(input, actionInputData, fileWriter, arrayResult);
  }

  public StringBuilder executeCommand() {
    StringBuilder message = new StringBuilder();
    HashMap<String, ArrayList<Double>> actorsList = new HashMap<String, ArrayList<Double>>();

    for (MovieInputData movie : super.getInput().getMovies()) {
      for (String actor : movie.getCast()) {
        if (actorsList.get(actor) == null) {
          actorsList.put(actor, new ArrayList<>());
          ArrayList<Double> aux = actorsList.get(actor);
          aux.add(movie.getTotalRating());
          actorsList.put(actor, aux);
        } else {
          ArrayList<Double> aux = actorsList.get(actor);
          aux.add(movie.getTotalRating());
          actorsList.put(actor, aux);
        }
      }
    }

    for (SerialInputData serial : super.getInput().getSerials()) {
      for (String actor : serial.getCast()) {
        if (actorsList.get(actor) == null) {
          actorsList.put(actor, new ArrayList<>());
          ArrayList<Double> aux = actorsList.get(actor);
          aux.add(serial.getTotalRatings());
          actorsList.put(actor, aux);
        } else {
          ArrayList<Double> aux = actorsList.get(actor);
          aux.add(serial.getTotalRatings());
          actorsList.put(actor, aux);
        }
      }
    }

    HashMap<String, Double> finalListActors = new HashMap<>();

    for (Map.Entry<String, ArrayList<Double>> entry : actorsList.entrySet()) {
      double sum = 0;
      int counter = 0;
      if (finalListActors.get(entry.getKey()) == null) {
        for (double rating : entry.getValue()) {
          sum += rating;
          if (rating != 0.0) {
            counter++;
          }
        }
        if (counter != 0) sum = sum / counter;
        if (sum != 0) finalListActors.put(entry.getKey(), sum);
      }
    }

    HashMap<String, Double> sortedMap;
    if (super.getActionInputData().getSortType().compareTo("asc") == 0) {
      sortedMap = SortingStrategyFactory.createStrategy("asc").sortHashMap(finalListActors);
    } else {
      sortedMap = SortingStrategyFactory.createStrategy("desc").sortHashMap(finalListActors);
    }

    ArrayList<Map.Entry<String, Double>> auxList = new ArrayList<>();
    auxList.addAll(sortedMap.entrySet());
    if (super.getActionInputData().getSortType().compareTo("asc") == 0) {
      auxList = SortingStrategyFactory.createStrategy("asc").bubbleSortForDouble(auxList);
    } else {
      auxList = SortingStrategyFactory.createStrategy("desc").bubbleSortForDouble(auxList);
    }

    message.append("Query result: [");
    int i = 0;
    Boolean added = false;
    for (Map.Entry<String, Double> entry : auxList) {
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
