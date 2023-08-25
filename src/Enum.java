/* Group number: 34

   Group members:
                Sanat Dusad (GitHub ID: 102313893)
                Ana Radojicic (GitHub ID: 102313888)
                Reya George (GitHub ID: 72013503)

*/
public class Enum {
    
    //enum for Terrain types
    public static enum Terrain{
        FOREST("Forest", Colors.DARK_GREEN_BACKGROUND), // dark green
        PRAIRIE("Prairie", Colors.YELLOW_BACKGROUND), // yellow
        WETLAND("Wetland", Colors.LIGHT_GREEN_BACKGROUND), // light green
        MOUNTAIN("Mountain", Colors.GREY_BACKGROUND), // grey
        RIVER("River", Colors.BLUE_BACKGROUND), ///blue
        FOREST_PRAIRIE("Forest_Prairie",null),
        FOREST_WETLAND("Forest_Wetland",null),
        FOREST_MOUNTAIN("Forest_Mountain",null),
        FOREST_RIVER("Forest_River",null),
        PRAIRIE_WETLAND("Prairie_Wetland",null),
        PRAIRIE_MOUNTAIN("Prairie_Mountain",null),
        PRAIRIE_RIVER("Prairie_River",null),
        WETLAND_MOUNTAIN("Wetland_Mountain",null),
        WETLAND_RIVER("Wetland_River",null),
        RIVER_MOUNTAIN("River_Mountain",null);

        private String TerrainType;
        private final String color;

        public String getColor() {
            return color;
        }

        Terrain(String TerrainType, String color) {
            this.TerrainType= TerrainType;
            this.color = color;
        }


        public String getTerrainType() {
            return TerrainType;
        }

    }


    //enum for wildlife types
    public static enum Wildlife {
        SALMON("S", "\u001B[31m"), // red
        BEAR("B", "\u001B[0;33;48;5;52m"), // brown
        ELK("E", "\u001B[30m"), // black
        HAWK("H", "\u001B[34m"), // blue
        FOX("F", "\u001B[38;5;208m"); // orange

        private String type;

        //color of the wildlife token
        private String color;

        Wildlife(String type, String color) {
            this.type = type;
            this.color = color;
        }

        public String getType() {
            return type;
        }

        public String getColor() {
            return color;
        }
    }

}
