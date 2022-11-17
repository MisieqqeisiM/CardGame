package com.example.cardgame.core.playerevents;

import com.example.cardgame.core.PlayerState;
import com.example.cardgame.core.utils.TurnState;

public class YouChallanged implements PlayerEvent{
    private final boolean won;

    public YouChallanged(boolean won) {
        this.won = won;
    }


    @Override
    public void apply(PlayerState state) {
        state.turnState = TurnState.CARD_PLAYED;
    }

    @Override
    public String toString() {
        if(won)
            return "Challenge accepted and won.";
        else
            return "Challenge accepted and lost.";
    }
}
