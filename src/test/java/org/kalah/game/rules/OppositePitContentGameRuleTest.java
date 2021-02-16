package org.kalah.game.rules;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kalah.game.Game;
import org.kalah.game.InitialGame;
import org.kalah.game.Pits;

public class OppositePitContentGameRuleTest {

    @Test
    public void TestFinishMoveOnSamePlayer1Pit() {
        var game = finishMoveOnSamePlayerPit(1, 2);
        Assertions.assertEquals(InitialGame.INITIAL_PIT_VALUE + 1, game.getPits().get(InitialGame.NUMBER_OF_PITS));
        Assertions.assertEquals(InitialGame.INITIAL_PLAYER_PIT_VALUE, game.getPits().get(2 * InitialGame.NUMBER_OF_PITS));
    }

    @Test
    public void TestFinishMoveOnSamePlayer2Pit() {
        var game = finishMoveOnSamePlayerPit(InitialGame.NUMBER_OF_PITS + 1, InitialGame.NUMBER_OF_PITS + 2);
        Assertions.assertEquals(InitialGame.INITIAL_PIT_VALUE + 1, game.getPits().get(2 * InitialGame.NUMBER_OF_PITS));
        Assertions.assertEquals(InitialGame.INITIAL_PLAYER_PIT_VALUE, game.getPits().get(InitialGame.NUMBER_OF_PITS));
    }

    private Game finishMoveOnSamePlayerPit(Integer startPitId, Integer endPitId) {
        // setup
        var game = new Game(null, new Pits(InitialGame.initPits(InitialGame.NUMBER_OF_PITS, InitialGame.INITIAL_PIT_VALUE)));
        game.getPits().put(endPitId, 1);

        // act
        new OppositePitContentGameRule().apply(game, startPitId, endPitId);

        // assert
        game.getPits()
                .entrySet()
                .stream()
                .filter(pit -> !pit.getKey().equals(endPitId))
                .filter(pit -> pit.getKey() != InitialGame.NUMBER_OF_PITS * 2 - endPitId)
                .filter(pit -> pit.getKey() % InitialGame.NUMBER_OF_PITS != 0) // skip players home pits
                .forEach(pit -> Assertions.assertEquals(InitialGame.INITIAL_PIT_VALUE, pit.getValue()));

        Assertions.assertEquals(InitialGame.INITIAL_PLAYER_PIT_VALUE, game.getPits().get(endPitId));
        Assertions.assertEquals(InitialGame.INITIAL_PLAYER_PIT_VALUE, game.getPits().get(InitialGame.NUMBER_OF_PITS * 2 - endPitId));
        return game;
    }

    @Test
    public void TestFinishMoveOnSamePlayer1HomePit() {
        var startPitId = 1;
        var endPitId = InitialGame.NUMBER_OF_PITS;
        var game = finishMoveOnSamePlayerHomePit(startPitId, endPitId);

        Assertions.assertEquals(0, game.getPits().get(2 * InitialGame.NUMBER_OF_PITS));
        Assertions.assertEquals(1, game.getPits().get(endPitId));
    }

    @Test
    public void TestFinishMoveOnSamePlayer2HomePit() {
        var startPitId = InitialGame.NUMBER_OF_PITS + 1;
        var endPitId =  2 * InitialGame.NUMBER_OF_PITS;
        var game = finishMoveOnSamePlayerHomePit(startPitId, endPitId);

        Assertions.assertEquals(0, game.getPits().get(InitialGame.NUMBER_OF_PITS));
        Assertions.assertEquals(1, game.getPits().get(endPitId));
    }

    public Game finishMoveOnSamePlayerHomePit(Integer startPitId, Integer endPitId) {
        // setup
        var game = new Game(null, new Pits(InitialGame.initPits(InitialGame.NUMBER_OF_PITS, InitialGame.INITIAL_PIT_VALUE)));
        game.getPits().put(endPitId, 1);

        // act
        new OppositePitContentGameRule().apply(game, startPitId, endPitId);

        // assert
        game.getPits()
                .entrySet()
                .stream()
                .filter(pit -> pit.getKey() % InitialGame.NUMBER_OF_PITS != 0) // skip players home pits
                .forEach(pit -> Assertions.assertEquals(InitialGame.INITIAL_PIT_VALUE, pit.getValue()));
        return game;
    }

    @Test
    public void TestFinishMoveOnSamePlayerNotEmptyPit() {

        // setup
        var endPitId = 2;
        var game = new Game(null, new Pits(InitialGame.initPits(InitialGame.NUMBER_OF_PITS, InitialGame.INITIAL_PIT_VALUE)));
        game.getPits().put(endPitId, 2);

        // act
        new OppositePitContentGameRule().apply(game, 1, endPitId);

        // assert
        game.getPits()
                .entrySet()
                .stream()
                .filter(pit -> !pit.getKey().equals(endPitId))
                .filter(pit -> pit.getKey() != InitialGame.NUMBER_OF_PITS * 2 - endPitId)
                .filter(pit -> pit.getKey() % InitialGame.NUMBER_OF_PITS != 0) // skip players home pits
                .forEach(pit -> Assertions.assertEquals(InitialGame.INITIAL_PIT_VALUE, pit.getValue()));

        Assertions.assertEquals(2, game.getPits().get(endPitId));
        Assertions.assertEquals(InitialGame.INITIAL_PIT_VALUE, game.getPits().get(InitialGame.NUMBER_OF_PITS * 2 - endPitId));
        Assertions.assertEquals(InitialGame.INITIAL_PLAYER_PIT_VALUE, game.getPits().get(InitialGame.NUMBER_OF_PITS));
        Assertions.assertEquals(InitialGame.INITIAL_PLAYER_PIT_VALUE, game.getPits().get(2 * InitialGame.NUMBER_OF_PITS));
    }

    @Test
    public void TestFinishMoveOnOtherPlayer2EmptyPit() {
        finishMoveOnOtherPlayerEmptyPit(1, InitialGame.NUMBER_OF_PITS + 1);
    }

    @Test
    public void TestFinishMoveOnOtherPlayer1EmptyPit() {
        finishMoveOnOtherPlayerEmptyPit(InitialGame.NUMBER_OF_PITS + 1, 1);
    }

    public void finishMoveOnOtherPlayerEmptyPit(Integer startPitId, Integer endPitId) {

        // setup
        var game = new Game(null, new Pits(InitialGame.initPits(InitialGame.NUMBER_OF_PITS, InitialGame.INITIAL_PIT_VALUE)));
        game.getPits().put(endPitId, 1);

        // act
        new OppositePitContentGameRule().apply(game, startPitId, endPitId);

        // assert
        game.getPits()
                .entrySet()
                .stream()
                .filter(pit -> !pit.getKey().equals(endPitId))
                .filter(pit -> pit.getKey() != InitialGame.NUMBER_OF_PITS * 2 - endPitId)
                .filter(pit -> pit.getKey() % InitialGame.NUMBER_OF_PITS != 0) // skip players home pits
                .forEach(pit -> Assertions.assertEquals(InitialGame.INITIAL_PIT_VALUE, pit.getValue()));

        Assertions.assertEquals(1, game.getPits().get(endPitId));
        Assertions.assertEquals(InitialGame.INITIAL_PIT_VALUE, game.getPits().get(InitialGame.NUMBER_OF_PITS * 2 - endPitId));
        Assertions.assertEquals(InitialGame.INITIAL_PLAYER_PIT_VALUE, game.getPits().get(InitialGame.NUMBER_OF_PITS));
        Assertions.assertEquals(InitialGame.INITIAL_PLAYER_PIT_VALUE, game.getPits().get(2 * InitialGame.NUMBER_OF_PITS));
    }
}
