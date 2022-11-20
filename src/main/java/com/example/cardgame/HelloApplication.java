package com.example.cardgame;

import com.example.cardgame.core.GameState;
import com.example.cardgame.core.UnoGame;
import com.example.cardgame.core.utils.RandomPlayer;
import com.example.cardgame.network.Client;
import com.example.cardgame.network.RemoteGame;
import com.example.cardgame.network.messages.GameData;
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

        dialog = new TextInputDialog("Server address");
        Optional<String> address = dialog.showAndWait();
        client(stage, name.get(), address.orElse("localhost"));

    }
    private static void client(Stage stage, String name, String address) throws IOException {

        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);
        var client = new Client(address, 3333, name) {
            @Override
            public void onReset(GameData gameData) {
                var player = new PlayerUI();
                root.getChildren().clear();
                player.getUI().prefWidthProperty().bind(root.widthProperty());
                player.getUI().prefHeightProperty().bind(root.heightProperty());
                stage.widthProperty().addListener((obs, oldVal, newVal) -> Platform.runLater(player::realign));
                stage.heightProperty().addListener((obs, oldVal, newVal) -> Platform.runLater(player::realign));
                root.getChildren().add(player.getUI());
                setPlayer(player, gameData.state());
                Platform.runLater(player::realign);
            }
        };

        var clientLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                client.update();
            }
        };
        clientLoop.start();

        stage.setTitle("UNO?");
        root.setBackground(new Background(new BackgroundFill(new Color(0.7, 0.7, 0.7, 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        stage.setScene(new Scene(root, 1000, 1000));

        stage.setOnCloseRequest(e -> {
            client.disconnect();
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}