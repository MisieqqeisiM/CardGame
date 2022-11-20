package com.example.cardgame.core.utils;

import com.example.cardgame.core.UnoPlayer;

import java.util.Random;

public class RandomPlayer extends UnoPlayer {
    Random random = new Random();
    @Override
    public void eventNotify() {
        nextEvent();
        makeRandomMove();
    }
    private void makeRandomMove() {
        var actions = state.getLegalActions();
        if(!actions.isEmpty()) {
            var action = actions.get(random.nextInt(actions.size()));
            playAction(action);
        }
    }

    @Override
    protected void onLoad() {
        makeRandomMove();
    }
}
