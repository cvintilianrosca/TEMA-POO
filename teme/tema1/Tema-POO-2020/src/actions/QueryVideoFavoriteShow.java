package actions;

import entertainment.Season;
import fileio.*;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.*;

public class QueryVideoFavoriteShow {
    private Input input;
    private ActionInputData actionInputData;
    private Writer fileWriter;
    private JSONArray arrayResult;

    public QueryVideoFavoriteShow(Input input, ActionInputData actionInputData, Writer fileWriter,JSONArray arrayResult){
        this.input=input;
        this.actionInputData= actionInputData;
        this.fileWriter=fileWriter;
        this.arrayResult=arrayResult;
    }

    public StringBuilder executeCommand() {
        StringBuilder message = new StringBuilder();
        List<String> years = actionInputData.getFilters().get(0);
        List<String> genres = actionInputData.getFilters().get(1);
        HashMap<String, Integer> listShows= new HashMap<String, Integer>();

        for (SerialInputData show: input.getSerials()) {
            Boolean yearFlag = true;
            Boolean genresFlag= false;

            if (!(years.isEmpty())){
                for (String year: years) {

                    if (year != null)
                        if (year.compareTo(String.valueOf(show.getYear()))!=0){
                            yearFlag=false;
                        }
                }
            }

            if (!genres.isEmpty()){
                for (String genre : genres){
                    for (String movieGenre: show.getGenres()) {
                        if (genre!= null)
                            if (genre.compareTo(movieGenre)== 0){
                                genresFlag=true;
//                            System.out.println(movie.getTitle());
                                break;
                            }
                    }
                }
            }

            if (genresFlag==true && yearFlag == true){
                if (!show.getSeasons().isEmpty()){
                    Season aux = show.getSeasons().get(0);
                    listShows.put(show.getTitle(), 0);
                   // System.out.println(show.getTitle());
                }
            }
        }
        for (UserInputData user : input.getUsers()) {
            for (String favorite: user.getFavoriteMovies()) {
                if (listShows.get(favorite)!= null){
                    listShows.put(favorite, listShows.get(favorite)+1);
                }
            }
        }


        HashMap<String, Integer> sortedMap;
        if (actionInputData.getSortType().compareTo("asc")==0){
            sortedMap=sort(listShows);
        }else {
            sortedMap=sortdesc(listShows);
        }

        message.append("Query result: [");
        int i=0;
        Boolean added = false;
        for (Map.Entry<String, Integer> entry: sortedMap.entrySet()) {
            if (i<actionInputData.getNumber()){
                if (entry.getValue()!= 0) {
                    message.append(entry.getKey());
                    message.append(", ");
                    added = true;
                }
            }
            i++;
        }
        if (added==true) {
            message.deleteCharAt(message.length() - 1);
            message.deleteCharAt(message.length() - 1);
        }
        message.append("]");


        return message;
    }


    private static HashMap sort(HashMap map) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
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


    public void execute() throws IOException {
        StringBuilder message = executeCommand();
        arrayResult.add(fileWriter.writeFile(Integer.toString(actionInputData.getActionId()), "message:", message.toString()));
    }
}
