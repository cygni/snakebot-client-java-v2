# snakebot-client-java
Snakebot is a code challenge developed by Cygni. 
This repository contains a Java client allowing you to develop and test a snake AI implementation.

## I found a bug!
For issues with this code, please contact [Benjamin](mailto:benjamin.eriksen@cygni.se), or submit a pull request with your fix.

## Requirements
- Java 17

## Installation
1. Clone the repository: `git clone https://github.com/TODO/`
2. Navigate inside `cd snakebot-client-java`
3. Run `./gradlew build` or open folder in IntelliJ.

## How to run
The run configurations are specified in the [application.yml](src/main/resources/application.yml).
Here you can specify your name, the server or the snake implementation that should be used.
                
### [Cygni server](http://snake.cygni.se/)
To connect your AI to the Cygni server simply run the following command from this location in your CLI:
```bash
./gradlew bootRun
```

### [Local server](http://localhost:8080/)
You can run your own local server for testing and debugging your implementation. The only requirement for this is [docker-compose](https://docs.docker.com/compose/install/).
After having installed and added docker-compose to your path run the following command from this directory:
```bash
docker-compose up -d
```                
You can now change the host in [application.yml](src/main/resources/application.yml) to your localhost at port 8080 and run the application:
```bash
./gradlew bootRun
```


# 

## The Game 
The goal of the game is to have the last snake alive. If your snake collides with a wall, object or another snake, it dies.
You gain points by eating stars, trapping other snakes to crash into you or nibbling on other snakes' tails.
Points are only used to determine which non-winning snakes to advance in a tournament setting, which lets you have another chance for victory!
Every turn, the current game board is broadcast to all players. 
Your task is to respond with a direction indicating your next move - either up, down, left or right. When all players have made their moves, or if 250 ms has passed, the turn is over.




#


## Your Snake Algorithm implementation

### Getting Started
This client has a naive snake implementation in the [Snakepit](src/main/java/se/cygni/snakebotclient/snakepit) called Slippy.

## Improving Slippy

You've already learned the most important lesson: "If my snake doesn't know where to go it will hurt". 
Your next step is to figure out how to keep your snake safe with some serious thinking. 
As of now, Slippy's brain is barely the size of a peanut and resides in the method shown below.

```java
        @Override
        public Direction getNextMove(GameState state) {
            Snake snake = state.getPlayerSnake();
            List<Direction> possibleMoves = getPossibleMoves(snake);
    
            if (!possibleMoves.isEmpty()) {
            return pickRandom(possibleMoves);
            } else {
            logger.info("Slippy panics as there is no possible moves");
            return Direction.DOWN;
            }
        }
```


## Tip #1: Shake that snake!

To issue commands to your snake you have to return a direction. 
The direction is a string and consists of enums < UP | DOWN | LEFT | RIGHT >. 
Remember that you can only make one move per game tick, so no bullet time or any other Matrix tricks buddy! 
Also keep in mind that in the event of failing to returning a direction within the time limit of 250ms,
your snake will automatically return the latest direction it was heading to.

#

## Tip #2: The only guard against the world is a thorough knowledge of it

To be able to execute good moves, you first have to have a deep understanding of the world your snake lives and grows in.
Your goal is to outlive the other snakes, but you may also have to consider other factors such as gathering points to make sure you advance in the tournament.

The _GameState_ contains everything you really need to know and gives you access to all the game tiles and snake positions.
Use this information wisely to get a good view of the game map and everything that resides inside it..!

#

## Tip 3: Give a snake the right mind, and it will eat the world

You now know how to move your snake around and understand the layout of the world. 
It's now time to synthesise this knowledge to form a puny brain or... a sentient life form. It's all you from here!
Now, if you've forgotten all those classes in AI and discrete mathematics from uni there is still hope for you. Take a look in the **Coordinate**-class.
It contains some nifty functions for calculating routes and distances.
Use them wisely, they can prove to come in handy! Or not..

## Psst (Last tip)

If you want to listen to specific events from the server, you can use the supplied onMessage function. 
Perhaps you want to switch strategy after some snakes are dead and start hoarding points..?
It's totally optional, but it exists if you want to use it.

