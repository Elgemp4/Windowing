package be.groupe18.windowing.strategies.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import be.groupe18.windowing.models.PRT;
import be.groupe18.windowing.models.QueryWindow;
import be.groupe18.windowing.models.Segment;

public class SimpleQueryStrategy implements QueryStrategy {
    @Override
    public List<Segment> query(PRT tree, QueryWindow queryWindow) {
        Objects.requireNonNull(tree, "PRT arrived null in " + this.getClass().getSimpleName());
        Objects.requireNonNull(queryWindow, "Query window arrived null in " + this.getClass().getSimpleName());
        List<Segment> results = new ArrayList<>();
        search(tree, queryWindow, results);
        return results;
    }

    private void search(PRT node, QueryWindow window, List<Segment> results) {
        
    }
}
