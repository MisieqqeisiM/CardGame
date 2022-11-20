package com.example.cardgame.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ChallengeButtons extends VBox {
    public ChallengeButtons(Runnable onAccept, Runnable onRefuse) {
        var message = new Label("Challenge?");
        message.setFont(Font.font("", FontWeight.BOLD, 20));
        message.setTextFill(Color.BLACK);

        var buttons = new HBox();
        buttons.setSpacing(10);
        var acceptButton = new FancyButton("YES", onAccept, Color.GREEN, 20, 50);
        var refuseButton = new FancyButton("NO", onRefuse, Color.RED, 20, 50);
        buttons.getChildren().add(acceptButton);
        buttons.getChildren().add(refuseButton);

        setAlignment(Pos.CENTER);
        setSpacing(5);
        getChildren().add(message);
        getChildren().add(buttons);


    }
}
