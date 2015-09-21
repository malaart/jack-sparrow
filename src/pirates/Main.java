package pirates;

public class Main {

    public static void main(String[] args) {

        String path = "resources/sources.csv";
        Integer gallons = 360;

        // JackSparrowHelper helper = new JackSparrowHelperImplVer1();
        JackSparrowHelper helper = new JackSparrowHelperImplVer2();

        Purchases shoppingBasket = helper.helpJackSparrow(path, gallons);

        System.out.format("%15s\t\t%7s\t\t%5s\n","Source", "Gallons", "Price");
        System.out.println("------------------------------------------");

        for (Purchase purchase : shoppingBasket.purchases) {
            System.out.format("%15s\t\t%d\t\t\t%3.2f\n", purchase.sourceName, purchase.numberOfGallons, purchase.priceOfGallon);
        }

        System.out.println("------------------------------------------");
        System.out.format("Total gallons :\t\t%d\n", shoppingBasket.numberOfGallons);
        System.out.format("Average price :\t\t%3.2f\n", shoppingBasket.averagePriceOfGallon);
        System.out.format("Total amount :\t\t%3.2f\n", shoppingBasket.averagePriceOfGallon * shoppingBasket.numberOfGallons);
    }
}
