package be.groupe18.windowing.models;

import java.util.function.BiFunction;

public class Segment {
    private final Vector2D firstPoint;
    private final Vector2D secondPoint;

    private final CompositeDouble origin;
    private final Interval interval;

    public final static BiFunction<Segment, Segment, Boolean> greaterMinIntervalThan = (Segment s1, Segment s2) -> CompositeDouble.greaterThan(s1.getMinInterval(), s2.getMinInterval());
    public final static BiFunction<Segment, Segment, Boolean> greaterOrigin = (Segment s1, Segment s2) -> CompositeDouble.greaterThan(s1.getOrigin(), s2.getOrigin());

    public Segment(Vector2D firstPoint, Vector2D secondPoint) {
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;

        this.origin = this.isVertical() ? firstPoint.getX() : firstPoint.getY();

        if(isVertical()){
            this.interval = new Interval(firstPoint.getY(), secondPoint.getY());
        }
        else{
            this.interval = new Interval(firstPoint.getX(), secondPoint.getX());
        }
    }

    public Vector2D getFirstPoint() {
        return firstPoint;
    }

    public Vector2D getSecondPoint() {
        return secondPoint;
    }

    public boolean isVertical() {
        return CompositeDouble.looseEqual(firstPoint.getX(), secondPoint.getX());
    }

    public CompositeDouble getOrigin() {
        return this.isVertical() ? firstPoint.getX() : firstPoint.getY();
    }

    public Interval getInterval() {
        return this.interval;
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