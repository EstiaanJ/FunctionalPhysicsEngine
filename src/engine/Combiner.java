package engine;

import scala.Array$;

import java.util.ArrayList;
import java.util.List;

public class Combiner {
    public static ArrayList<MassEntity> combineLists(ArrayList<MassEntity> listA, ArrayList<MassEntity> listB){
        List<MassEntity> newList = new ArrayList<MassEntity>(listA);
        newList.addAll(listB);

        return (ArrayList<MassEntity>) newList;
    }

    //public static

}
