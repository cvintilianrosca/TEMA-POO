package sortingstategies;

import java.util.*;

public class AscendingSortingStrategy implements SortingStrategy {


    @Override
    public HashMap sortHashMap(HashMap map) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(
                list,
                new Comparator() {
                    public int compare(Object o1, Object o2) {
                        return ((Comparable) ((Map.Entry) (o1)).getValue())
                                .compareTo(((Map.Entry) (o2)).getValue());
                    }
                });

        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;

    }

    @Override
    public ArrayList<Map.Entry<String, Double>> bubbleSortForDouble(ArrayList<Map.Entry<String, Double>> auxList) {
        int n = auxList.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (auxList.get(j).getValue().compareTo(auxList.get(j + 1).getValue()) == 0) {
                    if (auxList.get(j).getKey().compareTo(auxList.get(j + 1).getKey()) > 0) {
                        Map.Entry<String, Double> tmp = auxList.get(j);
                        auxList.set(j, auxList.get(j + 1));
                        auxList.set(j + 1, tmp);
                    }
                }
            }
        }
        return auxList;
    }

    @Override
    public ArrayList<Map.Entry<String, Integer>> bubbleSortForInteger(ArrayList<Map.Entry<String, Integer>> auxList) {
        int n = auxList.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (auxList.get(j).getValue().compareTo(auxList.get(j + 1).getValue()) == 0) {
                    if (auxList.get(j).getKey().compareTo(auxList.get(j + 1).getKey()) > 0) {
                        Map.Entry<String, Integer> tmp = auxList.get(j);
                        auxList.set(j, auxList.get(j + 1));
                        auxList.set(j + 1, tmp);
                    }
                }
            }
        }
        return auxList;
    }

    @Override
    public ArrayList<Map.Entry<String, Float>> bubbleSortForFLoat(ArrayList<Map.Entry<String, Float>> auxList) {
        int n = auxList.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (auxList.get(j).getValue().compareTo(auxList.get(j + 1).getValue()) == 0) {
                    if (auxList.get(j).getKey().compareTo(auxList.get(j + 1).getKey()) > 0) {
                        Map.Entry<String, Float> tmp = auxList.get(j);
                        auxList.set(j, auxList.get(j + 1));
                        auxList.set(j + 1, tmp);
                    }
                }
            }
        }
        return auxList;
    }
}
