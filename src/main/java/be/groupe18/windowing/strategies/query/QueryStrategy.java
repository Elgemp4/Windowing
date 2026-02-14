package be.groupe18.windowing.strategies.query;

import be.groupe18.windowing.models.PRT;
import be.groupe18.windowing.models.QueryWindow;
import be.groupe18.windowing.models.Segment;

import java.util.List;

public interface QueryStrategy {
    List<Segment> query(PRT tree, QueryWindow queryWindow);
}
