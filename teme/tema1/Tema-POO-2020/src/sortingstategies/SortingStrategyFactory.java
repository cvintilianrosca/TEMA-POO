package sortingstategies;

public class SortingStrategyFactory {

  public static SortingStrategy createStrategy(final String strategyType) {

    if (strategyType.compareTo("asc") == 0) {
      return new AscendingSortingStrategy();
    } else {
      return new DescendingSortingStrategy();
    }
  }
}
