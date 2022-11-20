package com.example.cardgame.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class FancyButton extends StackPane {
    FancyButton(String text, Runnable onAction, Color color, double fontSize, double width) {
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(5))));
        setBackground(new Background(new BackgroundFill(color, new CornerRadii(10), new Insets(3))));
        setAlignment(Pos.CENTER);
        setPrefWidth(width);
        var label = new Label(text);
        label.setTextFill(Color.BLACK);
        label.setFont(Font.font("", FontWeight.BOLD, fontSize));
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
