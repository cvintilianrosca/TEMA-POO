package actions;

import entertainment.Season;
import fileio.*;
import org.json.simple.JSONArray;

import java.io.IOException;

public class Rating {
    private Input input;
    private ActionInputData actionInputData;
    private Writer fileWriter;
    private JSONArray arrayResult;

    public Rating(Input input, ActionInputData actionInputData, Writer fileWriter,JSONArray arrayResult){
        this.input=input;
        this.actionInputData= actionInputData;
        this.fileWriter=fileWriter;
        this.arrayResult=arrayResult;
    }

    public StringBuilder executeCommand(){
       StringBuilder message =new StringBuilder();
        String username = actionInputData.getUsername();
        for (UserInputData user : input.getUsers()) {
            if (user.getUsername().compareTo(username)==0){
                if (actionInputData.getSeasonNumber()==0) {

                    if (user.getHistory().get(actionInputData.getTitle()) != null){
                        if (user.getMovieRatings().get(actionInputData.getTitle()) == null) {
                            user.getMovieRatings().put(actionInputData.getTitle(), (float) actionInputData.getGrade());
                            for (MovieInputData movie: input.getMovies()) {
                                if (movie.getTitle().compareTo(actionInputData.getTitle())==0){
                                    movie.getRatingList().add(actionInputData.getGrade());
                                }
                            }
                            message.append("success -> ");
                            message.append(actionInputData.getTitle());
                            message.append(" was rated with ");
                            message.append(actionInputData.getGrade());
                            message.append(" by ");
                            message.append(username);
                        } else {
                            message.append("error -> ");
                            message.append(actionInputData.getTitle());
                            message.append(" has been already rated");
                        }
                }else{
                        message.append("error -> ");
                        message.append(actionInputData.getTitle());
                        message.append(" is not seen");
                    }

                }
                else{
                    if (user.getHistory().get(actionInputData.getTitle()) != null) {
                        Boolean flag = false;
                        if (!(user.getShowRatingsArrayList().isEmpty())) {
                            //System.out.println("Abracadabra");
                            for (UserInputData.showRatings showRating : user.getShowRatingsArrayList()) {
                                if (showRating.getTitle().compareTo(actionInputData.getTitle()) == 0) {
                                    // System.out.println(showRating.getTitle());
                                    // System.out.println(showRating.getSeasonRatings().get(actionInputData.getSeasonNumber()));
                                    flag = true;
                                    // System.out.println("yes");
                                    if (showRating.getSeasonRatings().get(actionInputData.getSeasonNumber()) != null) {
                                        //System.out.println("yep");
                                        message.append("error -> ");
                                        message.append(actionInputData.getTitle());
                                        message.append(" has been already rated");
                                    } else {
                                       // System.out.println("AAAAAAAAAA");
                                        for (SerialInputData serial: input.getSerials()) {
                                            if (serial.getTitle().compareTo(actionInputData.getTitle())==0){
                                               // System.out.println("AAAAAAAAAAA");
                                                serial.getSeasons().get(actionInputData.getSeasonNumber()-1).getRatings().add(actionInputData.getGrade());
                                            }
                                        }
                                        showRating.getSeasonRatings().put(actionInputData.getSeasonNumber(), (float) actionInputData.getGrade());
                                        message.append("success -> ");
                                        message.append(actionInputData.getTitle());
                                        message.append(" was rated with ");
                                        message.append(actionInputData.getGrade());
                                        message.append(" by ");
                                        message.append(username);
                                    }
                                }
                            }
                        }

                        if (flag == false) {
                            // System.out.println("ahaha");
                            for (SerialInputData serial: input.getSerials()) {
                                if (serial.getTitle().compareTo(actionInputData.getTitle())==0){
                                    serial.getSeasons().get(actionInputData.getSeasonNumber()-1).getRatings().add(actionInputData.getGrade());
                                }
                            }
                            UserInputData.showRatings newRating = user.new showRatings(actionInputData.getTitle(), actionInputData.getSeasonNumber(), (float) actionInputData.getGrade());
                            user.getShowRatingsArrayList().add(newRating);
                            message.append("success -> ");
                            message.append(actionInputData.getTitle());
                            message.append(" was rated with ");
                            message.append(actionInputData.getGrade());
                            message.append(" by ");
                            message.append(username);
                        }

                    }
                    else{
                        message.append("error -> ");
                        message.append(actionInputData.getTitle());
                        message.append(" is not seen");
                    }
                }
            }
        }
        return message;
    }
    public void execute() throws IOException {
        StringBuilder message = executeCommand();
        arrayResult.add(fileWriter.writeFile(Integer.toString(actionInputData.getActionId()), "message:", message.toString()));
    }
}
