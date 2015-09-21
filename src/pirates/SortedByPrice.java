package pirates;

import java.util.Comparator;

/**
 * Created by artem on 8/30/15.
 */
public class SortedByPrice implements Comparator<Purchase> {

    public int compare(Purchase obj1, Purchase obj2) {

        Double price1 = obj1.priceOfGallon;
        Double price2 = obj2.priceOfGallon;

        return price1.compareTo(price2);
    }
}
