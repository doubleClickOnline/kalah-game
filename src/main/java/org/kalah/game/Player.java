package org.kalah.game;

public enum Player {

    PLAYER_1(1, InitialGame.NUMBER_OF_PITS),
    PLAYER_2(InitialGame.NUMBER_OF_PITS + 1, 2 * InitialGame.NUMBER_OF_PITS);

    private Integer minAllowedPitId;
    private Integer maxAllowedPitId;

    Player (Integer minAllowedPitId, Integer maxAllowedPitId) {
        this.minAllowedPitId = minAllowedPitId;
        this.maxAllowedPitId = maxAllowedPitId;
    }

    public boolean isAllowedStartPitId(Integer pitId) {
        return pitId >= minAllowedPitId && pitId <= maxAllowedPitId;
    }
}
