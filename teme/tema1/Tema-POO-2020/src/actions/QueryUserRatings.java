package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.*;

public class QueryUserRatings extends AbstractAction {

  public QueryUserRatings(
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
      sortedMap = sort(usersList);
    } else {
      sortedMap = sortdesc(usersList);
    }

    ArrayList<Map.Entry<String, Integer>> auxList = new ArrayList<>();
    for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
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

  ArrayList<Map.Entry<String, Integer>> bubbleSortasc(
      ArrayList<Map.Entry<String, Integer>> auxList) {
    int n = auxList.size();
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (auxList.get(j).getValue().compareTo(auxList.get(j + 1).getValue()) == 0) {
          if (auxList.get(j).getKey().compareTo(auxList.get(j + 1).getKey()) > 0) {
            Map.Entry<String, Integer> tmp = auxList.get(j);
            auxList.set(j, auxList.get(j + 1));
            auxList.set(j + 1, tmp);
          }
        }
      }
    }
    return auxList;
  }

  ArrayList<Map.Entry<String, Integer>> bubbleSortdesc(
      ArrayList<Map.Entry<String, Integer>> auxList) {
    int n = auxList.size();
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (auxList.get(j).getValue().compareTo(auxList.get(j + 1).getValue()) == 0) {
          if (auxList.get(j).getKey().compareTo(auxList.get(j + 1).getKey()) < 0) {
            Map.Entry<String, Integer> tmp = auxList.get(j);
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
