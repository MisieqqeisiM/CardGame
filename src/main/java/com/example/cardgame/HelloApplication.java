package com.example.cardgame;

import com.example.cardgame.core.UnoGame;
import com.example.cardgame.core.utils.RandomPlayer;
import com.example.cardgame.ui.PlayerUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("UNO?");
        Pane root = new Pane();

        var player = new PlayerUI();

        var game = new UnoGame(4);
        game.join(player);
        game.join(new RandomPlayer());
        game.join(new RandomPlayer());
        game.join(new RandomPlayer());

        root.getChildren().add(player.getUI());
        root.setBackground(new Background(new BackgroundFill(new Color(0.3, 0.3, 0.3, 1.0), CornerRadii.EMPTY, Insets.EMPTY)));

        player.getUI().prefWidthProperty().bind(root.widthProperty());
        player.getUI().prefHeightProperty().bind(root.heightProperty());
        stage.widthProperty().addListener((obs, oldVal, newVal) -> Platform.runLater(player::realign));

        stage.heightProperty().addListener((obs, oldVal, newVal) -> Platform.runLater(player::realign));


        stage.setScene(new Scene(root, 1000, 800));
        stage.show();

        Platform.runLater(player::realign);
    }

    public static void main(String[] args) {
        launch();
    }
}