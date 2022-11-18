package com.example.cardgame.core.events;

import com.example.cardgame.core.PlayerState;

public class YouDrawCards implements PlayerEvent{
    public final int player;
    public final int number;

    public YouDrawCards(int player, int number) {
        this.player = player;
        this.number = number;
    }

    @Override
    public String toString() {
        return "Player " + player + " draws " + number + " cards.";
    }

    @Override
    public void apply(PlayerState state) {
        state.turnState.onYouDrawCards(state, this);
    }
}
