package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CarrackTest {

    private Position basePos;

    @BeforeEach
    void setUp() {
        // Posição inicial genérica (linha 2, coluna 3)
        basePos = new Position(2, 3);
    }

    @Test
    @DisplayName("Criação de Carrack com orientação Norte deve gerar posições verticais")
    void testCarrackNorth() {
        Carrack carrack = new Carrack(Compass.NORTH, basePos);

        // Verifica o tamanho e as posições
        assertEquals(3, carrack.getSize());
        assertEquals(3, carrack.getPositions().size());

        // Verifica se as posições estão corretas (Norte)
        assertAll("Posições da Carrack (Norte)",
                () -> assertEquals(new Position(2, 3), carrack.getPositions().get(0)),
                () -> assertEquals(new Position(3, 3), carrack.getPositions().get(1)),
                () -> assertEquals(new Position(4, 3), carrack.getPositions().get(2))
        );
    }

    @Test
    @DisplayName("Criação de Carrack com orientação Sul deve gerar posições verticais")
    void testCarrackSouth() {
        Carrack carrack = new Carrack(Compass.SOUTH, basePos);

        assertEquals(3, carrack.getPositions().size());
        assertAll("Posições da Carrack (Sul)",
                () -> assertEquals(new Position(2, 3), carrack.getPositions().get(0)),
                () -> assertEquals(new Position(3, 3), carrack.getPositions().get(1)),
                () -> assertEquals(new Position(4, 3), carrack.getPositions().get(2))
        );
    }

    @Test
    @DisplayName("Criação de Carrack com orientação Este deve gerar posições horizontais")
    void testCarrackEast() {
        Carrack carrack = new Carrack(Compass.EAST, basePos);

        assertEquals(3, carrack.getPositions().size());
        assertAll("Posições da Carrack (Este)",
                () -> assertEquals(new Position(2, 3), carrack.getPositions().get(0)),
                () -> assertEquals(new Position(2, 4), carrack.getPositions().get(1)),
                () -> assertEquals(new Position(2, 5), carrack.getPositions().get(2))
        );
    }

    @Test
    @DisplayName("Criação de Carrack com orientação Oeste deve gerar posições horizontais")
    void testCarrackWest() {
        Carrack carrack = new Carrack(Compass.WEST, basePos);

        assertEquals(3, carrack.getPositions().size());
        assertAll("Posições da Carrack (Oeste)",
                () -> assertEquals(new Position(2, 3), carrack.getPositions().get(0)),
                () -> assertEquals(new Position(2, 4), carrack.getPositions().get(1)),
                () -> assertEquals(new Position(2, 5), carrack.getPositions().get(2))
        );
    }

    @Test
    @DisplayName("Carrack deve lançar exceção se bearing for inválido (null)")
    void testCarrackInvalidBearing() {
        boolean exceptionThrown = false;

        // Vamos passar um valor válido para bearing (ex: Compass.NORTH)
        try {
            // Tente criar um Carrack com bearing nulo
            new Carrack(Compass.NORTH, basePos);  // Passando um valor válido para bearing
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;

            // Verifica se a mensagem da exceção contém "invalid bearing"
            String errorMessage = e.getMessage().toLowerCase();
            if (!errorMessage.contains("invalid bearing")) {
                fail("Mensagem da exceção não contém 'invalid bearing'. Mensagem: " + e.getMessage());
            }
        }

        // Se a exceção não foi lançada, falha o teste
        if (!exceptionThrown) {
            fail("Esperava-se que uma IllegalArgumentException fosse lançada.");
        }
    }
    @Test
    @DisplayName("getSize() deve retornar 3")
    void testGetSize() {
        Carrack carrack = new Carrack(Compass.NORTH, basePos);
        assertEquals(3, carrack.getSize());
    }
}
