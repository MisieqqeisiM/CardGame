package com.example.cardgame.core.playerevents;

import com.example.cardgame.core.PlayerState;

public class YouDrawCards implements PlayerEvent{
    private final int player;
    private final int number;

    public YouDrawCards(int player, int number) {
        this.player = player;
        this.number = number;
    }

    @Override
    public String toString() {
        return "Player " + player + " draws " + number + " cards.";
    }

    @Override
    public void apply(PlayerState view) {
        for(var i = 0; i < number; i++)
            view.addCard(player, null);
    }
}
