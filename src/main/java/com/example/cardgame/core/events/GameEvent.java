package com.example.cardgame.core.events;

import com.example.cardgame.core.GameState;

import java.util.List;

public interface GameEvent {
    List<GameEvent> applyAndGetNextEvents(GameState state);
    PlayerEvent getPlayerEvent(int player, GameState game);
}
