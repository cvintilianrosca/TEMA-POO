package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.List;

public class Favorite {
   private Input input;
     private ActionInputData actionInputData;
    private Writer fileWriter;
    private JSONArray arrayResult;

    public Favorite(Input input, ActionInputData actionInputData, Writer fileWriter,JSONArray arrayResult){
        this.input=input;
        this.actionInputData= actionInputData;
        this.fileWriter=fileWriter;
        this.arrayResult=arrayResult;
    }

    public StringBuilder executeCommand(){
        StringBuilder message = new StringBuilder();
        String username= actionInputData.getUsername();
        List <UserInputData> usersList=input.getUsers();
        Boolean flag=false;
         if (!usersList.isEmpty()) {
             for (UserInputData userInputData : usersList) {
                 if (userInputData.getUsername().compareTo(username) == 0) {
                     if (userInputData.getHistory().get(actionInputData.getTitle()) == null) {

                         message.append("error -> ");
                         message.append(actionInputData.getTitle());
                         message.append(" is not seen");
                         return message;
                         // break
                     } else {
                         for (String favorite : userInputData.getFavoriteMovies()) {
                             if (favorite.compareTo(actionInputData.getTitle()) == 0) {
                                 message.append("error -> ");
                                 message.append(actionInputData.getTitle());
                                 message.append(" is already in favourite list");
                                 flag = true;
                                 //break
                                 return message;
                             }
                         }
                         if (flag == false) {
                             userInputData.getFavoriteMovies().add(actionInputData.getTitle());
                             message.append("success -> ");
                             message.append(actionInputData.getTitle());
                             message.append(" was added as favourite");
                         }
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
