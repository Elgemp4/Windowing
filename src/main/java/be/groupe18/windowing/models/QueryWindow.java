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
        return isOriginInRange(segment.getOrigin()) && isIntervalInRange(segment.getMinInterval(), segment.getMaxInterval());
    }

    public boolean isOriginInRange(CompositeDouble origin) {
        boolean lowerBoundOk = (originMin.isNegativeInfinite()) || CompositeDouble.equalOrGreaterThan(origin, originMin);
        boolean upperBoundOk = (originMax.isPositiveInfinite()) || CompositeDouble.equalOrGreaterThan(originMax, origin);

        return lowerBoundOk && upperBoundOk;
    }

    public boolean isIntervalInRange(CompositeDouble intervalStart, CompositeDouble intervalEnd) {
        boolean lowerBoundOk = (intervalMin.isNegativeInfinite()) || CompositeDouble.equalOrGreaterThan(intervalEnd, intervalMin);
        boolean upperBoundOk = (intervalMax.isPositiveInfinite()) || CompositeDouble.equalOrGreaterThan(intervalMax, intervalStart);
        return lowerBoundOk && upperBoundOk;
    }

    public CompositeDouble getOriginMin() { return originMin; }
    
    public CompositeDouble getOriginMax() { return originMax; }

    public CompositeDouble getIntervalMin() { return intervalMin; }

    public CompositeDouble getIntervalMax() { return intervalMax; }
}
