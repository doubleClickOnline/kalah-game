package org.kalah.game.rules;

import org.kalah.game.Game;
import org.kalah.game.InitialGame;
import org.kalah.game.Player;

/**
 *
 * Same player move
 */
public class SamePlayerGameRule implements Rule {

    @Override
    public void apply(Game game, Integer startPidId, Integer endPidId) {

        if (game.getActivePlayer() == null) {
            if (Player.PLAYER_1.isAllowedStartPitId(startPidId)) {
                game.setActivePlayer(Player.PLAYER_1);
            } else {
                game.setActivePlayer(Player.PLAYER_2);
            }
        }

        if (endPidId % InitialGame.NUMBER_OF_PITS != 0) {
            game.nextPlayer();
        }
    }
}
