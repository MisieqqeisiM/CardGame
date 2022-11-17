package com.example.cardgame.core.events;

import com.example.cardgame.core.CommonState;
import com.example.cardgame.core.cards.*;
import com.example.cardgame.core.utils.TurnState;

import java.util.List;

public class CardPlayed extends CommonEvent {
    private final int player;
    private final UnoCard card;

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
        if(state.turnState != TurnState.CHOOSING_CARD) throw new RuntimeException("Invalid event");
        state.removeCard(player, card);
        state.setCard(card);
        state.turnState = TurnState.CARD_PLAYED;
        return card.match(new CardMatcher<>() {
            public List<GameEvent> onNormal(NormalCard card) {
                state.nextTurn();
                return List.of();
            }
            public List<GameEvent> onPlusTwo(PlusTwoCard card) {
                return List.of(new DrawCards(state.nextPlayer(), 2), new SkipTurn());
            }
            public List<GameEvent> onPlusFour(PlusFourCard card) {
                state.turnState = TurnState.CHOOSING_PLUSFOUR_COLOR;
                return List.of();
            }
            public List<GameEvent> onReverse(ReverseCard card) {
                state.reverseDirection();
                state.nextTurn();
                return List.of();
            }
            public List<GameEvent> onSkip(SkipCard card) {
                state.nextTurn();
                state.nextTurn();
                return List.of();
            }
            public List<GameEvent> onWild(WildCard card) {
                state.turnState = TurnState.CHOOSING_WILDCARD_COLOR;
                return List.of();
            }
        });
    }
}
