package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FavoriteRecomandation extends AbstractAction {

  public FavoriteRecomandation(
      final Input input, final ActionInputData actionInputData,
      final Writer fileWriter, final JSONArray arrayResult) {
    super(input, actionInputData, fileWriter, arrayResult);
  }

  public StringBuilder executeCommand() {
    String username = super.getActionInputData().getUsername();
    StringBuilder message = new StringBuilder();
    HashMap<String, Integer> FavoritesMovie = new HashMap<>();
    UserInputData auxuser = null;
    for (UserInputData user : super.getInput().getUsers()) {
      if (user.getUsername().compareTo(username) == 0) {
        auxuser = user;
        if (auxuser.getSubscriptionType().compareTo("BASIC") == 0) {
          message.append("FavoriteRecommendation cannot be applied!");
          return message;
        }
      }
      for (String movie : user.getFavoriteMovies()) {
        FavoritesMovie.merge(movie, 1, Integer::sum);
      }
    }

    int max = 0;
    String aux = null;
    for (Map.Entry<String, Integer> entry : FavoritesMovie.entrySet()) {
      boolean flag = false;

      assert auxuser != null;
      for (String movie : auxuser.getFavoriteMovies()) {
        if (movie.compareTo(entry.getKey()) == 0) {
          flag = true;
          break;
        }
      }

      if (max < entry.getValue()
          && !flag
          && auxuser.getHistory().get(entry.getKey()) == null) {
        max = entry.getValue();
        aux = entry.getKey();
      }
    }

    if (aux != null) {
      message.append("FavoriteRecommendation result: ");
      message.append(aux);
    } else {
      message.append("FavoriteRecommendation cannot be applied!");
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
