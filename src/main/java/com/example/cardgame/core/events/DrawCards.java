package com.example.cardgame.core.events;

import com.example.cardgame.core.CommonState;
import com.example.cardgame.core.GameState;
import com.example.cardgame.core.cards.UnoCard;
import com.example.cardgame.core.playerevents.IDrawCards;
import com.example.cardgame.core.playerevents.PlayerEvent;
import com.example.cardgame.core.playerevents.YouDrawCards;

import java.util.List;
import java.util.stream.Stream;

public class DrawCards implements GameEvent {
    private final int player;
    private final int number;
    private List<UnoCard> cards;

    public DrawCards(int player, int number) {
        this.player = player;
        this.number = number;
    }

    @Override
    public List<GameEvent> applyAndGetNextEvents(GameState state) {
        cards = Stream.generate(state.deck::randomCard).limit(number).toList();
        state.hands.get(player).addCards(cards.stream());
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
