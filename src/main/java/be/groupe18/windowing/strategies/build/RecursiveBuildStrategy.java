package be.groupe18.windowing.strategies.build;

import be.groupe18.windowing.models.CompositeDouble;
import be.groupe18.windowing.models.PRT;
import be.groupe18.windowing.models.Segment;
import be.groupe18.windowing.strategies.median.MedianStrategy;
import be.groupe18.windowing.strategies.minimum.MinimumStrategy;
import be.groupe18.windowing.strategies.splitting.SplitStrategy;
import be.groupe18.windowing.utils.Pair;

import java.util.List;
import java.util.function.BiFunction;

public class RecursiveBuildStrategy implements BuildStrategy{

    private final MinimumStrategy<Segment> minimumStrategy;
    private final MedianStrategy<Segment> medianStrategy;
    private final SplitStrategy<Segment> splitStrategy;

    public RecursiveBuildStrategy(MinimumStrategy<Segment> minimumStrategy, MedianStrategy<Segment> medianStrategy, SplitStrategy<Segment> splitStrategy) {
        this.minimumStrategy = minimumStrategy;
        this.medianStrategy = medianStrategy;
        this.splitStrategy = splitStrategy;
    }

    @Override
    public PRT build(List<Segment> segments, int start, int end){

        int minSegmentIndex = getMinimumIntervalSegment(segments, start,  end);
        Segment minIntSegment = segments.get(minSegmentIndex);
        swap(segments, start, minSegmentIndex);
        start++;

        if(start >= end){
            PRT currentNode = new PRT();
            currentNode.setSegment(minIntSegment);
            return currentNode;
        }
        System.out.println("Start : " + start);
        System.out.println("End : " + end);
        int medianIndex = getMedian(segments, start, end);
        CompositeDouble median = segments.get(medianIndex).getOrigin();

        System.out.println();
        int pivotIndex = partition(segments, (el1, el2) -> CompositeDouble.greaterThan(el1.getOrigin(), el2.getOrigin()), start, end, medianIndex);
        PRT currentNode = new PRT();

        currentNode.setSegment(minIntSegment);
        currentNode.setMedian(median);
        currentNode.setLeftChild(build(segments, start, pivotIndex));
        currentNode.setRightChild(build(segments,pivotIndex,end));
        return currentNode;
    }

    //TODO : liste pré-triée pour éviter de devoir faire une boucle à chaque fois ?
    private int getMinimumIntervalSegment(List<Segment> segments, int start, int end) {
        return minimumStrategy.getMinimum(segments,
                (Segment s1, Segment s2) -> CompositeDouble.greaterThan(s1.getMinInterval(), s2.getMinInterval()), start, end);
    }

    /**
     * Swap two elements
     * @param elements
     * @param i1
     * @param i2
     */
    private void swap(List<Segment> elements, int i1, int i2) {
        Segment temp = elements.get(i1);
        elements.set(i1, elements.get(i2));
        elements.set(i2, temp);
    }

    //TODO : implementer la méthode pour calculer la médiane d'un segment
    private int getMedian(List<Segment> segments, int start, int end) {
        int medianIndex =  medianStrategy.computeMedian(segments,
                (Segment s1, Segment s2) -> CompositeDouble.greaterThan(s1.getOrigin(), s2.getOrigin()), start ,end);
        return medianIndex;
    }

    /**
     * Partition the input list into two groups, those lesser than the pivot before it and those greater than it after
     * @param elements
     * @param greaterThan
     * @param start
     * @param end
     * @param pivot
     * @return The rank of the pivot
     */
    public int partition(List<Segment> elements, BiFunction<Segment, Segment, Boolean> greaterThan, int start, int end, int pivot) {
        swap(elements, pivot, end-1);

        int j = start;
        for(int i =start; i < end-1; i++){
            if(greaterThan.apply(elements.get(end-1), elements.get(i))){
                swap(elements,i,j);
                j+=1;
            }
        }

        swap(elements, j, end-1);
        return j;
    }
}
