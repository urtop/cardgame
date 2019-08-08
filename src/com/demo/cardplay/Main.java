package com.demo.cardplay;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: MarkTao
 * @Date: 2019/8/8 19:29
 * @Version 1.0
 */
public class Main {
  public static void main(String[] args) {
    //init the Deck and load cards.
    Deck deck = new Deck();

    //shuffle the cards of deck
    deck.shuffle();


    //The player(thread) 0 should be the first one to took the card.
    AtomicInteger activeId = new AtomicInteger(0);

    ReentrantLock lock = new ReentrantLock();
    //Multi conditions for high performance
    Condition[] conditions = new Condition[Constant.MAX_PLAYER];

    for (int i = 0; i <Constant.MAX_PLAYER; i++) {
      conditions[i] = lock.newCondition();
      new Thread(new Hand(deck,lock,conditions,i,activeId), "p"+i).start();
    }
  }
}
