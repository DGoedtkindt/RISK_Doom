package appearance;

//The code below is Free to edit by the user.

import base.GColor;

/**
 * The different themes that defines the colors of different Elements on the scene.
 * 
 */
public enum Theme {
    DARK ("DARKNESS", 
         new GColor(0, 0, 0),
         new GColor(255, 0, 0),
         new GColor(40, 6, 40),
         new GColor(255, 255, 255),
         new GColor(150, 150, 150),
         new GColor(0, 220, 0)),
    
    WINE ("WINE SPOT", 
         new GColor(110,80,110),
         new GColor(55, 40, 55),
         new GColor(55, 40, 55),
         new GColor(255, 255, 255),
         new GColor(150, 150, 150),
         new GColor(0, 220, 0)),
    
    UNIWHITE ("MINIMALIST WHITE", 
         new GColor(220, 220, 210),
         new GColor(212, 212, 210),
         new GColor(220, 220, 210),
         new GColor(20, 20, 20),
         new GColor(200, 200, 200),
         new GColor(200, 90, 90)),
    
    UNIDARK ("MINIMALIST BLACK",
         new GColor(10,10,10),
         new GColor(17,17,17),
         new GColor(10,10,10),
         new GColor(255,255,255),
         new GColor(200,200,200),
         new GColor(200,20,20)),;
    
    public static Theme used = WINE;
    
    //From here the code should not be touched by the user.
    
    public String name;
    public GColor blankHexColor;
    public GColor blankHexBorderColor;
    public GColor backgroundColor;
    public GColor textColor;
    public GColor territoryColor;
    public GColor selectionColor;
    
    /**
     * Creates a Theme.
     * @param themeName The name of this Theme.
     * @param bhC The color of the Blank Hexes.
     * @param bhBC The color of the border of the Blank Hexes.
     * @param bC The color of the background.
     * @param tC The color of the text.
     * @param terrC The color of the Territories whithout Continent.
     * @param sC The color of the selected Selectables.
     */
    Theme(String themeName, GColor bhC, GColor bhBC, GColor bC, GColor tC, GColor terrC, GColor sC){
        
        if(themeName.equals("")){
            name = "A theme has no name";
        }else{
            name = themeName;
        }
        
        blankHexColor = bhC;
        blankHexBorderColor = bhBC;
        backgroundColor = bC;
        textColor = tC;
        territoryColor = terrC;
        selectionColor = sC;
        
    }
    
}
