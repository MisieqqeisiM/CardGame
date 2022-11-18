package com.example.cardgame.core.events;

import com.example.cardgame.core.PlayerState;
import com.example.cardgame.core.utils.Hand;

public class IChallenged implements PlayerEvent{
    public final Hand hand;

    public IChallenged(Hand hand) {
        this.hand = hand;
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
