package be.groupe18.windowing.strategies.query;

import be.groupe18.windowing.models.PST;
import be.groupe18.windowing.models.QueryWindow;
import be.groupe18.windowing.models.Segment;

import java.util.List;

public interface QueryStrategy {
    List<Segment> query(PST tree, QueryWindow queryWindow);
}
