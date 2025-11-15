package iscteiul.ista.battleship;

import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FleetTest {

    IShip mockShip(String category, boolean floating) {
        IShip ship = mock(IShip.class);
        when(ship.getCategory()).thenReturn(category);
        when(ship.stillFloating()).thenReturn(floating);
        return ship;
    }

    IPosition mockPosition() {
        return mock(IPosition.class);
    }

    @Test
    void printShips() {
        assertDoesNotThrow(() -> Fleet.printShips(List.of()));
    }

    @Test
    void getShips() {
        Fleet fleet = new Fleet();
        assertTrue(fleet.getShips().isEmpty());
    }

    @Test
    void addShip() {
        class TestFleet extends Fleet {
            @Override
            public boolean colisionRisk(IShip s) {
                return false;
            }
        }
        Fleet fleet = new TestFleet();

        for (int i = 0; i < Fleet.FLEET_SIZE; i++) {
            IShip ship = mock(IShip.class);
            when(ship.getLeftMostPos()).thenReturn(0);
            when(ship.getTopMostPos()).thenReturn(0);
            when(ship.getRightMostPos()).thenReturn(Fleet.BOARD_SIZE - 1);
            when(ship.getBottomMostPos()).thenReturn(Fleet.BOARD_SIZE - 1);
            assertTrue(fleet.addShip(ship));
        }
        // Agora tenta adicionar um extra, que deve falhar
        IShip extraShip = mock(IShip.class);
        when(extraShip.getLeftMostPos()).thenReturn(0);
        when(extraShip.getTopMostPos()).thenReturn(0);
        when(extraShip.getRightMostPos()).thenReturn(Fleet.BOARD_SIZE - 1);
        when(extraShip.getBottomMostPos()).thenReturn(Fleet.BOARD_SIZE - 1);
        assertFalse(fleet.addShip(extraShip));
    }


    @Test
    void getShipsLike() {
        Fleet fleet = new Fleet();
        IShip s1 = mockShip("Nau", true);
        IShip s2 = mockShip("Fragata", true);
        fleet.getShips().add(s1);
        fleet.getShips().add(s2);
        List<IShip> result = fleet.getShipsLike("Nau");
        assertEquals(1, result.size());
        assertEquals("Nau", result.get(0).getCategory());
    }

    @Test
    void getFloatingShips() {
        Fleet fleet = new Fleet();
        IShip s1 = mockShip("Nau", true);
        IShip s2 = mockShip("Fragata", false);
        fleet.getShips().add(s1);
        fleet.getShips().add(s2);
        List<IShip> result = fleet.getFloatingShips();
        assertEquals(1, result.size());
        assertTrue(result.get(0).stillFloating());
    }

    @Test
    void shipAt() {
        Fleet fleet = new Fleet();
        IShip s1 = mock(IShip.class);
        IPosition pos = mockPosition();
        when(s1.occupies(pos)).thenReturn(true);
        fleet.getShips().add(s1);
        assertEquals(s1, fleet.shipAt(pos));
    }

    @Test
    void printStatus() {
        Fleet fleet = new Fleet();
        assertDoesNotThrow(fleet::printStatus);
    }

    @Test
    void printShipsByCategory() {
        Fleet fleet = new Fleet();
        assertDoesNotThrow(() -> fleet.printShipsByCategory("Nau"));
    }

    @Test
    void printFloatingShips() {
        Fleet fleet = new Fleet();
        assertDoesNotThrow(fleet::printFloatingShips);
    }

    @Test
    void printAllShips() {
        Fleet fleet = new Fleet();
        assertDoesNotThrow(fleet::printAllShips);
    }
}