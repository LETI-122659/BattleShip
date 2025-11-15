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
    @DisplayName("Caravel deve lançar NullPointerException para bearing nulo")
    void testInvalidBearingNull() {
        // Espera-se que a criação de Caravel com bearing nulo lance uma NullPointerException
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new Caravel(null, basePos);  // Passando null como bearing
        });

        // Verifica se a mensagem contém "invalid bearing"
        String errorMessage = exception.getMessage().toLowerCase();
        assertTrue(errorMessage.contains("invalid bearing"), "A mensagem da exceção não contém 'invalid bearing'.");
    }

    @Test
    @DisplayName("Caravel deve lançar IllegalArgumentException para bearing inválido")
    void testInvalidBearingInvalidDirection() {
        // Espera-se que a criação de Caravel com uma direção inválida lance uma IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Caravel(Compass.valueOf("INVALID_DIRECTION"), basePos);  // Passando uma direção inválida
        });

        // Verifica se a mensagem contém "invalid bearing"
        String errorMessage = exception.getMessage().toLowerCase();
        assertTrue(errorMessage.contains("invalid bearing"), "A mensagem da exceção não contém 'invalid bearing'.");
    }
}
