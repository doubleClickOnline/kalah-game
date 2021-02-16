package org.kalah.game.repository;

import org.kalah.game.Game;
import org.kalah.game.exceptions.MissingGameException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MemoryGamesRepository implements GamesRepository {

    private final Map<Long, Game> repository;

    public MemoryGamesRepository() {
        this.repository = new ConcurrentHashMap<>();
    }

    public MemoryGamesRepository(Map<Long, Game> repository) {
        this.repository = repository;
    }

    @Override
    public void save(Long id, Game game) {
        this.repository.put(id, game);
    }


    @Override
    public Game getById(Long id) throws MissingGameException {

        if (!repository.containsKey(id)) {
            throw new MissingGameException(String.format("Missing game with id: %d", id));
        }
        return this.repository.get(id);
    }
}
