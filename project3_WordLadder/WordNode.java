package project3_WordLadder;

public class WordNode {
    String word;
    WordNode parent;

    public WordNode(String word) {
        this.word = word;
        this.parent = null;
    }
}
