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
    static String currCardInMiddle;
    static ConsoleColors consoleColors = new ConsoleColors();
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
        do {
            currCardInMiddle = deck.drawCard();
        } while(currCardInMiddle.contains("Draw")|| currCardInMiddle.contains("Reverse") || currCardInMiddle.contains("Skip"));

        // we need this to initialize the first card in the middle and then later it is updated

        // create a separate map that stores the hands of each player on line 106 *

        // initialize first player
        currentPlayer = playersList.get(0);

        do {
            System.out.print("Card in the middle: " );
            colorTheString(currCardInMiddle);
            System.out.println("Current Player: " + currentPlayer);
            System.out.println("Look Away!! All players except " + currentPlayer);
//            for(int i = 0; i <7;i++) {
//                System.out.println((7 - i) + " seconds left");
//                TimeUnit.SECONDS.sleep(1);
//            }
//            System.out.println("\\033[H\\033[2J");
            viewHand(currentPlayer);

//            System.out.println("Testing hand before collecting all the cards: " + playersHandMap.get(currentPlayer));
            currHand = playersHandMap.get(currentPlayer);
            decide = getUserDecision(); // prompts user and updates the decide variabl


            if (decide.equals("d")) {
                String newCard = deck.drawCard();
                currHand.add(newCard);
                System.out.println(currentPlayer + ", you drew a ");
                colorTheString(newCard);
                System.out.println("End of your turn.");
            }
            else if ((decide.equals("s") || !currHand.get(Integer.valueOf(decide)-1).equals("Draw")) && sum!=0) {
                System.out.print(currentPlayer + ", you have just drawn: \n");
                 for (int i=0; i<sum; i++) {
                     String card = deck.drawCard();
                     colorTheString(card);
                     currHand.add(card);
                     if (i==sum-1) {
                         break;
                     }
                     System.out.print(", ");
                 }
                System.out.println("\nEnd of your turn.");

                // System.out.println("Testing hand after collecting all the cards: " + playersHandMap.get(currentPlayer));
                 sum = 0;
                 currCardInMiddle = currCardInMiddle.split(" ")[0] + " Card";

            }
         
            else {
                // this block is for if the last card was not a wild card or a draw
                int d = Integer.valueOf(decide);
                if(validateCard(currCardInMiddle, currHand.get(d - 1))){
                    System.out.println("Good Choice!");
                    //make the card go in the middle
                    currCardInMiddle = currHand.get(d - 1);
                    //validate card must be completed first
                    dealWithPlayerPlayingFaceCards(d);
                }
                else {
                    System.out.println("Not a valid card! Please select again");
                    continue;
                }
            }

            if(currHand.isEmpty()) {
                System.out.println("Congratulations! Uno Out! The winner is " + currentPlayer + "!!!");
                System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣀⣀⣠⣤⣤⣄⣀⣀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⠤⠖⠊⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠙⠲⢤⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⡤⠊⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⡜⠀⠀⠀⠀⠀⠀⢀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢢⠀⠀⠀⠀⠀⢳⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⣸⠁⠀⠀⠀⠀⠀⠀⠀⠱⡀⠀⠀⠀⠀⠀⠀⠀⡀⠈⠀⡀⠀⠀⠀⠈⡇⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⡏⠀⠀⠀⠀⠀⠀⠀⠀⡰⠁⠀⠀⠀⠀⠀⠀⠀⠘⡆⡜⠁⠀⠀⠀⠀⢧⡀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⡇⠀⠀⠀⠀⠀⠀⠀⠸⡀⠀⠀⠀⠀⠀⣀⣤⡂⠀⠇⠱⠀⡀⠀⠀⠀⠀⡇⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⢇⠀⠀⠀⠀⠀⠀⠀⠀⠈⢄⡀⢠⣟⢭⣥⣤⠽⡆⠀⡶⣊⣉⣲⣤⢀⡞⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠘⣆⠀⠀⠀⠀⠀⠀⡀⠀⠐⠂⠘⠄⣈⣙⡡⡴⠀⠀⠙⣄⠙⣛⠜⠘⣆⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠈⢦⡀⠀⠀⠀⢸⠁⠀⠀⠀⠀⠀⠀⠄⠊⠀⠀⠀⠀⡸⠛⠀⠀⠀⢸⠆⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⠀⠈⠓⠦⢄⣘⣄⠀⠀⠀⠀⠀⠀⠀⡠⠀⠀⠀⠀⣇⡀⠀⠀⣠⠎⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣸⠁⠈⡟⠒⠲⣄⠀⠀⡰⠇⠖⢄⠀⠀⡹⡇⢀⠎⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡇⠀⠀⡇⠀⠀⠹⠀⡞⠀⠀⢀⠤⣍⠭⡀⢱⢸⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⢀⣀⣀⣠⠞⠀⠀⢠⡇⠀⠀⠀⠀⠁⠀⢴⠥⠤⠦⠦⡼⠀⢸⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⣀⣤⣴⣶⣿⣿⡟⠁⠀⠋⠀⠀⠀⢸⠁⠀⠀⠀⠀⠀⠀⠀⠑⣠⢤⠐⠁⠀⢸⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⣿⣿⣿⣿⣿⡟⠀⠀⠀⠀⠀⠀⠀⢸⡀⠀⠀⠀⠀⠀⠀⠀⠀⠬⠥⣄⠀⠀⠈⠲⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⣿⣿⣿⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠙⠦⣄⠀⠀⠀⠀⠀⠀⠀⠀⠈⢳⠀⠀⢀⣿⡀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⣿⣿⣿⣿⣿⣧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⠒⠦⠤⢤⣄⣀⣠⠤⢿⣶⣶⣿⣿⣿⣶⣤⡀⠀⠀⠀⠀⠀\n" +
                        "⣿⣿⣿⣿⣿⣿⣷⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡼⠁⠀⠀⣠⣾⣿⣿⣿⣿⣿⣿⣿⣿⣄⠀⠀⠀⠀\n" +
                        "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣶⣦⣤⣤⣀⣀⣀⣀⣀⣀⣀⣤⣤⣤⣶⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀");
            }

            currentPlayer = whoseTurn(players, reverse, skip, currentPlayer,playersList);
            skip = false;

            System.out.println("_¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶\n" +
                    "_¶¶___________________________________¶¶\n" +
                    "_¶¶___________________________________¶¶\n" +
                    "__¶¶_________________________________¶¶_\n" +
                    "__¶¶_________________________________¶¶_\n" +
                    "___¶¶_______________________________¶¶__\n" +
                    "___¶¶______________________________¶¶___\n" +
                    "____¶¶¶__________________________¶¶¶____\n" +
                    "_____¶¶¶¶_¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶_¶¶¶¶_____\n" +
                    "_______¶¶¶¶_¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶_¶¶¶¶_______\n" +
                    "_________¶¶¶¶_¶¶¶¶¶¶¶¶¶¶¶¶_¶¶¶¶_________\n" +
                    "___________¶¶¶¶¶_¶¶¶¶¶¶¶_¶¶¶¶___________\n" +
                    "______________¶¶¶¶_¶¶¶_¶¶¶______________\n" +
                    "________________¶¶¶_¶_¶¶________________\n" +
                    "_________________¶¶¶_¶¶_________________\n" +
                    "__________________¶¶_¶¶_________________\n" +
                    "__________________¶¶_¶__________________\n" +
                    "__________________¶¶_¶¶_________________\n" +
                    "________________¶¶¶_¶_¶¶¶_______________\n" +
                    "_____________¶¶¶¶¶__¶__¶¶¶¶¶____________\n" +
                    "__________¶¶¶¶¶_____¶_____¶¶¶¶__________\n" +
                    "________¶¶¶¶________¶_______¶¶¶¶¶_______\n" +
                    "_______¶¶¶__________¶__________¶¶¶¶_____\n" +
                    "_____¶¶¶____________¶____________¶¶¶____\n" +
                    "____¶¶¶_____________¶______________¶¶___\n" +
                    "___¶¶¶______________¶_______________¶¶__\n" +
                    "___¶¶_______________¶________________¶¶_\n" +
                    "__¶¶________________¶________________¶¶_\n" +
                    "__¶¶_______________¶¶¶________________¶_\n" +
                    "__¶¶_¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶_¶¶\n" +
                    "__¶¶_¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶_¶¶\n" +
                    "__¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶\n");
        } while(!currHand.isEmpty());



//
//        System.out.println("the player before method call " + currentPlayer);
//        System.out.println("the player after method call " + whoseTurn(players, true, false, currentPlayer, playersList));

    }

    static void colorTheString(String currCard) {
        String color = currCard.split(" ")[0];
        if (color.equals("Red")) {
            System.out.println(consoleColors.RED_BOLD + currCard +
                    consoleColors.RESET);
        }
        if (color.equals("Blue")) {
            System.out.println(consoleColors.BLUE_BOLD + currCard +
                    consoleColors.RESET);
        }
        if (color.equals("Yellow")) {
            System.out.println(consoleColors.YELLOW_BOLD + currCard+
                    consoleColors.RESET);
        } if (color.equals("Green")) {
            System.out.println(consoleColors.GREEN_BOLD + currCard +
                    consoleColors.RESET);
        }
        if (color.contains("Wild")) {
            System.out.println(consoleColors.WHITE_BOLD + currCard +
                    consoleColors.RESET);
        }
    }
    static void dealWithPlayerPlayingFaceCards(int d) {
        if (currCardInMiddle.contains("Draw-2")) {
            System.out.println("Uh oh next player must draw 2");
            sum += 2;
            //we should have a draw 2 method here
        }
        if (currCardInMiddle.contains("Draw-4")) {
            System.out.println("Uh oh next player must draw 4");
            sum += 4;
        }
        // redundant
//                    else {
//                        System.out.println("New card in the middle is " + currCardInMiddle);
//                    }
        playersHandMap.get(currentPlayer).remove(d - 1);
//                     System.out.println("Updated hand map for current player" + playersHandMap.get(currentPlayer));

        if (currCardInMiddle.contains("Wild")) {
//                        System.out.println("Wild card");
            String newColor;
            do {
                System.out.println("What color would you like to change the pile to? Red, Green, Blue or Yellow.");
                newColor = scanner.nextLine();
                newColor = newColor.split("",2)[0].toUpperCase() + newColor.split("",2)[1].toLowerCase();
            } while (!(newColor.equals("Red") || newColor.equals("Blue") || newColor.equals("Green") || newColor.equals("Yellow")));

            if (currCardInMiddle.contains("Draw-4")) {
                currCardInMiddle = newColor + " Anything";
            }
            else {
                currCardInMiddle = newColor + " Card";
            }


        } else if (currCardInMiddle.contains("Reverse")) {
            System.out.println("Reverse");
            reverse = !reverse;
        } else if (currCardInMiddle.contains("Skip")) {
            System.out.println("Skip");
            skip = true;
        }
    }

    static String getUserDecision() {
        boolean isValid = false;
        if (currCardInMiddle.contains("Draw") || currCardInMiddle.contains("Anything")) {
            System.out.println(consoleColors.PURPLE_BOLD +  "Since the previous card was a draw 2 or draw 4, type the option of a draw 2 or draw 4 card you want to play, " +
                            "or type s to collect " + sum + " cards" +
                            consoleColors.RESET
                   );
            do {
                try {
                    decide = scanner.nextLine();
                    if (decide.equals("s")) {
                        break;
                    }
                    currHand.get(Integer.valueOf(decide)-1);
                    isValid = true;
                }
                catch (Exception e) {
                    System.out.println("Invalid option! You must type s or a valid option from your hand. " + e.getMessage());
                }

            } while(!isValid);
        }

        else {
            System.out.println("Select the option of the card you want to play; 1, 2, 3 ... " +
                    "Type d if you want to draw a card and end your turn");
            do {
                try {
                    decide = scanner.nextLine();
                    if(decide.equals("d")) {
                        break;
                    }
                    currHand.get(Integer.valueOf(decide)-1);
                    isValid = true;
                }
                catch (Exception e) {
                    System.out.println("Invalid option! You must type d or a valid option from your hand. " + e.getMessage());
                }

            } while(!isValid);
        }

        return decide;
//        System.out.println(decide);
    }

//
//    /** method to deal x number of cards to player
//     *
//     */
//    public static void dealXCards(int numOfCards) {
//        // not sure what return type we want
//        // maybe return an array? or store an array
//    }
//
//    // ----------------------------------


    /**
     * method to view hand of cards
     */

    public static void viewHand(String currentPlayer) {

        // not sure what return type we want
        // maybe show user the cards and then have them choose which one they want to play and return that
        ArrayList<String> hand = playersHandMap.get(currentPlayer);
        int count = 1;
        for (String card: hand) {
            String color = card.split(" ")[0];
                System.out.print(" (" + count + ") ");
                colorTheString(card);
//            System.out.println(" (" + count + ") "+ card);
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




        return false;
    }


    //---------------------------

    /** edit the print rules to be for Uno
     *
     */

    public static void printRules() {
        System.out.println("--------------------\n"+
                "   \n" +
                "██╗░░░██╗███╗░░██╗░█████╗░\n" +
                "██║░░░██║████╗░██║██╔══██╗\n" +
                "██║░░░██║██╔██╗██║██║░░██║\n" +
                "██║░░░██║██║╚████║██║░░██║\n" +
                "╚██████╔╝██║░╚███║╚█████╔╝\n" +
                "░╚═════╝░╚═╝░░╚══╝░╚════╝░  \n"+
                "--------------------\n");
        System.out.println("Rules: \nEach player is distributed 7 cards. One card will be drawn from the pile and placed it in the middle of everyone." +
                "\nAt the beginning of the turn, the player can choose his card by matching the number or color from the center-placed card.\n\n" +
                " --> If the card is matched then the game continues to the next player.\n" +
                " --> If it’s not matched then you can draw any of the special cards from your hand. It can be a Wild Card or Wild draw 4 card. \n" +
                " --> If none of the cards matched (face cards or special cards) then the player has to pick the top card from the draw pile and ends their turn. \n\n" +
                "Also, don’t forget to throw special cards in between to stop others from winning and also for adding spice to the game.\n" +
                "The player who finishes their cards earlier wins the game.\n");



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
//                System.out.println(player);
            }
//            System.out.println("Turn List with no reversing" + turnList);
        }

//        System.out.println(turnList);
        //  j  -> k > l --> m
        //  m -> l ->  k --> j


        Iterator it  = turnList.iterator();
        while (it.hasNext()) {
            if (it.next().equals(currPlayer)) {
                if (skip) {
//                    System.out.println("test");
//                    System.out.println("Next: " + it.next());
//                    System.out.println("Next next: " + it.next());
                    // k --> skip l --> j

                    // if you're second to last
                    try {
                        // if you're last
                        if (currPlayer.equals(turnList.getLast())) {
                            Iterator head = turnList.iterator();
                            currPlayer = head.next().toString();
                            currPlayer = head.next().toString();
                            //   turnList.indexOf(turnList.getFirst());
                            break;
                        }
                        currPlayer = it.next().toString();
//                        System.out.println("currPlayer after one next:" + currPlayer);
                        currPlayer = it.next().toString();
//                        System.out.println("currPlayer after next twice:" + currPlayer);

                    }
                    catch(Exception e) {
                        // if it is the second to last one
                        // return the first one
                        currPlayer = turnList.getFirst();
                    }

                }
                else {
                    if (currPlayer.equals(turnList.getLast())) {
//                System.out.println("hey there ");
                        currPlayer = turnList.getFirst();
//                System.out.println("hey there: " + currPlayer);
                        break;
                    }
                    currPlayer = it.next().toString();
                    break;
                }
            }
        }
        return currPlayer;
    }

} 
