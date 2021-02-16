package org.kalah.game.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kalah.game.Game;
import org.kalah.game.InitialGame;
import org.kalah.game.Pits;
import org.kalah.game.Player;
import org.kalah.game.Status;
import org.kalah.game.exceptions.GameOverException;
import org.kalah.game.exceptions.IllegalPitIdException;
import org.kalah.game.exceptions.MissingGameException;
import org.kalah.game.exceptions.OtherPlayerTurnException;
import org.kalah.game.repository.GamesRepository;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class GameServiceTest {

    @Value( "${server.address}" )
    private String host;
    @Value( "${server.port}" )
    private String port;
    @MockBean
    private GamesRepository gamesRepository;
    @Autowired
    private GamesService gamesService;

    @Test
    public void testCreateGame() {
        // setup
        var gameId = 1L;
        // act
        var game = gamesService.createGame(gameId);
        // assert
        Assertions.assertEquals(gameId, Long.valueOf(game.getId()));
        Assertions.assertEquals("http://"+host+":"+port+"/games/"+gameId, game.getURL());
    }

    @Test
    public void testMakeMove() throws MissingGameException, OtherPlayerTurnException, IllegalPitIdException, GameOverException, JsonProcessingException {
        // setup
        var gameId = 1L;
        var pitId = 1;
        Mockito.when(gamesRepository.getById(gameId)).thenReturn(new Game(Status.IN_PROGRESS,
                new Pits(InitialGame.initPits(InitialGame.NUMBER_OF_PITS, InitialGame.INITIAL_PIT_VALUE))));
        // act
        var game = gamesService.makeMove(gameId, pitId);
        // assert
        Assertions.assertEquals("""
                {"id":"1","url":"http://"""+host+":"+port+ """
                       /games/1","status":{"1":"0","2":"7","3":"7","4":"7","5":"7","6":"7","7":"1","8":"6","9":"6","10":"6","11":"6","12":"6","13":"6","14":"0"}}""",
                new ObjectMapper().writeValueAsString(game));
    }

    @Test
    public void testMakeMoveInFinishedGame() throws MissingGameException {
        // setup
        var gameId = 1L;
        var pitId = 1;
        Mockito.when(gamesRepository.getById(gameId)).thenReturn(new Game(Status.OVER, null));
        // act
        Exception exception = Assertions.assertThrows(GameOverException.class, () -> {
            var game = gamesService.makeMove(gameId, pitId);
        });
        // assert
        Assertions.assertEquals(String.format("Game id: %d is over", gameId), exception.getMessage());
    }

    @Test
    public void testMakeMoveInGameWhenOtherPlayerTurn() throws MissingGameException {
        // setup
        var gameId = 1L;
        var pitId = 1;
        var activePlayer = Player.PLAYER_2;
        var game = Mockito.mock(Game.class);
        Mockito.when(game.isAllowedPlayerMakeMove(Mockito.anyInt())).thenReturn(Boolean.FALSE);
        Mockito.when(game.getActivePlayer()).thenReturn(activePlayer);
        Mockito.when(gamesRepository.getById(gameId)).thenReturn(game);
        // act
        Exception exception = Assertions.assertThrows(OtherPlayerTurnException.class, () -> {
            gamesService.makeMove(gameId, pitId);
        });
        // assert
        Assertions.assertEquals(String.format("Other player turn in game %d, active player %s, pit id: %d", gameId, activePlayer, pitId), exception.getMessage());
    }
}
