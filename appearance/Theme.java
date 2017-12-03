package appearance;

import base.*;

public enum Theme {
    DARK ("DARK", 
         new GColor(0, 0, 0),
         new GColor(255, 0, 0),
         new GColor(40, 6, 40),
         new GColor(255, 255, 255),
         new GColor(150, 150, 150),
         new GColor(0, 220, 0)),
    
    WINE ("WINE", 
         new GColor(110,80,110),
         new GColor(55, 40, 55),
         new GColor(55, 40, 55),
         new GColor(255, 255, 255),
         new GColor(150, 150, 150),
         new GColor(0, 220, 0)),
    
    PINK ("PINK", 
         new GColor(100, 100, 100),
         new GColor(0, 0, 0),
         new GColor(150, 30, 80),
         new GColor(200, 200, 100),
         new GColor(90, 90, 90),
         new GColor(0, 220, 0));
    
    public String name;
    public GColor blankHexColor;
    public GColor blankHexBorderColor;
    public GColor backgroundColor;
    public GColor textColor;
    public GColor territoryColor;
    public GColor selectionColor;
    
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
    
    public static Theme used = WINE;
    
}
