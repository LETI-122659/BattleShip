package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GalleonTest {

    @Test
    @DisplayName("Size is not null and positive")
    void testGetSize() {
        Galleon g = new Galleon(Compass.EAST, new Position(2, 3));
        assertNotNull(g.getSize());
        assertTrue(g.getSize() > 0);
    }

    @Test
    @DisplayName("Number of positions matches ship size")
    void testPositionsCountMatchesSize() {
        Galleon g = new Galleon(Compass.NORTH, new Position(1, 1));
        assertEquals(g.getSize().intValue(), g.getPositions().size());
    }

    @Test
    @DisplayName("Positions are contiguous when facing EAST")
    void testPositionsEast() {
        Position start = new Position(2, 3);
        Galleon g = new Galleon(Compass.EAST, start);

        for (int i = 0; i < g.getSize(); i++) {
            assertEquals(start.getRow(), g.getPositions().get(i).getRow());
            assertEquals(start.getColumn() + i, g.getPositions().get(i).getColumn());
        }
    }

    @Test
    @DisplayName("Positions are contiguous when facing WEST")
    void testPositionsWest() {
        Position start = new Position(5, 10);
        Galleon g = new Galleon(Compass.WEST, start);

        for (int i = 0; i < g.getSize(); i++) {
            assertEquals(start.getRow(), g.getPositions().get(i).getRow());
        }
    }

    @Test
    @DisplayName("Positions are contiguous when facing NORTH")
    void testPositionsNorth() {
        Position start = new Position(1, 1);
        Galleon g = new Galleon(Compass.NORTH, start);

        for (int i = 0; i < g.getSize(); i++) {
            assertEquals(start.getRow() + i, g.getPositions().get(i).getRow());
            assertEquals(start.getColumn(), g.getPositions().get(i).getColumn());
        }
    }

    @Test
    @DisplayName("Positions are contiguous when facing SOUTH")
    void testPositionsSouth() {
        Position start = new Position(3, 4);
        Galleon g = new Galleon(Compass.SOUTH, start);

        for (int i = 0; i < g.getSize(); i++) {
            assertEquals(start.getColumn(), g.getPositions().get(i).getColumn());
        }
    }

    @Test
    @DisplayName("Occupies returns correct values")
    void testOccupies() {
        Galleon g = new Galleon(Compass.NORTH, new Position(1, 1));

        assertTrue(g.occupies(new Position(1, 1)));
        assertTrue(g.occupies(new Position(1 + g.getSize() - 1, 1)));
        assertFalse(g.occupies(new Position(50, 50)));
    }

    @Test
    @DisplayName("Shoot marks position as hit")
    void testShoot() {
        Galleon g = new Galleon(Compass.EAST, new Position(2, 2));
        IPosition target = g.getPositions().get(1);

        assertFalse(target.isHit());
        g.shoot(target);
        assertTrue(target.isHit());
    }

    @Test
    @DisplayName("Ship still floating until all positions hit")
    void testStillFloating() {
        Galleon g = new Galleon(Compass.EAST, new Position(1, 1));

        assertTrue(g.stillFloating());

        for (int i = 0; i < g.getSize() - 1; i++) {
            g.shoot(g.getPositions().get(i));
            assertTrue(g.stillFloating());
        }

        g.shoot(g.getPositions().get(g.getSize() - 1));
        assertFalse(g.stillFloating());
    }

    @Test
    @DisplayName("Bounds: top, bottom, left, right positions")
    void testBounds() {
        Galleon g = new Galleon(Compass.NORTH, new Position(5, 5));

        assertEquals(5, g.getTopMostPos());
        assertEquals(5 + g.getSize() - 1, g.getBottomMostPos());
        assertEquals(5, g.getLeftMostPos());
        assertEquals(5, g.getRightMostPos());
    }

    @Test
    @DisplayName("Ship too close to another adjacent ship")
    void testTooCloseToShip() {
        Galleon g1 = new Galleon(Compass.NORTH, new Position(1, 1));
        Galleon g2 = new Galleon(Compass.EAST, new Position(2, 2));

        assertTrue(g1.tooCloseTo(g2));
    }

    @Test
    @DisplayName("Not too close when far away")
    void testNotTooClose() {
        Galleon g1 = new Galleon(Compass.NORTH, new Position(1, 1));
        Galleon g2 = new Galleon(Compass.EAST, new Position(50, 50));

        assertFalse(g1.tooCloseTo(g2));
    }
}
