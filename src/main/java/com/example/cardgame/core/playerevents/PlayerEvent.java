package com.example.cardgame.core.playerevents;

import com.example.cardgame.core.PlayerState;

public interface PlayerEvent {
    void apply(PlayerState view);
}
