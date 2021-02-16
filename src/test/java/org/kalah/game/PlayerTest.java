package org.kalah.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerTest {

    @Test
    public void testPlayer1AllowedStartPitIds() {
        for (int pitId = 1; pitId <= InitialGame.NUMBER_OF_PITS; pitId++) {
            Assertions.assertTrue(Player.PLAYER_1.isAllowedStartPitId(pitId));
        }
    }

    @Test
    public void testPlayer2AllowedStartPitIds() {
        for (int pitId = InitialGame.NUMBER_OF_PITS + 1; pitId <= 2 * InitialGame.NUMBER_OF_PITS; pitId++) {
            Assertions.assertTrue(Player.PLAYER_2.isAllowedStartPitId(pitId));
        }
    }

    @Test
    public void testPlayer1AllowedStartWithPlayer2PitIds() {
        for (int pitId = InitialGame.NUMBER_OF_PITS + 1; pitId <= 2 * InitialGame.NUMBER_OF_PITS; pitId++) {
            Assertions.assertFalse(Player.PLAYER_1.isAllowedStartPitId(pitId));
        }
    }

    @Test
    public void testPlayer2AllowedStartWithPlayer1PitIds() {
        for (int pitId = 1; pitId <= InitialGame.NUMBER_OF_PITS; pitId++) {
            Assertions.assertFalse(Player.PLAYER_2.isAllowedStartPitId(pitId));
        }
    }
}
