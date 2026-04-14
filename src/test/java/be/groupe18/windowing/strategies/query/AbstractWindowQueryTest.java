package be.groupe18.windowing.strategies.query;

import be.groupe18.windowing.application.strategy.build.BuildStrategy;
import be.groupe18.windowing.application.strategy.query.QueryStrategy;
import be.groupe18.windowing.domain.model.PST;
import be.groupe18.windowing.domain.model.QueryWindow;
import be.groupe18.windowing.domain.model.Segment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractWindowQueryTest {
    private List<Segment> queryResult;

    public AbstractWindowQueryTest(BuildStrategy buildStrategy, QueryWindow query, QueryStrategy strategy){
        List<Segment> segments = getAllSegments();
        PST tree = buildStrategy.build(segments, 0, segments.size());

        queryResult = strategy.query(tree, query);
    }

    protected abstract List<Segment> getInsideSegments();
    protected abstract List<Segment> getThroughSegments();
    protected abstract List<Segment> getPartiallyInsideSegments();
    protected abstract List<Segment> getOutsideGoodIntSegments();
    protected abstract List<Segment> getOutsideGoodOriginSegments();
    protected abstract List<Segment> getOutsideSegments();
    protected abstract List<Segment> getInsidePoint();

    private List<Segment> getAllSegments() {
        List<Segment> allSegments = new ArrayList<>();
        allSegments.addAll(getInsideSegments());
        allSegments.addAll(getThroughSegments());
        allSegments.addAll(getPartiallyInsideSegments());
        allSegments.addAll(getOutsideGoodIntSegments());
        allSegments.addAll(getOutsideGoodOriginSegments());
        allSegments.addAll(getOutsideSegments());
        allSegments.addAll(getInsidePoint());
        return allSegments;
    }

    protected List<Segment> getQueryResult() {
        return this.queryResult;
    }

    // Méthode utilitaire pour vérifier qu'AUCUN segment d'une liste n'est dans les résultats
    private void assertContainsNone(List<Segment> results, List<Segment> forbiddenSegments) {
        if (forbiddenSegments == null) return;
        for (Segment s : forbiddenSegments) {
            assertFalse(results.contains(s), "The query result should NOT contain this segment: " + s);
        }
    }

    @Test
    @DisplayName("Should contain segments which are completely within the query window")
    void shouldContainInsideSegments(){
        assertTrue(getQueryResult().containsAll(getInsideSegments()));
    }

    @Test
    @DisplayName("Should contain segments that go through the query window")
    void shouldContainThroughSegments(){
        assertTrue(getQueryResult().containsAll(getThroughSegments()));
    }

    @Test
    @DisplayName("Should contain segments which are partially in the query window")
    void shouldContainPartiallyInsideSegments(){
        // Corrigé : on vérifie bien dans queryResult
        assertTrue(getQueryResult().containsAll(getPartiallyInsideSegments()));
    }

    @Test
    @DisplayName("Should NOT contain segments outside the window (even if they share the good Interval)")
    void shouldNotContainOutsideGoodIntSegments(){
        assertContainsNone(getQueryResult(), getOutsideGoodIntSegments());
    }

    @Test
    @DisplayName("Should NOT contain segments outside the window (even if they share the good Origin)")
    void shouldNotContainOutsideGoodOriginSegments(){
        assertContainsNone(getQueryResult(), getOutsideGoodOriginSegments());
    }

    @Test
    @DisplayName("Should NOT contain segments that are totally outside the query window")
    void shouldNotContainOutsideSegments(){
        assertContainsNone(getQueryResult(), getOutsideSegments());
    }

    @Test
    @DisplayName("Should contain segments of size 0 (points) that are inside the window")
    void shouldContainInsidePoint(){
        assertTrue(getQueryResult().containsAll(getInsidePoint()));
    }
}