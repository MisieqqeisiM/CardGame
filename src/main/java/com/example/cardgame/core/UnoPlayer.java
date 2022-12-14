package com.example.cardgame.core;

import com.example.cardgame.core.actions.Action;
import com.example.cardgame.core.events.PlayerEvent;

import java.util.ArrayDeque;
import java.util.Queue;

public abstract class UnoPlayer {
    public PlayerState state;
    private PlayerControls controls;
    private Queue<PlayerEvent> events = new ArrayDeque<>();

    public UnoPlayer() {}
    public void load(PlayerState state, PlayerControls controls) {
        this.state = state;
        this.controls = controls;
        onLoad();
    }


    public void enqueue(PlayerEvent event) {
        events.add(event);
    }

    public abstract void eventNotify();


    public final void playAction(Action action) {
        controls.playAction(action);
    }

    public final PlayerEvent nextEvent() {
        var event = events.poll();
        if(event == null) return null;
        event.apply(state);
        return event;
    }
    protected abstract void onLoad();

}
