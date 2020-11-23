package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.List;

public class CommandInterpretor {
  private final Input input;
  private final Writer fileWriter;
  private final JSONArray arrayResult;

  public CommandInterpretor(final Input input, final Writer fileWriter,
                            final JSONArray arrayResult) {
    this.input = input;
    this.fileWriter = fileWriter;
    this.arrayResult = arrayResult;
  }
  /**
   * Function that check action type, and creates objects in order to solve query,
   * command, recommendations
   * <p>DO NOT MODIFY
   */
  public void interpretCommands() throws IOException {

    List<ActionInputData> list = input.getCommands();
    for (ActionInputData data : list) {
      if (data.getType() != null) {
        if (data.getActionType().compareTo("command") == 0) {
          if (data.getType().compareTo("favorite") == 0) {
            new Favorite(input, data, fileWriter, arrayResult).execute();
          }
          if (data.getType().compareTo("view") == 0) {
            new View(input, data, fileWriter, arrayResult).execute();
          }
          if (data.getType().compareTo("rating") == 0) {
            new Rating(input, data, fileWriter, arrayResult).execute();
          }
        }
        if (data.getType().compareTo("standard") == 0) {
          new Standard(input, data, fileWriter, arrayResult).execute();
        }
        if (data.getType().compareTo("best_unseen") == 0) {
          new BestUnseen(input, data, fileWriter, arrayResult).execute();
        }
        if (data.getType().compareTo("favorite") == 0
            && data.getActionType().compareTo("recommendation") == 0) {
          new FavoriteRecomandation(input, data, fileWriter, arrayResult).execute();
        }
        if (data.getType().compareTo("search") == 0) {
          new Search(input, data, fileWriter, arrayResult).execute();
        }
        if (data.getType().compareTo("popular") == 0) {
          new Popular(input, data, fileWriter, arrayResult).execute();
        }
      }

      if (data.getActionType().compareTo("query") == 0) {
        if (data.getObjectType().compareTo("users") == 0) {
          if (data.getCriteria().compareTo("num_ratings") == 0) {
            new QueryUserRatings(input, data, fileWriter, arrayResult).execute();
          }
        }

        if (data.getObjectType().compareTo("actors") == 0) {
          if (data.getCriteria().compareTo("awards") == 0) {
            new QueryActorsAward(input, data, fileWriter, arrayResult).execute();
          }
          if (data.getCriteria().compareTo("filter_description") == 0) {
            new QueryActorsFilterDescription(input, data, fileWriter, arrayResult).execute();
          }
          if (data.getCriteria().compareTo("average") == 0) {
            new QueryActorsAverageRatings(input, data, fileWriter, arrayResult).execute();
          }
        }
        if (data.getObjectType().compareTo("movies") == 0) {
          if (data.getCriteria().compareTo("longest") == 0) {
            new QueryVideoLongestMovie(input, data, fileWriter, arrayResult).execute();
          }
          if (data.getCriteria().compareTo("favorite") == 0) {
            new QueryVideoFavoriteMovie(input, data, fileWriter, arrayResult).execute();
          }
          if (data.getCriteria().compareTo("most_viewed") == 0) {
            new QueryVideoMostViewedMovie(input, data, fileWriter, arrayResult).execute();
          }
          if (data.getCriteria().compareTo("ratings") == 0) {
            new QueryVideoRatingMovie(input, data, fileWriter, arrayResult).execute();
          }
        }
        if (data.getObjectType().compareTo("shows") == 0) {
          if (data.getCriteria().compareTo("longest") == 0) {
            new QueryVideoLongestShow(input, data, fileWriter, arrayResult).execute();
          }
          if (data.getCriteria().compareTo("favorite") == 0) {
            new QueryVideoFavoriteShow(input, data, fileWriter, arrayResult).execute();
          }
          if (data.getCriteria().compareTo("most_viewed") == 0) {
            new QueryVideoMostViewedShow(input, data, fileWriter, arrayResult).execute();
          }
          if (data.getCriteria().compareTo("ratings") == 0) {
            new QueryVideoRatingShow(input, data, fileWriter, arrayResult).execute();
          }
        }
      }
    }
  }
  /**
   * Function that returns the ArrayResult
   *
   * <p>DO NOT MODIFY
   */
  public JSONArray getArrayResult() {
    return arrayResult;
  }
}
