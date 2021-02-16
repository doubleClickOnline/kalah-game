package org.kalah.game.rules;

import org.kalah.game.Game;
import org.kalah.game.InitialGame;
import org.kalah.game.Status;

import java.util.Map;

/**
 *
 * Finished game rule
 */
public class FinishedGameRule implements Rule {

    @Override
    public void apply(Game game, Integer startPidId, Integer endPidId) {

        var pits = game.getPits();
        var playerPitsContent = pits.entrySet()
                .stream()
                .filter(pit -> pit.getKey() < InitialGame.NUMBER_OF_PITS)
                .mapToInt(Map.Entry::getValue)
                .sum();

        var anotherPlayerPitsContent = pits.entrySet()
                .stream()
                .filter(pit -> pit.getKey() > InitialGame.NUMBER_OF_PITS)
                .filter(pit -> pit.getKey() < 2 * InitialGame.NUMBER_OF_PITS)
                .mapToInt(Map.Entry::getValue)
                .sum();

        // finished game condition
        if (playerPitsContent == 0 || anotherPlayerPitsContent == 0) {
            for (Map.Entry<Integer, Integer> pit : pits.entrySet()) {
                if (pit.getKey().equals(InitialGame.NUMBER_OF_PITS)) { // add player score
                    pit.setValue(pit.getValue() + playerPitsContent);
                    continue;
                }
                if (pit.getKey().equals(2 * InitialGame.NUMBER_OF_PITS)) { // add another player score
                    pit.setValue(pit.getValue() + anotherPlayerPitsContent);
                    continue;
                }
                pit.setValue(0);
            }
            game.setStatus(Status.OVER);
        }
    }
}