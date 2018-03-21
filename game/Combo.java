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
    
    public static boolean comboShown = false;
    
    private final ModeButton SAP_BUTTON = new ModeButton("Sap", Mode.SAP, Selector.IS_ATTACKABLE);
    private final NButton FORTRESS_BUTTON = new NButton(() -> {
                                            Turn.currentTurn.player.fortressProtection = true;
                                            Mode.setMode(Mode.DEFAULT);
                                            useFortress();
                                        }, "Fortress");
    private final NButton BATTLECRY_BUTTON = new NButton(() -> {
                                            Turn.currentTurn.player.battlecryBonus = 2;
                                            Mode.setMode(Mode.DEFAULT);
                                            useBattlecry();
                                        }, "Battlecry");
    private final NButton RECRUIT_BUTTON = new NButton(() -> {
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
        }else{
            addComboButtons();
        }
        
        ComboDisplayer.display();
        
    }
    
    /**
     * Adds the Combo Buttons to the World and shows them.
     */
    public void addComboButtons(){
        makeButtonsTransparent();
        addButtonsToWorld();
        makeUsableButtonsOpaque();
        comboShown = true;
    }
    
    /**
     * Removes the Combo Buttons from the World.
     */
    private void removeComboButtons(){
        removeButtonsFromWorld();
        comboShown = false;
        Mode.setMode(Mode.DEFAULT);
    }
    
    /**
     * Sets the transparency of the four Combo Buttons to a transparent value.
     */
    private void makeButtonsTransparent(){
        SAP_BUTTON.toggleUnusable();
        FORTRESS_BUTTON.toggleUnusable();
        BATTLECRY_BUTTON.toggleUnusable();
        RECRUIT_BUTTON.toggleUnusable();
    }
    
    /**
     * Adds the four Combo Buttons to the World.
     */
    private void addButtonsToWorld(){
        MyWorld.theWorld.addObject(SAP_BUTTON, Appearance.WORLD_WIDTH - 300, 990);
        MyWorld.theWorld.addObject(FORTRESS_BUTTON, Appearance.WORLD_WIDTH - 480, 990);
        MyWorld.theWorld.addObject(BATTLECRY_BUTTON, Appearance.WORLD_WIDTH - 660, 990);
        MyWorld.theWorld.addObject(RECRUIT_BUTTON, Appearance.WORLD_WIDTH - 840, 990);
    }
    
    /**
     * Sets the transparency of the usable Combo Buttons to an opaque value.
     */
    private void makeUsableButtonsOpaque(){
        
        boolean canPlayACombo = false;
        
        if(a >= 3){
            SAP_BUTTON.toggleUsable();
            canPlayACombo = true;
        }if(b >= 3){
            FORTRESS_BUTTON.toggleUsable();
            canPlayACombo = true;
        }if(c >= 3){
            BATTLECRY_BUTTON.toggleUsable();
            canPlayACombo = true;
        }if(a > 0 && b > 0 && c > 0){
            RECRUIT_BUTTON.toggleUsable();
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
        removeButtonsFromWorld();
    }
    
    /**
     * Removes the combo Pieces used for the Fortress Combo.
     */
    public void useFortress(){
        b -= 3;
        removeButtonsFromWorld();
    }
    
    /**
     * Removes the combo Pieces used for the Battlecry Combo.
     */
    public void useBattlecry(){
        c -= 3;
        removeButtonsFromWorld();
    }
    
    /**
     * Removes the combo Pieces used for the Recruit Combo.
     */
    public void useRecruit(){
        a--;
        b--;
        c--;
        removeButtonsFromWorld();
    }
    
    /**
     * Removes the Buttons.
     */
    private void removeButtonsFromWorld(){
        
        MyWorld.theWorld.removeObject(SAP_BUTTON);
        MyWorld.theWorld.removeObject(FORTRESS_BUTTON);
        MyWorld.theWorld.removeObject(BATTLECRY_BUTTON);
        MyWorld.theWorld.removeObject(RECRUIT_BUTTON);
        
    }
    
}
