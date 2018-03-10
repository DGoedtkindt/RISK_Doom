package appearance;

import greenfoot.GreenfootImage;
import java.awt.FontMetrics;

/**
 * Contains constant values.
 * 
 */
public class Appearance {
    //Taille du monde
    public static final int WORLD_WIDTH = 1920;
    public static final int WORLD_HEIGHT = 1080;
    
    //Pour la grille d'hexagones
    public static final int COLLUMN_NUMBER = 37;
    public static final int ROW_NUMBER = 19;
    
    //Transparence et opacité des acteurs
    public static final int TRANSPARENT = 30;
    public static final int OPAQUE = 255;
    
    //positions hexagonales pour dessiner le vide contenant l'affichage 
    //des bonus des continents
    public static final int CONTINENT_BONUS_ZONE_WIDTH = 12;
    public static final int CONTINENT_BONUS_ZONE_HEIGHT = 4;
    public static final int CONTINENT_BONUS_X_POSITION = COLLUMN_NUMBER / 2 - CONTINENT_BONUS_ZONE_WIDTH / 2;
    public static final int CONTINENT_BONUS_Y_POSITION = ROW_NUMBER - CONTINENT_BONUS_ZONE_HEIGHT;
    
    //Font
    public static final java.awt.Font AWT_FONT = new java.awt.Font("Monospaced", java.awt.Font.BOLD, 25);
    public static final greenfoot.Font GREENFOOT_FONT = new greenfoot.Font("Monospaced", true, false, 25);
    
    /**
     * Adds lines to a String written in the standard GREENFOOT_FONT until it 
     * fits in a given width when written on a GreenfootImage.
     * @param toWrap The text to modify.
     * @param maxWidth The maximum width in which the text should fit.
     * @return A modified text that fits in the given width.
     */
    public static String standardTextWrapping(String toWrap, int maxWidth){
        
        GreenfootImage testImage = new GreenfootImage(1, 1);
        
        FontMetrics fm = testImage.getAwtImage().getGraphics().getFontMetrics(AWT_FONT);
        
        String[] words = toWrap.split(" ");
        String finalString = "";
        
        String currentLine = "";
        
        for(String currentWord : words){
            
            int backslashNAt = currentWord.indexOf("\n");
            
            if(backslashNAt == 0){
                finalString += currentLine + "\n";
                currentLine = currentWord.substring(1, currentWord.length());
            }else {
                
                if(fm.stringWidth(currentLine + " " + currentWord) > maxWidth){
                    finalString += currentLine + "\n";
                    currentLine = "";
                
                }
                
                if(!currentLine.isEmpty()){
                    currentLine += " ";
                }

                currentLine += currentWord;
                
            }
            
        }
        
        finalString += currentLine;
        
        return finalString;
        
    }
    
}
