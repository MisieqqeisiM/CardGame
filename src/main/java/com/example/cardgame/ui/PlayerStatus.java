package com.example.cardgame.ui;

import com.example.cardgame.network.ConnectionState;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PlayerStatus extends HBox {
    Circle circle;
    Label label;
    public PlayerStatus(ConnectionState state, String name) {
        circle = new Circle(7);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(3);
        label = new Label();
        label.setTextFill(Color.BLACK);
        label.setFont(Font.font("", FontWeight.BOLD, 20));

        update(state, name);

        setSpacing(5);
        setAlignment(Pos.CENTER);
        getChildren().add(circle);
        getChildren().add(label);
    }

    public void update(ConnectionState state, String name) {
         var color = switch (state) {
            case LOCAL -> Color.gray(0.2);
            case CONNECTING -> Color.YELLOW;
            case CONNECTED -> Color.GREEN;
            case NONE -> Color.RED;
        };
        circle.setFill(color);
        label.setText(name);
    }
}
