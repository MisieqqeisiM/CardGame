package com.example.cardgame.core.events;

import com.example.cardgame.core.CommonState;
import com.example.cardgame.core.cards.*;

import java.util.List;

public class CardPlayed extends CommonEvent {
    public final int player;
    public final UnoCard card;

    public CardPlayed(int player, UnoCard card) {
        this.player = player;
        this.card = card;
    }

    @Override
    public String toString() {
        return "Player " + this.player + " played " + this.card + ".";
    }

    @Override
    protected List<GameEvent> applyAndGetNextEvents(CommonState state) {
        state.turnState.onCardPlayed(state, this);
        return card.match(new CardMatcher<>() {
            public List<GameEvent> onNormal(NormalCard card) {
                return List.of();
            }
            public List<GameEvent> onPlusTwo(PlusTwoCard card) {
                return List.of(new DrawCards(state.nextPlayer(), 2), new SkipTurn());
            }
            public List<GameEvent> onPlusFour(PlusFourCard card) {
                return List.of();
            }
            public List<GameEvent> onReverse(ReverseCard card) {
                return List.of();
            }
            public List<GameEvent> onSkip(SkipCard card) {
                return List.of();
            }
            public List<GameEvent> onWild(WildCard card) {
                return List.of();
            }
        });
    }
}
