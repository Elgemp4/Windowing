package be.groupe18.windowing.models;

/**
 * A class representing a vector in R²
 */
public class Vector2D {
    private final CompositeDouble x;
    private final CompositeDouble y;

    public Vector2D(double x, double y) {
        this.x = new CompositeDouble(x, y);
        this.y = new CompositeDouble(y, x);
    }

    /**
     * Getter for the x coordinate
     * @return A {@link CompositeDouble}
     */
    public CompositeDouble getX() {
        return x;
    }

    /**
     * Getter for the y coordinate
     * @return A {@link CompositeDouble}
     */
    public CompositeDouble getY() {
        return y;
    }
}
