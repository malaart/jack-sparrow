package pirates;

import java.util.Collection;
import java.util.TreeSet;

public class Purchases {
    Integer numberOfGallons = 0;
    Double totalAmount = 0.0;
    TreeSet<Purchase> purchases;
    Double averagePriceOfGallon = 0.0;

    public Purchases() {
        purchases = new TreeSet<Purchase>(new SortedByPrice());
    }

    public Purchases(Collection<Purchase> purchases) {
        this();
        this.purchases.addAll(purchases);

        for (Purchase purchase : purchases) {
            totalAmount += purchase.priceOfGallon * purchase.numberOfGallons;
            numberOfGallons += purchase.numberOfGallons;
        }

        this.averagePriceOfGallon = numberOfGallons == 0 ? 0 : totalAmount / numberOfGallons;
    }

    public void AddItem(Purchase item) {
        purchases.add(item);
        numberOfGallons += item.numberOfGallons;
        totalAmount += item.priceOfGallon * item.numberOfGallons;
        averagePriceOfGallon = numberOfGallons == 0 ? 0 : totalAmount / numberOfGallons;
    }

    public Purchases Copy() {
        return new Purchases(this.purchases);
    }
}
