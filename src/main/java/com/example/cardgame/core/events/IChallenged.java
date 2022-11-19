package com.example.cardgame.core.events;

import com.example.cardgame.core.PlayerState;
import com.example.cardgame.core.utils.Hand;

public class IChallenged implements PlayerEvent{
    public final Hand hand;
    public final boolean won;

    public IChallenged(Hand hand, boolean won) {
        this.hand = hand;
        this.won = won;
    }


    @Override
    public void apply(PlayerState state) {
        state.turnState.onIChallenged(state, this);
    }

    @Override
    public String toString() {
        return "I accepted. Other players hand: " + hand.toString();
    }
}
