package com.example.cardgame.core.utils;

import com.example.cardgame.core.UnoPlayer;
import com.example.cardgame.core.playerevents.PlayerEvent;

import java.util.Random;

public final class RandomPlayer extends UnoPlayer {
    Random random = new Random();
    @Override
    protected void eventNotify() {
        var event = nextEvent();
        var actions = state.getLegalActions();
        if(!actions.isEmpty()) {
            var action = actions.get(random.nextInt(actions.size()));
            playAction(action);
        }
    }

    @Override
    protected void onLoad() { }
}
