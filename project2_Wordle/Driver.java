package project2_Wordle;

import java.util.Scanner;

public class Driver {

    public void start(GameConfiguration config) {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game(config, scanner);
        game.runGame();
    }

    public static void main(String[] args) {
        Driver driver = new Driver();
        GameConfiguration game_config = new GameConfiguration(5, 4, true);
        driver.start(game_config);
    }
    
}
