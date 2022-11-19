package com.example.cardgame.ui;

import com.example.cardgame.core.cards.UnoCard;
import com.example.cardgame.core.utils.Hand;
import com.example.cardgame.core.utils.HandListener;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MyHandUI extends HBox {
    private Integer selectedCardID;
    private final List<CardUI> cards = new ArrayList<>();
    private final Consumer<UnoCard> onPick;

    private double centerX, centerY;
    public MyHandUI(Hand hand, Consumer<UnoCard> onPick) {
        this.onPick = onPick;
        setSpacing(-30);
        setCursor(Cursor.HAND);
        setMaxHeight(150);
        setPrefHeight(150);
        update(hand);
        setOnMouseMoved(e -> {
            Integer cardID = Math.min((int)e.getX() / 70, hand.getSize() - 1);
            if(!cardID.equals(selectedCardID)) {
                if(cards.isEmpty()) return;
                if(selectedCardID != null) {
                    unhighlight(this.cards.get(selectedCardID));
                }
                highlight(cards.get(cardID));
                selectedCardID = cardID;
            }
        });
        setOnMouseExited(e -> {
            if(cards.isEmpty()) return;
            if(selectedCardID != null) {
                unhighlight(this.cards.get(selectedCardID));
                selectedCardID = null;
            }
        });
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
            setCenter(centerX, centerY);
        }
    }
    public int getCardPosition(UnoCard card) {
        for(int i = 0; i < cards.size(); i++)
            if(cards.get(i).getCard().compareTo(card) >= 0)
                return i;
        return cards.size();
    }

    public void addCard(UnoCard card, Runnable onFinished) {
        if(selectedCardID != null) {
            unhighlight(this.cards.get(selectedCardID));
            selectedCardID = null;
        }
        int cardPosition = getCardPosition(card);
        var placeholder = new CardPlaceholder(false);
        getChildren().add(cardPosition, placeholder);
        var animation = placeholder.grow();
        animation.setOnFinished(e -> {
            var cardUI = new CardUI(card);
            cardUI.setOnMouseClicked(_e -> onPick.accept(cardUI.getCard()));
            cards.add(cardPosition, cardUI);
            getChildren().set(cardPosition, cardUI);
            onFinished.run();
        });

        setMaxWidth(getMaxWidth() + 70);
        new WidthTransition(Duration.seconds(0.3), getPrefWidth(), getPrefWidth()+70).play();
        animation.play();
    }

    public void removeCard(UnoCard card, Runnable onFinished) {
        if(selectedCardID != null) {
            unhighlight(this.cards.get(selectedCardID));
            selectedCardID = null;
        }
        int cardPosition = getCardPosition(card);
        var placeholder = new CardPlaceholder(true);
        getChildren().set(cardPosition, placeholder);
        var animation = placeholder.shrink();
        animation.setOnFinished(e -> {
            cards.remove(cardPosition);
            getChildren().remove(placeholder);
            setMaxWidth(getMaxWidth() - 70);
            setCenter(centerX, centerY);
            onFinished.run();
        });
        new WidthTransition(Duration.seconds(0.3), getPrefWidth(), getPrefWidth()-70).play();
        animation.play();
    }

    public void update(Hand hand) {
        cards.clear();
        getChildren().clear();
        hand.forEachCard(card -> cards.add(new CardUI(card)));
        cards.forEach(cardUI -> cardUI.setOnMouseClicked(e -> onPick.accept(cardUI.getCard())));
        getChildren().addAll(cards);
        selectedCardID = null;

        var width = hand.getSize() * 70 + 30;
        setMaxWidth(width);
        setPrefWidth(width);
        Platform.runLater(() -> setCenter(centerX, centerY));
    }

    public void setCenter(double x, double y) {
        this.centerX = x;
        this.centerY = y;
        setLayoutX(x - getPrefWidth() / 2);
        setLayoutY(y - getPrefHeight() / 2.);
    }
    private void highlight(CardUI card) {
        card.setScaleX(1.1);
        card.setScaleY(1.1);
        card.setViewOrder(-1);
    }

    private void unhighlight(CardUI card) {
        card.setScaleX(1);
        card.setScaleY(1);
        card.setViewOrder(0);
    }
}
