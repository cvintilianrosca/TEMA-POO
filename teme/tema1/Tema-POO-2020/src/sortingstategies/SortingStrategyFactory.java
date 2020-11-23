package sortingstategies;

public final class SortingStrategyFactory {

  private SortingStrategyFactory() {

  }
  /**
   * Function that creates an instance of SortingStrategy depending by strategyType
   *
   * <p>DO NOT MODIFY
   */
  public static SortingStrategy createStrategy(final String strategyType) {

    if (strategyType.compareTo("asc") == 0) {
      return new AscendingSortingStrategy();
    } else {
      return new DescendingSortingStrategy();
    }
  }
}
