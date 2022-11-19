package com.example.cardgame.core.cards;

import java.io.Serializable;

public abstract class UnoCard implements Comparable<UnoCard>, Serializable {
    private final Color color;
    public UnoCard(Color color) {
        assert(color != null);
        this.color = color;
    }
    public Color getColor() {
        return this.color;
    }
    public abstract boolean fitsOn(UnoCard card);
    public abstract <T> T match(CardMatcher<T> matcher);
}
