/* Group number: 34

   Group members:
                Sanat Dusad (GitHub ID: 102313893)
                Ana Radojicic (GitHub ID: 102313888)
                Reya George (GitHub ID: 72013503)

*/

import java.util.InputMismatchException;
import java.util.Scanner;

// board class representing the board of every player
public class Board {
    //4D array representing board
    public String[][][][] display = new String[10][11][4][1];
    public Tile [][] storage = new Tile[10][11]; //corresponding array for storing Tiles placed on the board

    //each player's board consists of a starterTile
    private StarterTile starterTile;

    //constructor
    public Board(StarterTile starterTile) {
        this.starterTile = starterTile;

    }

    //getters
    public Tile[][] getStorage() {
        return storage;
    }

    public String [][][][] getDisplay(){
        return display;
    }


    public StarterTile getStarterTile() {
        return starterTile;
    }

    String space = "      ";
    String [][] filler = new String[4][1];

    public void setFiller(){
        for(int i=0; i<4; i++){
            for(int j=0; j<1; j++){
                filler[i][j] = space;
            }
        }
    }
    public void setDisplay(){
        setFiller();
        for(int i=0; i<10; i++){
            for(int j=0; j<11; j++){
                display[i][j] = filler;
            }
        }
    }

    //adds starterTile to the centre of every player's board
    public void addStarterTile(){
        Tile t = new Tile();
        storage[4][5] = starterTile.getTile1();
        display[4][5] = t.createHabitatTile(starterTile.getTile1(), false);

        storage[5][4] = starterTile.getTile2();
        display[5][4] = t.createHabitatTile(starterTile.getTile2(), false);

        storage[5][6] = starterTile.getTile3();
        display[5][6] = t.createHabitatTile(starterTile.getTile3(), false);
    }


    //adds tile to a player's board
    public void addTileToBoard(int i, int j, Tile tile){
        display[i][j]= tile.createHabitatTile(tile, false); //string rep of tile for display
        storage[i][j] = tile; //actual tile variable stored in corresponding index
    }

    //removes tile from board, used when we are testing placements for bot strategy
    public void removeTile(int i, int j){
        display[i][j] = filler;
        storage[i][j] = null;
    }

    //add edited string(Tile) to board
    public void addStringTileToBoard(int i, int j, String[][] tile){

        display[i][j]= tile;
    }

    public void addWildlifeToken(int i, int j, Tile tile, Enum.Wildlife w){
        if(storage[i][j] == null) System.out.println("Token cannot be placed.");
        else {
            storage[i][j] = new Tile(storage[i][j].getTerrain(), w, null, null, false);
            display[i][j] = tile.createHabitatTile(storage[i][j], false);
        }
    }

    //place wildlife tokens on the board

    public void placeWildlifeToken(Enum.Wildlife w, Player p){

        int xCoordinate=0;
        int YCoordinate=0;
        boolean skip= false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\nHere is the token you can place");
        System.out.println(Tile.getColoredWildlifeString(w));

        System.out.println("\n\nHere are the available coordinates of the tiles where you can place your selected wildlife token. Please pick an x coordinate(0-9) and a y coordinate(0-10)\nFor example:- (1,0)\n");
        System.out.println("You can only place your selected token on a Tile which has that Token drawn!\n\n");
        displayBoard(getDisplay(),true);

        for (int i = 0; i < storage.length; i++) {

            skip = false;
            for (int j = 0; j < storage[i].length; j++) {

                if (this.getStorage()[i][j] != null) { // check if there is a token at this coordinate

                    // check if the existing token(s) at this coordinate match the type of the new token being placed
                    if ((this.getStorage()[i][j].isFrozen()) ||
                            (this.getStorage()[i][j].getWildlife1().getType() != w.getType()) &&
                                    (this.getStorage()[i][j].getWildlife2() == null ||
                                            this.getStorage()[i][j].getWildlife2().getType() != w.getType()) &&
                                    (this.getStorage()[i][j].getWildlife3() == null ||
                                            this.getStorage()[i][j].getWildlife3().getType() != w.getType())) {

                        skip = true; // set skip to false if there is a matching token
                        //break; // exit the loop if a matching token is found
                    }
                    else
                    {
                        skip = false;
                        break;
                    }
                }
                else{
                    skip = true;
                }
            }

            if (!skip) {
                break; // exit the outer loop if a matching token is found
            }
        }
        if(!skip){

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

            //error handling regarding placement of token
            while (isNotValidTokenPlacement(xCoordinate, YCoordinate, w)){

                System.out.println("Cannot place token here. Please pick a different coordinate\n");
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


            System.out.println("You picked ("+xCoordinate+", "+YCoordinate+")");
            Tile t = storage[xCoordinate][YCoordinate];

            //if token is placed on a keyStone tile, award the player a nature token
            if(t.isKeystone){
                Cascadia c= new Cascadia();
                System.out.println("You have won a nature token!");
                p.natureTokens++;

                c.totalNatureToken--;

            }

            String [][] t2 = t.tileDisplay(t.getTerrain(), w, null, null, false); //essentially creating new tile
            Tile t3= new Tile(t.getTerrain(), w, false);
            t3.setFrozen(true);
            storage[xCoordinate][YCoordinate]=t3;

            addStringTileToBoard(xCoordinate, YCoordinate, t2); //re-adding edited tile to board
            System.out.println("\n\nHere is your board after placing the Tile and Token pair\n\n");
            displayBoard(getDisplay(),false);
        }
        else{
            System.out.println("You cant place this token anywhere.Next player's turn.\n\n");
        }

    }

    //edge cases for incorrect token placement
    private boolean isNotValidTokenPlacement(int xCoordinate, int YCoordinate, Enum.Wildlife w){
        return (this.getStorage()[xCoordinate][YCoordinate]==null) ||
                (this.getStorage()[xCoordinate][YCoordinate].getWildlife1().getType() != w.getType() || this.getStorage()[xCoordinate][YCoordinate].getWildlife1() == null ) &&
                        (this.getStorage()[xCoordinate][YCoordinate].getWildlife2() == null ||
                                this.getStorage()[xCoordinate][YCoordinate].getWildlife2().getType() != w.getType()) &&
                        (this.getStorage()[xCoordinate][YCoordinate].getWildlife3() == null ||
                                this.getStorage()[xCoordinate][YCoordinate].getWildlife3().getType() != w.getType()) ||
                (this.getStorage()[xCoordinate][YCoordinate].isFrozen());
    }

    //displays board (set withCoordinate to true if you want to print the coordinates too)
    public void displayBoard(String [][][][] test, boolean withCordinate){
        for(int i=0; i<10; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 11; k++) {
                    if(withCordinate && j==0)
                    {
                        System.out.print("(" + i + ", " + k + ") ");
                    }
                    else if(withCordinate && j!=0)
                    {
                        System.out.print("       " );
                    }
                    System.out.print(test[i][k][j][0]);
                }
                System.out.println();
            }
        }
    }

}
