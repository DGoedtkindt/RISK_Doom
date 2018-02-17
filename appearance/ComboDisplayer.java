package appearance;

import base.Button;
import base.NButton;
import greenfoot.GreenfootImage;
import java.awt.FontMetrics;
import mainObjects.Combo;
import mainObjects.Player;
import mode.Mode;

public class ComboDisplayer extends Button{
    
    private static ComboDisplayer current = new ComboDisplayer(new Combo());
    private static NButton useCombos = new NButton(() -> {current.combo.use();}, "Use combo");
    
    private boolean shown = false;
    
    private Combo combo = null;
    
    private ComboDisplayer(Combo c){
        combo = c;
        createHiddenImage();
        
    }
    
    private void createComboImage(){
        GreenfootImage img = new GreenfootImage(1, 1);
        img.setFont(new greenfoot.Font("monospace", true, false, 17));
        FontMetrics fm = img.getAwtImage().getGraphics().getFontMetrics(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 17));
        
        int width = 180;
        int bandHeight = fm.getMaxAscent() + fm.getMaxDescent();
        int bandSeparation = 8;
        int side = bandHeight;
        int squareSeparation = 4;
        int squareLeftDistance = 13;
        
        img.scale(width, bandHeight * 3 + bandSeparation * 4);
        img.setColor(Theme.used.backgroundColor.darker());
        img.fill();
        img.setColor(Theme.used.textColor);
        img.drawString("A", 0,     bandHeight     + bandSeparation);
        img.drawString("B", 0, 2 * bandHeight + 2 * bandSeparation);
        img.drawString("C", 0, 3 * bandHeight + 3 * bandSeparation);
        img.setColor(Theme.used.selectionColor);
        
        for(int i = 0; i < combo.a(); i++){
            img.fillRect(squareLeftDistance + i * (side + squareSeparation), bandSeparation, side, side);
            
        }
        for(int i = 0; i < combo.b(); i++){
            img.fillRect(squareLeftDistance + i * (side + squareSeparation), bandHeight + 2 * bandSeparation, side, side);
            
        }
        for(int i = 0; i < combo.c(); i++){
            img.fillRect(squareLeftDistance + i * (side + squareSeparation), 2 * bandHeight + 3 * bandSeparation, side, side);
            
        }
        
        setImage(img);
        
    }
    
    private void createHiddenImage(){
        
        GreenfootImage img = new GreenfootImage(1, 1);
        img.setFont(new greenfoot.Font("monospace", true, false, 17));
        FontMetrics fm = img.getAwtImage().getGraphics().getFontMetrics(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 17));
        
        int width = 180;
        int bandHeight = fm.getMaxAscent() + fm.getMaxDescent();
        int bandSeparation = 5;
        
        img.scale(width, bandHeight * 3 + bandSeparation * 2);
        img.setColor(Theme.used.backgroundColor.darker());
        img.fill();
        img.setColor(Theme.used.textColor);
        img.drawRect(1, 1, img.getWidth() - 3, img.getHeight() - 3);
        img.drawString("COMBOS", (img.getWidth() - fm.stringWidth("COMBOS")) / 2, 
                                 (img.getHeight() + bandHeight) / 2);
        
        setImage(img);
        
    }
    
    private void toggleImage(){
        if(shown){
            createHiddenImage();
            shown = false;
        }else{
            createComboImage();
            shown = true;
        }
    }
    
    private void updateImage(){
        if(shown){
            createComboImage();
        }
    }
    
    public static void displayCombos(Player p){
        world().removeObject(current);
        current = new ComboDisplayer(p.combos());
        world().addObject(current, Appearance.WORLD_WIDTH - 90, 900);
        world().addObject(useCombos, Appearance.WORLD_WIDTH - 90, 990);
        
    }
    
    public static void updateDisplay(Player p){
        current.combo = p.combos();
        current.updateImage();
        world().addObject(useCombos, Appearance.WORLD_WIDTH - 90, 990);
    }
    
    @Override
    public void clicked() {
        if(Mode.mode() == Mode.GAME_DEFAULT){
            toggleImage();
        }
    }
    
    public static ComboDisplayer current(){
        return current;
    }
    
}