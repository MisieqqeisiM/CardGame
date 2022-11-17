package com.example.cardgame.ui;

import com.example.cardgame.core.UnoPlayer;
import com.example.cardgame.core.actions.*;
import com.example.cardgame.core.utils.TurnState;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class PlayerUI extends UnoPlayer {
    Pane ui = new Pane();
    MyHandUI hand;
    CardUI card;
    List<YourHandUI> hands = new ArrayList<>();
    ColorPicker picker;

    public PlayerUI() {

    }
    @Override
    protected void onLoad() {
        hand = new MyHandUI(state.hand, card -> playAction(new PlayCard(card)));
        card = new CardUI(state.card);
        picker = new ColorPicker(color -> playAction(new ChooseColor(color)));
        var buttons = new HBox();
        var drawCard = new Button(":(");
        drawCard.setOnAction(e -> playAction(new DrawCard()));
        var accept = new Button("B)");
        accept.setOnAction(e -> playAction(new AcceptChallenge()));
        var refuse = new Button(":')");
        refuse.setOnAction(e -> playAction(new RefuseChallenge()));
        buttons.getChildren().add(drawCard);
        buttons.getChildren().add(accept);
        buttons.getChildren().add(refuse);

        ui.getChildren().add(buttons);
        ui.getChildren().add(card);
        ui.getChildren().add(hand);
        ui.getChildren().add(picker);

        picker.setVisible(state.turnState == TurnState.CHOOSING_PLUSFOUR_COLOR || state.turnState == TurnState.CHOOSING_WILDCARD_COLOR);
        for(int i = (state.myId + 1) % state.playerCount; i != state.myId; i = (i + 1) % state.playerCount) {
            var yourHand = new YourHandUI(state.handSizes.get(i));
            hands.add(yourHand);
            ui.getChildren().add(yourHand);
        }
    }

    public void realign() {
        var centerX = ui.getWidth() / 2;
        var centerY = ui.getHeight() / 2;

        final var tableRadiusX = 300;
        final var tableRadiusY = 300;
        System.out.println(centerX);
        System.out.println(centerY);

        for(int i = 0; i < hands.size(); i++) {
            var position = (i + 1) % state.playerCount;
            hands.get(i).setCenter(
                    centerX - tableRadiusX * Math.sin(position * 2 * Math.PI / state.playerCount),
                    centerY + tableRadiusY * Math.cos(position * 2 * Math.PI / state.playerCount)
            );
        }

        hand.setCenter(centerX, centerY + tableRadiusY);
        card.setCenter(centerX, centerY);
        picker.setCenter(centerX, centerY - 150);
    }

    public Pane getUI() {
        return ui;
    }

    @Override
    protected void eventNotify() {
        var event = nextEvent();
        for(int i = 0; i < hands.size(); i++) {
            var player = (i + 1) % state.playerCount;
            hands.get(i).resize(state.handSizes.get(player));
        }
        picker.setVisible(state.turnState == TurnState.CHOOSING_PLUSFOUR_COLOR || state.turnState == TurnState.CHOOSING_WILDCARD_COLOR);
        hand.update(state.hand);
        card.update(state.card);
        System.out.println(event);
    }

}
