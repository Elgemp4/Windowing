package be.groupe18.windowing.models;

public class QueryWindow {
    private final CompositeDouble originMin;
    private final CompositeDouble originMax;

    private final CompositeDouble intervalMin;
    private final CompositeDouble intervalMax;

    public QueryWindow(CompositeDouble originMin, CompositeDouble originMax,
        CompositeDouble intervalMin, CompositeDouble intervalMax) {
            this.originMin = originMin;
            this.originMax = originMax;
            this.intervalMin = intervalMin;
            this.intervalMax = intervalMax;
        }

    public boolean contains(Segment segment) {
        return isOriginInRange(segment.getOrigin()) && isIntervalInRange(segment.getInterval());
    }

    private boolean isOriginInRange(CompositeDouble origin) {
        boolean lowerBoundOk = (originMin.isNegativeInfinite()) || CompositeDouble.equalOrGreaterThan(origin, originMin);
        boolean upperBoundOk = (originMax.isPositiveInfinite()) || CompositeDouble.equalOrGreaterThan(originMax, origin);

        return lowerBoundOk && upperBoundOk;
    }

    public boolean isIntervalInRange(Interval interval) {

        boolean lowerBoundOk = (intervalMin.isNegativeInfinite()) || CompositeDouble.equalOrGreaterThan(interval.getIntervalMax(), intervalMin);
        boolean upperBoundOk = (intervalMax.isPositiveInfinite()) || CompositeDouble.equalOrGreaterThan(intervalMax, interval.getIntervalMin());
        return lowerBoundOk && upperBoundOk;
    }

    public boolean isIntervalTooSmall(Interval interval) {
        return CompositeDouble.greaterThan(this.intervalMin, interval.getIntervalMax());
    }

    public boolean isIntervalTooBig(Interval interval) {
        return CompositeDouble.greaterThan(interval.getIntervalMin(), this.intervalMax);
    }


    public CompositeDouble getOriginMin() { return originMin; }
    
    public CompositeDouble getOriginMax() { return originMax; }

    public CompositeDouble getIntervalMin() { return intervalMin; }

    public CompositeDouble getIntervalMax() { return intervalMax; }
}
