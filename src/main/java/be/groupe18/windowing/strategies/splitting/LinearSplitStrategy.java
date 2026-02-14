package be.groupe18.windowing.strategies.splitting;

import be.groupe18.windowing.models.Segment;
import be.groupe18.windowing.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class LinearSplitStrategy implements SplitStrategy {
    @Override
    public Pair<List<Segment>, List<Segment>> split(List<Segment> segments) {

        List<Segment> horizontalSegments = new ArrayList<>();
        List<Segment> verticalSegments = new ArrayList<>();

        for (Segment segment : segments) {
            if (segment.isVertical()) {
                verticalSegments.add(segment);
            } else if (segment.isVertical()) {
                horizontalSegments.add(segment);
            }
        }

        return new Pair<>(horizontalSegments, verticalSegments);
    }
}
