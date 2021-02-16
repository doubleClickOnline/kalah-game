package org.kalah.game.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kalah.game.InitialGame;

public class UpdatedGameDTOTest {

    @Test
    public void tesUpdatedGameDTOSerialization() throws JsonProcessingException {
        var updatedGameDTO = new UpdatedGameDTO(123L, "http://host:8080/games/123", InitialGame.createGame().getPits());
        var expectedValue = """
                {"id":"123","url":"http://host:8080/games/123","status":{"1":"6","2":"6","3":"6","4":"6","5":"6","6":"6","7":"0","8":"6","9":"6","10":"6","11":"6","12":"6","13":"6","14":"0"}}""";

        Assertions.assertEquals(expectedValue, new ObjectMapper().writeValueAsString(updatedGameDTO));
    }
}
