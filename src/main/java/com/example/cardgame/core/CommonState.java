package com.example.cardgame.core;

import com.example.cardgame.core.cards.Color;
import com.example.cardgame.core.cards.PlusFourCard;
import com.example.cardgame.core.cards.UnoCard;
import com.example.cardgame.core.cards.WildCard;
import com.example.cardgame.core.utils.Direction;
import com.example.cardgame.core.utils.Hand;
import com.example.cardgame.core.utils.TurnState;

public abstract class CommonState {
    public int playerCount;
    public int currentPlayer;
    public Direction direction;
    public UnoCard card;
    public UnoCard previousCard = null;

    public TurnState turnState;

    public CommonState(int playerCount, int currentPlayer, Direction direction, UnoCard card, TurnState turnState) {
        this.playerCount = playerCount;
        this.currentPlayer = currentPlayer;
        this.direction = direction;
        this.card = card;
        this.turnState= turnState;
    }
    public CommonState(CommonState other) {
        this.playerCount = other.playerCount;
        this.currentPlayer = other.currentPlayer;
        this.direction = other.direction;
        this.card = other.card;
        this.turnState = other.turnState;
        this.previousCard = other.previousCard;
    }

    public void nextTurn() {
        currentPlayer = nextPlayer();
        turnState = TurnState.CHOOSING_CARD;
    }


    public void reverseDirection() {
        this.direction = direction.other();
    }
    public int nextPlayer() {
        return switch (direction) {
            case LEFT -> (currentPlayer + playerCount - 1) % playerCount;
            case RIGHT -> (currentPlayer + 1) % playerCount;
        };
    }

    public void chooseColor(Color color) {
        if (color == Color.NONE) throw new RuntimeException("NONE is not a creative color");
        switch (turnState) {
            case CHOOSING_PLUSFOUR_COLOR -> {
                card = new PlusFourCard(color);
                turnState = TurnState.CHALLENGE;
            }
            case CHOOSING_WILDCARD_COLOR -> {
                card = new WildCard(color);
                nextTurn();
            }
            default -> throw new RuntimeException("No color to choose in this state");
        }
    }

    public void setCard(UnoCard card) {
        previousCard = this.card;
        this.card = card;
    }

    public abstract void addCard(int player, UnoCard card);
    public abstract void removeCard(int player, UnoCard card);
}
