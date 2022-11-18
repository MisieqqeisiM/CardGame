package com.example.cardgame.core.events;

import com.example.cardgame.core.GameState;

import java.util.List;

public class ChallengeAccepted implements GameEvent {
    @Override
    public List<GameEvent> applyAndGetNextEvents(GameState state) {
        boolean won = state.hands.get(state.currentPlayer).losesChallenge(state.previousCard);
        state.turnState.onChallengeAccepted(state, this);
        if(won) return List.of(new DrawCards(state.currentPlayer, 4), new NextTurn());
        else return List.of(new DrawCards(state.nextPlayer(), 6), new NextTurn());
    }

    @Override
    public PlayerEvent getPlayerEvent(int player, GameState state) {
        var hand = state.hands.get(state.currentPlayer);
        if(player == state.nextPlayer())
            return new IChallenged(hand.clone());
        else
            return new YouChallanged(hand.losesChallenge(state.previousCard));
    }
}
