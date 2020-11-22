package actions;

import fileio.*;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.*;

public class QueryVideoMostViewedMovie {


    private Input input;
    private ActionInputData actionInputData;
    private Writer fileWriter;
    private JSONArray arrayResult;

    public QueryVideoMostViewedMovie(Input input, ActionInputData actionInputData, Writer fileWriter,JSONArray arrayResult){
        this.input=input;
        this.actionInputData= actionInputData;
        this.fileWriter=fileWriter;
        this.arrayResult=arrayResult;
    }

    public StringBuilder executeCommand(){

        StringBuilder message = new StringBuilder();
        HashMap<String, Integer> listMovies= new HashMap<String, Integer>();
        List<String> years = actionInputData.getFilters().get(0);
        List<String> genres = actionInputData.getFilters().get(1);

        for (MovieInputData movie: input.getMovies()) {
            Boolean yearFlag = true;
            Boolean genresFlag= false;

            if (years.get(0)!=null){
                    for (String year: years) {
                            if (year.compareTo(String.valueOf(movie.getYear()))!=0){
                                yearFlag=false;
                            }
                    }
                }else {
//                System.out.println("sds");
                yearFlag=true;
            }
            if (genres.get(0)!=null ){
                    for (String genre : genres){
                        for (String movieGenre: movie.getGenres()) {
                                if (genre.compareTo(movieGenre)== 0){
                                    genresFlag=true;
                                }
                        }
                    }

            }else {
//                System.out.println("fuufuf");
                genresFlag=true;
            }
//            System.out.println(movie.getTitle());
            if (genresFlag==true && yearFlag == true){
                listMovies.put(movie.getTitle(), 0);
            }

        }

        for (UserInputData user : input.getUsers()) {
            for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
                if (listMovies.get(entry.getKey()) != null){
                    listMovies.put(entry.getKey(), listMovies.get(entry.getKey())+entry.getValue());
                }
            }
        }

        HashMap<String, Integer> sortedMap;
        if (actionInputData.getSortType().compareTo("asc")==0){
            sortedMap=sort(listMovies);
        }else {
            sortedMap=sortdesc(listMovies);
        }



        ArrayList<Map.Entry<String, Integer>> auxList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry: sortedMap.entrySet()) {
//         System.out.println(entry.getKey()); System.out.println(entry.getValue());
            auxList.add(entry);


        }
//            System.out.println("kikiki");
        if (actionInputData.getSortType().compareTo("asc")==0) {
            auxList=bubbleSortasc(auxList);
        }else{
            auxList=bubbleSortdesc(auxList);
        }


        message.append("Query result: [");
        int i=0;
        Boolean added = false;
        for (Map.Entry<String,Integer> entry: auxList) {
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


    ArrayList<Map.Entry<String, Integer>> bubbleSortasc(ArrayList<Map.Entry<String, Integer>> auxList)
    {
        int n = auxList.size();
        for (int i = 0; i < n-1; i++){
            for (int j = 0; j < n-i-1; j++){
                if (auxList.get(j).getValue().compareTo(auxList.get(j+1).getValue())==0)
                {
                    if (auxList.get(j).getKey().compareTo(auxList.get(j+1).getKey())>0){
                        Map.Entry<String, Integer> tmp = auxList.get(j);
                        auxList.set(j,auxList.get(j+1));
                        auxList.set(j+1, tmp);
                    }
                }
            }
        }
        return auxList;
    }
    ArrayList<Map.Entry<String, Integer>> bubbleSortdesc(ArrayList<Map.Entry<String, Integer>> auxList)
    {
        int n = auxList.size();
        for (int i = 0; i < n-1; i++){
            for (int j = 0; j < n-i-1; j++){
                if (auxList.get(j).getValue().compareTo(auxList.get(j+1).getValue())==0)
                {
                    if (auxList.get(j).getKey().compareTo(auxList.get(j+1).getKey())<0){
                        Map.Entry<String, Integer> tmp = auxList.get(j);
                        auxList.set(j,auxList.get(j+1));
                        auxList.set(j+1, tmp);
                    }
                }
            }
        }
        return auxList;
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
