package com.example.cardgame.core.events;

import com.example.cardgame.core.CommonState;
import com.example.cardgame.core.cards.Color;

import java.util.List;

public class ColorChosen extends CommonEvent {
    public final Color color;
    public ColorChosen(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Color " + color + " has been chosen.";
    }
    @Override
    protected List<GameEvent> applyAndGetNextEvents(CommonState state) {
        state.turnState.onColorChosen(state, this);
        return List.of();
    }
}
