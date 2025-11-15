package iscteiul.ista.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FrigateTest {

    @Test
    void getSize() {
        Frigate f = new Frigate(Compass.NORTH, new Position(1, 1));
        assertEquals(Integer.valueOf(4), f.getSize());
    }
}