package org.kalah.game.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "url"})
public class CreatedGameDTO {

    @JsonProperty private String id;
    @JsonProperty private String url;

    public CreatedGameDTO(Long id, String url) {
        this.id = String.valueOf(id);
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getURL() {
        return url;
    }
}
