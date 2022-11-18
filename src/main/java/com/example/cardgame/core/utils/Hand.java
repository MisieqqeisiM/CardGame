package com.example.cardgame.core.utils;

import com.example.cardgame.core.cards.PlusFourCard;
import com.example.cardgame.core.cards.UnoCard;

import java.util.List;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Hand implements Cloneable{
    TreeMap<UnoCard, Integer> counts;
    int size;

    public Hand(Stream<UnoCard> cards) {
        counts = cards.collect(Collectors.groupingBy(
                Function.identity(),
                TreeMap::new,
                Collectors.summingInt(x -> 1)
        ));
        size = counts.values().stream().mapToInt(Integer::intValue).sum();
    }


    public void addCard(UnoCard card) {
        counts.merge(card, 1, Integer::sum);
        size++;
    }
    public void addCards(Stream<UnoCard> cards) {
        cards.forEach(this::addCard);
    }

    public void forEachCard(Consumer<UnoCard> consumer) {
        counts.forEach((card, count) -> {
            for(int i = 0; i < count; i++)
                consumer.accept(card);
        });
    }

    public boolean contains(UnoCard card) {
        return counts.containsKey(card);
    }

    public void removeCard(UnoCard card) {
        var count = counts.get(card);
        if(count == null) throw new RuntimeException("Tried removing nonexistent card from hand");
        if(count == 1) counts.remove(card);
        else counts.put(card, count - 1);
        size--;
    }

    public List<UnoCard> getAllThatFit(UnoCard card) {
        return counts.keySet().stream().filter(cardInHand -> cardInHand.fitsOn(card)).toList();
    }

    public boolean losesChallenge(UnoCard previousCard) {
        return counts.keySet().stream().anyMatch(cardInHand -> {
            if (cardInHand instanceof PlusFourCard) return false;
            return cardInHand.fitsOn(previousCard);
        });
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return counts.toString();
    }

    @Override
    public Hand clone() {
        try {
            Hand result = (Hand) super.clone();
            result.counts = new TreeMap<>(counts);
            return result;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone messed up in superclass");
        }
    }
}
