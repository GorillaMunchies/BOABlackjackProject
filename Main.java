import java.util.*;
import java.util.concurrent.TimeUnit;

class Main {
    static Scanner scanner = new Scanner(System.in);
    static HashMap<String, ArrayList<String> > playersHandMap = new HashMap<>();
    static ArrayList<String> playersList = new ArrayList<>();
    static String currentPlayer;
    static int players;
    static Deck deck = new Deck();
    public static void main(String[] args) throws InterruptedException {
        //display the rules and objective of the game
        printRules();
        // create uno deck using the standard 108 possible cards
        deck.createDeck();
        // suhffle deck and add each card onto a stack
        deck.shuffle();
        //prompt user for how many players we want and store the players names in a score hash map
        players();
        String ah = "Jessica";
        System.out.println(playersHandMap.get(ah));



        // draw card will pop a card off the stack
        String currCardInMiddle = deck.drawCard();
        System.out.println(currCardInMiddle);
        // create a separate map that stores the hands of each player on line 106 *



        // initialize first player -- maybe can add this as a condition that is
        // inside whoseTurn when we have a method to decide whether it is the first move ever
        currentPlayer = playersList.get(0);
        System.out.println("Look Away!! All players except " + currentPlayer);
        for(int i = 0; i <7;i++) {
            System.out.println((7 - i) + " seconds left");
            TimeUnit.SECONDS.sleep(1);
        }


        viewHand(currentPlayer);

        String decide = scanner.nextLine();



        System.out.println("curr before method call " + currentPlayer);
        System.out.println("curr after method call " + whoseTurn(players, true, false, currentPlayer, playersList));

    }

    /** method to deal x number of cards to player
     *
     */
    public static void dealXCards(int numOfCards) {
        // not sure what return type we want
        // maybe return an array? or store an array
    }

    // ----------------------------------


    /**
     * method to view hand of cards
     */

    public static ArrayList<String> viewHand(String currentPlayer) {
        // not sure what return type we want
        // maybe show user the cards and then have them choose which one they want to play and return that

        return playersHandMap.get(currentPlayer);
    }


    // -----------------------------

    /** method to validate current card played
     *  returns boolean
     */
    public static boolean validateCard(String currCardInMiddle, String cardPlayerWantsToPlay) {
        // have to split the string into two parts and see if either matches the currCardInMiddle

        return false;
    }


    //---------------------------

    /** edit the print rules to be for Uno
     *
     */

    public static void printRules() {
        System.out.println("--------------------\n"+
                "    Uno   \n"+
                "--------------------\n");
        System.out.println("Rules: \n Firstly distribute 7 cards to each player, take one card from the draw pile and placed it in the center of everyone. Also, you have to choose the first player randomly and then the game will continue clockwise, the left player will play the next." +
                "\n\nAt the beginning of the turn, the player can choose his card by matching the number or color from the center-placed card. If the card is matched then the game continues to the next player.\n\n" +
                "If it’s not matched then you can draw any of the special cards from your hand. It can be a Wild Card or Wild draw 4 card. We have already discussed it in the brief above.Enjoy playing!\n\n"+
                "If none of the cards matched (face cards or special cards) then the player has to pick the top card from the draw pile. If the drawn card cannot be played then the player has to pass their chance to the next player.\n\n" +
                "Also, don’t forget to throw special cards in between to make other players stop from winning and also for adding spice to the game.\n\n" +
                "The player who finishes their cards earlier, will automatically wins the match but to win the game you have to check the scoring points section.\n");



    }



    public static void players(){

        System.out.println("How many players will be playing in this game? 2-7 Players are allowed");
        players = Integer.valueOf(scanner.nextLine());
        //maybe throw an error if an int is not entered

        //for each player, ask for the name and save the names in a map

        for (int i=1; i<=players; i++) {
            System.out.println("What is the name of player " + i + "?");
            String playerName = scanner.nextLine();
            // create an exception if the user does not enter a name
            //if (playerName.isEmpty())
            ArrayList<String> hand = new ArrayList<>();
            for (int j = 0; j < 7; j++) {

                        hand.add(deck.drawCard());
            }
            playersHandMap.put(playerName,hand);

            playersList.add(playerName);
        }

    }


//    [1, 2, 3, 4]
//    [1, 2, 3, 4]
//     // if reverse
//    [1, 2, 3, 2, 1, 4, 3, 2, 1]
    public static String whoseTurn(int numOfPlayers, boolean reverse, boolean skip, String currPlayer, ArrayList<String> playersList) {
        // change the condition to

        LinkedList<String> turnList = new LinkedList<>();

        if (reverse) {
            int index = numOfPlayers - 1;
            System.out.println(numOfPlayers);
            // create a linked list of the players in reverse order
            while(index>=0) {
                String player = playersList.get(index);
                turnList.add(player);
                index--;
            }
            System.out.println("Turn List after reversing" + turnList);
        }
        else {
            for (String player: playersList) {
                turnList.add(player);
            }
            System.out.println("Turn List with no reversing" + turnList);
        }


        //  j  -> k > l --> m
        //  m -> l ->  k --> j
        Iterator it  = turnList.iterator();
        while (it.hasNext()) {

            if (currPlayer.equals(turnList.getLast())) {
                System.out.println("hey there ");
                currPlayer = turnList.getFirst();
                System.out.println("hey there: " + currPlayer);
                break;
            }
            else if (it.next().equals(currPlayer)) {
                if (skip) {
                    System.out.println("test");
//                    System.out.println("Next: " + it.next());
//                    System.out.println("Next next: " + it.next());
                    it.next();
                    currPlayer = it.next().toString();
                }
                else {
                    currPlayer = it.next().toString();
                    break;
                }
            }
        }


        return currPlayer;
    }




} 
