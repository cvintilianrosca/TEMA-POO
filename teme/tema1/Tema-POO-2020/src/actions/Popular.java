package actions;

import fileio.*;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.*;

public class Popular extends AbstractAction {

  public Popular(
      Input input, ActionInputData actionInputData, Writer fileWriter, JSONArray arrayResult) {
    super(input, actionInputData, fileWriter, arrayResult);
  }

  private static HashMap sortByValues(HashMap map) {
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
    String username = super.getActionInputData().getUsername();
    StringBuilder message = new StringBuilder();
    HashMap<String, Integer> genresViews = new HashMap<String, Integer>();
    UserInputData userAux = null;

    for (UserInputData user : super.getInput().getUsers()) {
      if (user.getUsername().compareTo(username) == 0) {
        userAux = user;
        if (userAux.getSubscriptionType().compareTo("BASIC") == 0) {
          message.append("PopularRecommendation cannot be applied!");
          return message;
        }
      }
      for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
        for (MovieInputData movie : super.getInput().getMovies()) {
          if (entry.getKey().compareTo(movie.getTitle()) == 0) {
            for (String genre : movie.getGenres()) {
              if (genresViews.containsKey(genre)) {
                genresViews.put(genre, genresViews.get(genre) + entry.getValue());
              } else {
                genresViews.put(genre, entry.getValue());
              }
            }
          }
        }
        for (SerialInputData serial : super.getInput().getSerials()) {
          if (entry.getKey().compareTo(serial.getTitle()) == 0) {
            for (String genre : serial.getGenres()) {
              if (genresViews.containsKey(genre)) {
                genresViews.put(genre, genresViews.get(genre) + entry.getValue());
              } else {
                genresViews.put(genre, entry.getValue());
              }
            }
          }
        }
      }
    }

    HashMap<String, Integer> sortedGenreViews = sortByValues(genresViews);
    boolean flag = false;
    for (Map.Entry<String, Integer> entry : sortedGenreViews.entrySet()) {

      for (MovieInputData movie : super.getInput().getMovies()) {
        for (String genre : movie.getGenres()) {
          if (genre.compareTo(entry.getKey()) == 0) {
            if (userAux.getHistory().get(movie.getTitle()) == null) {
              message.append("PopularRecommendation result: ");
              message.append(movie.getTitle());
              flag = true;
              return message;
            }
          }
        }
      }
    }
    for (Map.Entry<String, Integer> entry : sortedGenreViews.entrySet()) {
      for (SerialInputData serial : super.getInput().getSerials()) {
        for (String genre : serial.getGenres()) {
          if (genre.compareTo(entry.getKey()) == 0) {
            if (userAux.getHistory().get(serial.getTitle()) == null) {
              message.append("PopularRecommendation result: ");
              message.append(serial.getTitle());
              flag = true;
              return message;
            }
          }
        }
      }
    }

    if (flag == false) {
      message.append("PopularRecommendation cannot be applied!");
    }
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
