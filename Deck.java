import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;


/******************************************************************************
 *  Deal 108 cards uniformly at random.
 *
 *  % java Deck
 * Red 0
 * Red 1
 * ...
 * Red Skip
 * Wild Card
 * .....
 * Red Draw 2
 *
 * Possibilities:
 * 2 cards of each color 1-9
 * 2 cards of each color for Draw 2, Skip, and Reverse
 * 1 card of each color 0
 * 4 Wild Cards
 * 4 Wild Cards that are Draw 4
 ******************************************************************************/
public class Deck {

    // maybe the value in map should be an integer not a string?
//    HashMap<String, String> deck = new HashMap<>();
    ArrayList<String> deck = new ArrayList<>();
    Stack<String> deckStack = new Stack<>();
    String[] COLORS = {
            "Red", "Yellow", "Blue", "Green"
    };
    String[] TYPES = {
            "1", "2", "3", "4", "5", "6", "7", "8",
            "9", "Skip", "Reverse", "Draw-2"
    };

    public void createDeck() {
        // initialize deck
        // makes two cards of each type for color

        for (int i = 0; i < 2; i++) {
            for (String type : TYPES) {
                for (String color : COLORS) {
                    deck.add(color + " " + type);
                }
            }
        }

        // each color only has one 0
        for (String color : COLORS) {
            deck.add(color + " 0");
        }

        // add 4 wild cards
        int count = 0;
        while (count < 4) {
            deck.add("Wild Card");
            count++;
        }


        // add 4 black wild cards that have a draw 4
        count = 0;
        while (count < 4) {
            deck.add("Wild: Draw-4");
            count++;
        }

//        System.out.println(deck);
    }

    int n = 108;
    public void shuffle() {
        for (int i = 0; i < n; i++) {

            int r = i + (int) (Math.random() * (n-i));
            String temp = deck.get(r);

            // deck[r] = deck[i];
            deck.set(r, deck.get(i));

            // deck[i] = temp;
            deck.set(i, temp);
        }

        putInStack();
    }

    public void putInStack() {
        for (String card: deck) {
            deckStack.push(card);
        }
    }

    public String drawCard() {
        return deckStack.pop();
    }

}



