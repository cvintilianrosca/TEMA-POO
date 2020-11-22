package actions;

import fileio.*;
import org.json.simple.JSONArray;
import sortingstategies.SortingStrategy;
import sortingstategies.SortingStrategyFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Best_unseen extends AbstractAction {

  public Best_unseen(
      Input input, ActionInputData actionInputData, Writer fileWriter, JSONArray arrayResult) {
    super(input, actionInputData, fileWriter, arrayResult);
  }

  public StringBuilder executeCommand() {
    String username = super.getActionInputData().getUsername();
    StringBuilder message = new StringBuilder();
    HashMap<String, Double> listMovies = new HashMap<>();
    boolean flag = false;
    boolean flagnonzero = false;
    boolean check = false;
    String aux = null;
    for (UserInputData user : getInput().getUsers()) {
      if (user.getUsername().compareTo(username) == 0) {
        for (MovieInputData movie : getInput().getMovies()) {
          if (user.getHistory().get(movie.getTitle()) == null) {
            flag = true;
            if (movie.getTotalRating() != 0.0) {
              flagnonzero = true;
            }
            if (check == false) {
              if (movie.getTotalRating() == 0) {
                aux = movie.getTitle();
                check = true;
              }
            }
            listMovies.put(movie.getTitle(), movie.getTotalRating());
          }
        }
      }
    }
    System.out.println();
    if (flag == false) {
      message.append("BestRatedUnseenRecommendation cannot be applied!");
      return message;

    } else {
      if (flagnonzero == false) {
        message.append("BestRatedUnseenRecommendation result: ");
        message.append(aux);
        return message;
      } else {
        SortingStrategy sortingStrategy = SortingStrategyFactory.createStrategy("desc");
        listMovies = sortingStrategy.sortHashMap(listMovies);
        message.append("BestRatedUnseenRecommendation result: ");
        for (Map.Entry<String, Double> entry : listMovies.entrySet()) {
          message.append(entry.getKey());
          return message;
        }
      }
    }
    return message;
  }

  public void execute() throws IOException {
    StringBuilder message = executeCommand();
    getArrayResult()
        .add(
            getFileWriter()
                .writeFile(
                    Integer.toString(getActionInputData().getActionId()),
                    "message:",
                    message.toString()));
  }
}
