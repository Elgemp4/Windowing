package be.groupe18.windowing.strategies.query;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import be.groupe18.windowing.models.CompositeDouble;
import be.groupe18.windowing.models.PRT;
import be.groupe18.windowing.models.QueryWindow;
import be.groupe18.windowing.models.Segment;
import be.groupe18.windowing.models.Vector2D;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

@ExtendWith(MockitoExtension.class)
class SimpleQueryStrategyTest {

    private SimpleQueryStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new SimpleQueryStrategy();
    }

    @Nested
    @DisplayName("Test des inputs (null checks)")
    class InputValidationTests {

        @Test
        @DisplayName("Doit lever une exception si le PRT est null")
        void shouldThrowExceptionWhenTreeIsNull(@Mock QueryWindow window) {
            NullPointerException exception = assertThrows(
                    NullPointerException.class,
                    () -> strategy.query(null, window)
            );
            assertTrue(exception.getMessage().contains("PRT arrived null"));
        }

        @Test
        @DisplayName("Doit lever une exception si la query window est null")
        void shouldThrowExceptionWhenWindowIsNull(@Mock PRT tree) {
            NullPointerException exception = assertThrows(
                    NullPointerException.class,
                    () -> strategy.query(tree, null)
            );
            assertTrue(exception.getMessage().contains("Query window arrived null"));
        }
    }
    
    @Nested
    @DisplayName("Test des cas de base")
    class EdgeCasesTests {

        @Test
        @DisplayName("Doit retourner une liste vide si l'arbre est vide")
        void shouldReturnEmptyListWhenTreeIsEmpty(@Mock PRT emptyTree, @Mock QueryWindow window) {
            when(emptyTree.getSegment()).thenReturn(null);
            List<Segment> results = strategy.query(emptyTree, window);
            assertTrue(results.isEmpty(), "La liste de résultats doit être vide pour un arbre vide");
        }

        @Test
        @DisplayName("Doit retourner une liste vide si la fenêtre ne contient aucun point")
        void shouldReturnEmptyListWhenWindowMissesEverything(
                @Mock PRT rootNode, @Mock QueryWindow window, @Mock Segment segment, @Mock Vector2D point) {
            
            when(rootNode.getSegment()).thenReturn(segment);
            when(rootNode.isLeaf()).thenReturn(true);
            when(segment.getFirstPoint()).thenReturn(point);
            
            when(window.contains(point)).thenReturn(false);
            
            when(window.getIntervalMin()).thenReturn(mock(CompositeDouble.class));
            when(window.getIntervalMax()).thenReturn(mock(CompositeDouble.class));

            List<Segment> results = strategy.query(rootNode, window);

            assertTrue(results.isEmpty(), "Aucun segment ne doit être retourné si en dehors de la fenêtre");
        }
    }
}