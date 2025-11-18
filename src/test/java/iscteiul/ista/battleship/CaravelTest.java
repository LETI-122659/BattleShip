package iscteiul.ista.battleship;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CaravelTest {

    private IPosition basePos;

    @BeforeEach
    void setUp() {
        // Inicializa uma posição básica (exemplo: posição (0, 0))
        basePos = new Position(0, 0);
    }

    @Test
    @DisplayName("Caravel deve ter tamanho 2")
    void testGetSize() {
        // Cria uma instância da Caravel com bearing válido (exemplo: NORTH)
        Caravel caravel = new Caravel(Compass.NORTH, basePos);

        // Verifica se o tamanho da Caravel é 2
        assertEquals(2, caravel.getSize(), "O tamanho da Caravel deveria ser 2.");
    }

    @Test
    @DisplayName("Caravel deve adicionar 2 posições corretamente")
    void testGetPositions() {
        // Cria a instância da Caravel com uma direção válida
        Caravel caravel = new Caravel(Compass.NORTH, basePos);

        // Verifica se a Caravel possui exatamente 2 posições
        assertEquals(2, caravel.getPositions().size(), "A Caravel deveria ter 2 posições.");

        // Verifica se a primeira posição é a basePos e a segunda está na linha abaixo
        assertEquals(basePos, caravel.getPositions().get(0), "A primeira posição da Caravel não está correta.");
        assertEquals(new Position(basePos.getRow() + 1, basePos.getColumn()), caravel.getPositions().get(1),
                "A segunda posição da Caravel não está correta.");
    }
    @Test
    @DisplayName("Caravel ainda flutuando quando todas posições intactas")
    void testStillFloatingAllIntact() {
        Caravel caravel = new Caravel(Compass.NORTH, basePos);
        assertTrue(caravel.stillFloating());
    }

    @Test
    @DisplayName("Caravel não flutuando quando todas posições atingidas")
    void testStillFloatingAllHit() {
        Caravel caravel = new Caravel(Compass.NORTH, basePos);
        caravel.getPositions().forEach(IPosition::shoot);
        assertFalse(caravel.stillFloating());
    }

    @Test
    @DisplayName("Caravel ainda flutuando quando apenas uma posição atingida")
    void testStillFloatingPartialHit() {
        Caravel caravel = new Caravel(Compass.NORTH, basePos);
        caravel.getPositions().get(0).shoot();
        assertTrue(caravel.stillFloating());
    }

    @Test
    @DisplayName("Testa limites da Caravel")
    void testLimits() {
        Caravel caravel = new Caravel(Compass.EAST, basePos);
        assertEquals(0, caravel.getTopMostPos());
        assertEquals(0, caravel.getBottomMostPos());
        assertEquals(0, caravel.getLeftMostPos());
        assertEquals(1, caravel.getRightMostPos());
    }

    @Test
    @DisplayName("occupies retorna true para posição do navio")
    void testOccupiesTrue() {
        Caravel caravel = new Caravel(Compass.NORTH, basePos);
        assertTrue(caravel.occupies(basePos));
    }

    @Test
    @DisplayName("occupies retorna false para posição fora do navio e null")
    void testOccupiesFalse() {
        Caravel caravel = new Caravel(Compass.NORTH, basePos);
        assertFalse(caravel.occupies(new Position(10,10)));
        assertFalse(caravel.occupies(null));
    }

    @Test
    @DisplayName("Caravel posiciona corretamente quando EAST")
    void testPositionsEast() {
        Caravel caravel = new Caravel(Compass.EAST, basePos);
        assertEquals(new Position(0,0), caravel.getPositions().get(0));
        assertEquals(new Position(0,1), caravel.getPositions().get(1));
    }

    @Test
    @DisplayName("Caravel posiciona corretamente quando WEST")
    void testPositionsWest() {
        Caravel caravel = new Caravel(Compass.WEST, basePos);
        assertEquals(new Position(0,0), caravel.getPositions().get(0));
        assertEquals(new Position(0,1), caravel.getPositions().get(1));
    }
    @Test
    void positions_whenSouth_areCorrect() {
        IPosition base = new Position(3, 4);
        Caravel caravel = new Caravel(Compass.SOUTH, base);
        assertEquals(2, caravel.getPositions().size(), "Deveria ter 2 posições");
        assertEquals(base, caravel.getPositions().get(0));
        assertEquals(new Position(base.getRow() + 1, base.getColumn()), caravel.getPositions().get(1));
    }


}
