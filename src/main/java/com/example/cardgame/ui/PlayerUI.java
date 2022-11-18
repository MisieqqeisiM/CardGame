package com.example.cardgame.ui;

import com.example.cardgame.core.PlayerStateListener;
import com.example.cardgame.core.UnoPlayer;
import com.example.cardgame.core.actions.*;
import com.example.cardgame.core.cards.UnoCard;
import com.example.cardgame.core.events.PlayerEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class PlayerUI extends UnoPlayer {
    Pane ui = new Pane();
    MyHandUI hand;
    CardUI card;
    List<YourHandUI> hands = new ArrayList<>();
    ColorPicker picker;

    Queue<UIAnimation> animationQueue = new ArrayDeque<>();

    abstract class UIAnimation {
        abstract void play();
        void onFinished() {
            animationQueue.poll();
            if(!animationQueue.isEmpty())
                animationQueue.peek().play();
            else
                tryProcessEvent();
        }
    }

    void tryPlay(UIAnimation animation) {
        if(animationQueue.isEmpty()) {
            animationQueue.add(animation);
            animation.play();
        } else {
            animationQueue.add(animation);
        }
    }

    public PlayerUI() {

    }
    @Override
    protected void onLoad() {
        hand = new MyHandUI(state.hand, card -> playAction(new PlayCard(card)));
        card = new CardUI(state.card);
        picker = new ColorPicker(color -> playAction(new ChooseColor(color)));
        var buttons = new HBox();
        var drawCard = new Button(":(");
        drawCard.setOnAction(e -> playAction(new DrawCard()));
        var accept = new Button("B)");
        accept.setOnAction(e -> playAction(new AcceptChallenge()));
        var refuse = new Button(":')");
        refuse.setOnAction(e -> playAction(new RefuseChallenge()));
        buttons.getChildren().add(drawCard);
        buttons.getChildren().add(accept);
        buttons.getChildren().add(refuse);

        ui.getChildren().add(buttons);
        ui.getChildren().add(card);
        ui.getChildren().add(hand);
        ui.getChildren().add(picker);

        picker.setVisible(state.canChooseColor());
        for(int i = (state.myId + 1) % state.playerCount; i != state.myId; i = (i + 1) % state.playerCount) {
            var yourHand = new YourHandUI(state.handSizes.get(i));
            hands.add(yourHand);
            ui.getChildren().add(yourHand);
        }

        state.addListener(new PlayerStateListener() {
            @Override
            public void onCardDrawn(int player, UnoCard card) {
                if(player == state.myId){
                    tryPlay(new UIAnimation() {
                        @Override
                        void play() {
                            hand.addCard(card, this::onFinished);
                        }
                    });
                } else {
                    var handID = (player - state.myId) % state.playerCount - 1;
                    tryPlay(new UIAnimation() {
                        @Override
                        void play() {
                            hands.get(handID).addCard(this::onFinished);
                        }
                    });

                }
            }

            @Override
            public void onCardPlayed(int player, UnoCard card) {
                if(player == state.myId){
                    tryPlay(new UIAnimation() {
                        @Override
                        void play() {
                            hand.removeCard(card, this::onFinished);
                        }
                    });
                } else {
                    var handID = (player - state.myId) % state.playerCount - 1;
                    tryPlay(new UIAnimation() {
                        @Override
                        void play() {
                            hands.get(handID).removeCard(this::onFinished);
                        }
                    });
                }
            }
        });
    }

    public void realign() {
        var centerX = ui.getWidth() / 2;
        var centerY = ui.getHeight() / 2;

        final var tableRadiusX = 300;
        final var tableRadiusY = 300;
        System.out.println(centerX);
        System.out.println(centerY);

        for(int i = 0; i < hands.size(); i++) {
            var position = (i + 1) % state.playerCount;
            hands.get(i).setCenter(
                    centerX - tableRadiusX * Math.sin(position * 2 * Math.PI / state.playerCount),
                    centerY + tableRadiusY * Math.cos(position * 2 * Math.PI / state.playerCount)
            );
        }

        hand.setCenter(centerX, centerY + tableRadiusY);
        card.setCenter(centerX, centerY);
        picker.setCenter(centerX, centerY - 150);
    }

    public Pane getUI() {
        return ui;
    }

    void tryProcessEvent() {
        PlayerEvent event;
        while(animationQueue.isEmpty() && (event = nextEvent()) != null) {
            picker.setVisible(state.canChooseColor());
            card.update(state.card);
            System.out.println(event);
        }
    }

    @Override
    protected void eventNotify() {
        tryProcessEvent();
    }

}
