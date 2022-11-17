package com.example.cardgame.core.actions;

import com.example.cardgame.core.GameState;
import com.example.cardgame.core.events.ActionFrame;
import com.example.cardgame.core.events.GameEvent;

import java.util.List;

public interface Action {
    boolean isLegal(GameState state, ActionFrame frame);
    List<GameEvent> intoEvents(GameState state, ActionFrame frame);
}
