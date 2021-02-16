package org.kalah.game.repository;

import org.kalah.game.Game;
import org.kalah.game.exceptions.MissingGameException;

/**
 *
 * Games repository
 */
public interface GamesRepository {

    /**
     *
     * Save created game
     * @param id id
     * @param game game
     */
    void save(Long id, Game game);

    /**
     *
     * Find game by id
     * @param id
     * @return game
     * @throws MissingGameException missing game exception
     */
    Game getById(Long id) throws MissingGameException;
}
