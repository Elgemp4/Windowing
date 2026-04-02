package be.groupe18.windowing.strategies.query;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

class SimpleQueryStrategyTest {

    private SimpleQueryStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new SimpleQueryStrategy();
    }
}