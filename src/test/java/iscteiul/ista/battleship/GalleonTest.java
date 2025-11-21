package iscteiul.ista.battleship;

import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GalleonTest {

    @Test
    @TmsLink("TC-GALLEON-001")
    @DisplayName("Size is not null and positive")
    void testGetSize() {
        Galleon g = new Galleon(Compass.EAST, new Position(2, 3));
        assertNotNull(g.getSize());
        assertTrue(g.getSize() > 0);
    }

    @Test
    @TmsLink("TC-GALLEON-002")
    @DisplayName("Number of positions matches ship size")
    void testPositionsCountMatchesSize() {
        Galleon g = new Galleon(Compass.NORTH, new Position(1, 1));
        assertEquals(g.getSize().intValue(), g.getPositions().size());
    }

    @Test
    @TmsLink("TC-GALLEON-003")
    @DisplayName("Positions are correct when facing EAST")
    void testPositionsEast() {
        Position start = new Position(2, 3);
        Galleon g = new Galleon(Compass.EAST, start);

        assertEquals(new Position(2, 3), g.getPositions().get(0));
        assertEquals(new Position(3, 1), g.getPositions().get(1));
        assertEquals(new Position(3, 2), g.getPositions().get(2));
        assertEquals(new Position(3, 3), g.getPositions().get(3));
        assertEquals(new Position(4, 3), g.getPositions().get(4));
    }

    @Test
    @TmsLink("TC-GALLEON-004")
    @DisplayName("Positions are correct when facing WEST")
    void testPositionsWest() {
        Position start = new Position(5, 10);
        Galleon g = new Galleon(Compass.WEST, start);

        assertEquals(new Position(5, 10), g.getPositions().get(0));
        assertEquals(new Position(6, 10), g.getPositions().get(1));
        assertEquals(new Position(6, 11), g.getPositions().get(2));
        assertEquals(new Position(6, 12), g.getPositions().get(3));
        assertEquals(new Position(7, 10), g.getPositions().get(4));
    }

    @Test
    @TmsLink("TC-GALLEON-005")
    @DisplayName("Positions are correct when facing NORTH")
    void testPositionsNorth() {
        Position start = new Position(5, 5);
        Galleon g = new Galleon(Compass.NORTH, start);

        assertEquals(new Position(5, 5), g.getPositions().get(0));
        assertEquals(new Position(5, 6), g.getPositions().get(1));
        assertEquals(new Position(5, 7), g.getPositions().get(2));
        assertEquals(new Position(6, 6), g.getPositions().get(3));
        assertEquals(new Position(7, 6), g.getPositions().get(4));
    }


    @Test
    @TmsLink("TC-GALLEON-006")
    @DisplayName("Positions are correct when facing SOUTH")
    void testPositionsSouth() {
        Position start = new Position(3, 4);
        Galleon g = new Galleon(Compass.SOUTH, start);

        assertEquals(new Position(3, 4), g.getPositions().get(0));
        assertEquals(new Position(4, 4), g.getPositions().get(1));
        assertEquals(new Position(5, 3), g.getPositions().get(2));
        assertEquals(new Position(5, 4), g.getPositions().get(3));
        assertEquals(new Position(5, 5), g.getPositions().get(4));
    }



    @Test
    @TmsLink("TC-GALLEON-007")
    @DisplayName("Occupies returns correct values")
    void testOccupies() {
        Galleon g = new Galleon(Compass.NORTH, new Position(1, 1));

        assertTrue(g.occupies(new Position(1, 1)));
        assertFalse(g.occupies(new Position(50, 50)));
    }

    @Test
    @TmsLink("TC-GALLEON-008")
    @DisplayName("Shoot marks position as hit")
    void testShoot() {
        Galleon g = new Galleon(Compass.EAST, new Position(2, 2));
        IPosition target = g.getPositions().get(1);

        assertFalse(target.isHit());
        g.shoot(target);
        assertTrue(target.isHit());
    }

    @Test
    @TmsLink("TC-GALLEON-009")
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
    @TmsLink("TC-GALLEON-010")
    @DisplayName("Bounds: top, bottom, left, right positions")
    void testBounds() {
        Galleon g = new Galleon(Compass.NORTH, new Position(5, 5));

        assertEquals(5, g.getTopMostPos());
        assertEquals(3 + g.getSize() - 1, g.getBottomMostPos());
        assertEquals(5, g.getLeftMostPos());
        assertEquals(7, g.getRightMostPos());
    }

    @Test
    @TmsLink("TC-GALLEON-011")
    @DisplayName("Ship too close to another adjacent ship")
    void testTooCloseToShip() {
        Galleon g1 = new Galleon(Compass.NORTH, new Position(1, 1));
        Galleon g2 = new Galleon(Compass.EAST, new Position(2, 2));

        assertTrue(g1.tooCloseTo(g2));
    }

    @Test
    @TmsLink("TC-GALLEON-012")
    @DisplayName("Not too close when far away")
    void testNotTooClose() {
        Galleon g1 = new Galleon(Compass.NORTH, new Position(1, 1));
        Galleon g2 = new Galleon(Compass.EAST, new Position(50, 50));

        assertFalse(g1.tooCloseTo(g2));
    }
}
