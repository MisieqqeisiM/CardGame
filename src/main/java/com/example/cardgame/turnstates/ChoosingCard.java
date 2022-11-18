package com.example.cardgame.turnstates;

import com.example.cardgame.core.CommonState;
import com.example.cardgame.core.GameState;
import com.example.cardgame.core.PlayerState;
import com.example.cardgame.core.actions.Action;
import com.example.cardgame.core.actions.DrawCard;
import com.example.cardgame.core.actions.PlayCard;
import com.example.cardgame.core.cards.*;
import com.example.cardgame.core.events.CardPlayed;
import com.example.cardgame.core.events.DrawCards;
import com.example.cardgame.core.events.IDrawCards;
import com.example.cardgame.core.events.YouDrawCards;

import java.util.ArrayList;
import java.util.List;

public class ChoosingCard implements TurnState{
    @Override
    public void onCardPlayed(CommonState state, CardPlayed event) {
        state.removeCard(event.player, event.card);
        state.setCard(event.card);
        event.card.match(new CardMatcher<Void>() {
            public Void onNormal(NormalCard card) {
                state.nextTurn();
                return null;
            }
            public Void onPlusFour(PlusFourCard card) {
                state.turnState = new ChoosingPlusFourColor();
                return null;
            }
            public Void onReverse(ReverseCard card) {
                state.reverseDirection();
                state.nextTurn();
                return null;
            }
            public Void onSkip(SkipCard card) {
                state.nextTurn();
                state.nextTurn();
                return null;
            }
            public Void onWild(WildCard card) {
                state.turnState = new ChoosingWildcardColor();
                return null;
            }
        });
    }

    @Override
    public void onDrawCards(GameState state, DrawCards event) {
        state.hands.get(event.player).addCards(event.cards.stream());
        state.turnState = new RoundEnd();
    }

    @Override
    public void onIDrawCards(PlayerState state, IDrawCards event) {
        event.cards.forEach(card -> state.addCard(state.myId, card));
        state.turnState = new RoundEnd();
    }

    @Override
    public void onYouDrawCards(PlayerState state, YouDrawCards event) {
        for(var i = 0; i < event.number; i++)
            state.addCard(event.player, null);
        state.turnState = new RoundEnd();
    }


    @Override
    public List<Action> getLegalActions(PlayerState state) {
        var result = new ArrayList<Action>(state.hand.getAllThatFit(state.card).stream().map(PlayCard::new).toList());
        result.add(new DrawCard());
        return result;
    }

    @Override
    public boolean isActionTypeLegal(Action action) {
        return action instanceof PlayCard || action instanceof DrawCard;
    }
}
