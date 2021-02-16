package org.kalah.game;

import org.kalah.game.exceptions.IllegalPitIdException;

import java.util.Map;

public class Game {

    private Status status;
    private Player activePlayer;
    private final Pits pits;

    public Game(Status status, Pits pits) {
        this.status = status;
        this.pits = pits;
    }

    public synchronized Pits makeMove(Integer startPitId) throws IllegalPitIdException {
        var endPitId = pits.move(startPitId);
        InitialGame.RULES.forEach(
                rule -> rule.apply(this, startPitId, endPitId));
        return pits;
    }

    public synchronized Map<Integer, Integer> getPits() {return pits.getPits();}

    public synchronized void nextPlayer() {
        if (activePlayer == Player.PLAYER_1) {
            activePlayer = Player.PLAYER_2;
        } else {
            activePlayer = Player.PLAYER_1;
        }
    }

    public synchronized Status getStatus() {
        return status;
    }

    public synchronized void setStatus(Status status) {
        this.status = status;
    }

    public synchronized Player getActivePlayer() {
        return activePlayer;
    }

    public synchronized void setActivePlayer(Player activePlayer) { this.activePlayer = activePlayer; }

    public synchronized Boolean isAllowedPlayerMakeMove(Integer pitId) {

        // In the beginning of game there is unclear which player will start
        if (activePlayer == null) { return Boolean.TRUE; }

        return activePlayer.isAllowedStartPitId(pitId);
    }

    public synchronized Boolean isOver() {
        return status == Status.OVER;
    }
}