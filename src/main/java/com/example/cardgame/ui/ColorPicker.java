package com.example.cardgame.ui;

import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

import java.util.function.Consumer;

public class ColorPicker extends Pane {
    public ColorPicker(Consumer<com.example.cardgame.core.cards.Color> action) {
        setMaxWidth(100);
        setMaxHeight(100);
        getChildren().add(new ColorArc(0, 90, Color.RED,
                ()->action.accept(com.example.cardgame.core.cards.Color.RED)
        ));
        getChildren().add(new ColorArc(90, 90, Color.GREEN,
                ()->action.accept(com.example.cardgame.core.cards.Color.GREEN)
        ));
        getChildren().add(new ColorArc(180, 90, Color.BLUE,
                ()->action.accept(com.example.cardgame.core.cards.Color.BLUE)
        ));
        getChildren().add(new ColorArc(270, 90, Color.YELLOW,
                ()->action.accept(com.example.cardgame.core.cards.Color.YELLOW)
        ));
    }

    public void setCenter(double x, double y) {
        setLayoutX(x -  50);
        setLayoutY(y - 50);
    }

    private static class ColorArc extends Arc {
        ColorArc(double from, double angle, Color color, Runnable action) {
            super(50, 50, 50, 50, from, angle);
            setType(ArcType.ROUND);
            setStrokeWidth(5);
            setStroke(Color.BLACK);
            setStrokeLineJoin(StrokeLineJoin.ROUND);
            setFill(color);
            setCursor(Cursor.HAND);
            setOnMouseEntered(e -> {
                setRadiusX(55);
                setRadiusY(55);
                setViewOrder(-1);
            });
            setOnMouseExited(e -> {
                setRadiusX(50);
                setRadiusY(50);
                setViewOrder(0);
            });
            setOnMouseClicked(e->action.run());
        }
    }
}
