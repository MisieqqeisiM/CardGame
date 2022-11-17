package com.example.cardgame.core.actions;

import com.example.cardgame.core.GameState;
import com.example.cardgame.core.cards.Color;
import com.example.cardgame.core.events.ActionFrame;
import com.example.cardgame.core.events.ColorChosen;
import com.example.cardgame.core.events.GameEvent;
import com.example.cardgame.core.utils.TurnState;

import java.util.List;

public class ChooseColor implements Action {
    private final Color color;

    public ChooseColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean isLegal(GameState state, ActionFrame frame) {
        if(state.currentPlayer != frame.origin()) return false;
        return state.turnState == TurnState.CHOOSING_PLUSFOUR_COLOR
                || state.turnState == TurnState.CHOOSING_WILDCARD_COLOR;
    }

    @Override
    public List<GameEvent> intoEvents(GameState state, ActionFrame frame) {
        return List.of(new ColorChosen(color));
    }
}
