package org.kalah.game.rules;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kalah.game.Game;
import org.kalah.game.InitialGame;
import org.kalah.game.Pits;
import org.kalah.game.Status;

public class FinishedGameRuleTest {

    @Test
    public void TestPlayer1NoPebblesInPits() {

        // setup
        var pits = new Pits(InitialGame.initPits(InitialGame.NUMBER_OF_PITS, InitialGame.INITIAL_PIT_VALUE));
        for (var pitId = 1; pitId < InitialGame.NUMBER_OF_PITS; pitId++) {
            pits.getPits().put(pitId, 0);
        }
        var game = new Game(Status.IN_PROGRESS, pits);

        // act
        new FinishedGameRule().apply(game, 0, 0);

        // assert
        for (var pitId = 1; pitId < 2 * InitialGame.NUMBER_OF_PITS; pitId++) {
            Assertions.assertEquals(0, pits.getPits().get(pitId));
        }
        Assertions.assertEquals(36, pits.getPits().get(2 * InitialGame.NUMBER_OF_PITS));
        Assertions.assertEquals(Status.OVER, game.getStatus());
    }

    @Test
    public void TestPlayer1HasPebbleContentInPitsNotApplyFinishGame() {

        // setup
        var pits = new Pits(InitialGame.initPits(InitialGame.NUMBER_OF_PITS, InitialGame.INITIAL_PIT_VALUE));
        for (var pitId = 1; pitId < InitialGame.NUMBER_OF_PITS; pitId++) {
            pits.getPits().put(pitId, 0);
        }
        pits.getPits().put(1, 1);
        var game = new Game(Status.IN_PROGRESS, pits);

        // act
        new FinishedGameRule().apply(game, 0, 0);

        // assert
        Assertions.assertEquals(1, pits.getPits().get(1));
        for (var pitId = 2; pitId < InitialGame.NUMBER_OF_PITS; pitId++) {
            Assertions.assertEquals(0, pits.getPits().get(pitId));
        }
        for (var pitId = InitialGame.NUMBER_OF_PITS + 1; pitId < 2 * InitialGame.NUMBER_OF_PITS; pitId++) {
            Assertions.assertEquals(6, pits.getPits().get(pitId));
        }
        Assertions.assertEquals(0, pits.getPits().get(2 * InitialGame.NUMBER_OF_PITS));
        Assertions.assertEquals(Status.IN_PROGRESS, game.getStatus());
    }

    @Test
    public void TestPlayer2NoPebblesInPits() {

        // setup
        var pits = new Pits(InitialGame.initPits(InitialGame.NUMBER_OF_PITS, InitialGame.INITIAL_PIT_VALUE));
        for (var pitId = InitialGame.NUMBER_OF_PITS + 1; pitId < 2 * InitialGame.NUMBER_OF_PITS; pitId++) {
            pits.getPits().put(pitId, 0);
        }
        var game = new Game(Status.IN_PROGRESS, pits);

        // act
        new FinishedGameRule().apply(game, 0, 0);

        // assert
        for (var pitId = 1; pitId <= 2 * InitialGame.NUMBER_OF_PITS; pitId++) {
            if (pitId == InitialGame.NUMBER_OF_PITS) {
                continue;
            }
            Assertions.assertEquals(0, pits.getPits().get(pitId));
        }
        Assertions.assertEquals(InitialGame.INITIAL_PIT_VALUE * (InitialGame.NUMBER_OF_PITS - 1),
                game.getPits().get(InitialGame.NUMBER_OF_PITS));
        Assertions.assertEquals(Status.OVER, game.getStatus());
    }

    @Test
    public void TestPlayer2HasPebbleContentInPits() {

        // setup
        var pits = new Pits(InitialGame.initPits(InitialGame.NUMBER_OF_PITS, InitialGame.INITIAL_PIT_VALUE));
        for (var pitId = InitialGame.NUMBER_OF_PITS + 1; pitId < 2 * InitialGame.NUMBER_OF_PITS; pitId++) {
            pits.getPits().put(pitId, 0);
        }
        pits.getPits().put(InitialGame.NUMBER_OF_PITS + 1, 1);
        var game = new Game(Status.IN_PROGRESS, pits);

        // act
        new FinishedGameRule().apply(game, 0, 0);

        // assert
        Assertions.assertEquals(1, pits.getPits().get(InitialGame.NUMBER_OF_PITS + 1));
        for (var pitId = 1; pitId < InitialGame.NUMBER_OF_PITS; pitId++) {
            Assertions.assertEquals(6, pits.getPits().get(pitId));
        }
        for (var pitId = InitialGame.NUMBER_OF_PITS + 2; pitId <= 2 * InitialGame.NUMBER_OF_PITS; pitId++) {
            Assertions.assertEquals(0, pits.getPits().get(pitId));
        }
        Assertions.assertEquals(Status.IN_PROGRESS, game.getStatus());
    }
}