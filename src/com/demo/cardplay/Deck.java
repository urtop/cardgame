package com.demo.cardplay;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author: MarkTao
 * @Date: 2019/8/8 19:32
 * @Version 1.0
 */
public class Deck {

  private volatile Stack<Card> cards = new Stack<>();

  public AtomicBoolean isGameOver() {
    return gameOver;
  }

  public void setGameOver(AtomicBoolean gameOver) {
    this.gameOver = gameOver;
  }

  private AtomicBoolean gameOver = new AtomicBoolean(false);

  public Deck() {
    init();
  }

  public void init() {
    for (int i = 1; i <= 54; i++) {
      Card card = new Card(i);
      cards.add(card);
    }
  }

  public void shuffle() {
    Collections.shuffle(cards);
  }

  public Card pop() {
    if (!cards.empty()) {
      return cards.pop();
    } else {
      return null;
    }
  }

  public Collection<Card> getCards() {
    return cards;
  }

}
