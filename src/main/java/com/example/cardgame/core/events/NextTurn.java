package com.example.cardgame.core.events;

import com.example.cardgame.core.CommonState;
import com.example.cardgame.core.utils.TurnState;

import java.util.List;

public class NextTurn extends CommonEvent {
    @Override
    protected List<GameEvent> applyAndGetNextEvents(CommonState state) {
        state.nextTurn();
        return List.of();
    }

    @Override
    public String toString() {
        return "NEXT TURN";
    }
}
