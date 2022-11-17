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
    PlayerState(int player, CommonState state, Hand hand, List<Integer> handSizes) {
        super(state);
        this.myId = player;
        this.handSizes = new ArrayList<>(handSizes);
        this.hand = hand;
        System.out.println(this.hand);
    }

    public List<Action> getLegalActions() {
        var result = new ArrayList<Action>();
        switch(turnState) {
            case CHOOSING_CARD -> {
                if(currentPlayer == myId) {
                    result.addAll(hand.getAllThatFit(card).stream().map(PlayCard::new).toList());
                    result.add(new DrawCard());
                }
            }
            case CHOOSING_PLUSFOUR_COLOR, CHOOSING_WILDCARD_COLOR -> {
                if(currentPlayer == myId) {
                    result.add(new ChooseColor(Color.RED));
                    result.add(new ChooseColor(Color.GREEN));
                    result.add(new ChooseColor(Color.BLUE));
                    result.add(new ChooseColor(Color.YELLOW));
                }
            }
            case CHALLENGE -> {
                if(nextPlayer() == myId) {
                    result.add(new AcceptChallenge());
                    result.add(new RefuseChallenge());
                }
            }
            case CARD_PLAYED -> { }
        }
        return result;
    }

    @Override
    public void addCard(int player, UnoCard card) {
        handSizes.set(player, handSizes.get(player) + 1);
        if(myId == player)
            hand.addCard(card);
    }

    @Override
    public void removeCard(int player, UnoCard card) {
        handSizes.set(player, handSizes.get(player) - 1);
        if(myId == player)
            hand.removeCard(card);
    }
}
