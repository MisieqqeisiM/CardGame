package com.example.cardgame.core.cards;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class UnoDeck {
    private static final List<UnoCard> allCards = Arrays.asList(
            new NormalCard(Color.RED, (byte) 0),
            new NormalCard(Color.RED, (byte) 1),
            new NormalCard(Color.RED, (byte) 2),
            new NormalCard(Color.RED, (byte) 3),
            new NormalCard(Color.RED, (byte) 4),
            new NormalCard(Color.RED, (byte) 5),
            new NormalCard(Color.RED, (byte) 6),
            new NormalCard(Color.RED, (byte) 7),
            new NormalCard(Color.RED, (byte) 8),
            new NormalCard(Color.RED, (byte) 9),
            new NormalCard(Color.GREEN, (byte) 0),
            new NormalCard(Color.GREEN, (byte) 1),
            new NormalCard(Color.GREEN, (byte) 2),
            new NormalCard(Color.GREEN, (byte) 3),
            new NormalCard(Color.GREEN, (byte) 4),
            new NormalCard(Color.GREEN, (byte) 5),
            new NormalCard(Color.GREEN, (byte) 6),
            new NormalCard(Color.GREEN, (byte) 7),
            new NormalCard(Color.GREEN, (byte) 8),
            new NormalCard(Color.GREEN, (byte) 9),
            new NormalCard(Color.BLUE, (byte) 0),
            new NormalCard(Color.BLUE, (byte) 1),
            new NormalCard(Color.BLUE, (byte) 2),
            new NormalCard(Color.BLUE, (byte) 3),
            new NormalCard(Color.BLUE, (byte) 4),
            new NormalCard(Color.BLUE, (byte) 5),
            new NormalCard(Color.BLUE, (byte) 6),
            new NormalCard(Color.BLUE, (byte) 7),
            new NormalCard(Color.BLUE, (byte) 8),
            new NormalCard(Color.BLUE, (byte) 9),
            new NormalCard(Color.YELLOW, (byte) 0),
            new NormalCard(Color.YELLOW, (byte) 1),
            new NormalCard(Color.YELLOW, (byte) 2),
            new NormalCard(Color.YELLOW, (byte) 3),
            new NormalCard(Color.YELLOW, (byte) 4),
            new NormalCard(Color.YELLOW, (byte) 5),
            new NormalCard(Color.YELLOW, (byte) 6),
            new NormalCard(Color.YELLOW, (byte) 7),
            new NormalCard(Color.YELLOW, (byte) 8),
            new NormalCard(Color.YELLOW, (byte) 9),
            new ReverseCard(Color.RED),
            new ReverseCard(Color.GREEN),
            new ReverseCard(Color.BLUE),
            new ReverseCard(Color.YELLOW),
            new SkipCard(Color.RED),
            new SkipCard(Color.GREEN),
            new SkipCard(Color.BLUE),
            new SkipCard(Color.YELLOW),
            new PlusTwoCard(Color.RED),
            new PlusTwoCard(Color.GREEN),
            new PlusTwoCard(Color.BLUE),
            new PlusTwoCard(Color.YELLOW),
            new WildCard(Color.NONE),
            new WildCard(Color.NONE),
            new PlusFourCard(Color.NONE),
            new PlusFourCard(Color.NONE)
    );
    private final Random randomGenerator = new Random();

    public UnoCard randomCard() {
        return allCards.get(randomGenerator.nextInt(allCards.size()));
    }
}
