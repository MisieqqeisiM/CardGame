package com.example.cardgame.core.actions;

import com.example.cardgame.core.GameState;
import com.example.cardgame.core.cards.Color;
import com.example.cardgame.core.events.ColorChosen;
import com.example.cardgame.core.events.GameEvent;

import java.util.List;

public class ChooseColor implements Action {
    private final Color color;

    public ChooseColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean isLegal(GameState state, ActionFrame frame) {
        if(!state.turnState.isActionTypeLegal(this)) return false;
        return state.currentPlayer == frame.origin();
    }

    @Override
    public List<GameEvent> intoEvents(GameState state, ActionFrame frame) {
        return List.of(new ColorChosen(color));
    }
}
