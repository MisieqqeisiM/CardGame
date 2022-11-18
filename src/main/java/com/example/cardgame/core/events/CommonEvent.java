package com.example.cardgame.core.events;

import com.example.cardgame.core.CommonState;
import com.example.cardgame.core.GameState;
import com.example.cardgame.core.PlayerState;

import java.util.List;

public abstract class CommonEvent implements GameEvent, PlayerEvent{
    protected abstract List<GameEvent> applyAndGetNextEvents(CommonState state);

    @Override
    public final void apply(PlayerState state) {
        applyAndGetNextEvents(state);
    }

    @Override
    public final List<GameEvent> applyAndGetNextEvents(GameState state) {
        return applyAndGetNextEvents((CommonState) state);
    }

    @Override
    public PlayerEvent getPlayerEvent(int player, GameState game) {
        return this;
    };
}
