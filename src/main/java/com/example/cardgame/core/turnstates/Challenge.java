package com.example.cardgame.core.turnstates;

import com.example.cardgame.core.CommonState;
import com.example.cardgame.core.GameState;
import com.example.cardgame.core.PlayerState;
import com.example.cardgame.core.actions.AcceptChallenge;
import com.example.cardgame.core.actions.Action;
import com.example.cardgame.core.actions.RefuseChallenge;
import com.example.cardgame.core.events.ChallengeAccepted;
import com.example.cardgame.core.events.ChallengeRefused;
import com.example.cardgame.core.events.IChallenged;
import com.example.cardgame.core.events.YouChallanged;

import java.util.List;

public class Challenge implements TurnState {

    @Override
    public void onChallengeRefused(CommonState state, ChallengeRefused event) {
        state.turnState = new RoundEnd();
    }

    @Override
    public void onChallengeAccepted(GameState state, ChallengeAccepted event) {
        state.turnState = new RoundEnd();
    }

    @Override
    public void onIChallenged(PlayerState state, IChallenged event) {
        state.turnState = new RoundEnd();
    }

    @Override
    public void onYouChallenged(PlayerState state, YouChallanged event) {
        state.turnState = new RoundEnd();
    }

    @Override
    public List<Action> getLegalActions(PlayerState state) {
        return List.of(new AcceptChallenge(), new RefuseChallenge());
    }

    @Override
    public boolean isActionTypeLegal(Action action) {
        return action instanceof AcceptChallenge || action instanceof RefuseChallenge;
    }
}
