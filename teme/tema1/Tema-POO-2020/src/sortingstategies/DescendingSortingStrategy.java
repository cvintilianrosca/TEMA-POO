package sortingstategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DescendingSortingStrategy implements SortingStrategy{

    @Override
    public HashMap sortHashMap(final HashMap map) {
        List<Map.Entry> list;
        list = new LinkedList<Map.Entry>(map.entrySet());
        list.sort((o1, o2) -> ((Comparable) o2.getValue())
                .compareTo(o1.getValue()));

        HashMap<Object, Object> sortedHashMap = new LinkedHashMap<>();
        for (Map.Entry entry : list) {
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;

    }

    @Override
    public ArrayList<Map.Entry<String, Double>>
    bubbleSortForDouble(final ArrayList<Map.Entry<String, Double>> auxList) {
        int n = auxList.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (auxList.get(j).getValue().compareTo(auxList.get(j + 1).getValue()) == 0) {
                    if (auxList.get(j).getKey().compareTo(auxList.get(j + 1).getKey()) < 0) {
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
    public ArrayList<Map.Entry<String, Integer>>
    bubbleSortForInteger(final ArrayList<Map.Entry<String, Integer>> auxList) {
        int n = auxList.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (auxList.get(j).getValue().compareTo(auxList.get(j + 1).getValue()) == 0) {
                    if (auxList.get(j).getKey().compareTo(auxList.get(j + 1).getKey()) < 0) {
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
    public ArrayList<Map.Entry<String, Float>>
    bubbleSortForFLoat(final ArrayList<Map.Entry<String, Float>> auxList) {
        int n = auxList.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (auxList.get(j).getValue().compareTo(auxList.get(j + 1).getValue()) == 0) {
                    if (auxList.get(j).getKey().compareTo(auxList.get(j + 1).getKey()) < 0) {
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
