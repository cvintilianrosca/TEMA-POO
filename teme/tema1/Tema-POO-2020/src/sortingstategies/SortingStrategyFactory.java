package sortingstategies;

import java.util.HashMap;

public class SortingStrategyFactory {

    static public SortingStrategy createStrategy(String strategyType){

        if (strategyType.compareTo("asc")==0){
            return new AscendingSortingStrategy();
        }else{
            return new DescendingSortingStrategy();
        }
    }
}
