package game;

import appearance.Appearance;
import appearance.Theme;
import base.Button;
import base.NButton;
import greenfoot.GreenfootImage;
import java.awt.FontMetrics;
import mode.Mode;

/**
 * Displays the combo pieces a player has.
 * 
 */
public class ComboDisplayer extends Button{
    
    private final static ComboDisplayer DISPLAYER = new ComboDisplayer(new Combo());
    private static final NButton USE_COMBOS = new NButton(() -> {DISPLAYER.combo.use();}, "Use a Combo");
    
    private boolean shown = false;
    
    private Combo combo = null;
    
    /**
     * Creates a displayer.
     * @param p The Player currently playing.
     */
    private ComboDisplayer(Combo c){
        Mode.addModeChangeListener(() -> {
            if (Mode.mode() == Mode.DEFAULT){
                ComboDisplayer.USE_COMBOS.toggleUsable();
            }else{
                ComboDisplayer.USE_COMBOS.toggleUnusable();
            }
        });
        combo = c;
        createHiddenImage();
        
    }
    
    /**
     * Creates an image showing the combo pieces the Player has.
     */
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
        
        //Creates the image
        img.scale(width, bandHeight * 3 + bandSeparation * 4);
        img.setColor(Theme.used.backgroundColor.darker());
        img.fill();
        img.setColor(Theme.used.textColor);
        img.drawString("A", 0,     bandHeight     + bandSeparation);
        img.drawString("B", 0, 2 * bandHeight + 2 * bandSeparation);
        img.drawString("C", 0, 3 * bandHeight + 3 * bandSeparation);
        img.setColor(Theme.used.selectionColor);
        
        //Draws the squares
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
    
    /**
     * Creates an image showing nothing but the word "COMBOS".
     */
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
        img.setColor(Turn.currentTurn.player.color);
        img.drawRect(1, 1, img.getWidth() - 3, img.getHeight() - 3);
        img.setColor(Theme.used.textColor);
        img.drawString("COMBOS", (img.getWidth() - fm.stringWidth("COMBOS")) / 2, 
                                 (img.getHeight() + bandHeight) / 2);
        
        setImage(img);
        
    }
    
    /**
     * Switches between the shown and hidden image.
     */
    private void toggleImage(){
        if(shown){
            createHiddenImage();
            shown = false;
        }else{
            createComboImage();
            shown = true;
        }
    }
    
    /**
     * Update the displayer's image.
     */
    private void updateImage(){
        if(shown){
            createComboImage();
        } else 
            createHiddenImage();
    }
    
    /**
     * Updates the displayer for the current Player.
     */
    public static void display() {
        DISPLAYER.shown = false;
        Player currentPlayer = Turn.currentTurn.player;
        DISPLAYER.combo = currentPlayer.combos();
        DISPLAYER.updateImage();
        world().addObject(DISPLAYER, Appearance.WORLD_WIDTH - 90, 950);
        world().addObject(USE_COMBOS, Appearance.WORLD_WIDTH - 90, 1040);
        
    }
    
    @Override
    public void clicked() {
        toggleImage();
        
    }
    
}