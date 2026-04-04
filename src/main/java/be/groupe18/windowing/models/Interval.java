package be.groupe18.windowing.models;

public class Interval {
    private CompositeDouble intervalMin;
    private CompositeDouble intervalMax;

    public Interval(CompositeDouble firstInterval, CompositeDouble secondInterval) {
        this.intervalMin = CompositeDouble.min(firstInterval, secondInterval);
        this.intervalMax = CompositeDouble.max(firstInterval, secondInterval);
    }

    public CompositeDouble getIntervalMin() {
        return intervalMin;
    }

    public CompositeDouble getIntervalMax() {
        return intervalMax;
    }
}
