package com.example.cardgame.core.actions;

import com.example.cardgame.core.GameState;
import com.example.cardgame.core.events.GameEvent;

import java.io.Serializable;
import java.util.List;

public interface Action extends Serializable {
    boolean isLegal(GameState state, ActionFrame frame);
    List<GameEvent> intoEvents(GameState state, ActionFrame frame);
}
