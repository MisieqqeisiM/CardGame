package com.example.cardgame.core.events;

import com.example.cardgame.core.PlayerState;
import com.example.cardgame.core.cards.UnoCard;

import java.util.List;

public class IDrawCards implements PlayerEvent{
    public final List<UnoCard> cards;
    public IDrawCards(List<UnoCard> cards) {
        this.cards = cards;
    }

    @Override
    public void apply(PlayerState state) {
        state.turnState.onIDrawCards(state, this);
    }

    @Override
    public String toString() {
        return "I draw these cards: " + cards + ".";
    }
}
