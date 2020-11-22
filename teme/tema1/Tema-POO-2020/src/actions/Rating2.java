package actions;

import fileio.*;
import org.json.simple.JSONArray;

import java.io.IOException;

public class Rating2 {
    private Input input;
    private ActionInputData actionInputData;
    private Writer fileWriter;
    private JSONArray arrayResult;

    public Rating2(Input input, ActionInputData actionInputData, Writer fileWriter, JSONArray arrayResult){
        this.input=input;
        this.actionInputData= actionInputData;
        this.fileWriter=fileWriter;
        this.arrayResult=arrayResult;
    }

    public StringBuilder executeCommand(){
       StringBuilder message =new StringBuilder();
        String username = actionInputData.getUsername();
return message;
    }
    public void execute() throws IOException {
        StringBuilder message = executeCommand();
        arrayResult.add(fileWriter.writeFile(Integer.toString(actionInputData.getActionId()), "message:", message.toString()));
    }
}
