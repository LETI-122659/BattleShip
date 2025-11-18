package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FrigateTest {

    @Test
    @DisplayName("Frigate size must be 4")
    void testGetSize() {
        Frigate f = new Frigate(Compass.NORTH, new Position(1, 1));
        assertEquals(Integer.valueOf(4), f.getSize());
    }

    @Test
    @DisplayName("Positions generated correctly when facing NORTH")
    void testPositionsNorth() {
        Frigate f = new Frigate(Compass.NORTH, new Position(2, 3));

        assertEquals(4, f.getPositions().size());
        assertEquals(new Position(2, 3), f.getPositions().get(0));
        assertEquals(new Position(3, 3), f.getPositions().get(1));
        assertEquals(new Position(4, 3), f.getPositions().get(2));
        assertEquals(new Position(5, 3), f.getPositions().get(3));
    }

    @Test
    @DisplayName("Positions generated correctly when facing EAST")
    void testPositionsEast() {
        Frigate f = new Frigate(Compass.EAST, new Position(5, 1));

        assertEquals(4, f.getPositions().size());
        assertEquals(new Position(5, 1), f.getPositions().get(0));
        assertEquals(new Position(5, 2), f.getPositions().get(1));
        assertEquals(new Position(5, 3), f.getPositions().get(2));
        assertEquals(new Position(5, 4), f.getPositions().get(3));
    }



    @Test
    @DisplayName("Ship occupies a given position")
    void testOccupies() {
        Frigate f = new Frigate(Compass.NORTH, new Position(1, 1));
        assertTrue(f.occupies(new Position(1, 1)));
        assertTrue(f.occupies(new Position(4, 1)));
        assertFalse(f.occupies(new Position(10, 10)));
    }

    @Test
    @DisplayName("stillFloating returns false only when all parts are hit")
    void testStillFloating() {
        Frigate f = new Frigate(Compass.EAST, new Position(1, 1));

        // Initially floating
        assertTrue(f.stillFloating());

        // Hit 3/4 positions
        f.shoot(new Position(1, 1));
        f.shoot(new Position(1, 2));
        f.shoot(new Position(1, 3));
        assertTrue(f.stillFloating());

        // Hit the last one → should now sink
        f.shoot(new Position(1, 4));
        assertFalse(f.stillFloating());
    }

    @Test
    @DisplayName("shoot only marks hit on exact matching position")
    void testShoot() {
        Frigate f = new Frigate(Compass.NORTH, new Position(2, 2));
        IPosition target = f.getPositions().get(2);

        assertFalse(target.isHit());
        f.shoot(target);
        assertTrue(target.isHit());
    }

    @Test
    @DisplayName("Bounds: top, bottom, left, right positions")
    void testBounds() {
        Frigate f = new Frigate(Compass.NORTH, new Position(5, 5));

        assertEquals(5, f.getTopMostPos());
        assertEquals(8, f.getBottomMostPos());
        assertEquals(5, f.getLeftMostPos());
        assertEquals(5, f.getRightMostPos());
    }

    @Test
    @DisplayName("Too close to another ship")
    void testTooCloseToShip() {
        Frigate f1 = new Frigate(Compass.NORTH, new Position(1, 1));
        Frigate f2 = new Frigate(Compass.EAST, new Position(2, 2)); // adjacente

        assertTrue(f1.tooCloseTo(f2));
    }

    @Test
    @DisplayName("Not too close if far away")
    void testNotTooClose() {
        Frigate f1 = new Frigate(Compass.NORTH, new Position(1, 1));
        Frigate f2 = new Frigate(Compass.EAST, new Position(20, 20));

        assertFalse(f1.tooCloseTo(f2));
    }
}
