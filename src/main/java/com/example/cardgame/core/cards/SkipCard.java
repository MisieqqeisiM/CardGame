package com.example.cardgame.core.cards;

public final class SkipCard extends UnoCard{
    public SkipCard(Color color) {
        super(color);
        assert(color != Color.NONE);
    }

    @Override
    public boolean fitsOn(UnoCard card) {
        if(card.getColor().equals(getColor())) return true;
        return card instanceof SkipCard;
    }

    @Override
    public <T> T match(CardMatcher<T> matcher) {
        return matcher.onSkip(this);
    }

    @Override
    public String toString() {
        return getColor().toString() + "_SKIP";
    }

    @Override
    public int compareTo(UnoCard card) {
        int colorCmp = getColor().compareTo(card.getColor());
        if(colorCmp != 0) return colorCmp;
        if(card instanceof PlusTwoCard) return -1;
        if(card instanceof ReverseCard) return -1;
        if(card instanceof SkipCard) return 0;
        return 1;
    }
}
