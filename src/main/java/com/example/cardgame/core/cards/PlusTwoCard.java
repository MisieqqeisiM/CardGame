package com.example.cardgame.core.cards;

public final class PlusTwoCard extends UnoCard {
    public PlusTwoCard(Color color) {
        super(color);
        assert(color != Color.NONE);
    }

    @Override
    public boolean fitsOn(UnoCard card) {
        if(card.getColor().equals(getColor())) return true;
        return card instanceof PlusTwoCard;
    }

    @Override
    public <T> T match(CardMatcher<T> matcher) {
        return matcher.onPlusTwo(this);
    }

    @Override
    public String toString() {
        return getColor().toString() + "_+2";
    }

    @Override
    public int compareTo(UnoCard card) {
        int colorCmp = getColor().compareTo(card.getColor());
        if(colorCmp != 0) return colorCmp;
        if(card instanceof PlusTwoCard) return 0;
        return 1;
    }
}
