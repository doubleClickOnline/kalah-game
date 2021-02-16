package org.kalah.game.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kalah.game.Game;
import org.kalah.game.InitialGame;
import org.kalah.game.exceptions.MissingGameException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryGamesRepositoryTest {

    private Long gameId;
    private Game game;
    private Map<Long, Game> repositoryMap;
    private GamesRepository repository;

    @BeforeEach
    public void setup() {
        gameId = 123L;
        game = InitialGame.createGame();
        repositoryMap = new ConcurrentHashMap<Long, Game>();
        repository = new MemoryGamesRepository(repositoryMap);
    }

    @Test
    public void testSaveGame() throws MissingGameException {
        // act
        repository.save(gameId, game);

        // assert
        Assertions.assertEquals(1, repositoryMap.size());
        Assertions.assertSame(game, repositoryMap.get(gameId));
    }

    @Test
    public void testGetByIdGame() throws MissingGameException {
        // act
        repository.save(gameId, game);

        // assert
        Assertions.assertEquals(1, repositoryMap.size());
        Assertions.assertSame(game, repository.getById(gameId));
    }

    @Test
    public void testGetByIdMissingGame() {
        // act
        Exception exception = Assertions.assertThrows(MissingGameException.class, () -> {
            repository.getById(gameId);
        });

        // assert
        Assertions.assertEquals(String.format("Missing game with id: %d", gameId), exception.getMessage());
    }
}
