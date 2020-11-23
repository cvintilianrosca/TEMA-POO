package sortingstategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface SortingStrategy {
  /**
   * Function that sort a HashMap by value
   *
   * <p>DO NOT MODIFY
   */
  HashMap  sortHashMap(HashMap map);
  /**
   * Function that sort a list with
   * Map.Entry<String, Double> by key if value==0
   *
   * <p>DO NOT MODIFY
   */
  ArrayList<Map.Entry<String, Double>> bubbleSortForDouble(
      ArrayList<Map.Entry<String, Double>> auxList);
  /**
   * Function that sort a list with
   * Map.Entry<String, Integer> by key if value==0
   *
   * <p>DO NOT MODIFY
   */
  ArrayList<Map.Entry<String, Integer>> bubbleSortForInteger(
      ArrayList<Map.Entry<String, Integer>> auxList);
  /**
   * Function that sort a list with
   * Map.Entry<String, Float> by key if value==0
   *
   * <p>DO NOT MODIFY
   */
  ArrayList<Map.Entry<String, Float>> bubbleSortForFLoat(
      ArrayList<Map.Entry<String, Float>> auxList);

    }

