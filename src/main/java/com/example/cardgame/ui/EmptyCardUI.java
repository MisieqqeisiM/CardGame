package com.example.cardgame.ui;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class EmptyCardUI extends StackPane {
    public EmptyCardUI() {
        setMaxSize(100, 150);
        setMinSize(100, 150);
        setPrefSize(100, 150);
        setBackground(new Background(new BackgroundFill(Color.gray(0.5), new CornerRadii(10), new Insets(3))));
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(5))));
    }
}
