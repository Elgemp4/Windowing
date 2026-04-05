package be.groupe18.windowing.models;

import java.util.function.BiFunction;

/**
 * A class representing a Segment contains abstraction from it's x,y coordinates in the form
 * of the concept of origin and interval.
 * The origin of a {@link Segment} is the shared fixed coordinates of the two points making the segment
 * The interval of a {@link Segment} is the different coordinates of the two points making the segment
 */
public class Segment {
    private final Vector2D firstPoint;
    private final Vector2D secondPoint;

    private final CompositeDouble origin;
    private final Interval interval;

    public final static BiFunction<Segment, Segment, Boolean> greaterMinIntervalThan = (Segment s1, Segment s2) -> CompositeDouble.greaterThan(s1.getInterval().getIntervalMin(), s2.getInterval().getIntervalMin());
    public final static BiFunction<Segment, Segment, Boolean> greaterOrigin = (Segment s1, Segment s2) -> CompositeDouble.greaterThan(s1.getOrigin(), s2.getOrigin());

    /**
     * A constructor for a segment
     * @param firstPoint The first point of the segment
     * @param secondPoint The second point of the segment
     */
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

    /**
     * Return the first point of the segment
     * @return A {@link Vector2D}
     */
    public Vector2D getFirstPoint() {
        return firstPoint;
    }

    /**
     * Return the second point of the segment
     * @return A {@link Vector2D}
     */
    public Vector2D getSecondPoint() {
        return secondPoint;
    }

    /**
     * Helper method to know if the segment is vertical or horizontal
     * @return A boolean if the segment is vertical, false otherwise
     */
    public boolean isVertical() {
        return CompositeDouble.looseEqual(firstPoint.getX(), secondPoint.getX());
    }

    /**
     * Getter for the origin
     * @return A {@link CompositeDouble} containing the origin of the Segment
     */
    public CompositeDouble getOrigin() {
        return this.origin;
    }

    /**
     * Getter for the interval
     * @return A {@link Interval} containg the interval of the Segment
     */
    public Interval getInterval() {
        return this.interval;
    }
}