package org.kalah.game.rules;

import org.kalah.game.Game;

/**
 *
 * Game rule
 */
public interface Rule {
    void apply(Game game, Integer startPidId, Integer endPidId);
}