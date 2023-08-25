/* Group number: 34

   Group members:
                Sanat Dusad (GitHub ID: 102313893)
                Ana Radojicic (GitHub ID: 102313888)
                Reya George (GitHub ID: 72013503)

*/
import java.util.ArrayList;
public class Bot extends Player{
    public static final String[] BOT_NAMES = {"Champion", "AI", "Cyber", "CascadiaWiz", "Winner", "Titan", "Alpha"};
  
    public Bot(String name) {
        super(name);
    }

    //Stores which tile/token pair is chosen, where it is placed and what this combination scores.
    public class Variation{
        public Tile tile;
        public int score;
        public int xHabitat;
        public int yHabitat;
        public int xToken;
        public int yToken;
        public int rowChoice;
    }

    public void playingTheChoice(Variation choice, Player bot){
        System.out.println(bot.getName() + " has chosen this pair: ");
        choice.tile.displayHabitatTilesAndToken(choice.tile, true);
        placeOption(choice, bot);
    }

    public Variation pickingThePair(ArrayList<Tile> fourTiles, Player bot){
        Variation finalOne = new Variation();

        int max = 0;

        //calculating best option for each tile
        for(int i=0; i<4; i++){
            Tile easy = fourTiles.get(i);
            Variation current = bestTilePlacement(easy, bot.getBoard().getStorage(), bot, i);
            //setting the final choice to the highest scoring option
            if(current.score > max){
                max = current.score;
                finalOne = current;
            }
        }

        //if the scores are all the same, pick randomly.
        if(max == 100){
            finalOne = randomChoice(bot.getBoard().storage, fourTiles);
        }

        return finalOne;
    }
    private void placeOption(Variation choice, Player bot){
        bot.getBoard().addTileToBoard(choice.xHabitat, choice.yHabitat, choice.tile);
        if(bot.getBoard().getStorage()[choice.xToken][choice.yToken] != null) {
            if (bot.getBoard().getStorage()[choice.xToken][choice.yToken].isKeystone) bot.natureTokens++;
        }
        bot.getBoard().addWildlifeToken(choice.xToken, choice.yToken, bot.getBoard().getStorage()[choice.xToken][choice.yToken], choice.tile.getAssocToken());
    }

    private Variation randomChoice(Tile[][] t, ArrayList<Tile> fourTiles){
        Variation choice = new Variation();
        Tile func = new Tile();

        //choosing random tile to play
        choice.tile = fourTiles.get(func.randomIndex(4));

        //creating list of indices for tile and token placements
        ArrayList<int[]> tiles = validTilePlacements(t);
        if(tiles.size()<1){
            throw new IllegalArgumentException("No space");
        } else{
            //choosing random index to place habitat
            int index2 = func.randomIndex(tiles.size());
            choice.xHabitat = tiles.get(index2)[0];
            choice.yHabitat = tiles.get(index2)[1];
        }

        ArrayList<int[]> token = validTokenPlacements(t, choice.tile.getAssocToken());
        if(token.size()<1){
            System.out.println("Cannot place token anywhere. ");
        } else{
            //choosing random index to place token
            int index3 = func.randomIndex(token.size());
            choice.xToken = token.get(index3)[0];
            choice.yToken = token.get(index3)[1];
        }
        return choice;
    }


    //This function takes in 1 of the tile/token pairs at a time and calculates the max. score possible given the current board.
    private Variation bestTilePlacement(Tile t, Tile[][] board, Player p, int row){
        //Stores the highest score + index at which it occurs.
        Variation bestRes = new Variation();

        //next level of abstraction. Finding which indices for habitat tile AND wildlife token will give the highest score for this tile pair:
        bestRes.tile = t;
        bestRes.rowChoice = row;

        //score initialised to 0, will be updated
        bestRes.score = 0;

        //Iterating through board and checking if an index is a valid tile placement, if it is, find the highest possible score by calling bestTokenPlacement
        for(int i=0; i<9; i++){
            for(int j=0; j<10; j++){
                if(isValidTilePlacement(i,j,board)){
                    //Check what the highest score could be:
                    Tile [][] burner = makeBurnerBoard(board);
                    burner[i][j] = t;
                    //Pass this into wildlife token function
                    Variation currRes = bestTokenPlacement(t, i, j, burner, t.getAssocToken(), row);

                    if(currRes.score >= bestRes.score){
                        bestRes.score = currRes.score;
                        bestRes.xHabitat = currRes.xHabitat;
                        bestRes.yHabitat = currRes.yHabitat;
                        bestRes.xToken = currRes.xToken;
                        bestRes.yToken = currRes.yToken;
                    }
                }
            }
        }
        return bestRes;
    }

    /*
    Finding the highest scoring index for the wildlife token for the passed in tile at the passed in index.
        NOTE: the board passed into this function is not the player's board pre-turn, it is the board with an added habitat tile.
     */
    private Variation bestTokenPlacement(Tile t, int x, int y, Tile[][] board, Enum.Wildlife w, int row){
        //Scoring variable so that we can calculate the score for each potential position.
        Scoring score = new Scoring();

        //Stores the highest score + index at which it occurs.
        Variation bestRes = new Variation();

        //these elements do not change through-out the function. The function checks which index for wildlife token will give the highest score for these values.
        bestRes.tile = t;
        bestRes.xHabitat = x;
        bestRes.yHabitat = y;
        bestRes.rowChoice = row;

        //score will be updated during function call
        bestRes.score = 0;

        //Iterating through the board, if the tile is a valid token placement, calculate Score
        for(int i=0; i<9; i++){
            for(int j=0; j<10; j++){
                //Checking if tile is valid token placement:
                if(isValidTokenPlacement(i,j,w,board)){
                    //If it is, check the score for this placement (use burner board to avoid complications from editing original too much)
                    Tile [][] burner = makeBurnerBoard(board);
                    int currScore = 0;
                    if(burner[i][j].isKeystone){
                        currScore++;
                    }
                    placeTokenTest(i,j,w,burner[i][j].getTerrain(), burner);

                    currScore += score.calculateBoardScoreOnly(burner);

                    if(currScore >= bestRes.score){
                        bestRes.score = currScore;
                        bestRes.xToken = i;
                        bestRes.yToken = j;
                    }
                }
            }
        }
        return bestRes;
    }


    //placing token on burner board:
    private void placeTokenTest(int i, int j, Enum.Wildlife w, Enum.Terrain t, Tile [][] board){
        board[i][j] = new Tile(t, w, null, null, false);
    }
    private Tile[][] makeBurnerBoard(Tile [][] board){
        Tile [][] burner = new Tile[10][11];
        for(int i=0; i<9; i++){
            for(int j=0; j<10; j++){
                burner[i][j] = board[i][j];
            }
        }
        return burner;
    }

    //gives list of valid token placements
    private ArrayList<int[]> validTokenPlacements(Tile[][] board, Enum.Wildlife w){
        ArrayList<int[]> validTokenIndices = new ArrayList<>();

        int index = 0;

        for(int i=0; i<9; i++){
            for(int j=0; j<10; j++){
                if(isValidTokenPlacement(i, j, w, board)){
                    int[] valid = new int[2];
                    valid[0] = i;
                    valid[1] = j;
                    validTokenIndices.add(index, valid);
                    index++;
                }
            }
        }
        return validTokenIndices;
    }

    //gives list of valid tile placements
    private ArrayList<int[]> validTilePlacements(Tile[][] board){
        ArrayList<int[]> validTileIndices = new ArrayList<>();

        int index = 0;

        for(int i=0; i<9; i++){
            for(int j=0; j<10; j++){
                if(isValidTilePlacement(i, j, board)){
                    int[] valid = new int[2];
                    valid[0] = i;
                    valid[1] = j;
                    validTileIndices.add(index, valid);
                    index++;
                }
            }
        }
        return validTileIndices;
    }

    public static boolean isValidTokenPlacement(int i, int j, Enum.Wildlife w, Tile [][] board){
        if(board[i][j] == null){
            return false;
        }
        else{
            return board[i][j].getWildlife1() == w || board[i][j].getWildlife2() == w || board[i][j].getWildlife3() == w;
        }
    }

    public static boolean isValidTilePlacement(int i, int j, Tile[][] board){
        if (i == 0 && j == 0) {
            return (board[i][j] == null) && ((board[i][j+1] != null) || (board[i+1][j] != null));
        }
        if (i == 9 && j == 10) {
            return (board[i][j] == null) && ((board[i-1][j] != null) || (board[i][j-1] != null));
        }

        if (i == 0 && j > 0 && j < 10) {
            return (board[i][j] == null) && ((board[i][j+1] != null) || (board[i+1][j] != null) || (board[i][j-1] != null));
        }
        if (i == 9 && j > 0 && j < 10) {
            return (board[i][j] == null) && ((board[i-1][j] != null) || (board[i][j+1] != null) || (board[i][j-1] != null));
        }

        if (i > 0 && i < 9 && j == 0) {
            return (board[i][j] == null) && ((board[i-1][j] != null) || (board[i][j+1] != null) || (board[i+1][j] != null));
        }
        if (i > 0 && i < 9 && j == 10) {
            return (board[i][j] == null) && ((board[i-1][j] != null) || (board[i+1][j] != null) || (board[i][j-1] != null));
        }

        if (i == 0 && j == 10) {
            return (board[i][j] == null) && ((board[i+1][j] != null) || (board[i][j-1] != null));
        }
        if (i == 9 && j == 0) {
            return (board[i][j] == null) && ((board[i-1][j] != null) || (board[i][j+1] != null));
        }

        return (board[i][j] == null) && ((board[i-1][j] != null) || (board[i][j+1] != null) || (board[i+1][j] != null) || (board[i][j-1] != null));
    }



}
