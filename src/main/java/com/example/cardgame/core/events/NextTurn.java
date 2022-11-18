package com.example.cardgame.core.events;

import com.example.cardgame.core.CommonState;

import java.util.List;

public class NextTurn extends CommonEvent {
    @Override
    protected List<GameEvent> applyAndGetNextEvents(CommonState state) {
        state.turnState.onNextTurn(state, this);
        return List.of();
    }

    @Override
    public String toString() {
        return "NEXT TURN";
    }
}
