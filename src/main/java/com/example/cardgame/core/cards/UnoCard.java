package com.example.cardgame.core.cards;

public abstract class UnoCard implements Comparable<UnoCard>{
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
