package org.kalah.game.rules;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kalah.game.Game;
import org.kalah.game.Player;
import org.kalah.game.Status;

public class SamePlayerGameRuleTest {

    @Test
    public void testSamePlayer1Move() {

        var game = new Game(Status.IN_PROGRESS, null); // setup
        new SamePlayerGameRule().apply(game, 1, 7); // act
        Assertions.assertEquals(Player.PLAYER_1, game.getActivePlayer()); //assert
    }

    @Test
    public void testSamePlayer2Move() {

        var game = new Game(Status.IN_PROGRESS, null); // setup
        new SamePlayerGameRule().apply(game, 8, 14); // act
        Assertions.assertEquals(Player.PLAYER_2, game.getActivePlayer()); //assert
    }

    @Test
    public void testNextPlayerMove() {

        var game = new Game(Status.IN_PROGRESS, null); // setup
        new SamePlayerGameRule().apply(game, 8, 13); // act
        Assertions.assertEquals(Player.PLAYER_1, game.getActivePlayer()); //assert
    }

    @Test
    public void testNextPlayerMove2() {

        var game = new Game(Status.IN_PROGRESS,null); // setup
        new SamePlayerGameRule().apply(game, 2, 9); // act
        Assertions.assertEquals(Player.PLAYER_2, game.getActivePlayer()); //assert
    }
}
