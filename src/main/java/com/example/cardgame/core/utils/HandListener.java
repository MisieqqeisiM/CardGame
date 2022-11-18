package com.example.cardgame.core.utils;

import com.example.cardgame.core.cards.UnoCard;

import java.util.List;

public interface HandListener {
    void cardDrawn(UnoCard card);
    void cardPlayed(UnoCard card);
}
