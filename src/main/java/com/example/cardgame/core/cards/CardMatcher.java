package com.example.cardgame.core.cards;

public interface CardMatcher<T> {
    default T onNormal(NormalCard card) {return null;}
    default T onPlusTwo(PlusTwoCard card) {return null;}
    default T onPlusFour(PlusFourCard card) {return null;}
    default T onReverse(ReverseCard card) {return null;}
    default T onSkip(SkipCard card) {return null;}
    default T onWild(WildCard card) {return null;}
}
