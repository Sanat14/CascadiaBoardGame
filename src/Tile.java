/* Group number: 34

   Group members:
                Sanat Dusad (GitHub ID: 102313893)
                Ana Radojicic (GitHub ID: 102313888)
                Reya George (GitHub ID: 72013503)

*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Tile {

    ArrayList<Tile>habitatTiles = new ArrayList<Tile>(); //all 85 habitat tiles are stored in this arrayList
    private Enum.Terrain terrain;
    private Enum.Wildlife wildlife1;
    private Enum.Wildlife wildlife2;
    private Enum.Wildlife wildlife3;
    private Enum.Wildlife assocToken; //each tile(except starterTiles) has an associative token (which is displayed as a pair)
    public boolean isKeystone;
    public boolean isStarter;
    private boolean frozen=false;

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }


    private ArrayList<Enum.Wildlife> wildlifeTokens = new ArrayList<>();


    public ArrayList<Enum.Wildlife> getWildlifeTokens(){
        return this.wildlifeTokens;
    }

    public Enum.Wildlife getAssocToken() {
        return assocToken;
    }
    public void setAssocToken(Enum.Wildlife assocToken) {
        this.assocToken = assocToken;
    }

    public Enum.Wildlife getWildlife1() {
        return wildlife1;
    }
    public Enum.Wildlife getWildlife2() {
        return wildlife2;
    }
    public Enum.Wildlife getWildlife3() {
        return wildlife3;
    }

    public Enum.Terrain getTerrain() {
        return terrain;
    }

    public ArrayList<Tile> getHabitatTiles() {
        return habitatTiles ;
    }

    public boolean isStarter() {
        return isStarter;
    }

    //constructor for keystone tiles
    public Tile(Enum.Terrain terrain, Enum.Wildlife wildlife, boolean isStarter) {
        this(terrain, wildlife, null,null, true, isStarter);
    }

    //constructor for combination tiles (which have 2 wildlife tokens)
    public Tile(Enum.Terrain terrain, Enum.Wildlife wildlife1, Enum.Wildlife wildlife2, boolean isStarter) {
        this(terrain, wildlife1, wildlife2,null, false,isStarter);
    }

    //constructor for combination tiles (which have 3 wildlife tokens)
    public Tile(Enum.Terrain terrain, Enum.Wildlife wildlife1, Enum.Wildlife wildlife2, Enum.Wildlife wildlife3, boolean isStarter) {
        this(terrain, wildlife1, wildlife2, wildlife3, false, isStarter);
    }

    private Tile(Enum.Terrain terrain, Enum.Wildlife wildlife1, Enum.Wildlife wildlife2, Enum.Wildlife wildlife3, boolean isKeystone, boolean isStarter) {
        this.terrain = terrain;
        this.wildlife1 = wildlife1;
        this.wildlife2 = wildlife2;
        this.wildlife3=wildlife3;
        this.isKeystone = isKeystone;
        this.isStarter=isStarter;
    }

    public Tile(){

    };


    private ArrayList<Tile> combohabitatTiles = new ArrayList<Tile>();
    private ArrayList<Tile> temphabitatTiles = new ArrayList<Tile>();


    //takes tile as input
    public String[][] createHabitatTile(Tile t, boolean rotate){
        return tileDisplay(t.getTerrain(), t.getWildlife1(), t.getWildlife2(), t.getWildlife3(), rotate);
    }

    //takes individual tile components as input (used when editing tiles)
    public String [][] tileDisplay(Enum.Terrain t, Enum.Wildlife wl1, Enum.Wildlife wl2, Enum.Wildlife wl3, boolean rotate){
        //Creating variables for different segments of the tile
        String w1 = wildlifeTokenLetter(wl1);
        String w2 = wildlifeTokenLetter(wl2);
        String w3 = wildlifeTokenLetter(wl3);

        String[] colours = tileColour(t); //colour codes
        String c1 = colours[0];
        String c2 = colours[1];

        return colourPattern(c1, c2, w1, w2, w3, rotate);
    }

    //function to return colour/s of each terrain
    public String[] tileColour(Enum.Terrain t){
        String c1 = "", c2 = "";
        String [] colours = new String[2];

        if(t == Enum.Terrain.FOREST || t == Enum.Terrain.PRAIRIE || t == Enum.Terrain.WETLAND || t == Enum.Terrain.MOUNTAIN ||
                t == Enum.Terrain.RIVER ){
            c1= t.getColor();
            c2= t.getColor();
        }

        if(t == Enum.Terrain.FOREST_PRAIRIE){
            c1 = Colors.DARK_GREEN_BACKGROUND;
            c2 = Colors.YELLOW_BACKGROUND;
        }
        if(t == Enum.Terrain.FOREST_WETLAND){
            c1 = Colors.DARK_GREEN_BACKGROUND;
            c2 = Colors.LIGHT_GREEN_BACKGROUND;
        }
        if(t == Enum.Terrain.FOREST_MOUNTAIN){
            c1 = Colors.DARK_GREEN_BACKGROUND;
            c2 = Colors.GREY_BACKGROUND;
        }
        if(t == Enum.Terrain.FOREST_RIVER){
            c1 = Colors.DARK_GREEN_BACKGROUND;
            c2 = Colors.BLUE_BACKGROUND;
        }
        if(t == Enum.Terrain.PRAIRIE_WETLAND){
            c1 = Colors.YELLOW_BACKGROUND;
            c2 = Colors.LIGHT_GREEN_BACKGROUND;
        }
        if(t == Enum.Terrain.PRAIRIE_MOUNTAIN){
            c1 = Colors.YELLOW_BACKGROUND;
            c2 = Colors.GREY_BACKGROUND;
        }
        if(t == Enum.Terrain.PRAIRIE_RIVER){
            c1 = Colors.YELLOW_BACKGROUND;
            c2 = Colors.BLUE_BACKGROUND;
        }
        if(t == Enum.Terrain.WETLAND_MOUNTAIN){
            c1 = Colors.LIGHT_GREEN_BACKGROUND;
            c2 = Colors.GREY_BACKGROUND;
        }
        if(t == Enum.Terrain.WETLAND_RIVER){
            c1 = Colors.LIGHT_GREEN_BACKGROUND;
            c2 = Colors.BLUE_BACKGROUND;
        }
        if(t == Enum.Terrain.RIVER_MOUNTAIN){
            c1 = Colors.BLUE_BACKGROUND;
            c2 = Colors.GREY_BACKGROUND;
        }
        colours[0] = c1;
        colours[1] = c2;
        return colours;
    }

    //assigning char for each wildlife token
    public String wildlifeTokenLetter(Enum.Wildlife w){
        String token = "  ";
        if(w == null) return token;

        if(w==Enum.Wildlife.BEAR || w == Enum.Wildlife.SALMON || w == Enum.Wildlife.HAWK || w == Enum.Wildlife.ELK||  w == Enum.Wildlife.FOX ){
            token = w.getColor()+w.getType()+" "+Colors.RESET;
        }
        return token;
    }

    //creating the 2 kinds of tile colour orientations
    public String[][] colourPattern(String c1, String c2, String w1, String w2, String w3, boolean rotate){
        String [][] tile = new String[4][1];
        String chr = " ", chr2 = "   ", row = "      "; //filling empty space

        String center1 = c1 + chr + Colors.RESET + w1 + chr + chr + c2 + chr + Colors.RESET;
        String center2 = c1 + chr + Colors.RESET + w2 + w3 + c2 + chr + Colors.RESET;
        String half = c1 + chr2 + Colors.RESET + c2 + chr2 + Colors.RESET;

        if(!rotate) {
            //regular colour pattern:
            tile[0][0] = c1 + row + Colors.RESET;
            tile[1][0] = center1;
            tile[2][0] = center2;
            tile[3][0] = c2 + row + Colors.RESET;
        }
        if(rotate) {
            //post-rotation pattern:
            tile[0][0] = half;
            tile[1][0] = center1;
            tile[2][0] = center2;
            tile[3][0] = half;
        }
        return tile;
    }

    public int getHabitat(Enum.Terrain terrainType, Enum.Wildlife wildlifeType) {
        int habitat = 0;
        switch (terrainType) {
            case FOREST:
                if (wildlifeType == Enum.Wildlife.SALMON) {
                    habitat = 0;
                } else if (wildlifeType == Enum.Wildlife.BEAR) {
                    habitat = 4;
                } else if (wildlifeType == Enum.Wildlife.ELK) {
                    habitat = 3;
                } else if (wildlifeType == Enum.Wildlife.HAWK) {
                    habitat = 2;
                } else if (wildlifeType == Enum.Wildlife.FOX) {
                    habitat = 1;
                }
                break;
            case PRAIRIE:
                if (wildlifeType == Enum.Wildlife.SALMON) {
                    habitat = 0;
                } else if (wildlifeType == Enum.Wildlife.BEAR) {
                    habitat = 3;
                } else if (wildlifeType == Enum.Wildlife.ELK) {
                    habitat = 4;
                } else if (wildlifeType == Enum.Wildlife.HAWK) {
                    habitat = 1;
                } else if (wildlifeType == Enum.Wildlife.FOX) {
                    habitat = 2;
                }
                break;
            case WETLAND:
                if (wildlifeType == Enum.Wildlife.SALMON) {
                    habitat = 2;
                } else if (wildlifeType == Enum.Wildlife.BEAR) {
                    habitat = 3;
                } else if (wildlifeType == Enum.Wildlife.ELK) {
                    habitat = 1;
                } else if (wildlifeType == Enum.Wildlife.HAWK) {
                    habitat = 0;
                } else if (wildlifeType == Enum.Wildlife.FOX) {
                    habitat = 2;
                }
                break;
            case RIVER:
                if (wildlifeType == Enum.Wildlife.SALMON) {
                    habitat = 4;
                } else if (wildlifeType == Enum.Wildlife.BEAR) {
                    habitat = 1;
                } else if (wildlifeType == Enum.Wildlife.ELK) {
                    habitat = 2;
                } else if (wildlifeType == Enum.Wildlife.HAWK) {
                    habitat = 3;
                } else if (wildlifeType == Enum.Wildlife.FOX) {
                    habitat = 0;
                }
                break;
            case MOUNTAIN:
                if (wildlifeType == Enum.Wildlife.SALMON) {
                    habitat = 0;
                } else if (wildlifeType == Enum.Wildlife.BEAR) {
                    habitat = 4;
                } else if (wildlifeType == Enum.Wildlife.ELK) {
                    habitat = 3;
                } else if (wildlifeType == Enum.Wildlife.HAWK) {
                    habitat = 2;
                } else if (wildlifeType == Enum.Wildlife.FOX) {
                    habitat = 1;
                }
                break;
            default:
                System.out.println("Invalid terrain type");
                break;
        }
        return habitat;
    }


    //method which intialises the bag of habitat tiles
    public void initHabitatTiles(int num){
        int size=0;
        for (int i = 0; i < 5; i++) {
            Enum.Terrain terrain = Enum.Terrain.values()[i];

            // Loop through all the wildlife in the wildlife enum
            for (Enum.Wildlife wildlife : Enum.Wildlife.values()) {
                temphabitatTiles.add(new Tile(terrain, wildlife, false));
            }
        }
        initComboTiles();
        Collections.shuffle(temphabitatTiles);

        switch(num){ //determines how many tiles are used depending on the number of players
            case 2:
                size=43;
                break;

            case 3:
                size=63;
                break;

            case 4:
                size=83;
                break;

            default:
                break;

        }
        List<Tile> subList= temphabitatTiles.subList(0, size);
        habitatTiles.addAll(subList);

        Collections.shuffle(habitatTiles);

    }

    //method which creates all possible combination tiles (only 60 to be added to the main arrayList of habitat tiles)
    private void initComboTiles(){
        // Creates tiles with 2 wildlife types
        for (int i = 5; i < 15; i++) {
            Enum.Terrain terrain = Enum.Terrain.values()[i];
            for (int j = 0; j < Enum.Wildlife.values().length; j++) {
                for (int k = j + 1; k < Enum.Wildlife.values().length; k++) {
                    combohabitatTiles.add(new Tile(terrain, Enum.Wildlife.values()[j], Enum.Wildlife.values()[k], false));
                }
            }
        }

        // Creates tiles with 3 Wildlife types
        for (int i = 5; i < 15; i++) {
            Enum.Terrain terrain = Enum.Terrain.values()[i];
            for (int j = 0; j < Enum.Wildlife.values().length; j++) {
                for (int k = j + 1; k < Enum.Wildlife.values().length; k++) {
                    for (int l = k + 1; l < Enum.Wildlife.values().length; l++) {
                        combohabitatTiles.add(new Tile(terrain,Enum.Wildlife.values()[j], Enum.Wildlife.values()[k], Enum.Wildlife.values()[l],false));
                    }
                }
            }
        }
        Collections.shuffle(combohabitatTiles);

        List<Tile> subList= combohabitatTiles.subList(0, 60); //60 random tiles are added to the sublist
        temphabitatTiles.addAll(subList); //tiles in the sublist added to the main arrayList of habitat tiles
    }

    //Function to generate random number to be used as an index when picking a habitat tile or wildlife token randomly.
    public int randomIndex(int upperLimit){
        Random rand = new Random();
        return rand.nextInt(upperLimit);
    }


    public void initWildlifeTokens(){
        //Populating the 'bag'
        for(int i=0; i<100; i++){
            if(i<20){
                this.wildlifeTokens.add(i, Enum.Wildlife.ELK);
            }
            if(i>=20 && i<40){
                this.wildlifeTokens.add(i, Enum.Wildlife.BEAR);
            }
            if(i>=40 && i<60){
                this.wildlifeTokens.add(i, Enum.Wildlife.HAWK);
            }
            if(i>=60 && i<80){
                this.wildlifeTokens.add(i, Enum.Wildlife.SALMON);
            }
            if(i>=80){
                this.wildlifeTokens.add(i, Enum.Wildlife.FOX);
            }
        }
        //Shaking the 'bag'
        Collections.shuffle(wildlifeTokens);
    }


    //prints Tile with its associative token (set withToken to true if you want to print the assoc token)
    public void displayHabitatTilesAndToken(Tile tile, boolean withToken) {
        //print tiles
        System.out.println();
        String [][] tile2 = createHabitatTile(tile, false);
        for(int j=0; j<4; j++){
            System.out.print(tile2[j][0]);
            if(withToken){
                if(j==2)
                {

                    System.out.print("\t"+getColoredWildlifeString(tile.getAssocToken())+"\n");
                }
                else
                {
                    System.out.println();
                }
            }
            else{
                System.out.println();
            }
        }

    }


    //displays different possible rotations of the tile
    public String [][] displayRotation(Tile tile){

        Scanner scanner= new Scanner(System.in);
        String [][] t1 = tile.createHabitatTile(tile, false);
        String [][] t2 = rotateTile(tile, 1);
        String [][] t3 = rotateTile(tile, 2);
        String [][] t4 = rotateTile(tile, 3);

        System.out.println("\n\nHere are 4 possible rotations of the Tile you selected.");
        System.out.println("\n\n1. Original\n");

        for(int i=0; i<4; i++){
            System.out.println(t1[i][0]);
        }

        System.out.println("\n\n2. Rotated 90 degrees\n");
        for(int i=0; i<4; i++){
            System.out.println(t2[i][0]);
        }

        System.out.println("\n\n3. Rotated 180 degrees\n");
        for(int i=0; i<4; i++){
            System.out.println(t3[i][0]);
        }

        System.out.println("\n\n4. Rotated 270 degrees\n");
        for(int i=0; i<4; i++){
            System.out.println(t4[i][0]);
        }
        int choice=0;
        while (true) {
            try {
                System.out.println("Pick a number (1-4) to rotate the tile you placed on the board");
                choice = scanner.nextInt();

                if (choice >= 1 && choice <= 4) {
                    break;
                } else {
                    System.out.println("\nInvalid input. Please enter a number between 1-4.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a valid number between 1-4.\n");
                scanner.next();
            }
        }
        // Return the corresponding rotated tile
        switch (choice) {
            case 1:
                return t1;
            case 2:
                return t2;
            case 3:
                return t3;
            case 4:
                return t4;
            default:
                throw new IllegalArgumentException("Invalid choice: " + choice);
        }
    }

    public String[][] rotateTile(Tile t, int numRotations){
        String [][] tile = new String[4][1];
        if(t.getTerrain() == Enum.Terrain.RIVER || t.getTerrain() == Enum.Terrain.FOREST || t.getTerrain() == Enum.Terrain.MOUNTAIN || t.getTerrain() == Enum.Terrain.PRAIRIE || t.getTerrain() == Enum.Terrain.WETLAND)
            return t.createHabitatTile(t, false);

        String w1 = t.wildlifeTokenLetter(t.getWildlife1());
        String w2 = t.wildlifeTokenLetter(t.getWildlife2());
        String w3 = t.wildlifeTokenLetter(t.getWildlife3());

        String [] colours = t.tileColour(t.getTerrain());
        String c1 = "", c2 = "";

        //1 and 3 involve changing orientation of colours on tile
        if(numRotations == 1){
            c1 = colours[1];
            c2 = colours[0];
            tile = t.colourPattern(c1, c2, w1, w2, w3, true);
        }
        if(numRotations == 3){
            c1 = colours[0];
            c2 = colours[1];
            tile = t.colourPattern(c1, c2, w1, w2, w3, true);
        }
        if(numRotations == 2){
            c1 = colours[1];
            c2 = colours[0];
            tile = t.colourPattern(c1, c2, w1, w2, w3, false);
        }
        return tile;
    }

    //returns string representation of wildlife token
    public static String getColoredWildlifeString(Enum.Wildlife animal) {
        String colorCode = animal.getColor();
        String type = animal.getType();
        return colorCode + type + "\u001B[0m";
    }

}

