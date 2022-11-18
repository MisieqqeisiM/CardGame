package com.example.cardgame.core.actions;

import com.example.cardgame.core.GameState;
import com.example.cardgame.core.cards.UnoCard;
import com.example.cardgame.core.events.CardPlayed;
import com.example.cardgame.core.events.GameEvent;

import java.util.List;

public class PlayCard implements Action {
    private final UnoCard card;

    public PlayCard(UnoCard card) {
        this.card = card;
    }

    @Override
    public boolean isLegal(GameState state, ActionFrame frame) {
        if(!state.turnState.isActionTypeLegal(this)) return false;
        if(state.currentPlayer != frame.origin()) return false;
        return state.hands.get(frame.origin()).contains(card) && card.fitsOn(state.card);
    }

    @Override
    public List<GameEvent> intoEvents(GameState state, ActionFrame frame) {
        return List.of(new CardPlayed(frame.origin(), card));
    }
}
