import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.TimeUnit;

class Main {
    static Scanner scanner = new Scanner(System.in);
    static HashMap<String, ArrayList<String> > playersHandMap = new HashMap<>();
    static ArrayList<String> playersList = new ArrayList<>();
    static String currentPlayer;
    static int players;
    static Deck deck = new Deck();
    static boolean reverse = false;
    static boolean skip = false;
    static int sum = 0;
    static String decide;
    static  ArrayList<String> currHand;
    public static void main(String[] args) throws InterruptedException {
        //display the rules and objective of the game
        printRules();
        // create uno deck using the standard 108 possible cards
        deck.createDeck();
        // shuffle deck and add each card onto a stack
        deck.shuffle();
        //prompt user for how many players we want and store the players names in a array of cards in player hand hash map
        players();
        // draw card will pop a card off the stack
        String currCardInMiddle = deck.drawCard();
        System.out.println("Current Card in the middle: " + currCardInMiddle);
        // create a separate map that stores the hands of each player on line 106 *

        // initialize first player
        currentPlayer = playersList.get(0);

        do {
            System.out.println("Current Player: " + currentPlayer);
            System.out.println("Look Away!! All players except " + currentPlayer);
//            for(int i = 0; i <7;i++) {
//                System.out.println((7 - i) + " seconds left");
//                TimeUnit.SECONDS.sleep(1);
//            }
            viewHand(currentPlayer);

//            System.out.println("Testing hand before collecting all the cards: " + playersHandMap.get(currentPlayer));

            if (currCardInMiddle.contains("Draw"))  {
                System.out.println("Since the previous card was a draw 2 or draw 4, type the option of a draw 2 or draw 4 card you want to play, " +
                        "or type s to collect " + sum + " cards");
            }
            else {
                System.out.println("Select the option of the card you want to play; 1, 2, 3 ... " +
                        "Type d if you want to draw a card and end your turn");

            }
            decide = scanner.nextLine();
           currHand = playersHandMap.get(currentPlayer);
            if (decide.equals("d")){
                playersHandMap.get(currentPlayer).add(deck.drawCard());
                viewHand(currentPlayer);
                currentPlayer = whoseTurn(players, reverse,skip, currentPlayer,playersList);
            }
            else if (decide.equals("s")) {
                 for (int i=0; i<sum ; i++) {
                    currHand.add(deck.drawCard());
                 }
                 System.out.println("Testing hand after collecting all the cards: " + playersHandMap.get(currentPlayer));
                 sum = 0;
                 currCardInMiddle = currCardInMiddle.split(" ")[0] + " Card";
                 currentPlayer = whoseTurn(players, reverse,skip, currentPlayer,playersList);
            }
         
            else {
                int d = Integer.valueOf(decide);
                if(validateCard(currCardInMiddle, playersHandMap.get(currentPlayer).get(d - 1))){
                    System.out.println("Good Choice!");

                    //make the card go in the middle
                    currCardInMiddle = playersHandMap.get(currentPlayer).get(d - 1);
                    //validate card must be completed first
                    if(currCardInMiddle.contains("Draw-2")){
                        System.out.println("Uh oh next player must draw 2");
                        sum += 2;
                        //we should have a draw 2 method here
                    }
                    if (currCardInMiddle.contains("Draw-4")) {
                        sum += 4;
                    }
                    else {

                        System.out.println("New card in the middle is " + currCardInMiddle);

                        
                    }
                     playersHandMap.get(currentPlayer).remove(d - 1);
//                     System.out.println("Updated hand map for current player" + playersHandMap.get(currentPlayer));

                    if (currCardInMiddle.contains("Wild Card")) {
                        System.out.println("Wild card");
                        String newColor;
                        do {
                            System.out.println("What color would you like to change the pile to? Red, Green, Blue or Yellow. Please capitalize first letter!");
                            newColor = scanner.nextLine();
                        } while (!(newColor.equals("Red") || newColor.equals("Blue") || newColor.equals("Green") || newColor.equals("Yellow")));

                        
                        currCardInMiddle = newColor + " Card";
                    } else if (currCardInMiddle.contains("Reverse")) {
                        System.out.println("Reverse");
                        reverse = !reverse;
                    }

                    else if (currCardInMiddle.contains("Skip")) {
                        System.out.println("Skip");
                        skip = true;

                    }

                    currentPlayer = whoseTurn(players, reverse,skip, currentPlayer,playersList);
                    skip = false;
                }
                else {
                    System.out.println("Not a valid card! Please select again");
                }
            }


            System.out.println("Current Card in the middle " + currCardInMiddle);

        } while(!currHand.isEmpty());

        System.out.println("Congratulations! The winner is " + currentPlayer + " !!!");

//
//        System.out.println("the player before method call " + currentPlayer);
//        System.out.println("the player after method call " + whoseTurn(players, true, false, currentPlayer, playersList));

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

    public static void viewHand(String currentPlayer) {
        // not sure what return type we want
        // maybe show user the cards and then have them choose which one they want to play and return that
        ArrayList<String> hand = playersHandMap.get(currentPlayer);
        int count = 1;
        for (String card: hand) {
            System.out.println(" (" + count + ") "+ card);
            count++;
        }

    }


    // -----------------------------

    /** method to validate current card played
     *  returns boolean
     */
    public static boolean validateCard(String currCardInMiddle, String cardPlayerWantsToPlay) {
        // have to split the string into two parts and see if either matches the currCardInMiddle
        String colorOfCardInMiddle = currCardInMiddle.split(" ")[0];
        String colorPlayerWantsToPlay = cardPlayerWantsToPlay.split(" ")[0];
//        System.out.println("colorOfCardInMiddle " + colorOfCardInMiddle);
//        System.out.println("colorPlayerWantsToPlay " + colorPlayerWantsToPlay);

        String numberOfCardInMiddle = currCardInMiddle.split(" ")[1];
        String numberPlayerWantsToPlay = cardPlayerWantsToPlay.split(" ")[1];
//        System.out.println("numberOfCardInMiddle " + numberOfCardInMiddle);
//        System.out.println("numberPlayerWantsToPlay " + numberPlayerWantsToPlay);

        if(colorOfCardInMiddle.equals(colorPlayerWantsToPlay)) {

            return true;
        }
        if (numberOfCardInMiddle.equals(numberPlayerWantsToPlay)) {

            return true;
        }
        if (colorOfCardInMiddle.equals("Wild:") || colorOfCardInMiddle.equals("Wild")) {
            return true;
        }
        if (colorPlayerWantsToPlay.equals("Wild:") || colorPlayerWantsToPlay.equals("Wild")){
            return true;
        }
        if (numberPlayerWantsToPlay.equals(numberOfCardInMiddle)) {
            return true;
        }

//        if(cardPlayerWantsToPlay.equals("Wild Card - Choose any color and next person draws 4")){
//
//        }


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

        //[l,k,j]
        if (reverse) {
            int index = numOfPlayers - 1;
            System.out.println(numOfPlayers);
            // create a linked list of the players in reverse order
            while(index>=0) {
                String player = playersList.get(index);
                turnList.add(player);
                index--;
            }
//            System.out.println("Turn List for reversing" + turnList);
        }
        //[j,k,l]
        else {
            for (String player: playersList) {
                turnList.add(player);
            }
//            System.out.println("Turn List with no reversing" + turnList);
        }


        //  j  -> k > l --> m
        //  m -> l ->  k --> j


        Iterator it  = turnList.iterator();
        while (it.hasNext()) {

            if (currPlayer.equals(turnList.getLast())) {
//                System.out.println("hey there ");
                currPlayer = turnList.getFirst();
//                System.out.println("hey there: " + currPlayer);
                break;
            }
            else if (it.next().equals(currPlayer)) {
                if (skip) {
//                    System.out.println("test");
//                    System.out.println("Next: " + it.next());
//                    System.out.println("Next next: " + it.next());
                    // k --> skip l --> j
                    currPlayer = it.next().toString();
                    if (currPlayer.equals(turnList.getLast())) {
                        currPlayer = turnList.getFirst();
                        break;
                    }
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
