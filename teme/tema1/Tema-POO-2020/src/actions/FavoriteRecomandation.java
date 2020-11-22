package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;

public class FavoriteRecomandation {
    private Input input;
    private ActionInputData actionInputData;
    private Writer fileWriter;
    private JSONArray arrayResult;

    public FavoriteRecomandation(Input input, ActionInputData actionInputData, Writer fileWriter,JSONArray arrayResult){
        this.input=input;
        this.actionInputData= actionInputData;
        this.fileWriter=fileWriter;
        this.arrayResult=arrayResult;
    }

    public StringBuilder executeCommand(){
        String username = actionInputData.getUsername();
        StringBuilder message = new StringBuilder();
        HashMap<String, Integer> FavoritesMovie = new HashMap<String, Integer>();
        UserInputData auxuser=null;
        for (UserInputData user : input.getUsers()) {
            if (user.getUsername().compareTo(username)==0){
                auxuser=user;
                if (auxuser.getSubscriptionType().compareTo("BASIC")==0){
                    message.append("FavoriteRecommendation cannot be applied!");
                    return message;
                }
            }
            for (String movie : user.getFavoriteMovies()) {
                if(FavoritesMovie.get(movie) == null){
                    FavoritesMovie.put(movie, 1);
                }else{
                    FavoritesMovie.put(movie, (FavoritesMovie.get(movie)+1));
                }
            }
        }

        int max=0;
        String aux=null;
        for (Map.Entry<String, Integer> entry : FavoritesMovie.entrySet()) {
            Boolean flag =false;

            for (String movie : auxuser.getFavoriteMovies())
            {
                if (movie.compareTo(entry.getKey())==0){
                    flag=true;
                    break;
                }
            }

            if (max < entry.getValue()&& flag == false&& auxuser.getHistory().get(entry.getKey())==null){
                max= entry.getValue();
                aux= entry.getKey();
//                System.out.println(aux);
//                System.out.println(max);
            }
        }
//        System.out.println(aux);
//        System.out.println(max);
//        System.out.println(actionInputData.getActionId());
//        System.out.println();
        if (aux!=null){
        message.append("FavoriteRecommendation result: ");
        message.append(aux);
        }
        else {
            message.append("FavoriteRecommendation cannot be applied!");
        }
        return message;
    }



    public void execute() throws IOException {
        StringBuilder message = executeCommand();
        arrayResult.add(fileWriter.writeFile(Integer.toString(actionInputData.getActionId()), "message:", message.toString()));
    }
}
