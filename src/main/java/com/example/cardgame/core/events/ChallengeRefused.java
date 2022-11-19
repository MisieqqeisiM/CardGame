package com.example.cardgame.core.events;

import com.example.cardgame.core.CommonState;

import java.util.List;

public class ChallengeRefused extends CommonEvent {
    @Override
    protected List<GameEvent> applyAndGetNextEvents(CommonState state) {
        state.turnState.onChallengeRefused(state, this);
        return List.of(new DrawCards(state.nextPlayer(), 4), new SkipTurn());
    }

    @Override
    public String toString() {
        return "No challenge.";
    }
}
