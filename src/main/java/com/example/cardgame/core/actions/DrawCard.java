package com.example.cardgame.core.actions;

import com.example.cardgame.core.GameState;
import com.example.cardgame.core.events.*;
import com.example.cardgame.core.utils.TurnState;

import java.util.List;

public class DrawCard implements Action {
    @Override
    public boolean isLegal(GameState state, ActionFrame frame) {
        if(state.currentPlayer != frame.origin()) return false;
        return state.turnState == TurnState.CHOOSING_CARD;
    }

    @Override
    public List<GameEvent> intoEvents(GameState state, ActionFrame frame) {
        return List.of(new DrawCards(frame.origin(), 1), new NextTurn());
    }
}
