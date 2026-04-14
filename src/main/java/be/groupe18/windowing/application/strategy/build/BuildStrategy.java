package be.groupe18.windowing.application.strategy.build;

import java.util.List;

import be.groupe18.windowing.domain.model.PST;
import be.groupe18.windowing.domain.model.Segment;

/**
 * An interface for building PST using the strategy design pattern
 */
public interface BuildStrategy {
        /**
         * Build a PST using the list of segment provided
         * @param segments the list of segment on which the PST needs to be built upon
         * @param start the inclusive start index for the elements to use in the build process
         * @param end the exclusive end index for the elements to use in the build process can be equal to start but not greater
         * @return The resulting PST
         */
        PST build(List<Segment> segments, int start, int end);
}
