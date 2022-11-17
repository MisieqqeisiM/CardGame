package com.example.cardgame.core.playerevents;

import com.example.cardgame.core.PlayerState;
import com.example.cardgame.core.cards.UnoCard;

import java.util.List;

public class IDrawCards implements PlayerEvent{
    private final List<UnoCard> cards;
    public IDrawCards(List<UnoCard> cards) {
        this.cards = cards;
    }

    @Override
    public void apply(PlayerState view) {
        view.hand.addCards(cards.stream());
    }

    @Override
    public String toString() {
        return "I draw these cards: " + cards + ".";
    }
}
