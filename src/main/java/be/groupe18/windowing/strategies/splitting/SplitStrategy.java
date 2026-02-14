package be.groupe18.windowing.strategies.splitting;

import be.groupe18.windowing.models.Segment;
import be.groupe18.windowing.utils.Pair;

import java.util.List;

public interface SplitStrategy {
    /**
     * Splits the given segments into horizontal and vertical segments.
     * @param segments The raw list of segments to split.
     * @return A SplitResult containing the horizontal and vertical segments.
     */
    Pair<List<Segment>, List<Segment>> split(List<Segment> segments);
}
