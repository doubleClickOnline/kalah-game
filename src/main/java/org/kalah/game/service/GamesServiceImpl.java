package org.kalah.game.service;

import org.kalah.game.InitialGame;
import org.kalah.game.dto.CreatedGameDTO;
import org.kalah.game.dto.UpdatedGameDTO;
import org.kalah.game.exceptions.GameOverException;
import org.kalah.game.exceptions.IllegalPitIdException;
import org.kalah.game.exceptions.MissingGameException;
import org.kalah.game.exceptions.OtherPlayerTurnException;
import org.kalah.game.repository.GamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GamesServiceImpl implements GamesService {

    @Value( "${server.address}" )
    private String host;
    @Value( "${server.port}" )
    private String port;
    @Autowired
    private final GamesRepository repository;

    public GamesServiceImpl(GamesRepository repository) {
        this.repository = repository;
    }

    @Override
    public CreatedGameDTO createGame(Long id) {
        repository.save(id, InitialGame.createGame());
        return new CreatedGameDTO(id, String.format("http://%s:%s/games/%d", host, port, id));
    }

    @Override
    public UpdatedGameDTO makeMove(Long id, Integer pitId) throws MissingGameException, GameOverException, OtherPlayerTurnException, IllegalPitIdException {

        var game = repository.getById(id);
        if (game.isOver()) {
            throw new GameOverException(String.format("Game id: %d is over", id));
        }
        if (!game.isAllowedPlayerMakeMove(pitId)) {
            throw new OtherPlayerTurnException(
                    String.format("Other player turn in game %d, active player %s, pit id: %d", id, game.getActivePlayer(), pitId));
        }
        game.makeMove(pitId);
        return new UpdatedGameDTO(id,  String.format("http://%s:%s/games/%d", host, port, id), game.getPits());
    }
}
