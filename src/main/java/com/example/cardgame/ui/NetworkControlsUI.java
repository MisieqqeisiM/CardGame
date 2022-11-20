package com.example.cardgame.ui;

import com.example.cardgame.network.NetworkControls;
import com.example.cardgame.network.messages.NetworkMessage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class NetworkControlsUI extends VBox {
    public NetworkControlsUI(NetworkControls controls) {
        var addBot =  new FancyButton("add bot", controls::addBot, Color.GRAY, 15, 100);
        var removeBot =  new FancyButton("remove bot", controls::removeBot, Color.GRAY, 15, 100);
        var reset =  new FancyButton("reset", controls::reset, Color.GRAY, 15, 100);
        setSpacing(10);
        getChildren().add(addBot);
        getChildren().add(removeBot);
        getChildren().add(reset);
    }
}
