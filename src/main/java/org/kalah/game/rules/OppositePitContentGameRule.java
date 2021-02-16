package org.kalah.game.rules;

import org.kalah.game.Game;
import org.kalah.game.InitialGame;

/**
 *
 * If last stone end up in empty same player's (except home pit, then player just gets another turn) pit,
 * player should take opposite(other's player) pit content
 */
public class OppositePitContentGameRule implements Rule {

    @Override
    public void apply(Game game, Integer startPidId, Integer endPidId) {

        if (game.getPits().get(endPidId) != 1) { // last pebble wasn't put to empty pit
            return;
        }

        if (endPidId % InitialGame.NUMBER_OF_PITS == 0) { // end up in player's home pit
            return;
        }

        // end should be on same player's pit and not player's home pit
        if (startPidId <= InitialGame.NUMBER_OF_PITS && endPidId >= InitialGame.NUMBER_OF_PITS) {
            return;
        }

        // end should be on same player's pit and not player's home pit
        if (startPidId > InitialGame.NUMBER_OF_PITS && endPidId <= InitialGame.NUMBER_OF_PITS) {
            return;
        }

        var pits = game.getPits();
        var endPitContent = pits.get(endPidId);
        pits.put(endPidId, 0); // take pit content

        var oppositePitId = (2 * InitialGame.NUMBER_OF_PITS) - endPidId;
        var oppositePitContent = pits.get(oppositePitId);
        pits.put(oppositePitId, 0); // take opposite pit content

        if (startPidId < InitialGame.NUMBER_OF_PITS) { // add to first player home pit
            pits.put(InitialGame.NUMBER_OF_PITS, pits.get(InitialGame.NUMBER_OF_PITS) + oppositePitContent + endPitContent);
        }

        if (startPidId > InitialGame.NUMBER_OF_PITS) { // add to second player home pit
            pits.put(2 * InitialGame.NUMBER_OF_PITS, pits.get(2 * InitialGame.NUMBER_OF_PITS) + oppositePitContent + endPitContent);
        }
    }
}
