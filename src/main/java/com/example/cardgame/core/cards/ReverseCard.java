package com.example.cardgame.core.cards;

public final class ReverseCard extends UnoCard {
    public ReverseCard(Color color) {
        super(color);
        assert(color != Color.NONE);
    }

    @Override
    public boolean fitsOn(UnoCard card) {
        if(card.getColor().equals(getColor())) return true;
        return card instanceof ReverseCard;
    }

    @Override
    public <T> T match(CardMatcher<T> matcher) {
        return matcher.onReverse(this);
    }

    @Override
    public String toString() {
        return getColor().toString() + "_REVERSE";
    }

    @Override
    public int compareTo(UnoCard card) {
        int colorCmp = getColor().compareTo(card.getColor());
        if(colorCmp != 0) return colorCmp;
        if(card instanceof PlusTwoCard) return -1;
        if(card instanceof ReverseCard) return 0;
        return 1;
    }
}
