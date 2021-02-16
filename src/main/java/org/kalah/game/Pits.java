package org.kalah.game;

import org.kalah.game.exceptions.IllegalPitIdException;

import java.util.Map;
import java.util.Set;

/**
 *
 *  Game pits content
 */
public class Pits {

    private final Map<Integer, Integer> pits;

    public Pits(Map<Integer, Integer> pits) {
        this.pits = pits;
    }

    /**
     *
     * Make a game move
     *
     * @param pitId start pit id
     * @return end pit id
     */
    public synchronized Integer move(Integer pitId) throws IllegalPitIdException {

        Set<Integer> pitIds = pits.keySet();
        if (!pitIds.contains(pitId)) { // pit doesn't exist
            throw new IllegalPitIdException(String.format("Invalid pit id: %d", pitId));
        }

        if (pitId % InitialGame.NUMBER_OF_PITS == 0) { // player's house
            throw new IllegalPitIdException(String.format("Player's home pit id: %d", pitId));
        }

        var pitContent = getPitContent(pitId);
        pits.put(pitId, 0); // take all content
        return makeMove(++pitId, pitContent, pitId);
    }
    
    private Integer makeMove(Integer pitId, Integer value, Integer startPitId) {

        if (pitId > pits.size()) { // start from first pit
            pitId = 1;
        }

        if (startPitId < InitialGame.NUMBER_OF_PITS && pitId == 2 * InitialGame.NUMBER_OF_PITS) { // skip another player's house
            pitId = 1;
        }

        if (startPitId > InitialGame.NUMBER_OF_PITS && pitId == InitialGame.NUMBER_OF_PITS) { // skip another player's house
            pitId = 8;
        }

        var pitContent = getPitContent(pitId);
        pits.put(pitId, ++pitContent);
        value--;
        if (value <= 0) { // stop, all stones shared
            return pitId;
        }

        return makeMove(++pitId, value, startPitId);
    }

    private Integer getPitContent(Integer pitId) {
        return pits.get(pitId);
    }

    public Map<Integer, Integer> getPits() { return pits; }
}