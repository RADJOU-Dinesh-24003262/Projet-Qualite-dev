package Food.SeaFood;

import Food.FoodType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LobsterTest {

    private Lobster lobster;

    @BeforeEach
    void setUp() {
        lobster = new Lobster("Red Lobster");
    }

    @Test
    void getName() {
        assertEquals("Red Lobster", lobster.getName());
    }

    @Test
    void getType() {
        assertEquals(FoodType.SEAFOOD, lobster.getType());
    }

    @Test
    void isEdible() {
        assertTrue(lobster.isEdible());
    }

    @Test
    void testToString() {
        assertEquals("Red Lobster (SEAFOOD)", lobster.toString());
    }
}
