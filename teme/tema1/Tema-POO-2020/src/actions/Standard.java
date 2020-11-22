package actions;

import fileio.*;
import org.json.simple.JSONArray;

import java.io.IOException;

public class Standard {

    private Input input;
    private ActionInputData actionInputData;
    private Writer fileWriter;
    private JSONArray arrayResult;

    public Standard(Input input, ActionInputData actionInputData, Writer fileWriter,JSONArray arrayResult){
        this.input=input;
        this.actionInputData= actionInputData;
        this.fileWriter=fileWriter;
        this.arrayResult=arrayResult;
    }

    public StringBuilder executeCommand(){
        StringBuilder message = new StringBuilder();
        String username = actionInputData.getUsername();
boolean flag=false;
        for (UserInputData user: input.getUsers()) {
            if (user.getUsername().compareTo(username)==0){

                for (MovieInputData movie: input.getMovies()) {
                    if (user.getHistory().get(movie.getTitle()) == null){
                        message.append("StandardRecommendation result: ");
                        message.append(movie.getTitle());
                        flag=true;
                        return message;
                    }
                }
            }
        }
        if (flag == false){
            message.append("StandardRecommendation cannot be applied!");
        }
       return message;
    }

    public void execute() throws IOException {
        StringBuilder message = executeCommand();
        arrayResult.add(fileWriter.writeFile(Integer.toString(actionInputData.getActionId()), "message:", message.toString()));
    }
}
