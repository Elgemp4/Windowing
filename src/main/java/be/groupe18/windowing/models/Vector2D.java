package be.groupe18.windowing.models;

public class Vector2D {
    private final CompositeDouble x;
    private final CompositeDouble y;

    public Vector2D(double x, double y) {
        this.x = new CompositeDouble(x, y);
        this.y = new CompositeDouble(y, x);
    }

    public CompositeDouble getX() {
        return x;
    }
    public CompositeDouble getY() {
        return y;
    }
}
