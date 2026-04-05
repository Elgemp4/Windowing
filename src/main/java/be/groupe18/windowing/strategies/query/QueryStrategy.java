package be.groupe18.windowing.strategies.query;

import be.groupe18.windowing.models.PST;
import be.groupe18.windowing.models.QueryWindow;
import be.groupe18.windowing.models.Segment;

import java.util.List;

/**
 * An interface for the {@link PST} querying algorithm using the strategy design pattern
 */
public interface QueryStrategy {
    /**
     * Query the provided {@link PST} with the provided {@link QueryWindow}
     * @param tree The PST on which to perform the query
     * @param queryWindow The query window to use for the querying
     * @return The list of Segment that are withing the PST
     */
    List<Segment> query(PST tree, QueryWindow queryWindow);
}
