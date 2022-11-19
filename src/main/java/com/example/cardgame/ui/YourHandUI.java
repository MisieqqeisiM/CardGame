package com.example.cardgame.ui;

import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class YourHandUI extends HBox {
    int size;
    double centerX, centerY;
    public YourHandUI(int size) {
        this.size = size;
        setManaged(false);
        setSpacing(-80);
        setPrefWidth(size * 20 + 80);
        setPrefHeight(150);
        for(int i = 0; i < size; i++)
            getChildren().add(new EmptyCardUI());
    }

    class WidthTransition extends Transition {
        double from, to;
        public WidthTransition(Duration duration, double from, double to) {
            this.from = from;
            this.to = to;
            setCycleDuration(duration);
        }

        @Override
        protected void interpolate(double v) {
            setPrefWidth(from + v * (to - from));
            setCenter(centerX, centerY);
        }
    }
    public double getNewCardX() {
        return getWidth();
    }
    public void addCard(Runnable onFinished) {
        var animation = new WidthTransition(Duration.seconds(0.3), getPrefWidth(), getPrefWidth() + 20);
        animation.setOnFinished(e->{
            getChildren().add(new EmptyCardUI());
            onFinished.run();
        });
        animation.play();
    }

    public void removeCard(Runnable onFinished) {
        var animation = new WidthTransition(Duration.seconds(0.3), getPrefWidth(), getPrefWidth() - 20);
        getChildren().remove(getChildren().size() - 1);
        animation.setOnFinished(e->{
            onFinished.run();
        });
        animation.play();
    }

    public void setCenter(double x, double y) {
        this.centerX = x;
        this.centerY = y;
        setLayoutX(x - getPrefWidth() / 2);
        setLayoutY(y - getPrefHeight() / 2.);
    }
}
