package project2_Wordle;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    boolean testMode;
    int wordLength;
    int numGuesses;
    Scanner input;

    public Game(GameConfiguration config, Scanner scanner) {
        // Takes the game settings and the scanner in
        this.testMode = config.testMode;
        this.wordLength = config.wordLength;
        this.numGuesses = config.numGuesses;
        this.input = scanner;
    }
    public void runGame() {
        Dictionary dictionary;
        if (wordLength == 4) {
            dictionary = new Dictionary("4_letter_words.txt");
        } else if (wordLength == 5) {
            dictionary = new Dictionary("5_letter_words.txt");
        } else {
            dictionary = new Dictionary("6_letter_words.txt");
        }

        String yn = "y";
        System.out.println("Hello! Welcome to Wordle.");
        while (yn.equals("y")) {
            System.out.print("Do you want to play a new game? (y/n) ");
            yn = input.next();
            if (yn.equals("n")) {
                return;
            } else if (!yn.equals("y")) {
                yn = "y";
                continue;
            }

            ArrayList<Guess> guessHistory = new ArrayList<>();
            ArrayList<String> feedbackHistory = new ArrayList<>();

            int guessesLeft = numGuesses;

            String secretWord = dictionary.getRandomWord();
            if (testMode) { // if testing mode, print the secret word
                System.out.println(secretWord);
            }

            while (guessesLeft > 0) {
                System.out.print("Enter your guess: ");
                String word = input.next();

                if (word.equals("[history]")) {
                    for (int i = 0; i < guessHistory.size(); i++) {
                        System.out.println(guessHistory.get(i).word + "->" + feedbackHistory.get(i));
                    }
                    System.out.println("--------");
                    continue;
                }

                Guess guess = new Guess(word);
                GuessProcessor guessProcessor = new GuessProcessor(guess, secretWord, dictionary, wordLength);

                if (!guessProcessor.validGuess()) {
                    if (guess.word.length() != wordLength) {
                        System.out.println("This word has an incorrect length. Please try again.");
                    } else {
                        System.out.println("This word is not in the dictionary. Please try again.");
                    }
                    continue;
                }

                String feedback = guessProcessor.getFeedback();
                System.out.println(feedback);
                guessHistory.add(guess);
                feedbackHistory.add(feedback);

                guessesLeft--;

                if (guess.word.equals(secretWord)) {
                    System.out.println("Congratulations! You have guessed the word correctly.");
                    break;  //done playing the game
                }
                else if (guessesLeft > 0) {
                    System.out.println("You have " + guessesLeft + " guess(es) remaining.");
                }
                else {
                    System.out.println("You have run out of guesses.");
                    System.out.println("The correct word was '" + secretWord + "'.");
                }
            }
        }
    }
}

