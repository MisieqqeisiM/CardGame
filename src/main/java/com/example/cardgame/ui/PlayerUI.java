package com.example.cardgame.ui;

import com.example.cardgame.core.PlayerStateListener;
import com.example.cardgame.core.actions.*;
import com.example.cardgame.core.cards.UnoCard;
import com.example.cardgame.core.events.*;
import com.example.cardgame.network.NetworkControls;
import com.example.cardgame.network.NetworkUnoPlayer;
import com.example.cardgame.network.messages.NetworkInfo;
import com.example.cardgame.network.messages.PlayerStatusUpdate;
import javafx.animation.*;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class PlayerUI extends NetworkUnoPlayer {
    Pane ui = new Pane();
    MyHandUI hand;
    CardUI card;
    List<YourHandUI> hands = new ArrayList<>();
    List<PlayerStatus> statuses;
    ColorPicker picker;
    Circle playerMarker;
    NetworkControlsUI networkControls;

    EmptyCardUI deck;
    ChallengeButtons challengeButtons;
    Notifier notifier;

    Queue<UIAnimation> animationQueue = new ArrayDeque<>();

    @Override
    public void onNetworkInfo(NetworkInfo info) {
        statuses = info.info().stream().map(i -> new PlayerStatus(i.connectionState(), i.name())).toList();
        ui.getChildren().addAll(statuses);
        realignStatuses();
    }

    public void realignStatuses() {
        if(statuses == null) return;
        for(int i = 0; i < statuses.size(); i++) {
            statuses.get(i).setLayoutX(getHandX(i) - 100);
            statuses.get(i).setLayoutY(getHandY(i) - 110);
        }
    }

    public void realignNetworkControls() {
        if(networkControls == null) return;
        networkControls.setLayoutX(10);
        networkControls.setLayoutY(10);
    }

    @Override
    public void onPlayerJoined(PlayerStatusUpdate player) {
        statuses.get(player.id()).update(player.info().connectionState(), player.info().name());
    }

    @Override
    public void load(NetworkControls controls) {
        this.networkControls = new NetworkControlsUI(controls);
        ui.getChildren().add(networkControls);
        realignNetworkControls();
    }

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
        ui.getChildren().clear();
        hands.clear();
        hand = new MyHandUI(state.hand, card -> playAction(new PlayCard(card)));
        card = new CardUI(state.card);
        playerMarker = new Circle(15);
        playerMarker.setFill(Color.LIMEGREEN);
        playerMarker.setStroke(Color.BLACK);
        playerMarker.setStrokeWidth(5);
        picker = new ColorPicker(color -> playAction(new ChooseColor(color)));
        challengeButtons = new ChallengeButtons(() -> playAction(new AcceptChallenge()), () -> playAction(new RefuseChallenge()));

        deck = new EmptyCardUI();
        deck.setOnMouseEntered(e -> {
            deck.setScaleX(1.1);
            deck.setScaleY(1.1);
        });
        deck.setOnMouseExited(e -> {
            deck.setScaleX(1);
            deck.setScaleY(1);
        });

        notifier = new Notifier();

        deck.setCursor(Cursor.HAND);
        deck.setOnMouseClicked(e -> playAction(new DrawCard()));

        ui.getChildren().add(card);
        ui.getChildren().add(hand);
        ui.getChildren().add(deck);


        picker.setVisible(state.canChooseColor());
        challengeButtons.setVisible(state.canChallenge());

        for(int i = (state.myId + 1) % state.playerCount; i != state.myId; i = (i + 1) % state.playerCount) {
            var yourHand = new YourHandUI(state.handSizes.get(i));
            hands.add(yourHand);
            ui.getChildren().add(yourHand);
        }

        ui.getChildren().add(playerMarker);
        ui.getChildren().add(picker);
        ui.getChildren().add(challengeButtons);
        ui.getChildren().add(notifier);

        state.addListener(new PlayerStateListener() {
            @Override
            public void onCardDrawn(int player, UnoCard card) {
                if(player == state.myId){
                    tryPlay(new UIAnimation() {
                        @Override
                        void play() {
                            flyingCard(card, getDeckX(), getDeckY(), hand.getLayoutX() + hand.getCardPosition(card) * 70 + 15, getHandY(player));
                            hand.addCard(card, this::onFinished);
                        }
                    });
                } else {
                    tryPlay(new UIAnimation() {
                        @Override
                        void play() {
                            flyingCard(card, getDeckX(), getDeckY(), getHandX(player) + getHand(player).getPrefWidth() / 2 - 40, getHandY(player));
                            getHand(player).addCard(this::onFinished);
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
                            flyingCard(card, hand.getLayoutX() + hand.getCardPosition(card) * 70 + 40, getHandY(player), ui.getWidth() / 2, ui.getHeight() / 2);
                            hand.removeCard(card, () -> {
                                PlayerUI.this.card.update(card);
                                this.onFinished();
                            });
                        }
                    });
                } else {
                    tryPlay(new UIAnimation() {
                        @Override
                        void play() {
                            flyingCard(card, getHand(player).getLayoutX() + getHand(player).getPrefWidth() - 50, getHandY(player), ui.getWidth() / 2, ui.getHeight() / 2);
                            getHand(player).removeCard(() -> {
                                PlayerUI.this.card.update(card);
                                this.onFinished();
                            });
                        }
                    });
                }
            }

            @Override
            public void onNextRound(int currentPlayer) {
                tryPlay(new UIAnimation() {
                    @Override
                    void play() {
                        var animation = new TranslateTransition();
                        animation.setNode(playerMarker);
                        animation.setToX(getHandX(currentPlayer));
                        animation.setToY(getHandY(currentPlayer) + 100);
                        animation.setInterpolator(Interpolator.EASE_BOTH);
                        animation.setOnFinished(e -> onFinished());
                        animation.play();
                    }
                });
            }
        });
        realign();
    }

    void flyingCard(UnoCard card, double fromX, double fromY, double toX, double toY){
        Node flyingCard;
        if(card != null) flyingCard = new CardUI(card);
        else flyingCard = new EmptyCardUI();
        var flight = new TranslateTransition();
        flyingCard.setTranslateX(fromX - 50);
        flyingCard.setTranslateY(fromY - 75);
        flight.setNode(flyingCard);
        flight.setDuration(Duration.seconds(0.3));
        flight.setToX(toX - 50);
        flight.setToY(toY - 75);

        ui.getChildren().add(flyingCard);

        flight.setOnFinished(e -> {
            ui.getChildren().remove(flyingCard);
        });
        flight.play();
    }

    double getHandX(int player) {
        var handID = euclideanMod(player - state.myId, state.playerCount);
        var centerX = ui.getWidth() / 2;
        final var tableRadiusX = 300;
        return centerX - tableRadiusX * Math.sin(handID * 2 * Math.PI / state.playerCount);
    }

    double getHandY(int player) {
        var handID = euclideanMod(player - state.myId, state.playerCount);
        var centerY = ui.getHeight() / 2;
        final var tableRadiusY = 300;
        return centerY + tableRadiusY * Math.cos(handID * 2 * Math.PI / state.playerCount);
    }

    double getDeckX() {
        return ui.getWidth() - 100;
    }
    double getDeckY() {
        return ui.getHeight() - 100;
    }

    static int euclideanMod(int x, int y) {
        int r = Math.abs(x) % Math.abs(y);
        r *= Math.signum(x);
        r = (r + Math.abs(y)) % Math.abs(y);
        return r;
    }
    YourHandUI getHand(int player) {
        var handID = euclideanMod(player - state.myId, state.playerCount) - 1;
        return hands.get(handID);
    }

    public void realign() {
        if(state == null) return;
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
        realignStatuses();
        realignNetworkControls();
        playerMarker.setTranslateX(getHandX(state.currentPlayer));
        playerMarker.setTranslateY(getHandY(state.currentPlayer) + 100);

        hand.setCenter(centerX, centerY + tableRadiusY);
        card.setCenter(centerX, centerY);
        picker.setCenter(centerX, centerY - 150);
        deck.setCenter(getDeckX(), getDeckY());
        notifier.setCenter(centerX, 0);
        challengeButtons.setLayoutX(centerX - challengeButtons.getWidth()/2);
        challengeButtons.setLayoutY(centerY + 100);
    }

    public Pane getUI() {
        return ui;
    }

    void tryProcessEvent() {
        PlayerEvent event;
        while(animationQueue.isEmpty() && (event = nextEvent()) != null) {
            if(event instanceof YouChallanged)
                if(((YouChallanged)event).won)
                    notifier.showMessage("Challenge won");
                else
                    notifier.showMessage("Challenge lost");
            if(event instanceof IChallenged)
                if(((IChallenged)event).won)
                    notifier.showMessage("Challenge won");
                else
                    notifier.showMessage("Challenge lost");
            picker.setVisible(state.canChooseColor());
            challengeButtons.setVisible(state.canChallenge());
            if(event instanceof ColorChosen) {
                card.update(state.card);
            }
            System.out.println(event);
        }
    }

    @Override
    public void eventNotify() {
        tryProcessEvent();
    }

}
