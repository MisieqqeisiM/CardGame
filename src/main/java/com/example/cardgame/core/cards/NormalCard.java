package com.example.cardgame.core.cards;

public final class NormalCard extends  UnoCard{
    private final byte number;
    public NormalCard(Color color, byte number) {
        super(color);
        assert(color != Color.NONE);
        assert(number >= 0 && number <= 10);
        this.number = number;
    }
    public byte getNumber() {
        return number;
    }

    @Override
    public boolean fitsOn(UnoCard card) {
        if(card.getColor().equals(getColor())) return true;
        if(card instanceof NormalCard)
            return ((NormalCard) card).getNumber() == getNumber();
        return false;
    }

    @Override
    public <T> T match(CardMatcher<T> matcher) {
        return matcher.onNormal(this);
    }

    @Override
    public String toString() {
        return getColor().toString() + "_" + number;
    }

    @Override
    public int compareTo(UnoCard card) {
        int colorCmp = getColor().compareTo(card.getColor());
        if(colorCmp != 0) return colorCmp;
        if(!(card instanceof NormalCard)) return -1;
        return (int) getNumber() - (int) ((NormalCard) card).getNumber();
    }
}
