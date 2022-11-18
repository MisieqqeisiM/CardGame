package com.example.cardgame.core;

import com.example.cardgame.core.cards.UnoCard;
import com.example.cardgame.core.cards.UnoDeck;
import com.example.cardgame.core.utils.Direction;
import com.example.cardgame.core.utils.Hand;
import com.example.cardgame.turnstates.ChoosingCard;

import java.util.List;
import java.util.stream.Stream;

public class GameState extends CommonState {
    public List<Hand> hands;
    public UnoDeck deck = new UnoDeck();

    public GameState(int players) {
        super(players, 0, Direction.RIGHT, null, new ChoosingCard());
        this.card = deck.randomCard();
        this.hands = Stream.generate(
                () -> new Hand(Stream.generate(deck::randomCard).limit(7))
        ).limit(players).toList();
    }

    public PlayerState getPlayerView(int player) {
        if(player < 0 || player >= playerCount) throw new IllegalArgumentException("No such player");
        return new PlayerState(
                player,
                this,
                hands.get(player).clone(),
                hands.stream().map(Hand::getSize).toList()
        );
    }

    @Override
    public void addCard(int player, UnoCard card) {
        hands.get(player).addCard(card);
    }

    @Override
    public void removeCard(int player, UnoCard card) {
        hands.get(player).removeCard(card);
    }
}
