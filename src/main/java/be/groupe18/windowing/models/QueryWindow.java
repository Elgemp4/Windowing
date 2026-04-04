package be.groupe18.windowing.models;

import be.groupe18.windowing.utils.Pair;

import javax.management.Query;

public class QueryWindow {
    private final double originMin;
    private final double originMax;

    private final double intervalMin;
    private final double intervalMax;

    private QueryWindow(double originMin, double originMax,
                        double intervalMin, double intervalMax) {
            this.originMin = originMin;
            this.originMax = originMax;
            this.intervalMin = intervalMin;
            this.intervalMax = intervalMax;
    }

    public static Pair<QueryWindow, QueryWindow> buildQueryWindows(double startX, double endX, double startY, double endY){
        QueryWindow verticalQuery = new QueryWindow(startX, endX, startY, endY);
        QueryWindow horizontalQuery = new QueryWindow(startY, endY, startX, endX);

        return new Pair<>(verticalQuery, horizontalQuery);
    }


    public boolean contains(Segment segment) {
        return isOriginInRange(segment.getOrigin()) && isIntervalInRange(segment.getInterval());
    }

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
