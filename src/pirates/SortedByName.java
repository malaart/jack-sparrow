package pirates;

import java.util.Comparator;

/**
 * Created by artem on 8/30/15.
 */
public class SortedByName implements Comparator<Purchase> {

    public int compare(Purchase obj1, Purchase obj2) {

        String name1 = obj1.sourceName;
        String name2 = obj2.sourceName;

        return name1.compareTo(name2);
    }
}
