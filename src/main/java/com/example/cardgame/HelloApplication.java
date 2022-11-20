package com.example.cardgame;

import com.example.cardgame.core.UnoGame;
import com.example.cardgame.core.utils.RandomPlayer;
import com.example.cardgame.network.Client;
import com.example.cardgame.network.RemoteGame;
import com.example.cardgame.ui.PlayerUI;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        var dialog = new TextInputDialog();
        dialog.setHeaderText("Enter your name");
        Optional<String> name = dialog.showAndWait();
        if(name.isEmpty()) return;
        client(stage, name.get());

    }
    private static void client(Stage stage, String name) throws IOException {

        var player = new PlayerUI();
        var client = new Client("localhost", 3333, name, player);

        var clientLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                client.update();
            }
        };
        clientLoop.start();

        stage.setTitle("UNO?");
        Pane root = new Pane();
        root.getChildren().add(player.getUI());
        root.setBackground(new Background(new BackgroundFill(new Color(0.7, 0.7, 0.7, 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        player.getUI().prefWidthProperty().bind(root.widthProperty());
        player.getUI().prefHeightProperty().bind(root.heightProperty());
        stage.widthProperty().addListener((obs, oldVal, newVal) -> Platform.runLater(player::realign));
        stage.heightProperty().addListener((obs, oldVal, newVal) -> Platform.runLater(player::realign));
        stage.setScene(new Scene(root, 1000, 1000));

        stage.setOnCloseRequest(e -> {
            client.disconnect();
        });
        stage.show();
        Platform.runLater(player::realign);
    }

    public static void main(String[] args) {
        launch();
    }
}