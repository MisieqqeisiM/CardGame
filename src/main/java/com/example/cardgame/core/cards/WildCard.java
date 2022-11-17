package com.example.cardgame.core.cards;

public final class WildCard extends UnoCard{
    public WildCard(Color color) {
        super(color);
    }

    @Override
    public boolean fitsOn(UnoCard card) {
        return true;
    }

    @Override
    public <T> T match(CardMatcher<T> matcher) {
        return matcher.onWild(this);
    }

    @Override
    public String toString() {
        if(getColor() == Color.NONE) return "WILDCARD";
        return getColor().toString() + "_WILDCARD";
    }

    @Override
    public int compareTo(UnoCard card) {
        int colorCmp = getColor().compareTo(card.getColor());
        if(colorCmp != 0) return colorCmp;
        if(card instanceof PlusFourCard) return -1;
        if(card instanceof WildCard) return 0;
        return 1;
    }
}
