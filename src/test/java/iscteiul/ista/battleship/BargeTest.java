package iscteiul.ista.battleship;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BargeTest {

    private IPosition basePos;

    @BeforeEach
    void setUp() {
        // Criando uma posição inicial para a barca (por exemplo, na posição 0,0)
        basePos = new Position(0, 0);
    }

    @Test
    @DisplayName("Barge deve lançar exceção para bearing nulo")
    void testInvalidBearing() {
        // Espera-se que a criação de Barge com bearing inválido (nulo, por exemplo) lance uma IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Barge(null, basePos);  // Passando null para bearing
        });

        // Verifica se a mensagem contém a string "invalid bearing"
        String errorMessage = exception.getMessage().toLowerCase();
        assertTrue(errorMessage.contains("bearing"), "A mensagem de erro não contém 'bearing'.");
    }

    @Test
    @DisplayName("Barge deve ter tamanho 1")
    void testGetSize() {
        // Criar a instância da Barge com uma direção válida (NORTE, por exemplo)
        Barge barge = new Barge(Compass.NORTH, basePos);

        // Verificar se o tamanho da Barge é 1
        assertEquals(1, barge.getSize(), "O tamanho da Barge deveria ser 1.");
    }

    @Test
    @DisplayName("Barge deve adicionar a posição correta")
    void testGetPositions() {
        // Criando a instância da Barge
        Barge barge = new Barge(Compass.NORTH, basePos);

        // Verificar se a posição foi corretamente adicionada
        assertEquals(1, barge.getPositions().size(), "A Barge deveria ter uma posição na lista.");
        assertEquals(basePos, barge.getPositions().get(0), "A posição da Barge não está correta.");
    }
}
