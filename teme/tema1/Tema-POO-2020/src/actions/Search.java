package actions;

import fileio.*;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Search {
    private Input input;
    private ActionInputData actionInputData;
    private Writer fileWriter;
    private JSONArray arrayResult;
    HashMap<String, Double> listMovies= new HashMap<String, Double>();

    public Search(Input input, ActionInputData actionInputData, Writer fileWriter,JSONArray arrayResult){
        this.input=input;
        this.actionInputData= actionInputData;
        this.fileWriter=fileWriter;
        this.arrayResult=arrayResult;
    }
    public StringBuilder executeCommand(){
        StringBuilder message = new StringBuilder();
        String username = actionInputData.getUsername();
        Boolean flag=false;
        for (UserInputData user : input.getUsers()) {
            if (user.getUsername().compareTo(username)==0){
                for (MovieInputData movie: input.getMovies()) {
                    for (String genre : movie.getGenres()) {
                        if (genre.compareTo(actionInputData.getGenre())==0){
                            flag=true;
                            if (user.getHistory().get(movie.getTitle())==null){
                                listMovies.put(movie.getTitle(), (double)movie.getTotalRating());
                            }
                        }
                    }
                }

                for (SerialInputData serial: input.getSerials()) {
                    for (String genre : serial.getGenres()) {
                        if (genre.compareTo(actionInputData.getGenre())==0){
                            flag=true;
                            if (user.getHistory().get(serial.getTitle())==null){
                                listMovies.put(serial.getTitle(), serial.getTotalRatings());
                            }
                        }
                    }
                }

            }
        }
        if (flag == false || listMovies.isEmpty()){
            message.append("SearchRecommendation cannot be applied!");
            return message;
        }
        ArrayList<String> finalList = new ArrayList<>();

        for (Map.Entry<String, Double> entry: listMovies.entrySet()) {
           finalList.add(entry.getKey());
        }
       Collections.sort(finalList);
        message.append("SearchRecommendation result: [");
        flag=false;
        for (String element : finalList) {
            message.append(element).append(", ");
            flag=true;
        }
       if (flag){
           message.deleteCharAt(message.length()-1);
           message.deleteCharAt(message.length()-1);
       }
       message.append("]");

        return message;
    }

    public void execute() throws IOException {
        StringBuilder message = executeCommand();
        arrayResult.add(fileWriter.writeFile(Integer.toString(actionInputData.getActionId()), "message:", message.toString()));
    }
}
