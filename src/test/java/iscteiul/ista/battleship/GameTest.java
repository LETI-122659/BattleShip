package iscteiul.ista.battleship;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

class GameTest {

    private IFleet fleet;
    private Game game;
    private IPosition pos;
    private IShip ship;

    @BeforeEach
    void setUp() {
        fleet = mock(IFleet.class);
        game = new Game(fleet);

        pos = mock(IPosition.class);
        ship = mock(IShip.class);

        when(pos.getRow()).thenReturn(2);
        when(pos.getColumn()).thenReturn(3);
    }

    @AfterEach
    void tearDown() {
        fleet = null;
        game = null;
        pos = null;
        ship = null;
    }
    @Test
    void testRepeatedShot() {
        when(fleet.shipAt(pos)).thenReturn(null);

        game.fire(pos); // first time
        game.fire(pos); // repeated

        assertEquals(1, game.getRepeatedShots());
        assertEquals(1, game.getShots().size());
    }

    @Test
    void testHit() {
        when(fleet.shipAt(pos)).thenReturn(ship);
        when(ship.stillFloating()).thenReturn(true);

        IShip result = game.fire(pos);

        verify(ship).shoot(pos); // confirm hit registered
        assertEquals(1, game.getHits());
        assertNull(result); // ship not sunk
    }

    @Test
    void testMiss() {
        when(fleet.shipAt(pos)).thenReturn(null);

        IShip result = game.fire(pos);

        assertEquals(0, game.getHits());
        assertNull(result);
    }

    @Test
    void testPrintValidShots() {
        when(fleet.shipAt(pos)).thenReturn(null);

        game.fire(pos);

        assertDoesNotThrow(() -> game.printValidShots());
    }

    @Test
    void testPrintFleet() {
        IShip s = mock(IShip.class);
        IPosition p = mock(IPosition.class);

        when(p.getRow()).thenReturn(1);
        when(p.getColumn()).thenReturn(2);

        when(s.getPositions()).thenReturn(List.of(p));
        when(fleet.getShips()).thenReturn(List.of(s));

        assertDoesNotThrow(() -> game.printFleet());
    }

    @Test
    void testShotAtBoardEdgeValid() {
        // pos = (BOARD_SIZE, BOARD_SIZE) → dentro dos limites segundo o teu código
        when(pos.getRow()).thenReturn(Fleet.BOARD_SIZE);
        when(pos.getColumn()).thenReturn(Fleet.BOARD_SIZE);

        when(fleet.shipAt(pos)).thenReturn(null);

        assertDoesNotThrow(() -> game.fire(pos));
        assertEquals(1, game.getShots().size());
    }

    @Test
    void testShotOutsideBoardRowTooLarge() {
        when(pos.getRow()).thenReturn(Fleet.BOARD_SIZE + 1);
        when(pos.getColumn()).thenReturn(0);

        game.fire(pos);

        assertEquals(1, game.getInvalidShots());
        assertTrue(game.getShots().isEmpty());
    }

    @Test
    void testShotOutsideBoardColumnTooLarge() {
        when(pos.getRow()).thenReturn(0);
        when(pos.getColumn()).thenReturn(Fleet.BOARD_SIZE + 1);

        game.fire(pos);

        assertEquals(1, game.getInvalidShots());
        assertTrue(game.getShots().isEmpty());
    }

    @Test
    void testShotWithNegativeColumn() {
        when(pos.getRow()).thenReturn(4);
        when(pos.getColumn()).thenReturn(-5);

        game.fire(pos);

        assertEquals(1, game.getInvalidShots());
        assertEquals(0, game.getShots().size());
    }
    @Test
    void testGetRemainingShips() {
        IShip s1 = mock(IShip.class);
        IShip s2 = mock(IShip.class);
        IShip s3 = mock(IShip.class);

        // Simula que ainda existem 3 navios a flutuar
        when(fleet.getFloatingShips()).thenReturn(List.of(s1, s2, s3));

        int remaining = game.getRemainingShips();

        assertEquals(3, remaining);
        verify(fleet, times(1)).getFloatingShips();
    }

    @Test
    void testGetRemainingShips_NoMock() {
        IFleet realFleet = mock(IFleet.class);
        when(realFleet.getFloatingShips()).thenReturn(List.of());

        Game g = new Game(realFleet);

        assertDoesNotThrow(g::getRemainingShips);
        assertEquals(0, g.getRemainingShips());
        verify(realFleet, atLeastOnce()).getFloatingShips();
    }
}
