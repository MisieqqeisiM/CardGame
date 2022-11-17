package com.example.cardgame.ui;

import javafx.application.Platform;
import javafx.scene.layout.HBox;

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

    public void resize(int n) {
        while(n > size) {
            getChildren().add(new EmptyCardUI());
            size++;
        }
        while(n < size) {
            getChildren().remove(0);
            size--;
        }
        setPrefWidth(size * 20 + 80);
        Platform.runLater(() -> setCenter(centerX, centerY));
    }

    public void setCenter(double x, double y) {
        this.centerX = x;
        this.centerY = y;
        setLayoutX(x - getPrefWidth() / 2);
        setLayoutY(y - getPrefHeight() / 2.);
    }
}
