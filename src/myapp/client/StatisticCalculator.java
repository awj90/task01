package myapp.client;

import java.util.List;

// helper class to perform statistical calculations

public class StatisticCalculator {
    
    private List<Float> listOfFloats;
    private int numOfElements;

    // Constructor
    public StatisticCalculator(List<Float> listOfFloats) {
        this.listOfFloats = listOfFloats;
        this.numOfElements = listOfFloats.size();
    }
    
    // Getters
    public List<Float> getListOfFloats() {
        return listOfFloats;
    }

    public int getNumOfElements() {
        return numOfElements;
    }

    // Convert into stream and reduce all elements into a total sum using a lambda accumulator, divided by size of list to get the average
    public float calculateMean() {
        float totalSum = this.listOfFloats.stream()
                            .reduce( 0f, (a, b) -> {
                                return a + b;
                            });
        return totalSum / numOfElements;
    }

    // Standard Deviation = Square root of the mean sum of squared errors
    public float calculateStandardDeviation() {

        float mean = calculateMean();
        float sumOfSquaredErrors = 0f;

        for (float num : listOfFloats) {
            sumOfSquaredErrors += Math.pow(num - mean, 2);
        }

        return (float) Math.sqrt(sumOfSquaredErrors / numOfElements);
    }

}
