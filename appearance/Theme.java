package appearance;

import base.*;

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
