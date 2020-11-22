package actions;

import fileio.*;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.*;

public class QueryActorsAverageRatings extends AbstractAction {

  public QueryActorsAverageRatings(
      Input input, ActionInputData actionInputData, Writer fileWriter, JSONArray arrayResult) {
    super(input, actionInputData, fileWriter, arrayResult);
  }

  private static HashMap sort(HashMap map) {
    List list = new LinkedList(map.entrySet());
    Collections.sort(
        list,
        new Comparator() {
          public int compare(Object o1, Object o2) {
            return ((Comparable) ((Map.Entry) (o1)).getValue())
                .compareTo(((Map.Entry) (o2)).getValue());
          }
        });

    HashMap sortedHashMap = new LinkedHashMap();
    for (Iterator it = list.iterator(); it.hasNext(); ) {
      Map.Entry entry = (Map.Entry) it.next();
      sortedHashMap.put(entry.getKey(), entry.getValue());
    }
    return sortedHashMap;
  }

  private static HashMap sortdesc(HashMap map) {
    List list = new LinkedList(map.entrySet());
    Collections.sort(
        list,
        new Comparator() {
          public int compare(Object o1, Object o2) {
            return ((Comparable) ((Map.Entry) (o2)).getValue())
                .compareTo(((Map.Entry) (o1)).getValue());
          }
        });

    HashMap sortedHashMap = new LinkedHashMap();
    for (Iterator it = list.iterator(); it.hasNext(); ) {
      Map.Entry entry = (Map.Entry) it.next();
      sortedHashMap.put(entry.getKey(), entry.getValue());
    }
    return sortedHashMap;
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
      sortedMap = sort(finalListActors);
    } else {
      sortedMap = sortdesc(finalListActors);
    }

    ArrayList<Map.Entry<String, Double>> auxList = new ArrayList<>();
    for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
      auxList.add(entry);
    }
    if (super.getActionInputData().getSortType().compareTo("asc") == 0) {
      auxList = bubbleSortasc(auxList);
    } else {
      auxList = bubbleSortdesc(auxList);
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

  ArrayList<Map.Entry<String, Double>> bubbleSortasc(ArrayList<Map.Entry<String, Double>> auxList) {
    int n = auxList.size();
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (auxList.get(j).getValue().compareTo(auxList.get(j + 1).getValue()) == 0) {
          if (auxList.get(j).getKey().compareTo(auxList.get(j + 1).getKey()) > 0) {
            Map.Entry<String, Double> tmp = auxList.get(j);
            auxList.set(j, auxList.get(j + 1));
            auxList.set(j + 1, tmp);
          }
        }
      }
    }
    return auxList;
  }

  ArrayList<Map.Entry<String, Double>> bubbleSortdesc(
      ArrayList<Map.Entry<String, Double>> auxList) {
    int n = auxList.size();
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (auxList.get(j).getValue().compareTo(auxList.get(j + 1).getValue()) == 0) {
          if (auxList.get(j).getKey().compareTo(auxList.get(j + 1).getKey()) < 0) {
            Map.Entry<String, Double> tmp = auxList.get(j);
            auxList.set(j, auxList.get(j + 1));
            auxList.set(j + 1, tmp);
          }
        }
      }
    }
    return auxList;
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
