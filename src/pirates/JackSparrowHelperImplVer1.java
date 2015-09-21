package pirates;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.NumberFormatException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

public class JackSparrowHelperImplVer1 implements JackSparrowHelper {

    private TreeSet<Purchase> purchases = new TreeSet<Purchase>(new SortedByPrice());
    private TreeSet<Source> sources = new TreeSet<Source>(new SortedByPrice());
    private Double totalAmount = 0.0;
    // private Integer totalGallons = 0;

    public JackSparrowHelperImplVer1() {
    }

    public Purchases helpJackSparrow(String path, int numberOfGallons) {

        loadSourcesFromFile(path);

        Integer gallonsLeft = doShopping(numberOfGallons);

        return new Purchases(purchases);
    }

    private void loadSourcesFromFile(String path) {

        BufferedReader br = null;
        String line = "";

        try {
            br = new BufferedReader(new FileReader(path));

            Boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    // skip the first header line
                    isHeader = false;
                } else {
                    // use semicolon as separator
                    String[] tokens = line.split(";");

                    sources.add(new Source(tokens[0], Integer.valueOf(tokens[1]), Double.valueOf(tokens[2]),
                            Integer.valueOf(tokens[3]), Integer.valueOf(tokens[4])));
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
    }

    private Integer doShopping(Integer numberOfGallons) {

        Integer gallonsBought = 0;
        Integer gallonsLeft = numberOfGallons;
        Boolean done = false;

        Iterator<Source> iterator = sources.iterator();

        while (!done) {

            if (gallonsLeft > 0) {

                if (iterator.hasNext()) {
                    Source source = iterator.next();

                    Integer numberOfLots;

                    //Loops through all the rum sources starting from the cheapest ones
                    if (gallonsLeft > source.minSize) {
                        numberOfLots = Math.min(gallonsLeft / source.stepSize, source.numberOfGallons / source.stepSize);

                        if (numberOfLots > 0) {
                            gallonsBought = numberOfLots * source.stepSize;
                            purchases.add(new Purchase(source.sourceName, gallonsBought, source.priceOfGallon));
                            gallonsLeft = gallonsLeft - gallonsBought;
                            // totalAmount = totalAmount + source.priceOfGallon * gallonsBought;
                            // totalGallons = totalGallons + gallonsBought;
                        }
                    }
                } else {
                    done = true; // no more gallons available
                }
            } else {
                done = true; // no more gallons to be bought - shopping done
            }
        }

        return gallonsLeft; // if >0 then it means there's not enough rum
    }

}

