package com.example.cardgame.core.events;

import com.example.cardgame.core.PlayerState;

public interface PlayerEvent {
    void apply(PlayerState view);
}
