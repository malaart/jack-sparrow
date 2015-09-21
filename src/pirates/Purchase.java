package pirates;

public class Purchase {
    String sourceName;
    Integer numberOfGallons;
    Double priceOfGallon;

    public Purchase(String name, Integer size, Double price) {
        this.sourceName = name;
        this.numberOfGallons = size;
        this.priceOfGallon = price;
    }
}
