/* Group number: 34

   Group members:
                Sanat Dusad (GitHub ID: 102313893)
                Ana Radojicic (GitHub ID: 102313888)
                Reya George (GitHub ID: 72013503)

*/

//Main class to run the game
public class Game {

    public static void main(String args[]){
        Scoring score = new Scoring();
    
        System.out.println("*****************************************************************\n");
        System.out.println(Colors.ORANGE+"             WELCOME TO CASCADIA "+Colors.RESET);
        System.out.println(Colors.ORANGE+"                            Implemented by Group-34"+Colors.RESET);
        System.out.println(Colors.BLUE+"\n\nHere are the 5 Wildlife Scoring cards for this game!"+Colors.RESET);
        score.displayScoringRules();
        System.out.println("\n\n*****************************************************************\n\n");
        Cascadia game = new Cascadia(); 
        game.startGame();
    }

}
