package com.example.cardgame.core;

import com.example.cardgame.core.cards.UnoCard;

import java.util.List;

public interface PlayerStateListener {
    void onCardDrawn(int player, UnoCard card);
    void onCardPlayed(int player, UnoCard card);
    void onNextRound(int currentPlayer);
}
