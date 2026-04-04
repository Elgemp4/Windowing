package be.groupe18.windowing.models;

/**
 * PRT is a class for a Priority Research Three
 */
public class PST {
    private PST leftChild;
    private PST rightChild;
    private Segment segment;
    private CompositeDouble median;

    private int height = 1;


    public boolean isLeaf() {
        return leftChild == null && rightChild == null;
    }

    public PST getLeftChild() {
        return leftChild;
    }

    public PST getRightChild() {
        return rightChild;
    }

    public void setLeftChild(PST leftChild) {
        this.leftChild = leftChild;
        if(this.leftChild != null){
            this.height = Math.max(this.height, leftChild.getHeight() + 1);
        }
    }

    public void setRightChild(PST rightChild) {
        this.rightChild = rightChild;
        if(this.rightChild != null){
            this.height = Math.max(this.height, rightChild.getHeight() + 1);
        }
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    public CompositeDouble getMedian() {
        return median;
    }

    public void setMedian(CompositeDouble median) {
        this.median = median;
    }

    public int getHeight() {
        return height;
    }
}
