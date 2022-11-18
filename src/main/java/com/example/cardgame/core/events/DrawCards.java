package com.example.cardgame.core.events;

import com.example.cardgame.core.GameState;
import com.example.cardgame.core.cards.UnoCard;

import java.util.List;
import java.util.stream.Stream;

public class DrawCards implements GameEvent {
    public final int player;
    public final int number;
    public List<UnoCard> cards;

    public DrawCards(int player, int number) {
        this.player = player;
        this.number = number;
    }

    @Override
    public List<GameEvent> applyAndGetNextEvents(GameState state) {
        cards = Stream.generate(state.deck::randomCard).limit(number).toList();
        state.turnState.onDrawCards(state, this);
        return List.of();
    }

    @Override
    public PlayerEvent getPlayerEvent(int player, GameState game) {
        if(player == this.player)
            return new IDrawCards(cards);
        else
            return new YouDrawCards(this.player, number);
    }
}
