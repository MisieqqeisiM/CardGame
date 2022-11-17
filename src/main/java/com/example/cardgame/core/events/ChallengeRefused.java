package com.example.cardgame.core.events;

import com.example.cardgame.core.CommonState;
import com.example.cardgame.core.utils.TurnState;

import java.util.List;

public class ChallengeRefused extends CommonEvent {
    @Override
    protected List<GameEvent> applyAndGetNextEvents(CommonState state) {
        if(state.turnState != TurnState.CHALLENGE) throw new RuntimeException("Invalid event");
        state.turnState = TurnState.CARD_PLAYED;
        return List.of(new DrawCards(state.nextPlayer(), 4), new SkipTurn());
    }

    @Override
    public String toString() {
        return "No challenge.";
    }
}
