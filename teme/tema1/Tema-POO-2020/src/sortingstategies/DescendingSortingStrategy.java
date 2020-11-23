package sortingstategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DescendingSortingStrategy implements SortingStrategy {
  /**
   * Function that sort descending a HashMap by value
   *
   * <p>DO NOT MODIFY
   */
  @Override
  public HashMap sortHashMap(final HashMap map) {
    List<Map.Entry> list;
    list = new LinkedList<Map.Entry>(map.entrySet());
    list.sort((o1, o2) -> ((Comparable) o2.getValue()).compareTo(o1.getValue()));

    HashMap<Object, Object> sortedHashMap = new LinkedHashMap<>();
    for (Map.Entry entry : list) {
      sortedHashMap.put(entry.getKey(), entry.getValue());
    }
    return sortedHashMap;
  }
  /**
   * Function that sort descending a list with Map.Entry<String, Double> by key if value==0
   *
   * <p>DO NOT MODIFY
   */
  @Override
  public ArrayList<Map.Entry<String, Double>> sortForDouble(
      final ArrayList<Map.Entry<String, Double>> auxList) {
    auxList.sort(
        (o1, o2) -> {
          if (o1.getValue().compareTo(o2.getValue()) == 0) {
            return o2.getKey().compareTo(o1.getKey());
          }
          return 0;
        });
    return auxList;
  }
  /**
   * Function that sort descending a list with Map.Entry<String, Integer> by key if value==0
   *
   * <p>DO NOT MODIFY
   */
  @Override
  public ArrayList<Map.Entry<String, Integer>> sortForInteger(
      final ArrayList<Map.Entry<String, Integer>> auxList) {
    auxList.sort(
        (o1, o2) -> {
          if (o1.getValue().compareTo(o2.getValue()) == 0) {
            return o2.getKey().compareTo(o1.getKey());
          }
          return 0;
        });
    return auxList;
  }
  /**
   * Function that sort descending a list with Map.Entry<String, Float> by key if value==0
   *
   * <p>DO NOT MODIFY
   */
  @Override
  public ArrayList<Map.Entry<String, Float>> sortForFLoat(
      final ArrayList<Map.Entry<String, Float>> auxList) {
    auxList.sort(
        (o1, o2) -> {
          if (o1.getValue().compareTo(o2.getValue()) == 0) {
            return o2.getKey().compareTo(o1.getKey());
          }
          return 0;
        });
    return auxList;
  }
}
