package be.groupe18.windowing.application.strategy.build;

import static java.util.Collections.swap;

import be.groupe18.windowing.application.strategy.median.MedianStrategy;
import be.groupe18.windowing.application.strategy.minimum.MinimumStrategy;
import be.groupe18.windowing.domain.model.PST;
import be.groupe18.windowing.domain.model.Segment;
import java.util.List;

/**
 * The concrete implementation of {@link BuildStrategy} using a recursive algorithm to create
 * a PST by leveraging other minimum and median algorithms.
 */
public class RecursiveBuildStrategy implements BuildStrategy {

  private final MinimumStrategy<Segment> minimumStrategy;
  private final MedianStrategy<Segment> medianStrategy;

  /**
   * The main constructor of the algorithm
   * @param minimumStrategy The used minimum algorithm
   * @param medianStrategy The used median algorithm
   */
  public RecursiveBuildStrategy(
    MinimumStrategy<Segment> minimumStrategy,
    MedianStrategy<Segment> medianStrategy
  ) {
    this.minimumStrategy = minimumStrategy;
    this.medianStrategy = medianStrategy;
  }

  @Override
  public PST build(List<Segment> segments, int start, int end) {
    //If the user provided illegals arguments
    if (segments == null || start > end) {
      throw new IllegalArgumentException();
    }

    //If the subset is empty return null
    if (start == end) {
      return null;
    }

    int minSegmentIndex = minimumStrategy.getMinimum(
      segments,
      Segment.greaterMinIntervalThan,
      start,
      end
    );

    //Get the minimum segment on variable axis and put outside the bounds of the start and end pointer
    Segment minIntSegment = segments.get(minSegmentIndex);
    swap(segments, start, minSegmentIndex);
    start++;

    //If there is not elements left after finding the minimum no need to calculate the median
    //as this will be a leaf
    if (start == end) {
      PST currentNode = new PST();
      currentNode.setSegment(minIntSegment);
      return currentNode;
    }

    //Get the median of the origin of the segments
    int medianIndex = medianStrategy.computeMedian(
      segments,
      Segment.greaterOrigin,
      start,
      end
    );
    double median = segments.get(medianIndex).getOrigin().getAsDouble();

    PST currentNode = new PST();

    //Store data into the node
    currentNode.setSegment(minIntSegment);
    currentNode.setMedian(median);

    //Recursively build left and right subtree
    currentNode.setLeftChild(build(segments, start, medianIndex));
    currentNode.setRightChild(build(segments, medianIndex, end));
    return currentNode;
  }
}
