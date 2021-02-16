package org.kalah.game.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreatedGameDTOTest {

    @Test
    public void testCreatedGameDTOSerialization() throws JsonProcessingException {

        var createdGameDTO = new CreatedGameDTO(123L, "http://host:8080/games");
        var actualValue = new ObjectMapper().writeValueAsString(createdGameDTO);
        var expectedValue = """
                {"id":"123","url":"http://host:8080/games"}""";

        Assertions.assertEquals(expectedValue, actualValue);
    }
}
