package org.kalah.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kalah.game.exceptions.IllegalPitIdException;

public class GameTest {

    @Test
    public void testIsAllowedPlayer1MakeMoveInInitialGame() {
        var game = InitialGame.createGame();
        Assertions.assertTrue(game.isAllowedPlayerMakeMove(1));
    }

    @Test
    public void testIsAllowedPlayer2MakeMoveInInitialGame() {
        var game = InitialGame.createGame();
        Assertions.assertTrue(game.isAllowedPlayerMakeMove(InitialGame.NUMBER_OF_PITS + 1));
    }

    @Test
    public void testIsAllowedPlayer1MakeMoveWhenOtherPlayerTurn() {
        var game = InitialGame.createGame();
        game.setActivePlayer(Player.PLAYER_2);
        Assertions.assertFalse(game.isAllowedPlayerMakeMove(1));
    }

    @Test
    public void testIsAllowedPlayer2MakeMoveWhenOtherPlayerTurn() {
        var game = InitialGame.createGame();
        game.setActivePlayer(Player.PLAYER_1);
        Assertions.assertFalse(game.isAllowedPlayerMakeMove(InitialGame.NUMBER_OF_PITS + 1));
    }

    @Test
    public void testIsOverInitialGame() {
        var game = InitialGame.createGame();
        Assertions.assertFalse(game.isOver());
    }

    @Test
    public void testIsOverFinishedGame() {
        var game = new Game(Status.IN_PROGRESS,
                new Pits(InitialGame.initPits(InitialGame.NUMBER_OF_PITS, 0)));
        Assertions.assertFalse(game.isOver());
    }

    @Test
    public void testIsOverFinishedGame2() throws IllegalPitIdException {
        var game = new Game(Status.IN_PROGRESS,
                new Pits(InitialGame.initPits(InitialGame.NUMBER_OF_PITS, 0)));

        game.getPits().put(1, 1);
        var pits = game.makeMove(1);

        pits.getPits()
                .entrySet()
                .stream()
                .filter(pit -> pit.getKey() != InitialGame.NUMBER_OF_PITS)
                .forEach( pit -> Assertions.assertEquals(0, pit.getValue()));
        Assertions.assertEquals(1, pits.getPits().get(InitialGame.NUMBER_OF_PITS));

        Assertions.assertEquals(Player.PLAYER_2, game.getActivePlayer());
        Assertions.assertTrue(game.isOver());
    }
}
