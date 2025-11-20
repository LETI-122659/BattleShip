package iscteiul.ista.battleship;

import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes unitários da classe Ship com 100% branch coverage")
class ShipTest {

    private Ship ship;
    private TestPosition pos1, pos2, pos3;

    // Implementação mínima de IPosition para testes
    static class TestPosition implements IPosition {
        private final int row;
        private final int col;
        private boolean hit;

        TestPosition(int row, int col) {
            this.row = row;
            this.col = col;
            this.hit = false;
        }

        @Override
        public int getRow() { return row; }

        @Override
        public int getColumn() { return col; }

        @Override
        public boolean isHit() { return hit; }

        @Override
        public void shoot() { hit = true; }

        @Override
        public boolean isOccupied() { return false; }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof IPosition)) return false;
            IPosition other = (IPosition) o;
            return this.row == other.getRow() && this.col == other.getColumn();
        }

        @Override
        public boolean isAdjacentTo(IPosition pos) {
            int dr = Math.abs(this.row - pos.getRow());
            int dc = Math.abs(this.col - pos.getColumn());
            return dr <= 1 && dc <= 1 && !(dr == 0 && dc == 0);
        }

        @Override
        public void occupy() {}

        @Override
        public String toString() { return "(" + row + "," + col + ")"; }
    }

    // Subclasse concreta de Ship
    static class TestShip extends Ship {
        public TestShip(String category, Compass bearing, IPosition pos) {
            super(category, bearing, pos);
        }

        @Override
        public Integer getSize() {
            return positions.size();
        }
    }

    @BeforeEach
    void setUp() {
        pos1 = new TestPosition(1, 1);
        pos2 = new TestPosition(2, 2);
        pos3 = new TestPosition(3, 3);

        ship = new TestShip("fragata", Compass.NORTH, pos1);
        ship.getPositions().addAll(List.of(pos1, pos2, pos3));
    }

    @AfterEach
    void tearDown() {
        ship = null;
    }

    @Test
    @TmsLink("TC-SHIP-001")
    @DisplayName("buildShip() cria instâncias corretas")
    void buildShip() {
        Position pos = new Position(0,0);
        assertTrue(Ship.buildShip("barca", Compass.NORTH, pos) instanceof Barge);
        assertTrue(Ship.buildShip("caravela", Compass.NORTH, pos) instanceof Caravel);
        assertTrue(Ship.buildShip("nau", Compass.NORTH, pos) instanceof Carrack);
        assertTrue(Ship.buildShip("fragata", Compass.NORTH, pos) instanceof Frigate);
        assertTrue(Ship.buildShip("galeao", Compass.NORTH, pos) instanceof Galleon);
        assertNull(Ship.buildShip("invalido", Compass.NORTH, pos));
    }

    @Test
    @TmsLink("TC-SHIP-002")
    @DisplayName("getCategory, getBearing e getPosition")
    void getters() {
        assertEquals("fragata", ship.getCategory());
        assertEquals(Compass.NORTH, ship.getBearing());
        assertEquals(pos1, ship.getPosition());
    }

    @Test
    @TmsLink("TC-SHIP-003")
    @DisplayName("getPositions retorna lista completa")
    void getPositions() {
        assertEquals(3, ship.getPositions().size());
        assertTrue(ship.getPositions().containsAll(List.of(pos1, pos2, pos3)));
    }

    @Test
    @TmsLink("TC-SHIP-004")
    @DisplayName("stillFloating() retorna true/false corretamente")
    void stillFloating() {
        assertTrue(ship.stillFloating());
        pos1.shoot();
        pos2.shoot();
        pos3.shoot();
        assertFalse(ship.stillFloating());
    }

    @Test
    @TmsLink("TC-SHIP-005")
    @DisplayName("stillFloating() com lista vazia retorna false")
    void stillFloatingEmpty() {
        TestShip emptyShip = new TestShip("barca", Compass.EAST, new TestPosition(0,0));
        emptyShip.getPositions().clear();
        assertFalse(emptyShip.stillFloating());
    }

    @Test
    @TmsLink("TC-SHIP-006")
    @DisplayName("getTopMostPos, getBottomMostPos, getLeftMostPos, getRightMostPos")
    void positionBoundaries() {
        assertEquals(1, ship.getTopMostPos());
        assertEquals(3, ship.getBottomMostPos());
        assertEquals(1, ship.getLeftMostPos());
        assertEquals(3, ship.getRightMostPos());
    }

    @Test
    @TmsLink("TC-SHIP-007")
    @DisplayName("occupies() identifica corretamente as posições")
    void occupies() {
        assertTrue(ship.occupies(pos1));
        assertFalse(ship.occupies(new TestPosition(10,10)));
        assertFalse(ship.occupies(null)); // branch de posição null agora coberta
    }


    @Test
    @TmsLink("TC-SHIP-008")
    @DisplayName("tooCloseTo(IPosition) detecta proximidade")
    void tooCloseToPosition() {
        assertTrue(ship.tooCloseTo(new TestPosition(2,3)));
        assertFalse(ship.tooCloseTo(new TestPosition(10,10)));
    }

    @Test
    @TmsLink("TC-SHIP-009")
    @DisplayName("tooCloseTo(IShip) detecta proximidade entre navios")
    void tooCloseToShip() {
        Ship other = new TestShip("caravela", Compass.SOUTH, new TestPosition(4,3));
        other.getPositions().add(new TestPosition(4,3));
        assertTrue(ship.tooCloseTo(other));

        Ship distant = new TestShip("barca", Compass.EAST, new TestPosition(10,10));
        distant.getPositions().add(new TestPosition(10,10));
        assertFalse(ship.tooCloseTo(distant));

        // branch: navio sem posições
        Ship empty = new TestShip("nau", Compass.WEST, new TestPosition(0,0));
        empty.getPositions().clear();
        assertFalse(ship.tooCloseTo(empty));
    }

    @Test
    @TmsLink("TC-SHIP-010")
    @DisplayName("shoot() marca posição correta")
    void shoot() {
        ship.shoot(pos2);
        assertTrue(pos2.isHit());
        assertFalse(pos1.isHit());
    }

    @Test
    @TmsLink("TC-SHIP-011")
    @DisplayName("shoot() com posição não pertencente ao navio não altera hits")
    void shootNonExisting() {
        TestPosition notOnShip = new TestPosition(99, 99);
        ship.shoot(notOnShip);
        assertFalse(pos1.isHit());
        assertFalse(pos2.isHit());
        assertFalse(pos3.isHit());
    }

    @Test
    @TmsLink("TC-SHIP-012")
    @DisplayName("shoot() com lista vazia não lança exceção")
    void shootEmptyList() {
        TestShip emptyShip = new TestShip("barca", Compass.EAST, new TestPosition(0,0));
        emptyShip.getPositions().clear();
        assertDoesNotThrow(() -> emptyShip.shoot(new TestPosition(0,0)));
    }

    @Test
    @TmsLink("TC-SHIP-013")
    @DisplayName("toString() contém categoria, direção e posição inicial")
    void testToString() {
        String result = ship.toString();
        assertTrue(result.contains("fragata"));
        assertTrue(result.contains(String.valueOf(Compass.NORTH.getDirection()))); // 'n'
        assertTrue(result.contains(pos1.toString()));
    }
}
