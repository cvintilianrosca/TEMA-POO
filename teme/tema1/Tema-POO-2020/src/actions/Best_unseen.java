package actions;

import fileio.*;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.*;

public class Best_unseen {

    private Input input;
    private ActionInputData actionInputData;
    private Writer fileWriter;
    private JSONArray arrayResult;

    public Best_unseen(Input input, ActionInputData actionInputData, Writer fileWriter,JSONArray arrayResult){
        this.input=input;
        this.actionInputData= actionInputData;
        this.fileWriter=fileWriter;
        this.arrayResult=arrayResult;
    }

    public StringBuilder executeCommand(){
        String username = actionInputData.getUsername();
        StringBuilder message = new StringBuilder();
        HashMap<String, Double> listMovies = new HashMap<>();
        boolean flag = false;
        boolean flagnonzero=false;
        boolean check=false;
        String aux=null;
        for (UserInputData user: input.getUsers()) {
            if(user.getUsername().compareTo(username) == 0){
                for (MovieInputData movie: input.getMovies()) {
                    if (user.getHistory().get(movie.getTitle()) == null){
//
                        flag=true;
                        if (movie.getTotalRating() != 0.0){
                            flagnonzero=true;
                        }
                        if (check== false){
                            if (movie.getTotalRating()==0){
                                aux=movie.getTitle();
                                check=true;
                            }
                        }
//                        System.out.println(movie.getTitle());
//                        System.out.println(movie.getTotalRating());
                        //return message;
                        listMovies.put(movie.getTitle(), movie.getTotalRating());
                    }
                }
            }
        }
        System.out.println();
        if (flag == false){
            message.append("BestRatedUnseenRecommendation cannot be applied!");
            return message;

        }else{
            if (flagnonzero==false){
                message.append("BestRatedUnseenRecommendation result: ");
                message.append(aux);
//                System.out.println(message);
                return message;
            }else {
             listMovies=sortdesc(listMovies);
                message.append("BestRatedUnseenRecommendation result: ");
                for (Map.Entry<String, Double> entry : listMovies.entrySet()) {
                    message.append(entry.getKey());
//                    System.out.println(message);
                    return message;
                }
            }
        }
        return message;
    }
    public void execute() throws IOException {
        StringBuilder message = executeCommand();
        arrayResult.add(fileWriter.writeFile(Integer.toString(actionInputData.getActionId()), "message:", message.toString()));
    }
    private static HashMap sortdesc(HashMap map) {
        List list = new LinkedList(map.entrySet());
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
