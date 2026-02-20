package be.groupe18.windowing.models;

public class Segment {
    private Vector2D firstPoint;
    private Vector2D secondPoint;

    public Segment(Vector2D firstPoint, Vector2D secondPoint) {
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
    }

    public Vector2D getFirstPoint() {
        return firstPoint;
    }

    public Vector2D getSecondPoint() {
        return secondPoint;
    }

    public boolean isVertical() {
        return firstPoint.getX() == secondPoint.getX();
    }

    public CompositeDouble getOrigin() {
        return this.isVertical() ? firstPoint.getX() : firstPoint.getY();
    }


    public CompositeDouble getMinInterval() {
        if (isVertical()) {
            return CompositeDouble.min(getFirstPoint().getY(), getSecondPoint().getY());
        } else {
            return CompositeDouble.min(getFirstPoint().getX(), getSecondPoint().getX());
        }
    }

    public CompositeDouble getMaxInterval() {
        if (isVertical()) {
            return CompositeDouble.max(getFirstPoint().getY(), getSecondPoint().getY());
        } else {
            return CompositeDouble.max(getFirstPoint().getX(), getSecondPoint().getX());
        }
    }
}