package com.example.cardgame.core.cards;

public final class PlusFourCard extends UnoCard{
    public PlusFourCard(Color color) {
        super(color);
    }

    @Override
    public boolean fitsOn(UnoCard card) {
        return true;
    }

    @Override
    public <T> T match(CardMatcher<T> matcher) {
        return matcher.onPlusFour(this);
    }

    @Override
    public String toString() {
        if(getColor() == Color.NONE) return "+4";
        return getColor().toString() + "_+4";
    }

    @Override
    public int compareTo(UnoCard card) {
        int colorCmp = getColor().compareTo(card.getColor());
        if(colorCmp != 0) return colorCmp;
        if(card instanceof PlusFourCard) return 0;
        return 1;
    }
}
