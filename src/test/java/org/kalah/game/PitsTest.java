package org.kalah.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kalah.game.exceptions.IllegalPitIdException;

public class PitsTest {

    @Test
    public void testMakeMoveFromMissingPit() {
        var pits = new Pits(InitialGame.initPits(InitialGame.NUMBER_OF_PITS, InitialGame.INITIAL_PIT_VALUE));
        Exception exception = Assertions.assertThrows(IllegalPitIdException.class, () -> {
            pits.move(2 * InitialGame.NUMBER_OF_PITS + 1);
        });
        Assertions.assertEquals(exception.getMessage(), String.format("Invalid pit id: %d", 2 * InitialGame.NUMBER_OF_PITS + 1));
    }

    @Test
    public void testMakeMoveFromPlayerHomePit() {
        var pits = new Pits(InitialGame.initPits(InitialGame.NUMBER_OF_PITS, InitialGame.INITIAL_PIT_VALUE));
        Exception exception = Assertions.assertThrows(IllegalPitIdException.class, () -> {
            pits.move(InitialGame.NUMBER_OF_PITS);
        });
        Assertions.assertEquals(exception.getMessage(), String.format("Player's home pit id: %d", InitialGame.NUMBER_OF_PITS));
    }

    @Test
    public void testMakeMoveFromFirstPit() throws IllegalPitIdException {
        var pits = new Pits(InitialGame.initPits(InitialGame.NUMBER_OF_PITS, InitialGame.INITIAL_PIT_VALUE));
        var endPitId = pits.move(1);
        Assertions.assertEquals(InitialGame.NUMBER_OF_PITS, endPitId);
    }

    @Test
    public void testMakeMoveFromLastAllowedPit() throws IllegalPitIdException {
        var pits = new Pits(InitialGame.initPits(InitialGame.NUMBER_OF_PITS, InitialGame.INITIAL_PIT_VALUE));
        var endPitId = pits.move(2 * InitialGame.NUMBER_OF_PITS - 1);
        Assertions.assertEquals(InitialGame.NUMBER_OF_PITS - 2, endPitId);
    }

    @Test
    public void testMakeMoveSkipPlayer1HomePit() throws IllegalPitIdException {
        var pits = new Pits(InitialGame.initPits(InitialGame.NUMBER_OF_PITS, InitialGame.INITIAL_PIT_VALUE));
        var startPitId = 1;
        pits.getPits().put(startPitId, 2 * InitialGame.NUMBER_OF_PITS);
        var endPitId = pits.move(startPitId);
        Assertions.assertEquals(2, endPitId);
    }

    @Test
    public void testMakeMoveSkipPlayer2HomePit() throws IllegalPitIdException {
        var pits = new Pits(InitialGame.initPits(InitialGame.NUMBER_OF_PITS, InitialGame.INITIAL_PIT_VALUE));
        var startPitId = InitialGame.NUMBER_OF_PITS + 2;
        pits.getPits().put(startPitId, 2 * InitialGame.INITIAL_PIT_VALUE);
        var endPitId = pits.move(startPitId);
        Assertions.assertEquals(8, endPitId);
    }
}
