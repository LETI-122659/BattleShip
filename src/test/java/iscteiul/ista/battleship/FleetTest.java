// java
package iscteiul.ista.battleship;

import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FleetFullCoverageTest {

    private IShip mockShip(String category, boolean floating) {
        IShip ship = mock(IShip.class);
        when(ship.getCategory()).thenReturn(category);
        when(ship.stillFloating()).thenReturn(floating);
        when(ship.getLeftMostPos()).thenReturn(0);
        when(ship.getTopMostPos()).thenReturn(0);
        when(ship.getRightMostPos()).thenReturn(Fleet.BOARD_SIZE - 1);
        when(ship.getBottomMostPos()).thenReturn(Fleet.BOARD_SIZE - 1);
        when(ship.toString()).thenReturn(category + "-TOSTRING");
        // default safe behaviour
        when(ship.occupies(any())).thenReturn(false);
        when(ship.tooCloseTo(any(IShip.class))).thenReturn(false);
        return ship;
    }

    private IPosition mockPosition() {
        return mock(IPosition.class);
    }

    @Test
    void printShips_emptyAndNonEmpty_behaviour() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream old = System.out;
        System.setOut(new PrintStream(baos));
        try {
            // empty list -> no output
            Fleet.printShips(List.of());
            assertEquals("", baos.toString());

            baos.reset();
            IShip s1 = mock(IShip.class);
            when(s1.toString()).thenReturn("S1");
            IShip s2 = mock(IShip.class);
            when(s2.toString()).thenReturn("S2");

            Fleet.printShips(List.of(s1, s2));
            String out = baos.toString();
            assertTrue(out.contains("S1"));
            assertTrue(out.contains("S2"));
        } finally {
            System.setOut(old);
        }
    }

    @Test
    void addShip_rejectsOutsideBoard_and_handlesCollisionAndNoCollision() {
        Fleet fleet = new Fleet();

        // outside board rejected
        IShip bad = mock(IShip.class);
        when(bad.getLeftMostPos()).thenReturn(-1);
        when(bad.getTopMostPos()).thenReturn(0);
        when(bad.getRightMostPos()).thenReturn(Fleet.BOARD_SIZE - 1);
        when(bad.getBottomMostPos()).thenReturn(Fleet.BOARD_SIZE - 1);
        assertFalse(fleet.addShip(bad));

        // add first valid ship
        IShip first = mockShip("A", true);
        assertTrue(fleet.addShip(first));

        // second collides -> rejected
        IShip second = mockShip("B", true);
        when(first.tooCloseTo(second)).thenReturn(true);
        assertFalse(fleet.addShip(second));

        // if not too close -> allowed
        IShip third = mockShip("C", true);
        when(first.tooCloseTo(third)).thenReturn(false);
        assertTrue(fleet.addShip(third));
    }

    @Test
    void addShip_rejects_whenFleetFull() {
        Fleet fleet = new Fleet();

        int added = 0;
        int safety = 200;
        for (int i = 0; i < safety; i++) {
            IShip s = mockShip("T" + i, true);
            boolean ok = fleet.addShip(s);
            if (ok) added++;
            else break;
        }

        assertTrue(added > 0, "should add at least one ship");
        IShip extra = mockShip("EXTRA", true);
        assertFalse(fleet.addShip(extra));
    }

    @Test
    void shipAt_returnsShipWhenOccupies_and_nullOtherwise() {
        Fleet fleet = new Fleet();
        IPosition pos = mockPosition();
        IShip s = mockShip("S", true);
        when(s.occupies(pos)).thenReturn(true);
        fleet.getShips().add(s);

        assertSame(s, fleet.shipAt(pos));

        IPosition other = mockPosition();
        assertNull(fleet.shipAt(other));
    }

    @Test
    void getShipsLike_and_getFloatingShips_work_as_expected() {
        Fleet fleet = new Fleet();
        IShip s1 = mockShip("Nau", true);
        IShip s2 = mockShip("Nau", false);
        IShip s3 = mockShip("Fragata", true);
        fleet.getShips().add(s1);
        fleet.getShips().add(s2);
        fleet.getShips().add(s3);

        List<IShip> naus = fleet.getShipsLike("Nau");
        assertEquals(2, naus.size());
        for (IShip s : naus) assertEquals("Nau", s.getCategory());

        List<IShip> none = fleet.getShipsLike("Nonexistent");
        assertTrue(none.isEmpty());

        List<IShip> floating = fleet.getFloatingShips();
        assertEquals(2, floating.size());
        for (IShip s : floating) assertTrue(s.stillFloating());
    }

    @Test
    void colisionRisk_detects_withEmpty_and_withExisting() {
        Fleet fleet = new Fleet();
        IShip candidate = mockShip("X", true);
        assertFalse(fleet.colisionRisk(candidate));

        IShip existing = mockShip("E", true);
        fleet.getShips().add(existing);

        when(existing.tooCloseTo(candidate)).thenReturn(true);
        assertTrue(fleet.colisionRisk(candidate));

        when(existing.tooCloseTo(candidate)).thenReturn(false);
        assertFalse(fleet.colisionRisk(candidate));
    }

    @Test
    void printStatus_includes_all_relevant_outputs() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream old = System.out;
        System.setOut(new PrintStream(baos));
        try {
            Fleet fleet = new Fleet();
            IShip g = mockShip("Galeao", true);
            IShip f = mockShip("Fragata", false);
            fleet.getShips().add(g);
            fleet.getShips().add(f);

            fleet.printStatus();
            String out = baos.toString();
            assertTrue(out.contains("Galeao-TOSTRING"));
            assertTrue(out.contains("Fragata-TOSTRING"));
            // Galeao is floating -> should appear also in floating output
            assertTrue(out.contains("Galeao-TOSTRING"));
        } finally {
            System.setOut(old);
        }
    }
}
