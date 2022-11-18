package com.example.cardgame.core;

import com.example.cardgame.core.actions.ActionFrame;
import com.example.cardgame.core.events.GameEvent;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class UnoGame {
    private final GameState state;

    Queue<GameEvent> eventQueue = new ArrayDeque<>();
    List<UnoPlayer> players = new ArrayList<>();
    public UnoGame(int players) {
        state = new GameState(players);
    }

    public void join(UnoPlayer player) {
        int playerID = players.size();
        player.load(state.getPlayerView(playerID), action -> {
            var frame = new ActionFrame(playerID);
            if(action.isLegal(state, frame))
                applyEvents(action.intoEvents(state, frame));
        });
        players.add(player);
    }

    private void applyEvents(List<GameEvent> events) {
        if(events.isEmpty()) return;
        var newEvents = new ArrayList<GameEvent>();
        events.forEach(event -> {
            newEvents.addAll(event.applyAndGetNextEvents(state));
            for(int player = 0; player < players.size(); player++)
                players.get(player).enqueue(event.getPlayerEvent(player, state));
        });
        applyEvents(newEvents);
        events.forEach(event ->  players.forEach(UnoPlayer::eventNotify));
    }

}
