package pirates;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.NumberFormatException;
import java.util.*;

public class JackSparrowHelperImplVer2 implements JackSparrowHelper {

    private Map<String, Source> sources = new HashMap<String, Source>();

    public JackSparrowHelperImplVer2() { }

    public Purchases helpJackSparrow(String path, int numberOfGallons) {

        Integer totalGallons = loadSourcesFromFile(path);

        Purchases resultingPurchases = new Purchases();

        if (totalGallons - numberOfGallons < 0 ) {
            // we want to buy more rum than available, so we buy all
        }
        else {
            doShopping(numberOfGallons, totalGallons);
        }

        //now the sources collection contains optimal purchase
        for (Source source : sources.values()) {
            if (source.numberOfGallons > 0) //strip the zero purchases out
                resultingPurchases.AddItem(new Purchase(source.sourceName, source.numberOfGallons, source.priceOfGallon));
        }

        return resultingPurchases;
    }

    /**
     * Load the sources from file and returns
     */
    private Integer loadSourcesFromFile(String path) {

        BufferedReader br = null;
        Integer totalGallons = 0;

        try {
            String line = "";
            Integer size, minSize, stepSize;
            Double price;
            String name;
            Boolean isHeader = true;

            br = new BufferedReader(new FileReader(path));

            // iterate through each line in the source file and populate set of sources
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    // skip the first header line
                    isHeader = false;
                } else {
                    // use semicolon as separator
                    String[] tokens = line.split(";");
                    name = tokens[0];
                    size = Integer.valueOf(tokens[1]);
                    price = Double.valueOf(tokens[2]);
                    minSize = Integer.valueOf(tokens[3]);
                    stepSize = Integer.valueOf(tokens[4]);

                    sources.put(name, new Source(name, size, price, minSize, stepSize));
                    totalGallons += size;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return totalGallons;
    }

    /**
     * Find the optimal purchase
     */
    private void doShopping(Integer gallonsToBeBought, Integer totalGallons) {

        // STEP 1 :find the most expensive set that's left on stock after gallonsToBeBought were bought
        // (means the cheapest combination has been bought)
        Integer gallonsLeft = totalGallons - gallonsToBeBought;

        // most expensive combinations for [0..gallonsLeft]
        Purchases[] pc = new Purchases[gallonsLeft+1];
        // initialize
        for (Integer n = 0; n < pc.length; n++) {
            pc[n] = new Purchases();
        }

        // Use dynamic algorithm for finding optimal solution for subtasks 0..gallonsToBeBought
        for (Source src : sources.values()) {
            for (int n = gallonsLeft; n >= 0; n--) {
                if (n >= src.stepSize) {
                    Integer quantity = Math.min(src.numberOfGallons, n);

                    Integer k = src.stepSize;
                    // for (Integer k = src.stepSize; k <= quantity; k+= src.stepSize) {
                    while (k <= quantity) {
                        // System.err.printf("n=%d   k=%d\n", n, k);
                        Purchases smallerSet = pc[n - k];
                        Double testValue = smallerSet.totalAmount + k * src.priceOfGallon;
                        if (testValue > pc[n].totalAmount) {
                           (pc[n] = smallerSet.Copy()).AddItem(new Purchase(src.sourceName, k, src.priceOfGallon));
                        }

                        k += src.stepSize;
                        // since we cannot buy less than minSize, there must be either total size less min size left
                        // or the whole lot left
                        if (k > (src.numberOfGallons - src.minSize) && k < src.numberOfGallons)
                            k = src.numberOfGallons;
                    }
                }
            }
        }

        // Step 2 : now we know what's left on stock after we made the optimal purchase, so we can
        // restore that optimal purchase
        for (Purchase purchase : pc[gallonsLeft].purchases) {
            (sources.get(purchase.sourceName)).numberOfGallons -= purchase.numberOfGallons;
        }
    }
}

