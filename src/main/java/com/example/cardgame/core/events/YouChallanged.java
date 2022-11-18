package com.example.cardgame.core.events;

import com.example.cardgame.core.PlayerState;

public class YouChallanged implements PlayerEvent{
    private final boolean won;

    public YouChallanged(boolean won) {
        this.won = won;
    }


    @Override
    public void apply(PlayerState state) {
        state.turnState.onYouChallenged(state, this);
    }

    @Override
    public String toString() {
        if(won)
            return "Challenge accepted and won.";
        else
            return "Challenge accepted and lost.";
    }
}
