/* Group number: 34

   Group members:
                Sanat Dusad (GitHub ID: 102313893)
                Ana Radojicic (GitHub ID: 102313888)
                Reya George (GitHub ID: 72013503)

*/
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * This class implements numerous methods which handle the main logic of the game
 *
 * @author Sanat Dusad
 * @author Ana Radojicic
 * @version: JDK 19
 *
 *
 */

public class Cascadia {

    //arrayList containing the 4 Tile-token pairs to be displayed alongside every player's board
    private ArrayList <Tile> fourTiles= new ArrayList<Tile>();
    //players are stored in an ArrayList of type Player
    ArrayList<Player> players = new ArrayList<Player>();

    //total number of nature tokens to begin with
    public int totalNatureToken=20;
    private Tile test = new Tile();

    //getters and setters

    public int getTotalNatureToken() {
        return totalNatureToken;
    }

    public ArrayList<Tile> getFourTiles() {
        return fourTiles;
    }

    public void setFourTiles(ArrayList<Tile> fourTiles) {
        this.fourTiles = fourTiles;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     *
     *  This method runs the whole game with the help of the methods above.
     *
     */
    public void startGame() {

        playerInit();
        Collections.shuffle(players); //shuffles the players in ArrayList
        System.out.println("\nThe players will play in this order:");

        for (int i = 0, x = 1; i < players.size(); i++, x++) {

            System.out.println(x + "." + players.get(i).getName() + "\n");
        }

        test.initHabitatTiles(getPlayers().size());  //initialsing habitat tiles and wildlife tokens
        test.initWildlifeTokens();
        randomGenerateHabitatTilesAndToken(test.getHabitatTiles(), test.getWildlifeTokens(), 4); //4 random tiles are paired up with 4 random tokens
        int currentPlayerIndex = 0;
        boolean quit = false;
        int maxTurnsPerPlayer = 20;
        int numPlayers = getPlayers().size();
        int[] turnsTaken = new int[numPlayers];
        Scanner scanner = new Scanner(System.in);

        while (!quit) {

            dispayPlayerBoard(currentPlayerIndex);
            Board board = getPlayers().get(currentPlayerIndex).getBoard();

            
            if(getPlayers().get(currentPlayerIndex) instanceof Bot){

                botsTurn(currentPlayerIndex, board);
            }

            
            else if(getPlayers().get(currentPlayerIndex) instanceof Player){
                playersTurn(scanner, board, currentPlayerIndex);
            }

            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            turnsTaken[currentPlayerIndex]++;
            if (turnsTaken[currentPlayerIndex] == maxTurnsPerPlayer) {
                boolean allPlayersFinished = true;
                for (int i = 0; i < numPlayers; i++) {
                    if (turnsTaken[i] < maxTurnsPerPlayer) {
                        allPlayersFinished = false;
                        break;
                    }
                }
                if (allPlayersFinished) { //breaks out of the loop when all players have played 20 turns
                    System.out.println("\n\nAll players have played 20 turns. Time for scoring!");
                    break;
                }
            }

        }

        Scoring score = new Scoring();

        for(int i=0; i<players.size();i++){ //calculates score for each player
            System.out.println(Colors.ORANGE+"\n"+getPlayers().get(i).getName() + "'s score: "+Colors.RESET + score.calculateScore(getPlayers().get(i)));
        }
        System.out.println(Colors.ORANGE+"\nThanks for playing Cascadia!!"+Colors.RESET);
        scanner.close();
    }

    //logic for player's turn
    private void playersTurn(Scanner scanner, Board board, int currentPlayerIndex){
        System.out.println("\nType 'Select' to Select a tile  or 'quit' to exit the game");
        String command = scanner.next();
        while(!(command.equalsIgnoreCase("select")) && !(command.equalsIgnoreCase("quit"))){
            System.out.println("Invalid Input. Please type 'select' or 'quit'");
            command= scanner.next();
        }

        if (command.equalsIgnoreCase("select")) {

            int tileNumber = useNatureTokentoPickAnyPair(board, currentPlayerIndex);
            Scoring score = new Scoring();
            System.out.println(getPlayers().get(currentPlayerIndex).getName()+"'s" + " score after this turn:");
            score.displayIndividualScoreComponents(getPlayers().get(currentPlayerIndex));
            getFourTiles().remove(tileNumber-1); //removes the tile-token pair since its already used (from the arrayList of 4 pairs)
            randomGenerateHabitatTilesAndToken(test.getHabitatTiles(),test.getWildlifeTokens(),1);//replaces removed pair with a random pair
            System.out.println("\nNext player's turn\n\n");
        }
    }

    //logic for bot's turn
    private void botsTurn(int currentPlayerIndex, Board board){

        System.out.println("\n\n" + getPlayers().get(currentPlayerIndex).getName() + "'s turn:\n\n");
        Bot buddy = (Bot) getPlayers().get(currentPlayerIndex);
        pause();

        int tileNumber = buddy.pickingThePair(fourTiles, buddy).rowChoice;
        buddy.playingTheChoice(buddy.pickingThePair(fourTiles, buddy), buddy);

        pause();

        board.displayBoard(board.getDisplay(),false);

        pause();

        Scoring score = new Scoring();
        System.out.println("\n" + getPlayers().get(currentPlayerIndex).getName() + "'s score after this turn:");
        score.displayIndividualScoreComponents(buddy);

        getFourTiles().remove(tileNumber); //removes the tile-token pair since its already used (from the arrayList of 4 pairs)
        randomGenerateHabitatTilesAndToken(test.getHabitatTiles(),test.getWildlifeTokens(),1);//replaces removed pair with a random pair
    }

    /**
     * Functionality: This method initialises the board for each player and assigns a starterTile to each board.
     *                It also prints the names of all the players.
     */
    private void playerInit() {

        setPlayerName();
        setBotName();
        System.out.println("\nThe players are:");
        creatBoards();
    }

    /**
     //* @param num (number of players)
     *
     * Functionality: This method reads in the names of the players from the console and stores the players in an ArrayList
     */
    public void setPlayerName() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter name of the Player");
        String name= sc.next();
        players.add(new Player(name));

    }

    //picks a random name for the bot from the list of names and adds it to the player arrayList
    public void setBotName(){

        Random rand = new Random();
        int randomIndex = rand.nextInt(Bot.BOT_NAMES.length);
        Bot bot = new Bot(Bot.BOT_NAMES[randomIndex]);
        players.add(bot);
    }

    /**
     *
     * @param currentPlayerIndex (current player's index)
     *
     * Functionality: This method displays the current player's board before playing their turn and also checks if culling is available.
     *                It also checks if the user has a nature token available to be used for wiping a certain number of tokens.
     */
    private void dispayPlayerBoard(int currentPlayerIndex){

        System.out.println("\n" + getPlayers().get(currentPlayerIndex).getName() + "'s board");
        Board board = getPlayers().get(currentPlayerIndex).getBoard();
        board.displayBoard(board.getDisplay(),false);
        displayFourHabitatTilesAndToken(); //displaying the 4 Tiles and Tokens (paired together) from which the player picks one pair to play
        culling(test.getWildlifeTokens(), currentPlayerIndex); //check for culling
        useNatureTokentoWipe(currentPlayerIndex, test.getWildlifeTokens()); //check for natureTokens
    }

    /**
     *
     * //@param player current player
     * @param board current player's board
     * @param currentPlayerIndex current player's index
     * @return int number/index of tile selected by the player
     *
     * Functionality: This method implements the feature where a player can use a nature token to pair up any Tile and token and place them
     *                on their board.
     *
     */
    public int useNatureTokentoPickAnyPair(Board board, int currentPlayerIndex){

        int tileNumber=0;
        Scanner sc= new Scanner(System.in);
        if(getPlayers().get(currentPlayerIndex).getNatureTokens()>0){
            System.out.println("You have "+getPlayers().get(currentPlayerIndex).getNatureTokens()+" nature tokens. Would you like to use one to pick any Tile with any Wildlife token?");
            System.out.println("Enter 'yes' or 'no'.");

            String choice= sc.next();
            while(!(choice.equalsIgnoreCase("yes")) && !(choice.equalsIgnoreCase("No"))){

                System.out.println("Invalid input. Please enter 'yes' or 'no' to continue");
                choice= sc.next();
            }
            if(choice.equalsIgnoreCase("yes")){
                getPlayers().get(currentPlayerIndex).natureTokens--; //decrementing player's nature token if used
                totalNatureToken++;    // adding that token back to the pot of nature tokens
                System.out.println("\n\nHere are the 4 tiles. Please pick a number from 1-4:\n");
                int i=1;
                for(Tile tile: getFourTiles()){
                    System.out.print("\n"+i+". \n");
                    tile.displayHabitatTilesAndToken(tile, false);
                    i++;
                }

                int ans=0;
                while (true) {
                    try {
                        System.out.println("\nEnter Tile number(1-4) to pick a Tile\n");;
                        ans = sc.nextInt();

                        if (ans >= 1 && ans <= 4) {
                            break;
                        } else {
                            System.out.println("\nInvalid input. Please enter a number between 1-4.\n");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("\nInvalid input. Please enter a valid number between 1-4.\n");
                        sc.next();
                    }
                }

                System.out.println("\nYou have picked the following Tile\n\n");
                test.displayHabitatTilesAndToken(getFourTiles().get(ans-1), false); //displays the tile picked


                System.out.println("\n\nHere are the 4 tokens. Please pick a number from 1-4");
                int j=1;
                for(Tile tile:getFourTiles()){

                    System.out.print("\n"+j+".  "+Tile.getColoredWildlifeString(tile.getAssocToken()));
                    j++;
                }

                int tokenNum= 0;
                while (true) {
                    try {
                        System.out.println("\nEnter token number(1-4) to pick a Token\n");;
                        tokenNum = sc.nextInt();

                        if (tokenNum >= 1 && tokenNum <= 4) {
                            break;
                        } else {
                            System.out.println("\nInvalid input. Please enter a number between 1-4.\n");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("\nInvalid input. Please enter a valid number between 1-4.\n");
                        sc.next();
                    }
                }

                System.out.println("\n\nHere is the pair that you have selected:");
                getFourTiles().get(ans-1).setAssocToken(getFourTiles().get(tokenNum-1).getAssocToken());
                test.displayHabitatTilesAndToken(getFourTiles().get(ans-1), true); //displays the pair picked by the player
                pickTileCordinates(ans,sc,board); //allows user to place the tile at a valid coordinate
                board.placeWildlifeToken(getFourTiles().get(ans-1).getAssocToken(), players.get(currentPlayerIndex));//allows user to place the token at a valid coordinate
                return ans; //returns index of tile placed
            }
            else if(choice.equalsIgnoreCase("no")){

                System.out.println("\n\nContinuing with the game....");
                tileNumber = tileSelection(sc);
                pickTileCordinates(tileNumber,sc,board);
                board.placeWildlifeToken(getFourTiles().get(tileNumber-1).getAssocToken(), players.get(currentPlayerIndex));
                return tileNumber;
            }
        }

        tileNumber = tileSelection(sc);
        pickTileCordinates(tileNumber,sc,board);
        board.placeWildlifeToken(getFourTiles().get(tileNumber-1).getAssocToken(), players.get(currentPlayerIndex));
        return tileNumber;

    }


    /**
     *
     * @param scanner
     * @return int (index of tile picked by the user)
     *
     * Functionality: This method uses a 'Scanner' object to read in the number/index of the tile picked by the user and returns
     *                that number to be used later on.
     */
    private int tileSelection(Scanner scanner) {
        int tileNumber;

        //error handling regarding tile number entered
        while (true) {
            try {
                System.out.println("\nEnter Tile number(1-4) to pick a pair of Tile and Token\n");;
                tileNumber = scanner.nextInt();

                if (tileNumber >= 1 && tileNumber <= 4) {
                    break;
                } else {
                    System.out.println("\nInvalid input. Please enter a number between 1-4.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a valid number between 1-4.\n");
                scanner.next();
            }
        }
        System.out.println("\n\nYou have selected the following pair: ");
        test.displayHabitatTilesAndToken(getFourTiles().get(tileNumber-1), true); //displays the tile selected
        return tileNumber;
    }

    /**
     *
     * @param tileNumber index of the tile selected
     * @param scanner Scanner object
     * @param board Board object(representing the current player's board)
     *
     * Functionality: This method reads in the 'xCoordinate' and 'yCoordinate' from the player and places their selected tile at those
     *                coordinates on the player's board (provided the coordinates are valid). It also then displays all possible rotations of the selected tile
     *                and allows the user to rotate their tile.
     */
    private void pickTileCordinates(int tileNumber, Scanner scanner,Board board) {

        System.out.println("\n\nHere are the available coordinates where you can place your selected tile. Please pick an x coordinate(0-9) and a y coordinate(0-10)\nFor example:- (1,0)");
        System.out.println("Choose where you place your tile wisely!\n");
        board.displayBoard(board.getDisplay(),true);

        int xCoordinate;
        int YCoordinate;
        while (true) {
            try {
                System.out.print("\n\nEnter x-coordinate(0-9): \n");
                xCoordinate = scanner.nextInt();
                if(xCoordinate>=0 && xCoordinate<=9){
                    break;
                } else{
                    System.out.println("\nInvalid input. Please pick a number between 0-9.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter an integer.\n");
                scanner.next();
            }
        }
        while (true) {
            try {
                System.out.print("Enter y-coordinate(0-10): \n");
                YCoordinate = scanner.nextInt();
                if(YCoordinate>=0 && YCoordinate<=10){
                    break;
                } else{
                    System.out.println("\nInvalid input. Please pick a number between 0-10.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next();
            }
        }

        //error handling as to where a tile can be placed
        while(isNotValidTilePlacement(xCoordinate, YCoordinate, board)) {

            System.out.println("Cannot place tile here. Please pick a valid cooridinate!\n");
            while (true) {
                try {
                    System.out.print("\n\nEnter x-coordinate(0-9): \n");
                    xCoordinate = scanner.nextInt();
                    if(xCoordinate>=0 && xCoordinate<=9){
                        break;
                    } else{
                        System.out.println("\nInvalid input. Please pick a number between 0-9.\n");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("\nInvalid input. Please enter an integer.\n");
                    scanner.next();
                }
            }
            while (true) {
                try {
                    System.out.print("Enter y-coordinate(0-10): \n");
                    YCoordinate = scanner.nextInt();
                    if(YCoordinate>=0 && YCoordinate<=10){
                        break;
                    } else{
                        System.out.println("\nInvalid input. Please pick a number between 0-10.\n");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter an integer.\n");
                    scanner.next();
                }
            }

        }

        System.out.println("\n\nYou picked ("+xCoordinate+", "+YCoordinate+")\n\n");

        board.addTileToBoard(xCoordinate, YCoordinate, getFourTiles().get(tileNumber-1)); //adds Tile picked to the player's board based on the coordinate they choose
        System.out.println("\n\nHere's how your board looks. You can rotate the tile you placed if you want!\n\n");
        board.displayBoard(board.getDisplay(),false);
        String[][] str= test.displayRotation(getFourTiles().get(tileNumber-1)); //displays possible rotations of the selected tile
        board.addStringTileToBoard(xCoordinate, YCoordinate, str); //rotates the tile accordingly
    }

    //edge cases for incorrect tile placement
    private boolean isNotValidTilePlacement(int xCoordinate, int YCoordinate, Board board){

        return board.getStorage()[xCoordinate][YCoordinate]!=null ||
                ((xCoordinate > 0 && board.getStorage()[xCoordinate-1][YCoordinate]==null) && (xCoordinate < 9 && board.getStorage()[xCoordinate+1][YCoordinate]==null)
                        && (YCoordinate > 0 && board.getStorage()[xCoordinate][YCoordinate-1]==null) && (YCoordinate < 10 && board.getStorage()[xCoordinate][YCoordinate+1]==null));
    }

    /**
     *
     * @param habitatTiles arrayList of habitat tiles
     * @param tokens arrayList of tokens
     * @param number number of pairs to be generated
     *
     * Functionality: This method takes a random tile(from the arrayList of tiles) and a random wildlife token(from the arrayList of tokens)
     *                and pairs them up together and adds the pair to the arrayList of 4 Tile-Token pairs from which the player picks one
     *                to place on their board.
     */
    private void randomGenerateHabitatTilesAndToken(ArrayList<Tile> habitatTiles,ArrayList<Enum.Wildlife> tokens,int number) {
        Collections.shuffle(habitatTiles);
        Collections.shuffle(tokens);

        for (int i = 0; i < number; i++) {

            int randomIndex= new Random().nextInt(habitatTiles.size()); //fetching tile from random index
            int randomIndexToken= new Random().nextInt(tokens.size()); //fetching token from random index
            Tile tile= habitatTiles.remove(randomIndex);
            Enum.Wildlife token = tokens.remove(randomIndexToken);
            tile.setAssocToken(token); //setting token as a pair to the tile
            fourTiles.add(tile); //adding the pair to the arrayList of 4 pairs

        }
    }

    /**
     *
     * @param tokens arrayList of wildlife tokens
     *
     * Functionality: This method implements the culling feature. It first checks the number of tokens which are the same. If all 4 tokens
     *                are the same, culling is automatically done by replacing all 4 tokens. If 3 tokens are the same, the player is given
     *                an option to cull. If the player enters 'yes' the 3 same tokens are replaced. If the player enters 'no', the game
     *                continues without replacing any of the tokens.
     */
    private void culling (ArrayList<Enum.Wildlife> tokens, int currentPlayerIndex){
        Scanner sc= new Scanner(System.in);
        //indexes stored in an arrayList, needed to replace same tokens at particular indexes
        ArrayList<Integer> indexes= new ArrayList<Integer>();
        ArrayList<Integer> indexes2= new ArrayList<Integer>();

        Tile t1;
        Tile t2;
        Tile t3;

        indexes.add(0);
        indexes2.add(1);
        int count=1;
        int count2=1;
        for(int i=0; i<fourTiles.size(); i++){
            Enum.Wildlife token1= fourTiles.get(i).getAssocToken();
            for(int j=i+1; j<fourTiles.size();j++){
                if(token1.getType().equals(fourTiles.get(j).getAssocToken().getType())){

                    if(i==0){
                        count++;
                        indexes.add(j);
                    }
                    if(i==1){
                        indexes2.add(j);
                        count2++;
                    }
                }
            }
        }

        if(count==3 || count2==3){

            if(getPlayers().get(currentPlayerIndex) instanceof Bot){

                System.out.println("\n\nAutomatic cull detected. Changing tokens....\n");
                t1= getFourTiles().get(indexes.get(0));
                tokens.add(t1.getAssocToken()); //replaced token is added back to the arrayList of wildlife tokens
                changeAssocToken(t1, tokens); //new token(taken from the arrayList) is paired up with the tile

                t2= getFourTiles().get(indexes.get(1));
                tokens.add(t2.getAssocToken());
                changeAssocToken(t2, tokens);

                t3= getFourTiles().get(indexes.get(2));
                tokens.add(t3.getAssocToken());
                changeAssocToken(t3, tokens);
                System.out.println("\n\nThe tokens have been replaced!\n\n");
                displayFourHabitatTilesAndToken();

            }
            else if(getPlayers().get(currentPlayerIndex) instanceof Player){
                System.out.println("\n\nYou have an option to cull since 3 tokens are the same. Would you like to cull?\n");
                System.out.println("\n\nEnter 'yes' to cull or 'no' to continue\n");

                String choice = sc.next();
                while(!(choice.equalsIgnoreCase("yes")) && !(choice.equalsIgnoreCase("No"))){
                    System.out.println("\n\nInvalid input. Please enter 'yes' to cull or 'no' to continue");
                    choice= sc.next();
                }
                if(choice.equalsIgnoreCase("yes")){
                    t1= getFourTiles().get(indexes.get(0));
                    tokens.add(t1.getAssocToken()); //replaced token is added back to the arrayList of wildlife tokens
                    changeAssocToken(t1, tokens); //new token(taken from the arrayList) is paired up with the tile

                    t2= getFourTiles().get(indexes.get(1));
                    tokens.add(t2.getAssocToken());
                    changeAssocToken(t2, tokens);

                    t3= getFourTiles().get(indexes.get(2));
                    tokens.add(t3.getAssocToken());
                    changeAssocToken(t3, tokens);
                    System.out.println("\n\nThe tokens have been replaced!\n\n");
                    displayFourHabitatTilesAndToken();
                }
                else{
                    System.out.println("\n\nYou have chosen not to cull. Continuing with the game....\n\n");
                    displayFourHabitatTilesAndToken();
                }
            }

        }

        //if all 4 tokens are the same, culling is done automatically
        if(count==4){
            System.out.println("\n\nAutomatic cull detected. Changing tokens....\n");
            for(Tile tile: getFourTiles()){
                tokens.add(tile.getAssocToken());
                changeAssocToken(tile, tokens);
            }
            displayFourHabitatTilesAndToken();
        }

    }

    /**
     *
     * //@param player current player
     * @param tokens ArrayList of wildlife tokens
     *
     * Functionality: This method handles the logic for using nature tokens to wipe wildlife tokens(by calling wipeToken method)
     *                It reads in the number of tokens the player wants to replace and passes that as a parameter to the wipeToken
     *                method which replaces that number of tokens.
     */
    public void useNatureTokentoWipe(int currentPlayerIndex,ArrayList <Enum.Wildlife> tokens ){

        if(getPlayers().get(currentPlayerIndex) instanceof Bot){
            Random random = new Random();
            int randomNumber = random.nextInt(2);
            String answer = (randomNumber == 0) ? "yes" : "no";

            if(answer.equalsIgnoreCase("yes")){

                int randomNum = random.nextInt(4) + 1;
                System.out.println(getPlayers().get(currentPlayerIndex).getName()+ " decides to use nature token to wipe "+ randomNum+ " tokens.");
                wipeToken(tokens, randomNum);

            }
            else if(answer.equalsIgnoreCase("no")){
                System.out.println(getPlayers().get(currentPlayerIndex).getName()+ " decides not to use nature token. Continuing with the game....");
            }
        }
        else if(getPlayers().get(currentPlayerIndex) instanceof Player){
            Scanner scan= new Scanner(System.in);
            if(getPlayers().get(currentPlayerIndex).getNatureTokens()>0){ //if the player has nature tokens
                System.out.println("\n\nYou have "+ getPlayers().get(currentPlayerIndex).getNatureTokens()+" nature tokens. Would you like to use one to wipe any number of tokens?\n");
                System.out.println("Enter 'yes' or 'no.");
                String choice= scan.next();

                while(!(choice.equalsIgnoreCase("yes")) && !(choice.equalsIgnoreCase("No"))){
                    System.out.println("Invalid input. Please enter 'yes' or 'no' to continue");
                    choice= scan.next();
                }
                if(choice.equalsIgnoreCase("yes")){
                    getPlayers().get(currentPlayerIndex).natureTokens--; //decrementing player's nature token if used
                    totalNatureToken++;    // adding that token back to the pot of nature tokens
                    int num=0;
                    while (true) {
                        try {
                            System.out.println("\nHow many tokens do you want to wipe?. Pick a number between 1-4\n");
                            num = scan.nextInt();
                            if (num >= 1 && num <= 4) {
                                break;
                            } else {
                                System.out.println("\nInvalid input. Please enter a number between 1-4.\n");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter an integer.\n");
                            scan.next();
                        }
                    }
                    wipeToken(tokens, num); //replace number of tokens entered by the player
                }
                else if(choice.equalsIgnoreCase("no")){
                    System.out.println("\n\nContinuing with the game");
                    displayFourHabitatTilesAndToken();
                }
            }
        }

    }


    /**
     *
     * @param tokens ArrayList of wildlife tokens
     * @param number number of tokens to be wiped(replaced)
     *
     * Functionality: This method handles the logic for replacing a certain number(which the player picks) of tokens in the Tile-token
     *                pairs. Replaced tokens are added back to the original arrayList of wildlife tokens.
     */
    public void wipeToken(ArrayList <Enum.Wildlife> tokens, int number){

        Tile t1,t2,t3;

        if(number==1){
            System.out.println("\n\nWiping 1 token....\n\n");
            t1=getFourTiles().get(0);
            tokens.add(t1.getAssocToken()); //adding the token back to the original arrayList
            changeAssocToken(t1, tokens);
            displayFourHabitatTilesAndToken();
        }
        else if(number==2){
            System.out.println("\n\nWiping 2 tokens....\n\n");
            t1=getFourTiles().get(0);
            tokens.add(t1.getAssocToken());
            changeAssocToken(t1, tokens);

            t2=getFourTiles().get(1);
            tokens.add(t2.getAssocToken());
            changeAssocToken(t2, tokens);
            displayFourHabitatTilesAndToken();
        }
        else if(number==3){
            System.out.println("\n\nWiping 3 tokens....\n\n");
            t1=getFourTiles().get(0);
            tokens.add(t1.getAssocToken());
            changeAssocToken(t1, tokens);

            t2=getFourTiles().get(1);
            tokens.add(t2.getAssocToken());
            changeAssocToken(t2, tokens);

            t3= getFourTiles().get(2);
            tokens.add(t3.getAssocToken());
            changeAssocToken(t3, tokens);
            displayFourHabitatTilesAndToken();
        }
        else {
            System.out.println("\n\nWiping all 4 tokens....\n\n");
            for(Tile tile: getFourTiles()){
                tokens.add(tile.getAssocToken());
                changeAssocToken(tile, tokens);
            }
            displayFourHabitatTilesAndToken();
        }
    }

    /**
     *
     * @param t Tile object
     * @param tokens ArrayList of wildlife tokens
     *
     * Functionality: This method changes the associative token of a tile (changes the token in the Tile-Token pair)
     */
    public void changeAssocToken(Tile t, ArrayList <Enum.Wildlife> tokens){
        Collections.shuffle(tokens);
        Enum.Wildlife token= tokens.remove(0);
        t.setAssocToken(token);
    }

    /**
     *
     * Functionality: This method displays the 4 Tile-Token pairs
     */
    private void displayFourHabitatTilesAndToken() {
        Tile t= new Tile();
        System.out.println("The 4 pairs of Tiles and Tokens are:");
        for(Tile tile: getFourTiles()){
            t.displayHabitatTilesAndToken(tile, true);
        }
    }

    private void pause(){
        try {
            // Wait for 3 seconds
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Handle the exception
        }
    }

    //initialises every player's board
    private void creatBoards(){

        //initalize starter tile
        StarterTile starter = new StarterTile();
        starter.initStarterTile();

        for (Player player : getPlayers()) {

            System.out.println(player.getName());
            player.createBoard(starter.getStarterTiles());
            player.getBoard().setDisplay();
            player.getBoard().addStarterTile();
        }
    }

}
