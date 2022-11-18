package com.example.cardgame.core;

import com.example.cardgame.core.actions.Action;
import com.example.cardgame.core.events.PlayerEvent;

import java.util.ArrayDeque;
import java.util.Queue;

public abstract class UnoPlayer {
    protected PlayerState state;
    private PlayerControls controls;
    private Queue<PlayerEvent> events = new ArrayDeque<>();

    public UnoPlayer() {}
    void load(PlayerState state, PlayerControls controls) {
        this.state = state;
        this.controls = controls;
        onLoad();
    }


    void enqueue(PlayerEvent event) {
        events.add(event);
    }

    protected abstract void eventNotify();


    protected final void playAction(Action action) {
        controls.playAction(action);
    }

    protected final PlayerEvent nextEvent() {
        var event = events.poll();
        if(event == null) return null;
        event.apply(state);
        return event;
    }
    protected abstract void onLoad();

}
