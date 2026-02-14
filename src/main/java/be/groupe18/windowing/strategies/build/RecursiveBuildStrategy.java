package be.groupe18.windowing.strategies.build;

import be.groupe18.windowing.models.PRT;
import be.groupe18.windowing.models.Segment;
import be.groupe18.windowing.utils.Pair;
import jdk.jshell.spi.ExecutionControl;

import java.util.List;

public class RecursiveBuildStrategy implements BuildStrategy{
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

        double median = getMedian(segments);
        Pair<List<Segment>, List<Segment>> splitSegments = split(segments, median);
        PRT currentNode = new PRT();

        currentNode.setSegment(minIntSegment);
        currentNode.setMedian(median);
        currentNode.setLeftChild(build(splitSegments.getFirst()));
        currentNode.setRightChild(build(splitSegments.getSecond()));
        return currentNode;
    }

    //TODO : liste pré-triée pour éviter de devoir faire une boucle à chaque fois ?
    //TODO : Mettre dans une classe à part ? (ex : MinimumIntervalStrategy)
    private Segment getMinimumIntervalSegment(List<Segment> segments) {
        double minX = Double.POSITIVE_INFINITY;
        Segment minSegment = null;

        for(Segment segment : segments) {
            double x = segment.getMinInterval();
            if(x < minX) {
                minX = x;
                minSegment = segment;
            }
        }

        return minSegment;
    }

    //TODO : implementer la méthode pour calculer la médiane d'un segment
    //TODO : Mettre dans une classe à part ? (ex : MedianStrategy)
    private double getMedian(List<Segment> segments) {
        return 0;
    }

    //TODO : implementer la méthode pour split les segments en fonction de la médiane
    //TODO : Mettre dans une classe à part ? (ex : SplitStrategy)
    private Pair<List<Segment>, List<Segment>> split(List<Segment> segments, double median) {
        return new Pair(null, null);
    }
}
