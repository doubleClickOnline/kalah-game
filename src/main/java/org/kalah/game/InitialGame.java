package org.kalah.game;

import org.kalah.game.rules.FinishedGameRule;
import org.kalah.game.rules.OppositePitContentGameRule;
import org.kalah.game.rules.Rule;
import org.kalah.game.rules.SamePlayerGameRule;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * Initial game constants
 */
public class InitialGame {

    public static final Integer NUMBER_OF_PITS = 7;
    public static final Integer INITIAL_PIT_VALUE = 6;
    public static final Integer INITIAL_PLAYER_PIT_VALUE = 0;

    public static final List<Rule> RULES = List.of(new OppositePitContentGameRule(), new SamePlayerGameRule(), new FinishedGameRule());

    /**
     *
     * Create initial game pits with default content
     * @return initial game pits with default content
     */
    public static Map<Integer, Integer> initPits(Integer numberOfPits, Integer defaultPitContent) {

        // init all pits
        var pits = new ConcurrentHashMap<Integer, Integer>();
        for (int i = 1; i <= numberOfPits * 2; i++) {
            if (i % numberOfPits == 0) { // player's pit
                pits.put(i, INITIAL_PLAYER_PIT_VALUE);
            } else {
                pits.put(i, defaultPitContent);
            }
        }
        return pits;
    }

    /**
     *
     * Create game
     * @return new game
     */
    public static Game createGame() {
        return new Game(Status.IN_PROGRESS, new Pits(InitialGame.initPits(InitialGame.NUMBER_OF_PITS, InitialGame.INITIAL_PIT_VALUE)));
    }
}