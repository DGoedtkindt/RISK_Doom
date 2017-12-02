package appearance;

import java.util.ArrayList;
import base.*;

public class Theme {
    
    protected static ArrayList<Theme> themeList = new ArrayList<Theme>();
    
    public Theme(String themeName, GColor bhC, GColor bhBC, GColor bC, GColor tC, GColor terrC, GColor sC){
        
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
        
        themeList.add(this);
        
    }
    
    public String name;
    public GColor blankHexColor;
    public GColor blankHexBorderColor;
    public GColor backgroundColor;
    public GColor textColor;
    public GColor territoryColor;
    public GColor selectionColor;
    
    //Real theme
    static public final Theme BASIC_DARK = new Theme("Basic Dark", 
                                                     new GColor(0, 0, 0),
                                                     new GColor(255, 0, 0),
                                                     new GColor(40, 6, 40),
                                                     new GColor(255, 255, 255),
                                                     new GColor(150, 150, 150),
                                                     new GColor(0, 220, 0));
    
    static public final Theme DARIO = new Theme("Basic Dark", 
                                                     new GColor(110,80,110),
                                                     new GColor(55, 40, 55),
                                                     new GColor(55, 40, 55),
                                                     new GColor(255, 255, 255),
                                                     new GColor(150, 150, 150),
                                                     new GColor(0, 220, 0));
    //Example
    static public final Theme HORRIBLE = new Theme("Horrible", 
                                                   new GColor(100, 100, 100),
                                                   new GColor(0, 0, 0),
                                                   new GColor(150, 30, 80),
                                                   new GColor(200, 200, 100),
                                                   new GColor(90, 90, 90),
                                                   new GColor(0, 220, 0));
    
    
    public static Theme used = DARIO;
    
}
