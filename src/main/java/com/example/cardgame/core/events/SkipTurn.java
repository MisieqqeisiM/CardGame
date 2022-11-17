package com.example.cardgame.core.events;

import com.example.cardgame.core.CommonState;

import java.util.List;

public class SkipTurn extends CommonEvent{
    @Override
    protected List<GameEvent> applyAndGetNextEvents(CommonState state) {
        state.nextTurn();
        state.nextTurn();
        return List.of();
    }

    @Override
    public String toString() {
        return "TURN SKIPPED";
    }
}
