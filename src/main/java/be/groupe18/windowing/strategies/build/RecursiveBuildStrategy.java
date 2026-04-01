package be.groupe18.windowing.strategies.build;

import be.groupe18.windowing.models.CompositeDouble;
import be.groupe18.windowing.models.PRT;
import be.groupe18.windowing.models.Segment;
import be.groupe18.windowing.strategies.median.MedianStrategy;
import be.groupe18.windowing.strategies.minimum.MinimumStrategy;
import be.groupe18.windowing.strategies.pivot_split.PivotSplitStrategy;

import java.util.List;

import static java.util.Collections.swap;

public class RecursiveBuildStrategy implements BuildStrategy{

    private final MinimumStrategy<Segment> minimumStrategy;
    private final MedianStrategy<Segment> medianStrategy;
    private final PivotSplitStrategy<Segment> pivotSplitStrategy;

    public RecursiveBuildStrategy(MinimumStrategy<Segment> minimumStrategy, MedianStrategy<Segment> medianStrategy, PivotSplitStrategy<Segment> pivotSplitStrategy) {
        this.minimumStrategy = minimumStrategy;
        this.medianStrategy = medianStrategy;
        this.pivotSplitStrategy = pivotSplitStrategy;
    }

    @Override
    public PRT build(List<Segment> segments, int start, int end){
        //If the user provided illegals arguments
        if (segments == null || start > end) {
            throw new IllegalArgumentException();
        }

        //If the subset is empty return null
        if(start == end) {
            return null;
        }

        int minSegmentIndex = getMinimumIntervalSegment(segments, start,  end);

        //Get the minimum segment on variable axis and put outside the bounds of the start and end pointer
        Segment minIntSegment = segments.get(minSegmentIndex);
        swap(segments, start, minSegmentIndex);
        start++;

        //If there is not elements left after finding the minimum no need to calculate the median
        //as this will be a leaf
        if(start == end){
            PRT currentNode = new PRT();
            currentNode.setSegment(minIntSegment);
            return currentNode;
        }

        //Get the median of the origin of the segments
        int medianIndex = getMedian(segments, start, end);
        CompositeDouble median = segments.get(medianIndex).getOrigin();

        PRT currentNode = new PRT();

        //Store data into the node
        currentNode.setSegment(minIntSegment);
        currentNode.setMedian(median);

        //Recursively build left and right subtree
        currentNode.setLeftChild(build(segments, start, medianIndex ));
        currentNode.setRightChild(build(segments,medianIndex,end));
        return currentNode;
    }

    private int getMinimumIntervalSegment(List<Segment> segments, int start, int end) {
        return minimumStrategy.getMinimum(segments,
                (Segment s1, Segment s2) -> CompositeDouble.greaterThan(s1.getMinInterval(), s2.getMinInterval()), start, end);
    }

    private int getMedian(List<Segment> segments, int start, int end) {
        return  medianStrategy.computeMedian(segments,
                (Segment s1, Segment s2) -> CompositeDouble.greaterThan(s1.getOrigin(), s2.getOrigin()), start ,end);
    }
}
