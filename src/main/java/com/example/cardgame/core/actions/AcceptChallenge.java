package com.example.cardgame.core.actions;

import com.example.cardgame.core.GameState;
import com.example.cardgame.core.events.ChallengeAccepted;
import com.example.cardgame.core.events.GameEvent;

import java.util.List;

public class AcceptChallenge implements Action {
    @Override
    public boolean isLegal(GameState state, ActionFrame frame) {
        if(!state.turnState.isActionTypeLegal(this)) return false;
        return frame.origin() == state.nextPlayer();
    }

    @Override
    public List<GameEvent> intoEvents(GameState state, ActionFrame frame) {
        return List.of(new ChallengeAccepted());
    }
}
