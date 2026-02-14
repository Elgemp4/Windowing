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

    public double getOrigin(){
        return (firstPoint.getFirst()  == secondPoint.getFirst()) ? firstPoint.getFirst() : firstPoint.getSecond();
    }

    public Vector2D getInterval(){
        return (firstPoint.getFirst()  == secondPoint.getFirst()) ? new Vector2D(firstPoint.getSecond(), secondPoint.getSecond()) : new Vector2D(firstPoint.getFirst(), secondPoint.getFirst());
    }}