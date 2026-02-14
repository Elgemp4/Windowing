package be.groupe18.windowing.models;

/**
 * PRT is a class for a Priority Research Three
 */
public class PRT {
    private PRT leftChild;
    private PRT rightChild;
    private Segment segment;
    private double median;

    public PRT() {
        this.segment = segment;
        this.median = median;
    }

    public boolean isLeaf() {
        return leftChild == null && rightChild == null;
    }

    public PRT getLeftChild() {
        return leftChild;
    }

    public PRT getRightChild() {
        return rightChild;
    }

    public void setLeftChild(PRT leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(PRT rightChild) {
        this.rightChild = rightChild;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    public double getMedian() {
        return median;
    }

    public void setMedian(double median) {
        this.median = median;
    }
}
