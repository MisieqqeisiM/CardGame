package com.example.cardgame.ui;

import com.example.cardgame.core.cards.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;

public class CardUI extends StackPane {
    private UnoCard card;
    public CardUI(UnoCard card) {
        super();
        setMaxSize(100, 150);
        setMinSize(100, 150);
        setPrefSize(100, 150);
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(5))));
        update(card);

    }

    public void update(UnoCard card) {
        this.card = card;
        getChildren().clear();
        setBackground(new Background(new BackgroundFill(fromCardColor(card), new CornerRadii(10), new Insets(3))));
        var background = new Ellipse(40, 60);
        background.setFill(Color.gray(0.9));
        background.setStroke(Color.BLACK);
        background.setStrokeWidth(4);
        getChildren().add(background);
        var label = new Label(card.match(new CardMatcher<>() {
            @Override
            public String onNormal(NormalCard card) {
                return String.valueOf(card.getNumber());
            }

            @Override
            public String onPlusTwo(PlusTwoCard card) {
                return "+2";
            }

            @Override
            public String onPlusFour(PlusFourCard card) {
                return "+4";
            }

            @Override
            public String onReverse(ReverseCard card) {
                return "R";
            }

            @Override
            public String onSkip(SkipCard card) {
                return "∅";
            }
            @Override
            public String onWild(WildCard card) {
                return "?";
            }
        }));
        label.setFont(Font.font(50));
        label.setAlignment(Pos.CENTER);
        label.setTextFill(Color.BLACK);
        getChildren().add(label);
    }
    public UnoCard getCard() {
        return card;
    }
    public void setCenter(double x, double y) {
        setLayoutX(x - getPrefWidth() / 2);
        setLayoutY(y - getPrefHeight() / 2);
    }
    private static Color fromCardColor(UnoCard card) {
        return switch(card.getColor()) {
            case RED -> Color.RED;
            case GREEN -> Color.GREEN;
            case BLUE -> Color.BLUE;
            case YELLOW -> Color.YELLOW;
            case NONE -> Color.color(0.1, 0.1, 0.1);
        };
    }
}
