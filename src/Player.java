/* Group number: 34

   Group members:
                Sanat Dusad (GitHub ID: 102313893)
                Ana Radojicic (GitHub ID: 102313888)
                Reya George (GitHub ID: 72013503)

*/
import java.util.ArrayList;
import java.util.Random;

public class Player {

    private String name;
    private Board board; //each player object has their own board
    public int natureTokens; 

    //getters
    public String getName() {
        return name;
    }

    public Board getBoard() {
        return board;
    }
    public int getNatureTokens(){
        return natureTokens;
    }

    //constructor
    public Player(String name) {
        this.name = name;
        natureTokens = 0;
    }

    //adds a starterTile to each player's board
    public void createBoard(ArrayList<StarterTile> starterTiles) {
        StarterTile selectedStarterTile = getRandomStarterTile(starterTiles);
        this.board = new Board(selectedStarterTile);
    }


    //fetches a random starterTile and removes it once used
    private StarterTile getRandomStarterTile(ArrayList<StarterTile> starterTiles) {

        if (starterTiles.isEmpty()) {
            return null;
        }
        int randomIndex = new Random().nextInt(starterTiles.size());
        return starterTiles.remove(randomIndex);
    }

}
