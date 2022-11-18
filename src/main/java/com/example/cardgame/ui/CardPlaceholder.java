package com.example.cardgame.ui;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;

import javafx.scene.paint.Color;
import javafx.util.Duration;

public class CardPlaceholder extends Pane {
    public CardPlaceholder(boolean full){
        setMaxHeight(150);
        setMinHeight(150);
        setPrefHeight(150);
        if(full)
            setPrefWidth(100);
        else
            setPrefWidth(30);
        //setBackground(new Background(new BackgroundFill(Color.GREEN, new CornerRadii(10), new Insets(3))));
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
        }
    }
    Transition grow() {
        return new WidthTransition(Duration.seconds(0.3), 30, 100);
    }
    Transition shrink() {
        return new WidthTransition(Duration.seconds(0.3), 100, 30);
    }
}
