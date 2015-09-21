package pirates;

/**
 * Created by artem on 8/29/15.
 */
public class Source extends Purchase {

    Integer minSize;
    Integer stepSize;

    public Source(String name, Integer size, Double price, Integer minSize, Integer stepSize) {
        super(name, size, price);
        this.minSize = minSize;
        this.stepSize = stepSize;
    }

    public Double getStepPrice() {
        return priceOfGallon * stepSize;
    }
}
