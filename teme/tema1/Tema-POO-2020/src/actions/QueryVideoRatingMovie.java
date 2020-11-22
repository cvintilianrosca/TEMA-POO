package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.*;

public class QueryVideoRatingMovie extends AbstractAction {

  public QueryVideoRatingMovie(
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
    HashMap<String, Float> listMovies = new HashMap<String, Float>();
    List<String> years = super.getActionInputData().getFilters().get(0);
    List<String> genres = super.getActionInputData().getFilters().get(1);

    for (MovieInputData movie : super.getInput().getMovies()) {
      Boolean yearFlag = true;
      Boolean genresFlag = false;
      if (years.get(0) != null) {
        for (String year : years) {

          if (year != null)
            if (year.compareTo(String.valueOf(movie.getYear())) != 0) {
              yearFlag = false;
            }
        }
      } else {
        yearFlag = true;
      }
      if (genres.get(0) != null) {
        for (String genre : genres) {
          for (String movieGenre : movie.getGenres()) {
            if (genre != null)
              if (genre.compareTo(movieGenre) == 0) {
                genresFlag = true;
              }
          }
        }
      } else {
        genresFlag = true;
      }

      if (genresFlag == true && yearFlag == true) {
        listMovies.put(movie.getTitle(), (float) movie.getTotalRating());
      }
    }
    HashMap<String, Float> sortedMap;
    if (super.getActionInputData().getSortType().compareTo("asc") == 0) {
      sortedMap = sort(listMovies);
    } else {
      sortedMap = sortdesc(listMovies);
    }

    ArrayList<Map.Entry<String, Float>> auxList = new ArrayList<>();
    for (Map.Entry<String, Float> entry : sortedMap.entrySet()) {
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
    for (Map.Entry<String, Float> entry : auxList) {
      if (i < super.getActionInputData().getNumber()) {
        if (entry.getValue() != 0) {
          message.append(entry.getKey());
          message.append(", ");
          added = true;
        }
      }
      i++;
    }
    if (added == true) {
      message.deleteCharAt(message.length() - 1);
      message.deleteCharAt(message.length() - 1);
    }
    message.append("]");
    System.out.println();
    return message;
  }

  ArrayList<Map.Entry<String, Float>> bubbleSortasc(ArrayList<Map.Entry<String, Float>> auxList) {
    int n = auxList.size();
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (auxList.get(j).getValue().compareTo(auxList.get(j + 1).getValue()) == 0) {
          if (auxList.get(j).getKey().compareTo(auxList.get(j + 1).getKey()) > 0) {
            Map.Entry<String, Float> tmp = auxList.get(j);
            auxList.set(j, auxList.get(j + 1));
            auxList.set(j + 1, tmp);
          }
        }
      }
    }
    return auxList;
  }

  ArrayList<Map.Entry<String, Float>> bubbleSortdesc(ArrayList<Map.Entry<String, Float>> auxList) {
    int n = auxList.size();
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (auxList.get(j).getValue().compareTo(auxList.get(j + 1).getValue()) == 0) {
          if (auxList.get(j).getKey().compareTo(auxList.get(j + 1).getKey()) < 0) {
            Map.Entry<String, Float> tmp = auxList.get(j);
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
