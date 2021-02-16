package org.kalah.game.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kalah.game.InitialGame;
import org.kalah.game.Player;
import org.kalah.game.dto.CreatedGameDTO;
import org.kalah.game.dto.UpdatedGameDTO;
import org.kalah.game.exceptions.GameOverException;
import org.kalah.game.exceptions.IllegalPitIdException;
import org.kalah.game.exceptions.MissingGameException;
import org.kalah.game.exceptions.OtherPlayerTurnException;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@SpringBootTest
public class GameControllerTest {

    @MockBean
    private GamesService gamesService;
    @Autowired
    private GamesController gamesController;
    private PrintStream defaultErrStream;
    private ByteArrayOutputStream errStream;

    @BeforeEach
    public void setupStandardOutStream() {
        errStream = new ByteArrayOutputStream();
        defaultErrStream = System.err;
        System.setErr(new PrintStream(errStream));
    }

    @AfterEach
    public void cleanUpOutStream() {
        System.setErr(defaultErrStream);
    }

    @Test
    public void testCreateGame() throws JsonProcessingException {
        // setup
        var gameId = 1L;
        var url = "http://localhost:8080/games/" + gameId;
        Mockito.when(gamesService.createGame(Mockito.anyLong())).thenReturn(new CreatedGameDTO(gameId, url));
        // act
        var response = gamesController.createGame();
        // assert
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals("""
                {"id":"1","url":"http://localhost:8080/games/1"}""", new ObjectMapper().writeValueAsString(response.getBody()));
    }

    @Test
    public void testMakeMoveException() throws MissingGameException, IllegalPitIdException, GameOverException, OtherPlayerTurnException {
        // setup
        var gameId = 1L;
        var pitId = 1;
        Mockito.when(gamesService.makeMove(gameId, pitId)).thenThrow(new IllegalArgumentException());
        // act
        var response = gamesController.updateGame(gameId, pitId);
        // assert
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testMakeMoveInGame() throws MissingGameException, IllegalPitIdException, GameOverException, OtherPlayerTurnException {
        // setup
        var gameId = 1L;
        var pitId = 1;
        Mockito.when(gamesService.makeMove(gameId, pitId)).thenReturn(
                new UpdatedGameDTO(gameId, "http://localhost:8080/games/" + gameId,
                        InitialGame.initPits(InitialGame.NUMBER_OF_PITS, InitialGame.INITIAL_PIT_VALUE)));

        // act
        var response = gamesController.updateGame(gameId, pitId);

        // assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("""
                {"id":"1","url":"http://localhost:8080/games/1","status":{"1":"6","2":"6","3":"6","4":"6","5":"6","6":"6","7":"0","8":"6","9":"6","10":"6","11":"6","12":"6","13":"6","14":"0"}}""", response.getBody());
    }

    @Test
    public void testMakeMoveInNotCreatedGame() throws MissingGameException, IllegalPitIdException, GameOverException, OtherPlayerTurnException {
        // setup
        var gameId = 1L;
        var pitId = 1;
        Mockito.when(gamesService.makeMove(gameId, pitId)).thenThrow(
                new MissingGameException(String.format("Missing game with id: %d", gameId)));

        // act
        var response = gamesController.updateGame(gameId, pitId);

        // assert
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals(String.format("Missing game with id: %d", gameId), response.getBody());
        Assertions.assertTrue(errStream.toString().contains(String.format("MissingGameException: Missing game with id: %d", gameId)));
    }

    @Test
    public void testMakeMoveInFinishedGame() throws MissingGameException, IllegalPitIdException, GameOverException, OtherPlayerTurnException {
        // setup
        var gameId = 1L;
        var pitId = 1;
        Mockito.when(gamesService.makeMove(gameId, pitId)).thenThrow(
                new GameOverException(String.format("Game id: %d is over", gameId)));

        // act
        var response = gamesController.updateGame(gameId, pitId);

        // assert
        Assertions.assertEquals(HttpStatus.GONE, response.getStatusCode());
        Assertions.assertEquals(String.format("Game id: %d is over", gameId), response.getBody());
        Assertions.assertTrue(errStream.toString().contains(String.format("GameOverException: Game id: %d is over", gameId)));
    }

    @Test
    public void testMakeMoveInGameWithIncorrectPitId() throws MissingGameException, IllegalPitIdException, GameOverException, OtherPlayerTurnException {
        // setup
        var gameId = 1L;
        var pitId = -1;
        Mockito.when(gamesService.makeMove(gameId, pitId)).thenThrow(
                new IllegalPitIdException(String.format("Invalid pit id: %d", pitId)));

        // act
        var response = gamesController.updateGame(gameId, pitId);

        // assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(String.format("Invalid pit id: %d", pitId), response.getBody());
        Assertions.assertTrue(errStream.toString().contains(String.format("IllegalPitIdException: Invalid pit id: %d", pitId)));
    }

    @Test
    public void testMakeMoveInGameWhenOtherPlayerTurn() throws MissingGameException, IllegalPitIdException, GameOverException, OtherPlayerTurnException {
        // setup
        var gameId = 1L;
        var pitId = 1;
        Mockito.when(gamesService.makeMove(gameId, pitId)).thenThrow(
                new OtherPlayerTurnException(
                        String.format("Other player turn in game %d, active player %s, pit id: %d", gameId, Player.PLAYER_2, pitId)));

        // act
        var response = gamesController.updateGame(gameId, pitId);

        // assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(String.format("Other player turn in game %d, active player %s, pit id: %d", gameId, Player.PLAYER_2, pitId), response.getBody());
        Assertions.assertTrue(errStream.toString().contains(
                String.format("OtherPlayerTurnException: Other player turn in game %d, active player %s, pit id: %d", gameId, Player.PLAYER_2, pitId)));
    }

}
