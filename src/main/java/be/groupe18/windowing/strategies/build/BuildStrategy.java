package be.groupe18.windowing.strategies.build;

import be.groupe18.windowing.models.PRT;
import be.groupe18.windowing.models.Segment;

import java.util.List;

public interface BuildStrategy {
        PRT build(List<Segment> segments);
}
