package be.groupe18.windowing.models;

public class QueryWindow {
    private final CompositeDouble xMin;
    private final CompositeDouble xMax;

    private final CompositeDouble yMin;
    private final CompositeDouble yMax;

    public QueryWindow(CompositeDouble xMin, CompositeDouble xMax,
        CompositeDouble yMin, CompositeDouble yMax) {
            this.xMin = xMin;
            this.xMax = xMax;
            this.yMin = yMin;
            this.yMax = yMax;
        }

    public boolean contains(Vector2D point) {
        return isXInRange(point.getX()) && isYInRange(point.getY());
    }

    private boolean isXInRange(CompositeDouble x) {
        boolean lowerBoundOk = (xMin == null) || !CompositeDouble.greaterThan(xMin, x);
        boolean upperBoundOk = (xMax == null) || !CompositeDouble.greaterThan(x, xMax);
        return lowerBoundOk && upperBoundOk;
    }

    private boolean isYInRange(CompositeDouble y) {
        boolean lowerBoundOk = (yMin == null) || !CompositeDouble.greaterThan(yMin, y);
        boolean upperBoundOk = (yMax == null) || !CompositeDouble.greaterThan(y, yMax);
        return lowerBoundOk && upperBoundOk;
    }
}
