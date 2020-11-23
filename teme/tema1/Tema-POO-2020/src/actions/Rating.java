package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;

public class Rating extends AbstractAction {

  public Rating(
      final Input input, final ActionInputData actionInputData,
      final Writer fileWriter, final JSONArray arrayResult) {
    super(input, actionInputData, fileWriter, arrayResult);
  }
  /**
   * Function that compute the command Rating, modifies the database,
   * build the result message and returns it
   *
   * <p>DO NOT MODIFY
   */
  public StringBuilder executeCommand() {
    StringBuilder message = new StringBuilder();
    String username = super.getActionInputData().getUsername();
    for (UserInputData user : super.getInput().getUsers()) {
      if (user.getUsername().compareTo(username) == 0) {
        if (super.getActionInputData().getSeasonNumber() == 0) {

          if (user.getHistory().get(super.getActionInputData().getTitle()) != null) {
            if (user.getMovieRatings().get(super.getActionInputData().getTitle()) == null) {
              user.getMovieRatings()
                  .put(
                      super.getActionInputData().getTitle(),
                      (float) super.getActionInputData().getGrade());
              for (MovieInputData movie : super.getInput().getMovies()) {
                if (movie.getTitle().compareTo(super.getActionInputData().getTitle()) == 0) {
                  movie.getRatingList().add(super.getActionInputData().getGrade());
                }
              }
              message.append("success -> ");
              message.append(super.getActionInputData().getTitle());
              message.append(" was rated with ");
              message.append(super.getActionInputData().getGrade());
              message.append(" by ");
              message.append(username);
            } else {
              message.append("error -> ");
              message.append(super.getActionInputData().getTitle());
              message.append(" has been already rated");
            }
          } else {
            message.append("error -> ");
            message.append(super.getActionInputData().getTitle());
            message.append(" is not seen");
          }

        } else {
          if (user.getHistory().get(super.getActionInputData().getTitle()) != null) {
            boolean flag = false;
            if (!(user.getShowRatingsArrayList().isEmpty())) {
              // System.out.println("Abracadabra");
              for (UserInputData.ShowRatings showRating : user.getShowRatingsArrayList()) {
                if (showRating.getTitle().compareTo(super.getActionInputData().getTitle()) == 0) {
                  // System.out.println(showRating.getTitle());
                  flag = true;
                  // System.out.println("yes");
                  if (showRating
                          .getSeasonRatings()
                          .get(super.getActionInputData().getSeasonNumber())
                      != null) {
                    // System.out.println("yep");
                    message.append("error -> ");
                    message.append(super.getActionInputData().getTitle());
                    message.append(" has been already rated");
                  } else {

                    for (SerialInputData serial : super.getInput().getSerials()) {
                      if (serial.getTitle().compareTo(super.getActionInputData().getTitle()) == 0) {
                        serial
                            .getSeasons()
                            .get(super.getActionInputData().getSeasonNumber() - 1)
                            .getRatings()
                            .add(super.getActionInputData().getGrade());
                      }
                    }
                    showRating
                        .getSeasonRatings()
                        .put(
                            super.getActionInputData().getSeasonNumber(),
                            (float) super.getActionInputData().getGrade());
                    message.append("success -> ");
                    message.append(super.getActionInputData().getTitle());
                    message.append(" was rated with ");
                    message.append(super.getActionInputData().getGrade());
                    message.append(" by ");
                    message.append(username);
                  }
                }
              }
            }

            if (!flag) {
              // System.out.println("ahaha");
              for (SerialInputData serial : super.getInput().getSerials()) {
                if (serial.getTitle().compareTo(super.getActionInputData().getTitle()) == 0) {
                  serial
                      .getSeasons()
                      .get(super.getActionInputData().getSeasonNumber() - 1)
                      .getRatings()
                      .add(super.getActionInputData().getGrade());
                }
              }
              UserInputData.ShowRatings newRating =
                      new UserInputData.ShowRatings(
                              super.getActionInputData().getTitle(),
                              super.getActionInputData().getSeasonNumber(),
                              (float) super.getActionInputData().getGrade());
              user.getShowRatingsArrayList().add(newRating);
              message.append("success -> ");
              message.append(super.getActionInputData().getTitle());
              message.append(" was rated with ");
              message.append(super.getActionInputData().getGrade());
              message.append(" by ");
              message.append(username);
            }

          } else {
            message.append("error -> ");
            message.append(super.getActionInputData().getTitle());
            message.append(" is not seen");
          }
        }
      }
    }
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
