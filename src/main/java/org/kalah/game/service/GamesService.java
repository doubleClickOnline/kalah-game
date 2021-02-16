package org.kalah.game.service;

import org.kalah.game.dto.CreatedGameDTO;
import org.kalah.game.dto.UpdatedGameDTO;
import org.kalah.game.exceptions.GameOverException;
import org.kalah.game.exceptions.IllegalPitIdException;
import org.kalah.game.exceptions.MissingGameException;
import org.kalah.game.exceptions.OtherPlayerTurnException;

public interface GamesService {

    CreatedGameDTO createGame(Long id);

    UpdatedGameDTO makeMove(Long id, Integer pitId) throws MissingGameException, GameOverException, OtherPlayerTurnException, IllegalPitIdException;
}
