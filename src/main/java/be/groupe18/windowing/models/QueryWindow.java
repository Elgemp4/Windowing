package be.groupe18.windowing.models;

import be.groupe18.windowing.utils.Tuple;

/**
 * A class representing an x,y coordinates abstracted class which rely on the principle of origin and interval
 * to define the query bounds.
 */
public class QueryWindow {
    private final double originMin;
    private final double originMax;

    private final double intervalMin;
    private final double intervalMax;

    /**
     * Private constructor used within the class to build a QueryWindow
     * @param originMin the minimal origin of the query window
     * @param originMax the maximal origin of the query window
     * @param intervalMin the minimal interval of the query window
     * @param intervalMax the maximal interval of the query window
     */
    private QueryWindow(double originMin, double originMax,
                        double intervalMin, double intervalMax) {
            this.originMin = originMin;
            this.originMax = originMax;
            this.intervalMin = intervalMin;
            this.intervalMax = intervalMax;
    }

    /**
     * Static method that allows the reliable construction of queries windows for vertical and horizontal query windows
     * It abstracts the "conversion" logic of x,y coordinates into the concept of origin and interval for each of them
     * @param minX the minimal x bound of the query window
     * @param maxX the maximal x bound of the query window
     * @param minY the minimal y bound of the query window
     * @param maxY the maximal y bound of the query window
     * @return A {@link Tuple} of {@link QueryWindow} the first one being the vertical query window and the second one
     * being the horizontal query window.
     */
    public static Tuple<QueryWindow, QueryWindow> buildQueryWindows(double minX, double maxX, double minY, double maxY){
        QueryWindow verticalQuery = new QueryWindow(minX, maxX, minY, maxY);
        QueryWindow horizontalQuery = new QueryWindow(minY, maxY, minX, maxX);

        return new Tuple<>(verticalQuery, horizontalQuery);
    }

    /**
     * Tells whether a {@link Segment} is withing the query window
     * @param segment a {@link Segment} for which we wish to check is it's in the query window
     * @return True if the {@link Segment} is in the query window, false otherwise
     */
    public boolean contains(Segment segment) {
        return isOriginInRange(segment.getOrigin()) && isIntervalInRange(segment.getInterval());
    }

    /**
     * Check whether the provided origin is withing the bounds of the query window
     * @param origin A {@link CompositeDouble} representing the origin of a segment
     * @return True if the origin is within the query window bounds, false otherwise
     */
    private boolean isOriginInRange(CompositeDouble origin) {
        boolean lowerBoundOk = (originMin == Double.NEGATIVE_INFINITY) || origin.getAsDouble() >= originMin;
        boolean upperBoundOk = (originMax == Double.POSITIVE_INFINITY) || originMax >= origin.getAsDouble();

        return lowerBoundOk && upperBoundOk;
    }

    public boolean isIntervalInRange(Interval interval) {
        boolean lowerBoundOk = (intervalMin == Double.NEGATIVE_INFINITY) || interval.getIntervalMax().getAsDouble() >= intervalMin;
        boolean upperBoundOk = (intervalMax == Double.POSITIVE_INFINITY) || intervalMax >= interval.getIntervalMin().getAsDouble();
        return lowerBoundOk && upperBoundOk;
    }

    public boolean isIntervalTooSmall(Interval interval) {
        return this.intervalMin > interval.getIntervalMax().getAsDouble();
    }

    public boolean isIntervalTooBig(Interval interval) {
        return interval.getIntervalMin().getAsDouble() > this.intervalMax;
    }


    public double getOriginMin() { return originMin; }
    
    public double getOriginMax() { return originMax; }

    public double getIntervalMin() { return intervalMin; }

    public double getIntervalMax() { return intervalMax; }
}
