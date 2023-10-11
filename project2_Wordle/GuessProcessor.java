package project2_Wordle;

import java.util.ArrayList;

public class GuessProcessor {

    Guess guess;
    String secretWord;
    Dictionary dictionary;
    int wordLength;

    public GuessProcessor(Guess guess, String secretWord, Dictionary dictionary, int wordLength) {
        this.guess = guess;
        this.secretWord = secretWord;
        this.dictionary = dictionary;
        this.wordLength = wordLength;
    }

    public boolean validGuess() {
        String g = guess.word;

        if(!(g.length() == wordLength)) {
            return false;
        }

        if(!(dictionary.containsWord(g))) {
            return false;
        }

        return true;
    }

    public String getFeedback() {
        char[] feedback = new char[wordLength];
        for (int i = 0; i < wordLength; i++) {
            feedback[i] = '_';
        }

        char[] secret_word = secretWord.toCharArray();
        /* Arraylist to indicate which letters of the secret word have been 'marked' off (either green or yellow from the guess). Once
        a letter has been marked, its position will be replaced with '*' in this list to let us know that we on longer need to look
        for that letter in the guess */
        ArrayList<Character> markedLetters = new ArrayList<>();
        for (char c : secret_word) {
            markedLetters.add(c);
        }

        // First, mark off all letters that are green (the right letter in the right place)
        for (int i = 0; i < wordLength; i++) {
            if (guess.word.charAt(i) == secretWord.charAt(i)) {
                feedback[i] = 'G';
                markedLetters.set(i, '*');
            }
        }
        // Next, mark off all letters that are yellow (right letter, but in the wrong place)
        for (int i = 0; i < wordLength; i++) {
            if (feedback[i] == 'G') {   // if current letter has already been marked green, go to next letter
                continue;
            }
            if (markedLetters.contains(guess.word.charAt(i))) { // if current letter exists in the marked letters list (has NOT yet been marked off), then mark it off as yellow
                int j = markedLetters.indexOf(guess.word.charAt(i));
                markedLetters.set(j, '*');
                feedback[i] = 'Y';
            }
        }

        String fb = "";
        for (char c : feedback) {
            fb += c;
        }
        return fb;
    }
}

