package org.kalah.game.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.kalah.game.dto.CreatedGameDTO;
import org.kalah.game.exceptions.GameOverException;
import org.kalah.game.exceptions.IllegalPitIdException;
import org.kalah.game.exceptions.MissingGameException;
import org.kalah.game.exceptions.OtherPlayerTurnException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/games")
public class GamesController {

    private final AtomicLong counter = new AtomicLong();
    @Autowired
    private final GamesService service;

    public GamesController(GamesService service) {
        this.service = service;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedGameDTO> createGame() {

        var createdGameDTO = service.createGame(counter.incrementAndGet());
        return new ResponseEntity<>(createdGameDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{gameId}/pits/{pitId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateGame(@PathVariable("gameId") Long gameId, @PathVariable("pitId") Integer pitId) {

        try {
            var updatedGame = service.makeMove(gameId, pitId);
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(updatedGame), HttpStatus.OK);
        } catch (MissingGameException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (GameOverException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.GONE);
        } catch (OtherPlayerTurnException | IllegalPitIdException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
