package actions;

import fileio.*;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.*;

public class Popular {


    private Input input;
    private ActionInputData actionInputData;
    private Writer fileWriter;
    private JSONArray arrayResult;

    public Popular(Input input, ActionInputData actionInputData, Writer fileWriter,JSONArray arrayResult){
        this.input=input;
        this.actionInputData= actionInputData;
        this.fileWriter=fileWriter;
        this.arrayResult=arrayResult;
    }
    public StringBuilder executeCommand(){
        String username = actionInputData.getUsername();
        StringBuilder message = new StringBuilder();
        HashMap<String, Integer> genresViews = new HashMap<String, Integer>();
        UserInputData userAux=null;

        for (UserInputData user : input.getUsers()) {
            if (user.getUsername().compareTo(username)==0){
                userAux=user;
                if (userAux.getSubscriptionType().compareTo("BASIC")==0){
                    message.append("PopularRecommendation cannot be applied!");
                    return message;
                }
            }
            for (Map.Entry<String,Integer> entry : user.getHistory().entrySet()) {
                for (MovieInputData movie : input.getMovies()) {
                    if (entry.getKey().compareTo(movie.getTitle())==0){
                        for (String genre : movie.getGenres()) {
                            if (genresViews.containsKey(genre)){
                                genresViews.put(genre, genresViews.get(genre)+ entry.getValue());
                            }else{
                                genresViews.put(genre, entry.getValue());
                            }
                        }
                    }
                }
                for (SerialInputData serial : input.getSerials()) {
                    if (entry.getKey().compareTo(serial.getTitle())==0){
                        for (String genre : serial.getGenres()) {
                            if (genresViews.containsKey(genre)){
                                genresViews.put(genre, genresViews.get(genre)+ entry.getValue());
                            }else{
                                genresViews.put(genre, entry.getValue());
                            }
                        }
                    }
                }
            }
        }


        HashMap<String, Integer> sortedGenreViews = sortByValues(genresViews);
boolean flag = false;
        for (Map.Entry<String, Integer> entry : sortedGenreViews.entrySet()) {

            for (MovieInputData movie: input.getMovies()) {
                for (String genre: movie.getGenres()) {
                    if (genre.compareTo(entry.getKey())==0){
                        if (userAux.getHistory().get(movie.getTitle())==null){
                            message.append("PopularRecommendation result: ");
                            message.append(movie.getTitle());
                            flag=true;
                            return message;
                        }
                    }
                }
            }
        }
        for (Map.Entry<String, Integer> entry : sortedGenreViews.entrySet()) {
            for (SerialInputData serial : input.getSerials()) {
                for (String genre: serial.getGenres()) {
                    if (genre.compareTo(entry.getKey())==0){
                        if (userAux.getHistory().get(serial.getTitle())==null){
                            message.append("PopularRecommendation result: ");
                            message.append(serial.getTitle());
                            flag=true;
                            return message;
                        }
                    }
                }
            }
        }

        if (flag == false){
            message.append("PopularRecommendation cannot be applied!");
        }
        return message;
    }




    public void execute() throws IOException {
        StringBuilder message = executeCommand();
        arrayResult.add(fileWriter.writeFile(Integer.toString(actionInputData.getActionId()), "message:", message.toString()));
    }

    private static HashMap sortByValues(HashMap map) {
        List list = new LinkedList(map.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue())
                        .compareTo(((Map.Entry) (o1)).getValue());
            }
        });

        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }

}
