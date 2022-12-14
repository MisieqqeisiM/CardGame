package com.example.cardgame.core.turnstates;

import com.example.cardgame.core.CommonState;
import com.example.cardgame.core.PlayerState;
import com.example.cardgame.core.actions.Action;
import com.example.cardgame.core.actions.ChooseColor;
import com.example.cardgame.core.cards.Color;
import com.example.cardgame.core.cards.WildCard;
import com.example.cardgame.core.events.ColorChosen;

import java.util.List;

public class ChoosingWildcardColor implements TurnState {
    @Override
    public void onColorChosen(CommonState state, ColorChosen event) {
        state.card = new WildCard(event.color);
        state.nextTurn();
    }

    @Override
    public List<Action> getLegalActions(PlayerState state) {
        return List.of(
                new ChooseColor(Color.RED),
                new ChooseColor(Color.GREEN),
                new ChooseColor(Color.BLUE),
                new ChooseColor(Color.YELLOW)
        );
    }

    @Override
    public boolean isActionTypeLegal(Action action) {
        return action instanceof ChooseColor;
    }

}
