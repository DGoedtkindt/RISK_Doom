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
                                            Mode.setMode(Mode.GAME_DEFAULT);
                                            useFortress();
                                        }, "Fortress");
    private NButton battlecryButton = new NButton(() -> {
                                            Turn.currentTurn.player.battlecryBonus = 2;
                                            Mode.setMode(Mode.GAME_DEFAULT);
                                            useBattlecry();
                                        }, "Battlecry");
    private NButton recruitButton = new NButton(() -> {
                                            Player p = Turn.currentTurn.player;
                                            p.addArmiesToHand(5);
                                            Mode.setMode(Mode.GAME_DEFAULT);
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
            Mode.setMode(Mode.GAME_DEFAULT);
            
        }else{
            
            sapButton.makeTransparent();
            fortressButton.makeTransparent();
            battlecryButton.makeTransparent();
            recruitButton.makeTransparent();
            
            MyWorld.theWorld.addObject(sapButton, Appearance.WORLD_WIDTH - 300, 990);
            MyWorld.theWorld.addObject(fortressButton, Appearance.WORLD_WIDTH - 480, 990);
            MyWorld.theWorld.addObject(battlecryButton, Appearance.WORLD_WIDTH - 660, 990);
            MyWorld.theWorld.addObject(recruitButton, Appearance.WORLD_WIDTH - 840, 990);
            
            if(a >= 3){
                sapButton.makeOpaque();
                Mode.setMode(Mode.SELECTING_COMBO);
            }if(b >= 3){
                fortressButton.makeOpaque();
                Mode.setMode(Mode.SELECTING_COMBO);
            }if(c >= 3){
                battlecryButton.makeOpaque();
                Mode.setMode(Mode.SELECTING_COMBO);
            }if(a > 0 && b > 0 && c > 0){
                recruitButton.makeOpaque();
                Mode.setMode(Mode.SELECTING_COMBO);
            }
            
            comboShown = true;
            
        }
        
        ComboDisplayer.display();
        
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
