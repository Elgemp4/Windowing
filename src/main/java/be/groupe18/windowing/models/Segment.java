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
        return firstPoint.getFirst() == secondPoint.getFirst();
    }

    public double getOrigin() {
        return this.isVertical() ? firstPoint.getFirst() : firstPoint.getSecond();
    }


    public double getMinInterval() {
        if (isVertical()) {
            return Math.min(getFirstPoint().getSecond(), getSecondPoint().getSecond());
        } else {
            return Math.min(getFirstPoint().getFirst(), getSecondPoint().getFirst());
        }
    }

    public double getMaxInterval() {
        if (isVertical()) {
            return Math.max(getFirstPoint().getSecond(), getSecondPoint().getSecond());
        } else {
            return Math.max(getFirstPoint().getFirst(), getSecondPoint().getFirst());
        }
    }
}