package sortingstategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SortingStrategy {

  HashMap  sortHashMap(HashMap map);

  ArrayList<Map.Entry<String, Double>> bubbleSortForDouble(
      ArrayList<Map.Entry<String, Double>> auxList);

  ArrayList<Map.Entry<String, Integer>> bubbleSortForInteger(
      ArrayList<Map.Entry<String, Integer>> auxList);

  ArrayList<Map.Entry<String, Float>> bubbleSortForFLoat(
      ArrayList<Map.Entry<String, Float>> auxList);

    }

