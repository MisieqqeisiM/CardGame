package com.example.cardgame.core.playerevents;

import com.example.cardgame.core.PlayerState;
import com.example.cardgame.core.utils.Hand;
import com.example.cardgame.core.utils.TurnState;

public class IChallenged implements PlayerEvent{
    private final Hand hand;

    public IChallenged(Hand hand) {
        this.hand = hand;
    }


    @Override
    public void apply(PlayerState state) {
        state.turnState = TurnState.CARD_PLAYED;
    }

    @Override
    public String toString() {
        return "I accepted. Other players hand: " + hand.toString();
    }
}
