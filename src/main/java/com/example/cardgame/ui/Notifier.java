package com.example.cardgame.ui;

import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.ArrayDeque;
import java.util.Queue;

public class Notifier extends StackPane {
    private final Label label;
    double centerX, centerY;

    Queue<String> messages = new ArrayDeque<>();
    public Notifier() {
        setBackground(new Background(new BackgroundFill(Color.gray(0.7), new CornerRadii(10), new Insets(3))));
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(5))));
        label = new Label("text");
        label.setFont(Font.font(30));
        label.setTextFill(Color.BLACK);
        setAlignment(Pos.CENTER);
        setPrefWidth(300);
        setPrefHeight(50);
        getChildren().add(label);
    }

    void setCenter(double x, double y) {
        centerX = x;
        centerY = y;
        setLayoutX(centerX - getPrefWidth() / 2);
        setLayoutY(centerY - getPrefHeight() - 10);
    }

    public void showMessage(String text) {
        if(messages.isEmpty()) {
            messages.add(text);
            show(text);
        } else messages.add(text);
    }

    void show(String text) {
        label.setText(text);
        var transition = new TranslateTransition();
        transition.setNode(this);
        transition.setToY(getPrefHeight());
        transition.setDuration(Duration.seconds(0.2));
        transition.setOnFinished(e -> {
            waitAndHide();
        });
        transition.play();
    }

    void waitAndHide() {
        var transition = new TranslateTransition();
        transition.setNode(this);
        transition.setDuration(Duration.seconds(0.5));
        transition.setOnFinished(e -> {
            hide();
        });
        transition.play();
    }

    void hide() {
        var transition = new TranslateTransition();
        transition.setNode(this);
        transition.setToY(0);
        transition.setDuration(Duration.seconds(0.2));
        transition.setOnFinished(e -> {
            messages.poll();
            if(!messages.isEmpty()) {
                show(messages.peek());
            }
        });
        transition.play();
    }

}
