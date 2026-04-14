package be.groupe18.windowing.strategies.query;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import be.groupe18.windowing.application.strategy.build.RecursiveBuildStrategy;
import be.groupe18.windowing.application.strategy.query.SimpleQueryStrategy;

@ExtendWith(MockitoExtension.class)
class SimpleQueryStrategyTest {

    private SimpleQueryStrategy queryStrategy;
    private RecursiveBuildStrategy buildStrategy;

    private <T> ArrayList<T> mutableList(T... elements) {
        return new ArrayList<>(Arrays.asList(elements));
    }

/*
    @BeforeEach
    void setUp() {
        queryStrategy = new SimpleQueryStrategy();
        buildStrategy = new RecursiveBuildStrategy(
                new LinearMinimumStrategy<>(),
                new QuickSelectMedianStrategy<>(new LinearPivotSplitStrategy<>()),
                new LinearPivotSplitStrategy<>());
    }

    @Nested
    @DisplayName("Test des inputs (null checks)")
    class InputValidationTests {

        @Test
        @DisplayName("Doit lever une exception si le PST est null")
        void shouldThrowExceptionWhenTreeIsNull(@Mock QueryWindow window) {
            NullPointerException exception = assertThrows(
                    NullPointerException.class,
                    () -> queryStrategy.query(null, window)
            );
            assertTrue(exception.getMessage().contains("PST arrived null"));
        }

        @Test
        @DisplayName("Doit lever une exception si la query window est null")
        void shouldThrowExceptionWhenWindowIsNull(@Mock PST tree) {
            NullPointerException exception = assertThrows(
                    NullPointerException.class,
                    () -> queryStrategy.query(tree, null)
            );
            assertTrue(exception.getMessage().contains("Query window arrived null"));
        }
    }
    /*
    @Nested
    @DisplayName("Test des cas de base")
    class EdgeCasesTests {

        @Test
        @DisplayName("Doit retourner une liste vide si l'arbre est vide")
        void shouldReturnEmptyListWhenTreeIsEmpty(@Mock PST emptyTree, @Mock QueryWindow window) {
            when(emptyTree.getSegment()).thenReturn(null);
            List<Segment> results = queryStrategy.query(emptyTree, window);
            assertTrue(results.isEmpty(), "La liste de résultats doit être vide pour un arbre vide");
        }

        @Test
        @DisplayName("Doit retourner une liste vide si la fenêtre ne contient aucun point")
        void shouldReturnEmptyListWhenWindowMissesEverything(
                @Mock PST rootNode, @Mock QueryWindow window, @Mock Segment segment, @Mock Vector2D point) {

            when(rootNode.getSegment()).thenReturn(segment);
            when(rootNode.isLeaf()).thenReturn(true);
            when(segment.getFirstPoint()).thenReturn(point);

            when(window.contains(point)).thenReturn(false);

            when(window.getIntervalMin()).thenReturn(mock(CompositeDouble.class));
            when(window.getIntervalMax()).thenReturn(mock(CompositeDouble.class));

            List<Segment> results = queryStrategy.query(rootNode, window);

            assertTrue(results.isEmpty(), "Aucun segment ne doit être retourné si en dehors de la fenêtre");
        }
    }*/
}