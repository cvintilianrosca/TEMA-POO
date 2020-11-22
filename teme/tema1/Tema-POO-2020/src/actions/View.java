package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.List;

public class View{
    private Input input;
    private ActionInputData actionInputData;
    private Writer fileWriter;
    private JSONArray arrayResult;

    public View(Input input, ActionInputData actionInputData, Writer fileWriter,JSONArray arrayResult ){
        this.input=input;
        this.actionInputData= actionInputData;
        this.fileWriter=fileWriter;
        this.arrayResult=arrayResult;
    }


    public StringBuilder executeCommand(){
        StringBuilder message= new StringBuilder();
        String username = actionInputData.getUsername();
        List<UserInputData> usersList=input.getUsers();
        for (UserInputData user : usersList) {
            if (user.getUsername().compareTo(username)==0){
                if(user.getHistory().get(actionInputData.getTitle()) != null){
                   int views= user.getHistory().get(actionInputData.getTitle());
                   views+=1;
                   user.getHistory().put(actionInputData.getTitle(), views);
                   message.append("success -> ");
                   message.append(actionInputData.getTitle());
                   message.append(" was viewed with total views of ");
                   message.append(views);
                }
                else{
                    user.getHistory().put(actionInputData.getTitle(), 1);
                    message.append("success -> ");
                    message.append(actionInputData.getTitle());
                    message.append(" was viewed with total views of 1");
                }
            }
        }
        return message;
    }

    public void execute() throws IOException{
        StringBuilder message = executeCommand();
        arrayResult.add(fileWriter.writeFile(Integer.toString(actionInputData.getActionId()), "message:", message.toString()));
    }
}
