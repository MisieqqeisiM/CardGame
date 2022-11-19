package com.example.cardgame.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ChallengeButtons extends VBox {
    class Button extends StackPane{
        Button(String text, Runnable onAction, Color color) {
            setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(5))));
            setBackground(new Background(new BackgroundFill(color, new CornerRadii(10), new Insets(3))));
            setAlignment(Pos.CENTER);
            setPrefWidth(50);
            var label = new Label(text);
            label.setTextFill(Color.BLACK);
            label.setFont(Font.font("", FontWeight.BOLD, 20));
            getChildren().add(label);
            setCursor(Cursor.HAND);
            setOnMouseEntered(e -> {
                setScaleX(1.1);
                setScaleY(1.1);
            });

            setOnMouseExited(e -> {
                setScaleX(1);
                setScaleY(1);
            });
            setOnMouseClicked(e -> onAction.run());
        }
    }
    public ChallengeButtons(Runnable onAccept, Runnable onRefuse) {
        var message = new Label("Challenge?");
        message.setFont(Font.font("", FontWeight.BOLD, 20));
        message.setTextFill(Color.BLACK);

        var buttons = new HBox();
        buttons.setSpacing(10);
        var acceptButton = new Button("YES", onAccept, Color.GREEN);
        var refuseButton = new Button("NO", onRefuse, Color.RED);
        buttons.getChildren().add(acceptButton);
        buttons.getChildren().add(refuseButton);

        setAlignment(Pos.CENTER);
        setSpacing(5);
        getChildren().add(message);
        getChildren().add(buttons);


    }
}
