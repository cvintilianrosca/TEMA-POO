package actions;

import actor.ActorsAwards;
import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.*;

public class QueryActorsAward extends AbstractAction {

  public QueryActorsAward(
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
    List<String> listAwardsInput = super.getActionInputData().getFilters().get(3);
    ArrayList<ActorsAwards> listAwards = new ArrayList<>();
    for (String award : listAwardsInput) {
      switch (award) {
        case "BEST_PERFORMANCE":
          listAwards.add(ActorsAwards.BEST_PERFORMANCE);
          break;
        case "BEST_DIRECTOR":
          listAwards.add(ActorsAwards.BEST_DIRECTOR);
          break;
        case "PEOPLE_CHOICE_AWARD":
          listAwards.add(ActorsAwards.PEOPLE_CHOICE_AWARD);
          break;
        case "BEST_SUPPORTING_ACTOR":
          listAwards.add(ActorsAwards.BEST_SUPPORTING_ACTOR);
          break;
        case "BEST_SCREENPLAY":
          listAwards.add(ActorsAwards.BEST_SCREENPLAY);
          break;
      }
    }
    HashMap<String, Integer> listActorsAward = new HashMap<String, Integer>();

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
      sortedlist = sortdesc(listActorsAward);
    } else {
      sortedlist = sort(listActorsAward);
    }

    ArrayList<Map.Entry<String, Integer>> auxList = new ArrayList<>();
    for (Map.Entry<String, Integer> entry : sortedlist.entrySet()) {
      //         System.out.println(entry.getKey()); System.out.println(entry.getValue());
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
