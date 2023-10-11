package project3_WordLadder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
 
public class Main {
    // global variables
    public static ArrayList<String> wordLadder; // array list containing the words for the word ladder solution
    public static Set<String> pastWordsDFS;    // set of words we've visited (used in DFS methods only)
    public static Set<String> dictionary;
    
    public static void main(String[] args) throws Exception {
 
        Scanner kb;
        PrintStream ps; // output file, for student testing and grading only
        // If arguments are specified, read/write from/to files instead of Std IO.
        if (args.length != 0) {
            kb = new Scanner(new File(args[0]));
            ps = new PrintStream(new File(args[1]));
            System.setOut(ps);              // redirect output to ps
        } else {
            kb = new Scanner(System.in);    // default input from Stdin
            ps = System.out;                // default output to Stdout
        }
        initialize();
 
        wordLadder = parse(kb); // get the start and end word from the user
        if (wordLadder.size() == 0) {
            return;
        }
 


        /* Below are the two solutions to the word ladder (test one at a time): */ 

        // DFS:
        //wordLadder = getWordLadderDFS(wordLadder.get(0), wordLadder.get(1));
        // BFS:
        wordLadder = getWordLadderBFS(wordLadder.get(0), wordLadder.get(1));
 
        // Output:
        printLadder(wordLadder);
    }
 

    /* Constructor for global variables */
    public static void initialize() {
        // initialize the global variables
        wordLadder = new ArrayList<>();
        dictionary = makeDictionary();
        pastWordsDFS = new HashSet<>();
    }


    /* Do not modify makeDictionary */
    public static Set<String> makeDictionary() {
        Set<String> words = new HashSet<String>();
        Scanner infile = null;
        try {
            infile = new Scanner(new File("project3_WordLadder/five_letter_words.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Dictionary File not Found!");
            e.printStackTrace();
            System.exit(1);
        }
        while (infile.hasNext()) {
            words.add(infile.next().toUpperCase());
        }
        return words;
    }


    /**
    * @param keyboard Scanner connected to System.in
    * @return ArrayList of Strings containing start word and end word.
    * If command is /quit, return empty ArrayList.
    */
    public static ArrayList<String> parse(Scanner keyboard) {
        System.out.print("Enter two five-letter words, with a single space inbetween: ");
        String startWord = (keyboard.next()).toLowerCase();
        if (startWord.equals("/quit")) {
            wordLadder.clear();
            return wordLadder;
        }
        String endWord = (keyboard.next()).toLowerCase();
 
        wordLadder.add(startWord);
        wordLadder.add(endWord);
        return wordLadder;
    }


    /**
    * @param ladder
    * prints the word ladder if one is found
    * otherwise prints that one cannot be found
    */
    public static void printLadder(ArrayList<String> ladder) {
        if (ladder == null) {
            return;
        }
        if (ladder.size() <= 2) {
            System.out.println("no word ladder can be found between " + ladder.get(0) + " and " + ladder.get(1) + ".");
        }
        else {
            System.out.println("a " + (ladder.size() - 2) + "-rung word ladder exists between " + ladder.get(0) + " and " + ladder.get(ladder.size()-1) + ".");
            for (int i = 0; i < ladder.size(); i++) {
                System.out.println(ladder.get(i));
            }
        }
    }





    /* DFS AND HELPER METHODS BELOW */

    /**
    * @param start
    * @param end
    * @return an ArrayList of strings in the word ladder
    */
    public static ArrayList<String> getWordLadderDFS(String start, String end) {
        start = start.toLowerCase();
        end = end.toLowerCase();
        int depthLimit = 200; // change the depth limit as needed (used for optimization)
        for (int i = 1; i <= depthLimit; i++) {
            if (isPathFound(start, end, 0, i)) {
                return wordLadder;
            }
            else {
                wordLadder.clear();
                pastWordsDFS.clear();
            }
        }
        wordLadder.add(start);
        wordLadder.add(end);
        return wordLadder;  // no word ladder was found, return list with start and end
    }


    /**
    * @param start
    * @param end
    * @param depth
    * @param depthLimit
    * @return a boolean indicating if a path is found
    */
    public static boolean isPathFound(String start, String end, int depth, int depthLimit) {
        wordLadder.add(start);
        if (start.equals(end)) {    // base case
            return true;
        }
        if (depth >= depthLimit) {  // continues if we haven't reached the limit
            return false;
        }
        pastWordsDFS.add(start);   // keeps track of words we already visited
        for (int i = 0; i < start.length(); i++) {
            for (int j = 0; j < 26; j++) {
                char ascii = (char)('a' + j);
                if (start.charAt(i) != ascii && depth < depthLimit) {
                    String newStart = start.substring(0, i) + ascii + start.substring(i + 1);
                    if (dictionary.contains(newStart.toUpperCase()) && !pastWordsDFS.contains(newStart)) {
                        if (isPathFound(newStart, end, depth + 1, depthLimit)) {
                            return true;
                        }
                        else {
                            wordLadder.remove(wordLadder.size() - 1);   // no path was found, so remove word from ladder
                        }
                    }
                }
            }
        }
        return false;
    }





    /* BFS AND HELPER METHODS BELOW */

    /**
    * @param start
    * @param end
    * @return an ArrayList of strings in the word ladder
    */
    public static ArrayList<String> getWordLadderBFS(String start, String end) {

        String startWord = start.toLowerCase();
        String endWord = end.toLowerCase();

        HashSet<String> discoveredWords = new HashSet<String>();
        Queue<WordNode> nodeQueue = new LinkedList<WordNode>();
 
        WordNode startNode = new WordNode(startWord);
        discoveredWords.add(startNode.word);
        nodeQueue.offer(startNode);
 
        while (!nodeQueue.isEmpty()) {
            WordNode currNode = nodeQueue.poll();
            if ((currNode.word).equals(endWord)) {
                return getLadderFromNode(currNode); // found full word ladder from start word to end word
            }
 
            // generate all the neighboring nodes of current node
            ArrayList<WordNode> currNodeNeighbors = generateNeighbors(currNode, dictionary);
 
            // traverse all the neighbors that we found
            for (int i = 0; i < currNodeNeighbors.size(); i++) {
                WordNode neighborNode = currNodeNeighbors.get(i);
                String neighborWord = neighborNode.word;
                if (!(discoveredWords.contains(neighborWord))) {
                    discoveredWords.add(neighborWord);
                    neighborNode.parent = currNode; // set the parent to the current word node (used to traverse back up the BFS search)
                    nodeQueue.offer(neighborNode);  // put neighbor word node onto the queue so we can later search off of it, if needed
                }
            }
        }

        // no word ladder found between start and end words
        ArrayList<String> noLadder = new ArrayList<String>();
        noLadder.add(startWord);
        noLadder.add(endWord);
        return noLadder;
    }
 

    /**
    * @param currNode
    * @param dictionary
    * @return an ArrayList of StringNodes
    * generates all neighboring nodes of current node
    */
    public static ArrayList<WordNode> generateNeighbors(WordNode currNode, Set<String> dictionary) {
 
        char[] currWordArr = currNode.word.toCharArray();
        ArrayList<WordNode> neighborNodes = new ArrayList<WordNode>();
 
        for (int i = 0; i < currWordArr.length; i++) {
            char originalLetter = currWordArr[i];
            for (char testLetter = 'a'; testLetter <= 'z'; testLetter++) {
                if (currWordArr[i] == testLetter || currWordArr[i] == Character.toUpperCase(testLetter)) {
                    continue;
                }
 
                currWordArr[i] = testLetter;
                String testWord = String.valueOf(currWordArr);
                if (dictionary.contains(testWord.toUpperCase())) {    // note: dictionary is all uppercase
                    WordNode neighborNode = new WordNode(testWord);
                    neighborNodes.add(neighborNode);
                }

                currWordArr[i] = originalLetter;
            }
        }
        return neighborNodes;
    }
 

    /**
    * @param wordNode
    * @return an ArrayList of Strings
    * creates the word ladder from a found path
    */
    public static ArrayList<String> getLadderFromNode(WordNode wordNode) {
        ArrayList<String> reversedLadder = new ArrayList<String>();
        while (wordNode != null) {
            reversedLadder.add(wordNode.word);
            wordNode = wordNode.parent;
        }
        for(int i = 0, j = reversedLadder.size() - 1; i < j; i++) {
            reversedLadder.add(i, reversedLadder.remove(j));
        }
        return reversedLadder;
    }
}
 