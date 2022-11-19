package com.example.cardgame.core.events;

import com.example.cardgame.core.PlayerState;

import java.io.Serializable;

public interface PlayerEvent extends Serializable {
    void apply(PlayerState view);
}
