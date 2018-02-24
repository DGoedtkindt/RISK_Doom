package game;

import appearance.Appearance;
import base.MyWorld;
import base.NButton;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import mode.Mode;
import mode.ModeButton;
import selector.Selector;

public class Combo {
    
    private static boolean comboShown = false;
    
    private int a = 0;
    private int b = 0;
    private int c = 0;
    
    public void addA(){
        a++;
    }
    
    public void addB(){
        b++;
    }
    
    public void addC(){
        c++;
    }
    
    public void addRandomCombo(){
        
        int randomCombo = Greenfoot.getRandomNumber(3);
        
        switch(randomCombo){
            
            case 0 : addA();
                break;
            case 1 : addB();
                break;
            case 2 : addC();
                break;
            
        }
        
    }
    
    public int comboPiecesNumber(){
        return a + b + c;
    }
    
    public int a(){
        return a;
    }
    
    public int b(){
        return b;
    }
    
    public int c(){
        return c;
    }
    
    static public void display(Player p){
        ComboDisplayer.displayCombos(p);
    }
    
    private ModeButton sapButton = new ModeButton("backToHome.png", Mode.SAP, Selector.IS_NOT_OWNED_CLOSE_TO_OWNED_TERRITORY);
    private NButton fortressButton = new NButton(() -> {
                                            Turn.currentTurn.player.fortressProtection = true;
                                            Mode.setMode(Mode.GAME_DEFAULT);
                                            useFortress();
                                        }, new GreenfootImage("backToHome.png"));
    private NButton battlecryButton = new NButton(() -> {
                                            Turn.currentTurn.player.battlecryBonus = 2;
                                            Mode.setMode(Mode.GAME_DEFAULT);
                                            useBattlecry();
                                        }, new GreenfootImage("backToHome.png"));
    private NButton recruitButton = new NButton(() -> {
                                            Turn.currentTurn.player.armiesInHand += 5;
                                            Mode.setMode(Mode.GAME_DEFAULT);
                                            useRecruit();
                                        }, new GreenfootImage("backToHome.png"));
    
    public void use(){
        
        if(comboShown){
            
            removeComboButtons();
            comboShown = false;
            
        }else{
            
            if(a >= 3){
                Mode.setMode(Mode.SELECTING_COMBO);
                MyWorld.theWorld.addObject(sapButton, Appearance.WORLD_WIDTH - 270, 990);
                comboShown = true;
            }if(b >= 3){
                Mode.setMode(Mode.SELECTING_COMBO);
                MyWorld.theWorld.addObject(fortressButton, Appearance.WORLD_WIDTH - 360, 990);
                comboShown = true;
            }if(c >= 3){
                Mode.setMode(Mode.SELECTING_COMBO);
                MyWorld.theWorld.addObject(battlecryButton, Appearance.WORLD_WIDTH - 450, 990);
                comboShown = true;
            }if(a > 0 && b > 0 && c > 0){
                Mode.setMode(Mode.SELECTING_COMBO);
                MyWorld.theWorld.addObject(recruitButton, Appearance.WORLD_WIDTH - 540, 990);
                comboShown = true;
            }
            
        }
        
    }
    
    public void useSap(){
        a -= 3;
    }
    
    public void useFortress(){
        b -= 3;
    }
    
    public void useBattlecry(){
        c -= 3;
    }
    
    public void useRecruit(){
        a--;
        b--;
        c--;
    }
    
    public void removeComboButtons(){
        
        MyWorld.theWorld.removeObject(sapButton);
        MyWorld.theWorld.removeObject(fortressButton);
        MyWorld.theWorld.removeObject(battlecryButton);
        MyWorld.theWorld.removeObject(recruitButton);
        
    }
    
}