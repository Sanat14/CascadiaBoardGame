/* Group number: 34

   Group members:
                Sanat Dusad (GitHub ID: 102313893)
                Ana Radojicic (GitHub ID: 102313888)
                Reya George (GitHub ID: 72013503)

*/
import java.util.ArrayList;

public class Scoring {
    //static ints used when indicating which neighbouring tile you are checking in function checkNeighbour
    public static final int UP = 1;
    public static final int RIGHT = 2;
    public static final int DOWN = 3;
    public static final int LEFT = 4;

    //for naming clarity when calling checkDiagonalNeighbour
    public static final int UP_LEFT = 1;
    public static final int UP_RIGHT = 2;
    public static final int DOWN_RIGHT = 3;
    public static final int DOWN_LEFT = 4;

    public void displayScoringRules(){
        
        System.out.println(Colors.ORANGE+"\n\nHow you can score for each wildlife token: "+Colors.RESET);

        //displaying rules for each wildlife
        bearScoring();
        elkScoring();
        salmonScoring();
        hawkScoring();
        foxScoring();
    }

    //Unsure should parameter be changed to bot or left as player (doesn't make much of a difference)
    public int calculateScore(Player bot){
        int score = calculateBoardScoreOnly(bot.getBoard().getStorage()) + bot.getNatureTokens();
        return score;
    }

    public int calculateBoardScoreOnly(Tile [][] board){
        int score = calculateWildlife(board) + calculateHabitat(board);
        return score;
    }

    //displays final score for each individual wildlife card
    public void displayIndividualScoreComponents(Player player){
        Tile [][] t = player.getBoard().getStorage();
        System.out.println("Score for Wildlife: " + calculateWildlife(t));
        System.out.println("Score for Habitat: " + calculateHabitat(t));
        System.out.println("Nature token bonus! " + player.getNatureTokens());
        System.out.println("Total Score: " + calculateScore(player));
    }
    public int calculateWildlife(Tile[][] board){
        int score = 0;
        score += calculateBear(board);
        score += calculateElk(board);
        score += calculateSalmon(board);
        score += calculateHawk(board);
        score += calculateFox(board);
        return score;
    }

    //calculating points scored from terrain types
    public int calculateHabitat(Tile[][] t){
        int score = 0;
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 10; j++){
                if(t[i][j] != null){
                    if(t[i][j].getHabitat(Enum.Terrain.FOREST, Enum.Wildlife.BEAR) == 1){
                        score += 1;
                    }
                    if(t[i][j].getHabitat(Enum.Terrain.FOREST, Enum.Wildlife.ELK) == 1){
                        score += 1;
                    }
                    if(t[i][j].getHabitat(Enum.Terrain.FOREST, Enum.Wildlife.FOX) == 1){
                        score += 1;
                    }
                    if(t[i][j].getHabitat(Enum.Terrain.FOREST, Enum.Wildlife.HAWK) == 1){
                        score += 1;
                    }
                    if(t[i][j].getHabitat(Enum.Terrain.FOREST, Enum.Wildlife.SALMON) == 1){
                        score += 1;
                    }
                    if(t[i][j].getHabitat(Enum.Terrain.MOUNTAIN, Enum.Wildlife.BEAR) == 1){
                        score += 1;
                    }
                    if(t[i][j].getHabitat(Enum.Terrain.MOUNTAIN, Enum.Wildlife.ELK) == 1){
                        score += 1;
                    }
                    if(t[i][j].getHabitat(Enum.Terrain.MOUNTAIN, Enum.Wildlife.FOX) == 1){
                        score += 1;
                    }
                    if(t[i][j].getHabitat(Enum.Terrain.MOUNTAIN, Enum.Wildlife.HAWK) == 1){
                        score += 1;
                    }
                    if(t[i][j].getHabitat(Enum.Terrain.MOUNTAIN, Enum.Wildlife.SALMON) == 1){
                        score += 1;
                    }
                    if(t[i][j].getHabitat(Enum.Terrain.RIVER, Enum.Wildlife.BEAR) == 1){
                        score += 1;
                    }
                    if(t[i][j].getHabitat(Enum.Terrain.RIVER, Enum.Wildlife.ELK) == 1){
                        score += 1;
                    }
                    if(t[i][j].getHabitat(Enum.Terrain.RIVER, Enum.Wildlife.FOX) == 1){
                        score += 1;
                    }
                    if(t[i][j].getHabitat(Enum.Terrain.RIVER, Enum.Wildlife.HAWK) == 1){
                        score += 1;
                    }
                    if(t[i][j].getHabitat(Enum.Terrain.RIVER, Enum.Wildlife.SALMON) == 1){
                        score += 1;
                    }
                    if(t[i][j].getHabitat(Enum.Terrain.WETLAND, Enum.Wildlife.BEAR) == 1){
                        score += 1;
                    }
                    if(t[i][j].getHabitat(Enum.Terrain.WETLAND, Enum.Wildlife.ELK) == 1){
                        score += 1;
                    }
                    if(t[i][j].getHabitat(Enum.Terrain.WETLAND, Enum.Wildlife.FOX) == 1){
                        score += 1;
                    }
                    if(t[i][j].getHabitat(Enum.Terrain.WETLAND, Enum.Wildlife.HAWK) == 1){
                        score += 1;
                    }
                    if(t[i][j].getHabitat(Enum.Terrain.WETLAND, Enum.Wildlife.SALMON) == 1){
                        score += 1;
                    }
                }
            }
        }
        return score;
    }


    //SCORING HELPER FUNCTIONS:
    //compares wildlife tokens on neighbours directly next to/above/below, returns true if they are the same
    public boolean checkNeighbour(Tile [][] t, int i, int j, int direction) {
        //just i edge case:
        if (i == 0 && j > 0 && j < 10) {
            //up
            if (direction == 1) return false;
            //right
            if (direction == 2) {
                if (t[i][j + 1] == null) return false;
                if (t[i][j].getWildlife1() == t[i][j + 1].getWildlife1()) return true;

            }
            //down
            if (direction == 3) {
                if (t[i + 1][j] == null) return false;
                if (t[i][j].getWildlife1() == t[i + 1][j].getWildlife1()) return true;
            }
            //left
            if (direction == 4) {
                if (t[i][j - 1] == null) return false;
                if (t[i][j].getWildlife1() == t[i][j - 1].getWildlife1()) return true;
            }
        }

        if (i == 9 && j > 0 && j < 10) {
            //up
            if (direction == 1) {
                if (t[i - 1][j] == null) return false;
                if (t[i][j].getWildlife1() == t[i - 1][j].getWildlife1()) return true;
            }
            //right
            if (direction == 2) {
                if (t[i][j + 1] == null) return false;
                if (t[i][j].getWildlife1() == t[i][j + 1].getWildlife1()) return true;
            }
            //down
            if (direction == 3) return false;
            //left
            if (direction == 4) {
                if (t[i][j - 1] == null) return false;
                if (t[i][j].getWildlife1() == t[i][j - 1].getWildlife1()) return true;
            }
        }

        //i and j edge cases:
        if (i == 0 && j == 0) {
            //up
            if (direction == 1) return false;
            //right
            if (direction == 2) {
                if (t[i][j + 1] == null) return false;
                if (t[i][j].getWildlife1() == t[i][j + 1].getWildlife1()) return true;
            }
            //down
            if (direction == 3) {
                if (t[i + 1][j] == null) return false;
                if (t[i][j].getWildlife1() == t[i + 1][j].getWildlife1()) return true;
            }
            //left
            if (direction == 4) return false;
        }

        if (i == 0 && j == 10) {
            //up
            if (direction == 1) return false;
            //right
            if (direction == 2) return false;
            //down
            if (direction == 3) {
                if (t[i + 1][j] == null) return false;
                if (t[i][j].getWildlife1() == t[i + 1][j].getWildlife1()) return true;
            }
            //left
            if (direction == 4) {
                if (t[i][j - 1] == null) return false;
                if (t[i][j].getWildlife1() == t[i][j - 1].getWildlife1()) return true;
            }
        }

        if (i == 9 && j == 0) {
            //up
            if (direction == 1) {
                if (t[i - 1][j] == null) return false;
                if (t[i][j].getWildlife1() == t[i - 1][j].getWildlife1()) return true;
            }
            //right
            if (direction == 2) {
                if (t[i][j + 1] == null) return false;
                if (t[i][j].getWildlife1() == t[i][j + 1].getWildlife1()) return true;
            }
            //down
            if (direction == 3) return false;
            //left
            if (direction == 4) return false;
        }

        if (i == 9 && j == 10) {
            //up
            if (direction == 1) {
                if (t[i - 1][j] == null) return false;
                if (t[i][j].getWildlife1() == t[i - 1][j].getWildlife1()) return true;
            }
            //right
            if (direction == 2) return false;
            //down
            if (direction == 3) return false;
            //left
            if (direction == 4) {
                if (t[i][j - 1] == null) return false;
                if (t[i][j].getWildlife1() == t[i][j - 1].getWildlife1()) return true;
            }
        }

        //just j edge case
        if (j == 0 && i > 0 && i < 9) {
            //up
            if (direction == 1) {
                if (t[i - 1][j] == null) return false;
                if (t[i][j].getWildlife1() == t[i - 1][j].getWildlife1()) return true;
            }
            //right
            if (direction == 2) {
                if (t[i][j + 1] == null) return false;
                if (t[i][j].getWildlife1() == t[i][j + 1].getWildlife1()) return true;
            }
            //down
            if (direction == 3) {
                if (t[i + 1][j] == null) return false;
                if (t[i][j].getWildlife1() == t[i + 1][j].getWildlife1()) return true;
            }
            //left
            if (direction == 4) return false;
        }

        if (j == 10 && i > 0 && i < 9) {
            if (direction == 1) {
                if (t[i - 1][j] == null) return false;
                if (t[i][j].getWildlife1() == t[i - 1][j].getWildlife1()) return true;
            }
            if (direction == 2) return false;

            if (direction == 3) {
                if (t[i + 1][j] == null) return false;
                if (t[i][j].getWildlife1() == t[i + 1][j].getWildlife1()) return true;
            }
            if (direction == 4) {
                if (t[i][j - 1] == null) return false;
                if (t[i][j].getWildlife1() == t[i][j - 1].getWildlife1()) return true;
            }
        }

//normal cases:
        //up
        if (direction == 1) {
            if (t[i - 1][j] == null) return false;
            if (t[i][j].getWildlife1() == t[i - 1][j].getWildlife1()) return true;
        }
        //right
        if (direction == 2) {
            if (t[i][j + 1] == null) return false;
            if (t[i][j].getWildlife1() == t[i][j + 1].getWildlife1()) return true;
        }
        //down
        if (direction == 3) {
            if (t[i + 1][j] == null) return false;
            if (t[i][j].getWildlife1() == t[i + 1][j].getWildlife1()) return true;
        }
        //left
        if (direction == 4) {
            if (t[i][j - 1] == null) return false;
            if (t[i][j].getWildlife1() == t[i][j - 1].getWildlife1()) return true;
        }
        return false;
    }

    //compares wildlife tokens on neighbours touching each CORNER of tile, returns true if they are the same
    public boolean checkDiagonalNeighbour(Tile[][] t, int i, int j, int direction){
        //just i edge case:
        if(i==0 && j>0 && j<10){
            //up-left
            if(direction == 1)return false;
            //up-right
            if(direction == 2)return false;
            //down-right
            if(direction == 3){
                if(t[i+1][j+1] == null) return false;
                if(t[i][j].getWildlife1() == t[i+1][j+1].getWildlife1()) return true;
            }
            //down-left
            if(direction == 4){
                if(t[i+1][j-1] == null) return false;
                if(t[i][j].getWildlife1() == t[i+1][j-1].getWildlife1()) return true;
            }
        }

        if(i==9 && j>0 && j<10){
            //up-left
            if(direction == 1){
                if(t[i-1][j-1] == null) return false;
                if(t[i][j].getWildlife1() == t[i-1][j-1].getWildlife1()) return true;
            }
            //up-right
            if(direction == 2){
                if(t[i-1][j+1] == null) return false;
                if(t[i][j].getWildlife1() == t[i-1][j+1].getWildlife1()) return true;
            }
            //down-right
            if(direction == 3)return false;
            //down-left
            if(direction == 4)return false;
        }

        //i and j edge cases:
        if(i==0 && j==0){
            //up-left
            if(direction == 1) return false;
            //up-right
            if(direction == 2)return false;
            //down-right
            if(direction == 3){
                if(t[i+1][j+1] == null) return false;
                if(t[i][j].getWildlife1() == t[i+1][j+1].getWildlife1()) return true;
            }
            //down-left
            if(direction == 4) return false;
        }

        if(i==0 && j==10){
            //up-left
            if(direction == 1) return false;
            //up-right
            if(direction == 2) return false;
            //down-right
            if(direction == 3) return false;
            //down-left
            if(direction == 4){
                if(t[i+1][j-1] == null) return false;
                if(t[i][j].getWildlife1() == t[i+1][j-1].getWildlife1())return true;
            }
        }

        if(i==9 && j==0){
            //up-left
            if(direction == 1) return false;
            //up-right
            if(direction == 2){
                if(t[i-1][j+1] == null) return false;
                if(t[i][j].getWildlife1() == t[i-1][j+1].getWildlife1())return true;
            }
            //down-right
            if(direction == 3)return false;
            //down-left
            if(direction == 4)return false;
        }

        if(i==9 && j==10){
            //up-left
            if(direction == 1){
                if(t[i-1][j-1] == null)return false;
                if(t[i][j].getWildlife1() == t[i-1][j-1].getWildlife1())return true;
            }
            //up-right
            if(direction == 2)return false;
            //down -right
            if(direction == 3)return false;
            //down-left
            if(direction == 4)return false;
        }

        //just j edge case
        if(j==0 && i>0 && i<9){
            //up-left
            if(direction == 1)return false;
            //up-right
            if(direction == 2){
                if(t[i-1][j+1] == null) return false;
                if(t[i][j].getWildlife1() == t[i-1][j+1].getWildlife1())return true;
            }
            //down-right
            if(direction == 3){
                if(t[i+1][j+1] == null)return false;
                if(t[i][j].getWildlife1() == t[i+1][j+1].getWildlife1()) return true;
            }
            //down-left
            if(direction == 4)return false;
        }

        if(j==10 && i>0 && i<9){
            //up-left
            if(direction == 1){
                if(t[i-1][j-1] == null) return false;
                if(t[i][j].getWildlife1() == t[i-1][j-1].getWildlife1())return true;
            }
            //up-right
            if(direction == 2)return false;
            //down-right
            if(direction == 3)return false;
            //down-left
            if(direction == 4){
                if(t[i+1][j-1] == null)return false;
                if(t[i][j].getWildlife1() == t[i+1][j-1].getWildlife1())return true;
            }
        }

//normal cases:
        //up-left
        if(direction == 1){
            //System.out.println("1");
            if(t[i-1][j-1] == null) return false;
            if(t[i][j].getWildlife1() == t[i-1][j-1].getWildlife1())return true;
        }
        //up-right
        if(direction == 2){
            //System.out.println("2");
            if(t[i-1][j+1] == null) return false;
            if(t[i][j].getWildlife1() == t[i-1][j+1].getWildlife1())return true;
        }
        //down-right
        if(direction == 3){
            //System.out.println("3");
            if(t[i+1][j+1] == null) return false;
            if(t[i][j].getWildlife1() == t[i+1][j+1].getWildlife1())return true;
        }
        //down-left
        if(direction == 4){
            //System.out.println("4");
            if(t[i+1][j-1] == null)return false;
            if(t[i][j].getWildlife1() == t[i+1][j-1].getWildlife1())return true;
        }
        return false;
    }

    //creates list of unique wildlife in neighbouring tiles
    public ArrayList<Enum.Wildlife> uniqueWildlifeList(ArrayList<Tile> neighbours){
        ArrayList<Enum.Wildlife> uniqueAnimals = new ArrayList<>();
        int index = 0;
        for (int k = 0; k < neighbours.size(); k++) {
            if (!uniqueAnimals.contains(neighbours.get(k).getWildlife1())) {
                uniqueAnimals.add(index, neighbours.get(k).getWildlife1());
                index++;
            }
        }
        return uniqueAnimals;
    }

    //creates list of non-null neighbours
    public ArrayList<Tile> getNeighbours(Tile[][] t, int i, int j){
        //create Arraylist of non-null surrounding tiles:
        ArrayList<Tile> neighbours = new ArrayList<>();
        int currIndex = 0;
        //if neighbour is not-null, add to list.
        if (t[i - 1][j] != null) {
            neighbours.add(currIndex, t[i - 1][j]);
            currIndex++;
        }
        if (t[i][j - 1] != null) {
            neighbours.add(currIndex, t[i][j - 1]);
            currIndex++;
        }
        if (t[i][j + 1] != null) {
            neighbours.add(currIndex, t[i][j + 1]);
            currIndex++;
        }
        if (t[i + 1][j] != null) {
            neighbours.add(currIndex, t[i + 1][j]);
        }
        return neighbours;
    }


    /*
    As only scoring card A is being used for the bot, I have edited the Scoring class
    to reduce redundancy and increase readability.

    Only scoring card A for each wildlife is now present.
     */

    //BEARS:
    public void bearScoring() {
        System.out.println(Colors.ORANGE+"\n\nTo score for BEARS:"+ Colors.RESET);
        System.out.println("Score increasing numbers of points based on the total number of pairs of bears!");
        System.out.println("Score 4 points for each pair!\n");
        System.out.println("\n(Note that in order to score, there cannot be neighbouring bears that are not included in the group being scored)");
    }

    public int calculateBear(Tile[][] t){
        int score = 0;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 10; j++) {
                if (t[i][j] != null && t[i][j].getWildlife1() == Enum.Wildlife.BEAR && t[i][j].isFrozen()) {
                    score += bear1(t, i, j);
                }
            }
        }
        return score;
    }
    public int bear1(Tile[][] t, int i, int j) {
        int score = 0;
        //to avoid double counting pairs: only check right neighbour and neighbour below
        if (!checkNeighbour(t, i, j, UP) && checkNeighbour(t, i, j, RIGHT) && !checkNeighbour(t, i, j, DOWN) && !checkNeighbour(t, i, j, LEFT)) {
            if (!checkNeighbour(t, i, j + 1, UP) && !checkNeighbour(t, i, j + 1, RIGHT) && !checkNeighbour(t, i, j + 1, DOWN)) {
                score += 4;
            }
        }
        if (!checkNeighbour(t, i, j, UP) && !checkNeighbour(t, i, j, RIGHT) && checkNeighbour(t, i, j, DOWN) && !checkNeighbour(t, i, j, LEFT)) {
            if (!checkNeighbour(t, i + 1, j, LEFT) && !checkNeighbour(t, i + 1, j, DOWN) && !checkNeighbour(t, i + 1, j, RIGHT)) {
                score += 4;
            }
        }
        return score;
    }

    //ELK:
    public void elkScoring() {
        System.out.println(Colors.ORANGE+"\n\nTo score for ELK:"+Colors.RESET);
        System.out.println("Score for groups in straight lines!");
        System.out.println("\nFor 1 elk score 2 points!\n" +
                "Score an increasing number of points for every other elk in the group!\n");
        System.out.println("Note that groups of elk CAN be beside each-other. However each elk can only be counted as part of 1 group");
    }
    public int calculateElk(Tile[][] t) {
        int score = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 10; j++) {
                if (t[i][j] != null && t[i][j].getWildlife1() == Enum.Wildlife.ELK && t[i][j].isFrozen()) {
                    score += elk1(t, i, j);
                }
            }
        }

        return score;
    }
    public int elk1(Tile[][] t, int i, int j) {
        int score = 0;
        int count = 2;
        //checks how many elk are in linear group, starts check if an elk has a right neighbour but not a left (to avoid double counting)
        if (checkNeighbour(t, i, j, RIGHT) && !checkNeighbour(t, i, j, LEFT)) {
            score += count;
            while (checkNeighbour(t, i, j, RIGHT)) {
                count++;
                score += count;
                j++;
            }
        } else if (!checkNeighbour(t, i, j, RIGHT) && !checkNeighbour(t, i, j, LEFT)) {
            score = 2; //1 elk
        }
        return score;
    }

    //SALMON:
    public void salmonScoring(){
        System.out.println(Colors.ORANGE+"\n\nTo score for SALMON:"+Colors.RESET);
        System.out.println("Salmon score for creating runs of salmon!" +
                "\nA run is defined such that the corners of the tiles are touching. " +
                "A salmon can be adjacent to no more than 2 other salmon in order for it to be considered a run.");
        System.out.println("Score for each run, based on size!");
        System.out.println("\nScore 3 points for every salmon in the run!\n");
    }
    public int calculateSalmon(Tile[][] t){
        int score = 0;
        for(int i=0; i<9; i++){
            for(int j=0; j<10; j++){
                if(t[i][j] != null && t[i][j].getWildlife1() == Enum.Wildlife.SALMON && t[i][j].isFrozen()){
                    score += salmon1(t, i, j);
                }
            }
        }
        return score;
    }
    public int salmon1(Tile[][] t, int i, int j){
        int score = 0;

        int run = 0;

        //to avoid double counting, only start checking if all left neighbours are not salmon
        if(!checkDiagonalNeighbour(t, i,j,DOWN_LEFT) && !checkNeighbour(t,i,j, LEFT) && !checkDiagonalNeighbour(t, i,j, UP_LEFT) && !checkNeighbour(t, i,j,UP)){
            run = salmonHelper(t,i,j);
        }
        score = (3*run);
        return score;
    }

    public int salmonHelper(Tile[][] t, int i, int j){
        if(t[i][j] == null) return 0;

        if(t[i][j].getWildlife1() != Enum.Wildlife.SALMON){
            return 0;
        }

        //method works recursively to find the longest scoring run of salmon starting from a certain index
        //it only checks right neighbours to avoid to double counting
        int up_right = salmonHelper(t, i-1, j+1);
        int right = salmonHelper(t, i, j+1);
        int right_down = salmonHelper(t, i+1, j+1);
        int down = salmonHelper(t, i+1, j);

        return Math.max(Math.max(up_right, right), Math.max(right_down, down)) +1;
    }

    //FOXES:
    public void foxScoring(){
        System.out.println(Colors.ORANGE+"\nTo score for Fox:"+Colors.RESET);
        System.out.println("Foxes score for being adjacent to other animals! Depending on whether conditions are met in any of the\n" +
                "adjacent habitat spaces.");
        System.out.println("For each fox, score 1 point for every unique wildlife directly adjacent to it! (This includes other foxes)");
    }
    public int calculateFox(Tile[][] t){
        int score = 0;
        for(int i=0; i<9; i++){
            for(int j=0; j<10; j++){
                if(t[i][j] != null && t[i][j].getWildlife1() == Enum.Wildlife.FOX && t[i][j].isFrozen()){
                    score += fox1(t, i, j);
                }
            }
        }
        return score;
    }
    public int fox1(Tile[][] t, int i, int j){
        int score = 0;
        //create Arraylist of non-null surrounding tiles:
        ArrayList<Tile> neighbours = getNeighbours(t,i,j);

        //arraylist is populated with non-null entries:
        if (neighbours.size() == 1) score = 1;
        else {
            score = uniqueWildlifeList(neighbours).size();
        }
        return score;
    }


    //HAWKS:
    public void hawkScoring() {
        System.out.println(Colors.ORANGE+"\n\nTo score for HAWKS:"+Colors.RESET);
        System.out.println("Hawks score for spreading out over the landscape. Hawks can score for either each hawk, each pair of hawks,\n" +
                "or for lines of sight between hawks. A line of sight is a straight line from flat side to flat side of the hexagons, as\n" +
                "pictured. A line of sight is only interrupted by the presence of another hawk ");

        System.out.println("\nScore points for each hawk that is not adjacent to another hawk!");
        System.out.println("\nScore 2 points for each hawk!");
    }
    public int calculateHawk(Tile [][] t){
        int score = 0;
        for(int i=0; i<9; i++){
            for(int j=0; j<10; j++){
                if(t[i][j] != null && t[i][j].getWildlife1() == Enum.Wildlife.HAWK && t[i][j].isFrozen()){
                    score += hawk1(t, i, j);
                }
            }
        }
        return score;
    }
    public int hawk1(Tile[][] t, int i, int j){
        int score =0;
        //checking hawk has no adjacent hawk neighbours
        if(!checkNeighbour(t, i,j, LEFT) && !checkNeighbour(t, i,j,UP) && !checkNeighbour(t, i,j,RIGHT) && !checkNeighbour(t, i,j,DOWN) && !checkDiagonalNeighbour(t, i,j, UP_LEFT) && !checkDiagonalNeighbour(t, i, j, UP_RIGHT) && !checkDiagonalNeighbour(t, i, j, DOWN_RIGHT) && !checkDiagonalNeighbour(t, i, j, DOWN_LEFT)){
            score = 2;
        }
        return score;
    }

}