package iscteiul.ista.battleship;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da classe Position com 100% branch coverage")
class PositionTest {

    private Position position;

    @BeforeEach
    void setUp() {
        position = new Position(2, 3);
    }

    @AfterEach
    void tearDown() {
        position = null;
    }

    @Test
    void testGetRowAndColumn() {
        assertEquals(2, position.getRow());
        assertEquals(3, position.getColumn());
    }

    @Test
    void testEquals() {
        Position same = new Position(2, 3);
        Position diffRow = new Position(4, 3);
        Position diffCol = new Position(2, 4);
        Object otherType = new Object();

        // branch this == other
        assertEquals(position, position);

        // branch other == null
        assertNotEquals(position, null);

        // branch outro tipo
        assertNotEquals(position, otherType);

        // branch row e col iguais
        assertEquals(position, same);

        // branch row diferente
        assertFalse(position.equals(diffRow));

        // branch col diferente
        assertFalse(position.equals(diffCol));
    }

    @Test
    void testHashCode() {
        Position same = new Position(2, 3);
        assertEquals(position.hashCode(), same.hashCode());

        // branch estado diferente (isHit ou isOccupied)
        Position p1 = new Position(2,3);
        Position p2 = new Position(2,3);
        p1.occupy();
        p2.shoot();
        assertNotEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testIsAdjacentTo() {
        Position adj1 = new Position(2, 2);
        Position adj2 = new Position(1, 3);
        Position nonAdj = new Position(5, 5);
        Position self = new Position(2, 3);
        Position nullPos = null;

        // branches true
        assertTrue(position.isAdjacentTo(adj1));
        assertTrue(position.isAdjacentTo(adj2));

        // branch false adjacente
        assertFalse(position.isAdjacentTo(nonAdj));

        // branch false mesma posição
        assertFalse(position.isAdjacentTo(self));

        // branch false null
        assertFalse(position.isAdjacentTo(nullPos));
    }

    @Test
    void testOccupyAndIsOccupied() {
        assertFalse(position.isOccupied());
        position.occupy();
        assertTrue(position.isOccupied());
    }

    @Test
    void testShootAndIsHit() {
        assertFalse(position.isHit());
        position.shoot();
        assertTrue(position.isHit());
    }

    @Test
    void testToString() {
        String s = position.toString();
        assertTrue(s.contains("Linha"));
        assertTrue(s.contains("Coluna"));
        assertTrue(s.contains("2"));
        assertTrue(s.contains("3"));
    }
}
