package be.groupe18.windowing.models;

/**
 * Represent a wrapper around the interval of a segment
 */
public class Interval {
    private final CompositeDouble intervalMin;
    private final CompositeDouble intervalMax;

    /**
     * Main constructor of the interval class, will sort the two provided interval to determine which one is the
     * min and which one is the max
     * @param firstInterval The first coordinate of the interval
     * @param secondInterval The second coordinate of the interval
     */
    public Interval(CompositeDouble firstInterval, CompositeDouble secondInterval) {
        this.intervalMin = CompositeDouble.min(firstInterval, secondInterval);
        this.intervalMax = CompositeDouble.max(firstInterval, secondInterval);
    }

    /**
     * Getter for the minimal interval
     * @return A CompositeDouble which is the smaller number in the interval
     */
    public CompositeDouble getIntervalMin() {
        return intervalMin;
    }

    /**
     * Getter for the maximal interval
     * @return A CompositeDouble which is the biggest number in the interval
     */
    public CompositeDouble getIntervalMax() {
        return intervalMax;
    }
}
