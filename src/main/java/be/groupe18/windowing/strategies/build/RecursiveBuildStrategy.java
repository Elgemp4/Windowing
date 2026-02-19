package be.groupe18.windowing.strategies.build;

import be.groupe18.windowing.models.CompositeDouble;
import be.groupe18.windowing.models.PRT;
import be.groupe18.windowing.models.Segment;
import be.groupe18.windowing.strategies.median.MedianStrategy;
import be.groupe18.windowing.strategies.minimum.MinimumStrategy;
import be.groupe18.windowing.strategies.splitting.SplitStrategy;
import be.groupe18.windowing.utils.Pair;

import java.util.List;

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
    public PRT build(List<Segment> segments) {
        if(segments.isEmpty()) {
            return null;
        }

        Segment minIntSegment = getMinimumIntervalSegment(segments);
        segments.remove(minIntSegment);

        if(segments.isEmpty()){
            PRT currentNode = new PRT();
            currentNode.setSegment(minIntSegment);
            return currentNode;
        }

        CompositeDouble median = getMedian(segments);
        Pair<List<Segment>, List<Segment>> splitSegments = split(segments, median);
        PRT currentNode = new PRT();

        currentNode.setSegment(minIntSegment);
        currentNode.setMedian(median);
        currentNode.setLeftChild(build(splitSegments.getFirst()));
        currentNode.setRightChild(build(splitSegments.getSecond()));
        return currentNode;
    }

    //TODO : liste pré-triée pour éviter de devoir faire une boucle à chaque fois ?
    private Segment getMinimumIntervalSegment(List<Segment> segments) {
        return minimumStrategy.getMinimum(segments,
                (Segment s1, Segment s2) -> CompositeDouble.greaterThan(s1.getMinInterval(), s2.getMinInterval()));
    }

    //TODO : implementer la méthode pour calculer la médiane d'un segment
    private CompositeDouble getMedian(List<Segment> segments) {
        Segment medianSegment =  medianStrategy.computeMedian(segments,
                (Segment s1, Segment s2) -> CompositeDouble.greaterThan(s1.getOrigin(), s2.getOrigin()));

        return medianSegment.getOrigin();
    }

    //TODO : implementer la méthode pour split les segments en fonction de la médiane
    private Pair<List<Segment>, List<Segment>> split(List<Segment> segments, CompositeDouble median) {
        return splitStrategy.split(segments, (Segment s) -> CompositeDouble.greaterThan(median, s.getOrigin()));
    }
}
