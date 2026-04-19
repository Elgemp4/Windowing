package be.groupe18.windowing.domain.model;

/**
 * PST is a class that implements a single node of a Priority Search Three
 */
public class PST {

  private PST leftChild;
  private PST rightChild;
  private Segment segment;
  private double median;

  private int height = 1;

  /**
   * Helper method to determine if this node is a leaf
   * @return True if it's a leaf, false otherwise
   */
  public boolean isLeaf() {
    return leftChild == null && rightChild == null;
  }

  /**
   * Getter for the left subtree
   * @return The root node of the left subtree
   */
  public PST getLeftChild() {
    return leftChild;
  }

  /**
   * Getter for the right subtree
   * @return The root node of the right subtree
   */
  public PST getRightChild() {
    return rightChild;
  }

  /**
   * Set the left subtree
   * @param leftChild The root node of the left subtree
   */
  public void setLeftChild(PST leftChild) {
    this.leftChild = leftChild;
    if (this.leftChild != null) {
      this.height = Math.max(this.height, leftChild.getHeight() + 1);
    }
  }

  /**
   * Set the right subtree
   * @param rightChild The root node of the right subtree
   */
  public void setRightChild(PST rightChild) {
    this.rightChild = rightChild;
    if (this.rightChild != null) {
      this.height = Math.max(this.height, rightChild.getHeight() + 1);
    }
  }

  /**
   * Getter for the {@link Segment} associated with the current node
   * @return An instance of {@link Segment} which is stored withing the current node
   */
  public Segment getSegment() {
    return segment;
  }

  /**
   * Settter for the {@link Segment} associated with te current node
   * @param segment A {@link Segment} instance
   */
  public void setSegment(Segment segment) {
    this.segment = segment;
  }

  /**
   * Getter for the median of the node of both of the subtrees
   * @return A double representing the median of all the data in the subtrees
   */
  public double getMedian() {
    return median;
  }

  /**
   * Getter for the median of the node of both of the subtrees
   * @param median a double representing the median
   */
  public void setMedian(double median) {
    this.median = median;
  }

  /**
   * A getter for the current heigh of this node
   * <b>This method is strictly used for unit testing purposes it's a value which is computed during
   * the building process of the PST. It's not intended to be used for the business logic.</b>
   * @return a int representing the heigh of this node
   */
  public int getHeight() {
    return height;
  }
}
