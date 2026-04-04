package be.groupe18.windowing.strategies.build;

import be.groupe18.windowing.models.PST;
import be.groupe18.windowing.models.Segment;

import java.util.List;

public interface BuildStrategy {
        /**
         * Build a PRT using the list of segment provided
         * @param segments the list of segment on which the PRT needs to be built upon
         * @param start the inclusive start index for the elements to use in the build process
         * @param end the exclusive end index for the elements to use in the build process can be equal to start but not greater
         * @return The resulting PRT
         */
        PST build(List<Segment> segments, int start, int end);
}
