/* Group number: 34

   Group members:
                Sanat Dusad (GitHub ID: 102313893)
                Ana Radojicic (GitHub ID: 102313888)
                Reya George (GitHub ID: 72013503)

*/
import java.util.ArrayList;
import java.util.Collections;

//starter tiles are made up of 3 tiles
public class StarterTile{
    private Tile tile1;
    private Tile tile2;
    private Tile tile3;

    public Tile getTile1() {
        return tile1;
    }
    public Tile getTile2() {
        return tile2;
    }

    public Tile getTile3() {
        return tile3;
    }

    public StarterTile(Tile tile1, Tile tile2, Tile tile3) {
        this.tile1 = tile1;
        this.tile2 = tile2;
        this.tile3 = tile3;
    }

    public StarterTile(){

    }

    //arrayList to store starter tiles
    ArrayList<StarterTile> starterTiles= new ArrayList<StarterTile>();

    public ArrayList<StarterTile> getStarterTiles() {
        return starterTiles;
    }


    //method to initialise starter tiles
    public void initStarterTile(){
        Tile t1 = new Tile(Enum.Terrain.WETLAND, Enum.Wildlife.HAWK, true);
        Tile t2 = new Tile(Enum.Terrain.PRAIRIE_MOUNTAIN, Enum.Wildlife.BEAR, Enum.Wildlife.FOX, null, true);
        Tile t3 = new Tile(Enum.Terrain.FOREST_RIVER, Enum.Wildlife.SALMON, Enum.Wildlife.ELK, Enum.Wildlife.HAWK, true);

        Tile t4 = new Tile(Enum.Terrain.MOUNTAIN, Enum.Wildlife.BEAR,true);
        Tile t5 = new Tile(Enum.Terrain.PRAIRIE_RIVER, Enum.Wildlife.BEAR, Enum.Wildlife.SALMON, null, true);
        Tile t6 = new Tile(Enum.Terrain.FOREST_WETLAND, Enum.Wildlife.HAWK, Enum.Wildlife.ELK, Enum.Wildlife.FOX, true);

        Tile t7 = new Tile(Enum.Terrain.FOREST, Enum.Wildlife.ELK, true);
        Tile t8 = new Tile(Enum.Terrain.PRAIRIE_WETLAND, Enum.Wildlife.FOX, Enum.Wildlife.SALMON, true);
        Tile t9 = new Tile(Enum.Terrain.RIVER_MOUNTAIN, Enum.Wildlife.BEAR, Enum.Wildlife.HAWK, Enum.Wildlife.ELK, true);

        Tile t10 = new Tile(Enum.Terrain.RIVER,Enum.Wildlife.SALMON, true);
        Tile t11 = new Tile(Enum.Terrain.WETLAND_MOUNTAIN, Enum.Wildlife.FOX, Enum.Wildlife.HAWK, true);
        Tile t12 = new Tile(Enum.Terrain.FOREST_PRAIRIE, Enum.Wildlife.BEAR, Enum.Wildlife.SALMON, Enum.Wildlife.ELK, true);

        Tile t13 = new Tile(Enum.Terrain.PRAIRIE,Enum.Wildlife.FOX, true);
        Tile t14 = new Tile(Enum.Terrain.FOREST_MOUNTAIN, Enum.Wildlife.BEAR, Enum.Wildlife.ELK, true);
        Tile t15 = new Tile(Enum.Terrain.WETLAND_RIVER, Enum.Wildlife.FOX, Enum.Wildlife.SALMON, Enum.Wildlife.HAWK, true);


        starterTiles.add(new StarterTile(t1, t2, t3));
        starterTiles.add(new StarterTile(t4, t5, t6));
        starterTiles.add(new StarterTile(t7, t8, t9));
        starterTiles.add(new StarterTile(t10, t11, t12));
        starterTiles.add(new StarterTile(t13, t14, t15));

        Collections.shuffle(starterTiles);
    }

}

