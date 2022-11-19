package com.example.cardgame.core;

import com.example.cardgame.core.actions.*;
import com.example.cardgame.core.cards.Color;
import com.example.cardgame.core.cards.UnoCard;
import com.example.cardgame.core.utils.Hand;

import java.util.ArrayList;
import java.util.List;

public class PlayerState extends CommonState {
    public Hand hand;
    public List<Integer> handSizes;
    public int myId;

    List<PlayerStateListener> listeners = new ArrayList<>();
    PlayerState(int player, CommonState state, Hand hand, List<Integer> handSizes) {
        super(state);
        this.myId = player;
        this.handSizes = new ArrayList<>(handSizes);
        this.hand = hand;
        System.out.println(this.hand);
    }

    public void addListener(PlayerStateListener listener) {
        listeners.add(listener);
    }

    public List<Action> getLegalActions() {
        return turnState.getLegalActions(this);
    }

    public boolean canChooseColor() {
        return turnState.isActionTypeLegal(new ChooseColor(Color.RED)) && currentPlayer == myId;
    }

    public boolean canChallenge() {
        return turnState.isActionTypeLegal(new AcceptChallenge()) && nextPlayer() == myId;
    }
    @Override
    public void addCard(int player, UnoCard card) {
        listeners.forEach(listener->listener.onCardDrawn(player, card));
        handSizes.set(player, handSizes.get(player) + 1);
        if(myId == player)
            hand.addCard(card);
    }

    @Override
    public void nextTurn() {
        super.nextTurn();
        listeners.forEach(listener->listener.onNextRound(currentPlayer));
    }

    @Override
    public void removeCard(int player, UnoCard card) {
        listeners.forEach(listener->listener.onCardPlayed(player, card));
        handSizes.set(player, handSizes.get(player) - 1);
        if(myId == player)
            hand.removeCard(card);
    }
}
