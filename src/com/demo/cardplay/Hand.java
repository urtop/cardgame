package com.demo.cardplay;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: MarkTao
 * @Date: 2019/8/8 19:36
 * @Version 1.0
 */
public class Hand implements Runnable {

    //The id for identify every thread
    private int id;

    //current active thread ID
    private AtomicInteger activeId;

    private Deck deck;

    private ReentrantLock lock;

    private Condition[] conditions;

    private int score;

    public Hand(Deck deck, ReentrantLock lock, Condition[] conditions, int id, AtomicInteger activeId) {
        this.lock = lock;
        this.conditions = conditions;
        this.deck = deck;
        this.id = id;
        this.activeId = activeId;
    }

    public void play() {
        System.err.println(Thread.currentThread().getName() + " is starting play the game.");
        //flag to start or end
        while (!deck.isGameOver().get()) {
            try {
                lock.lock();
                //it should always start with player 0
                if (id != activeId.get()) {
                    conditions[id].await();
                }
                //just call desk's pop method
                takeCard(this.deck.pop(), deck);
                //the activeId value is around 0 to max player
                if (activeId.incrementAndGet() >= Constant.MAX_PLAYER) {
                    activeId.set(0);
                }

                if (deck.isGameOver().get()) {
                    //notify to all other players thread,the game is over
                    for (int i = 0; i < conditions.length; i++) {
                        if (i == id)
                            continue;
                        conditions[i].signal();
                    }
                } else {
                    //notify to next player thread to take cards by sequence,if game is not over
                    conditions[activeId.get()].signal();
                }
                Thread.sleep(Constant.WAIT_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        System.out.println("Game over," + Thread.currentThread().getName() + " quit! Final score " + this.score);
    }

    public void takeCard(Card card, Deck deck) throws InterruptedException {
        //if other player thread is win,then no need to take card any more
        if (!deck.isGameOver().get()) {
            // if the deck is out of cards
            if (card == null) {
                System.out.println(Thread.currentThread().getName() + "took no cards,course deck is out of cards.");
                deck.setGameOver(new AtomicBoolean(true));
                return;
            }
            System.out.println(Thread.currentThread().getName() + " took a card,the rank is " + card.getRank() + " the suit is " + card.getSuit());
            score += card.getRank();
            if (score >= Constant.WINNER_SCORE) {
                System.err.println(Thread.currentThread().getName() + "  You win!");
                deck.setGameOver(new AtomicBoolean(true));
            } else {
                System.out.println(Thread.currentThread().getName() + " after took " + card.getRank() + "  the total score is ==> " + this.score);
            }
        }
    }

    @Override
    public void run() {
        this.play();
    }
}
