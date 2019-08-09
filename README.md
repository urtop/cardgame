## The CardGame
---
### Abstract 
This code comes with below features:
1. High performance,multi conditions with ReentrantLock,no use of Synchronized.
2. No write lock,with CAS features,considering there is only one Producer.
3. Easy to expand with multi  producers / consumers .
4. If the deck is out of cards,it will notify all the player threads and gracefully end the game.
---

### How to run
##### Go to source root and run   com/demo/cardplay/Main.java
---

### Optional config
in Constant.java:
- can setup the max qty for play user
- can setup the wait_Time for each user after took the card
- can setup the winner score.
---

### Further enchanment suggestions
- It is not the best practices neither Thread join nor Synchronized on this specific scenario .
- It should use MQ with blocking features in real production of distributed servers/clusters for data exchanges.
- It should use Redis / ZooKeeper /Database as distributed locks for performance and avoid race conditions.
- It also can be modified with ThreadPool way as the classic producers and consumer mode.
- With the tight time,it may have some flaws,**Thanks for your reading!**
---