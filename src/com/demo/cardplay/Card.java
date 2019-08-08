package com.demo.cardplay;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: MarkTao
 * @Date: 2019/8/8 19:45
 * @Version 1.0
 */
public class Card {
    private int seq;
    private int rank;
    private boolean jokerFlag = false;
    private Map<Integer, String> suitsMap = new HashMap<Integer, String>() {
        {
            put(1, "DIAMONDS");
            put(2, "CLUBS");
            put(3, "HEARTS");
            put(4, "SPADES");
        }
    };

    public Card(int rawNumber) {
        this.seq = rawNumber;
        this.rank = (rawNumber > 13) ? rawNumber % 13 + 1 : rawNumber;
        if (rawNumber > 52) {
            jokerFlag = true;
            //if the card is joker,then rank is 20
            this.rank = 20;
        }

    }

    public String getSuit() {
        if (this.jokerFlag) {
            return "joker";
        }
        int suitNumber = this.seq / 13 + 1;
        return this.suitsMap.get(suitNumber);
    }

    public int getSeq() {
        return seq;
    }

    public boolean isJokerFlag() {
        return jokerFlag;
    }

    public int getRank() {
        return rank;
    }

}
