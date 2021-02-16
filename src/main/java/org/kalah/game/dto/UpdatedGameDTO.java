package org.kalah.game.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.Map;

@JsonPropertyOrder({"id", "url", "status"})
public class UpdatedGameDTO extends CreatedGameDTO {

    @JsonSerialize(contentUsing = ToStringSerializer.class)
    private Map<Integer, Integer> status;

    public UpdatedGameDTO(Long id, String url, Map<Integer, Integer> game) {
        super(id, url);
        this.status = game;
    }
}
