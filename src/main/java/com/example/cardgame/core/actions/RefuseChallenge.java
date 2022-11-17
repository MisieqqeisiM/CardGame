package com.example.cardgame.core.actions;

import com.example.cardgame.core.GameState;
import com.example.cardgame.core.events.*;
import com.example.cardgame.core.utils.TurnState;

import java.util.List;

public class RefuseChallenge implements Action {
    @Override
    public boolean isLegal(GameState state, ActionFrame frame) {
        if(frame.origin() != state.nextPlayer()) return false;
        return state.turnState == TurnState.CHALLENGE;
    }

    @Override
    public List<GameEvent> intoEvents(GameState state, ActionFrame frame) {
        return List.of(new ChallengeRefused());
    }
}
