package game;

import appearance.Appearance;
import base.MyWorld;
import base.NButton;
import greenfoot.Greenfoot;
import mode.Mode;
import mode.ModeButton;
import selector.Selector;

/**
 * The representation of the Combos and combo pieces a Player has.
 * 
 */
public class Combo {
    
    private static boolean comboShown = false;
    
    private ModeButton sapButton = new ModeButton("Sap", Mode.SAP, Selector.IS_ATTACKABLE);
    private NButton fortressButton = new NButton(() -> {
                                            Turn.currentTurn.player.fortressProtection = true;
                                            Mode.setMode(Mode.DEFAULT);
                                            useFortress();
                                        }, "Fortress");
    private NButton battlecryButton = new NButton(() -> {
                                            Turn.currentTurn.player.battlecryBonus = 2;
                                            Mode.setMode(Mode.DEFAULT);
                                            useBattlecry();
                                        }, "Battlecry");
    private NButton recruitButton = new NButton(() -> {
                                            Player p = Turn.currentTurn.player;
                                            p.addArmiesToHand(5);
                                            Mode.setMode(Mode.DEFAULT);
                                            useRecruit();
                                        }, "Recruit");
    
    private int a = 0;
    private int b = 0;
    private int c = 0;
    
    /**
     * Adds a random combo piece to the list.
     */
    public void addRandomCombo(){
        if(comboPiecesNumber() < 5) {
            int randomCombo = Greenfoot.getRandomNumber(3);

            switch(randomCombo){

                case 0 : a++;
                    break;
                case 1 : b++;
                    break;
                case 2 : c++;
                    break;

            }
            
            ComboDisplayer.display();
        }
    }
    
    /**
     * The number of combo pieces a Player has.
     */
    private int comboPiecesNumber(){
        return a + b + c;
    }
    
    /**
     * The number of A pieces.
     * @return The number of A pieces.
     */
    public int a(){
        return a;
    }
    
    /**
     * The number of B pieces.
     * @return The number of B pieces.
     */
    public int b(){
        return b;
    }
    
    /**
     * The number of C pieces.
     * @return The number of C pieces.
     */
    public int c(){
        return c;
    }
    
    /**
     * Gives the Player his current Combo options.
     */
    public void use(){
        
        if(comboShown){
            
            removeComboButtons();
            comboShown = false;
            Mode.setMode(Mode.DEFAULT);
            
        }else{
            
            makeButtonsTransparent();
            addButtonsToWorld();
            makeUsableButtonsOpaque();
            
            comboShown = true;
            
        }
        
        ComboDisplayer.display();
        
    }
    
    /**
<<<<<<< HEAD
=======
     * Sets the transparency of the four Combo Buttons to a transparent value.
     */
    private void makeButtonsTransparent(){
        sapButton.toggleUnusable();
        fortressButton.toggleUnusable();
        battlecryButton.toggleUnusable();
        recruitButton.toggleUnusable();
    }
    
    /**
     * Adds the four Combo Buttons to the World.
     */
    private void addButtonsToWorld(){
        MyWorld.theWorld.addObject(sapButton, Appearance.WORLD_WIDTH - 300, 990);
        MyWorld.theWorld.addObject(fortressButton, Appearance.WORLD_WIDTH - 480, 990);
        MyWorld.theWorld.addObject(battlecryButton, Appearance.WORLD_WIDTH - 660, 990);
        MyWorld.theWorld.addObject(recruitButton, Appearance.WORLD_WIDTH - 840, 990);
    }
    
    /**
     * Sets the transparency of the usable Combo Buttons to an opaque value.
     */
    private void makeUsableButtonsOpaque(){
        
        boolean canPlayACombo = false;
        
        if(a >= 3){
            sapButton.toggleUsable();
            canPlayACombo = true;
        }if(b >= 3){
            fortressButton.toggleUsable();
            canPlayACombo = true;
        }if(c >= 3){
            battlecryButton.toggleUsable();
            canPlayACombo = true;
        }if(a > 0 && b > 0 && c > 0){
            recruitButton.toggleUsable();
            canPlayACombo = true;
        }
        
        if(canPlayACombo){
            Mode.setMode(Mode.SELECTING_COMBO);
        }
        
    }
    
    /**
     * Removes the combo Pieces used for the Sap Combo.
     */
    public void useSap(){
        a -= 3;
        removeComboButtons();
    }
    
    /**
     * Removes the combo Pieces used for the Fortress Combo.
     */
    public void useFortress(){
        b -= 3;
        removeComboButtons();
    }
    
    /**
     * Removes the combo Pieces used for the Battlecry Combo.
     */
    public void useBattlecry(){
        c -= 3;
        removeComboButtons();
    }
    
    /**
     * Removes the combo Pieces used for the Recruit Combo.
     */
    public void useRecruit(){
        a--;
        b--;
        c--;
        removeComboButtons();
    }
    
    /**
     * Removes the Buttons
     */
    public void removeComboButtons(){
        
        MyWorld.theWorld.removeObject(sapButton);
        MyWorld.theWorld.removeObject(fortressButton);
        MyWorld.theWorld.removeObject(battlecryButton);
        MyWorld.theWorld.removeObject(recruitButton);
        
    }
    
}
