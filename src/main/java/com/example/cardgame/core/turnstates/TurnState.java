package com.example.cardgame.core.turnstates;

import com.example.cardgame.core.CommonState;
import com.example.cardgame.core.GameState;
import com.example.cardgame.core.PlayerState;
import com.example.cardgame.core.actions.Action;
import com.example.cardgame.core.events.*;
import com.example.cardgame.core.events.CardPlayed;

import java.io.Serializable;
import java.util.List;

public interface TurnState extends Serializable {
    default void onCardPlayed(CommonState state, CardPlayed event) {
        throw new RuntimeException("Unexpected Event");
    }
    default void onChallengeRefused(CommonState state, ChallengeRefused event) {
        throw new RuntimeException("Unexpected Event");
    }
    default void onColorChosen(CommonState state,  ColorChosen event) {
        throw new RuntimeException("Unexpected Event");
    }
    default void onNextTurn(CommonState state,  NextTurn event) {
        throw new RuntimeException("Unexpected Event");
    }
    default void onSkipTurn(CommonState state,  SkipTurn event) {
        throw new RuntimeException("Unexpected Event");
    }

    default void onChallengeAccepted(GameState state, ChallengeAccepted event) {
        throw new RuntimeException("Unexpected Event");
    }
    default void onIChallenged(PlayerState state, IChallenged event) {
        throw new RuntimeException("Unexpected Event");
    }
    default void onYouChallenged(PlayerState state, YouChallanged event) {
        throw new RuntimeException("Unexpected Event");
    }

    default void onDrawCards(GameState state, DrawCards event) {
        throw new RuntimeException("Unexpected Event");
    }
    default void onIDrawCards(PlayerState state, IDrawCards event) {
        throw new RuntimeException("Unexpected Event");
    }
    default void onYouDrawCards(PlayerState state, YouDrawCards event) {
        throw new RuntimeException("Unexpected Event");
    }
    List<Action> getLegalActions(PlayerState state);
    boolean isActionTypeLegal(Action action);
}
