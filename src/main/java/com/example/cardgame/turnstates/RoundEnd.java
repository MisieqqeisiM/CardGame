package com.example.cardgame.turnstates;

import com.example.cardgame.core.CommonState;
import com.example.cardgame.core.GameState;
import com.example.cardgame.core.PlayerState;
import com.example.cardgame.core.actions.Action;
import com.example.cardgame.core.events.DrawCards;
import com.example.cardgame.core.events.NextTurn;
import com.example.cardgame.core.events.SkipTurn;
import com.example.cardgame.core.events.IDrawCards;
import com.example.cardgame.core.events.YouDrawCards;

import java.util.List;

public class RoundEnd implements TurnState {

    @Override
    public void onNextTurn(CommonState state, NextTurn event) {
        state.nextTurn();
        state.turnState = new ChoosingCard();
    }

    @Override
    public void onSkipTurn(CommonState state, SkipTurn event) {
        state.nextTurn();
        state.nextTurn();
        state.turnState = new ChoosingCard();
    }

    @Override
    public void onDrawCards(GameState state, DrawCards event) {
        state.hands.get(event.player).addCards(event.cards.stream());
        state.turnState = new RoundEnd();
    }

    @Override
    public void onIDrawCards(PlayerState state, IDrawCards event) {
        event.cards.forEach(card -> state.addCard(state.myId, card));
        state.turnState = new RoundEnd();
    }

    @Override
    public void onYouDrawCards(PlayerState state, YouDrawCards event) {
        for(var i = 0; i < event.number; i++)
            state.addCard(event.player, null);
        state.turnState = new RoundEnd();
    }
    @Override
    public List<Action> getLegalActions(PlayerState state) {
        return List.of();
    }

    @Override
    public boolean isActionTypeLegal(Action action) {
        return false;
    }
}
