package iscteiul.ista.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GalleonTest {

    @Test
    void getSizeNotNullAndPositive() {
        Galleon g = new Galleon(Compass.EAST, new Position(2, 3));
        assertNotNull(g.getSize());
        assertTrue(g.getSize() > 0);
    }

    @Test
    void positionsCountMatchesSize() {
        Galleon g = new Galleon(Compass.NORTH, new Position(1, 1));
        assertEquals(g.getSize().intValue(), g.getPositions().size());
    }

    @Test
    void positionsForEastAreContiguous() {
        Position start = new Position(2, 3);
        Galleon g = new Galleon(Compass.EAST, start);

        assertEquals(g.getSize().intValue(), g.getPositions().size());

        for (int i = 0; i < g.getPositions().size(); i++) {
            assertEquals(start.getRow(), g.getPositions().get(i).getRow(), "row at index " + i);
            assertEquals(start.getColumn() + i, g.getPositions().get(i).getColumn(), "column at index " + i);
        }
    }

    @Test
    void positionsForNorthAreContiguous() {
        Position start = new Position(1, 1);
        Galleon g = new Galleon(Compass.NORTH, start);

        assertEquals(g.getSize().intValue(), g.getPositions().size());

        for (int i = 0; i < g.getPositions().size(); i++) {
            assertEquals(start.getRow() + i, g.getPositions().get(i).getRow(), "row at index " + i);
            assertEquals(start.getColumn(), g.getPositions().get(i).getColumn(), "column at index " + i);
        }
    }
}