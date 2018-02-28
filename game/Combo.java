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
    
    public void addRandomCombo(){
        if(comboPiecesNumber()<5) {
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
    
    private int comboPiecesNumber(){
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
    
    private ModeButton sapButton = new ModeButton("backToHome.png", Mode.SAP, Selector.IS_ATTACKABLE);
    private NButton fortressButton = new NButton(() -> {
                                            Turn.currentTurn.player.fortressProtection = true;
                                            Mode.setMode(Mode.DEFAULT);
                                            useFortress();
                                        }, new GreenfootImage("backToHome.png"));
    private NButton battlecryButton = new NButton(() -> {
                                            Turn.currentTurn.player.battlecryBonus = 2;
                                            Mode.setMode(Mode.DEFAULT);
                                            useBattlecry();
                                        }, new GreenfootImage("backToHome.png"));
    private NButton recruitButton = new NButton(() -> {
                                            Player p = Turn.currentTurn.player;
                                            p.addArmiesToHand(5);
                                            Mode.setMode(Mode.DEFAULT);
                                            useRecruit();
                                        }, new GreenfootImage("backToHome.png"));
    
    public void use(){
        
        if(comboShown){
            
            removeComboButtons();
            comboShown = false;
            
        }else{
            
            Mode.setMode(Mode.SELECTING_COMBO);
            if(a >= 3){
                MyWorld.theWorld.addObject(sapButton, Appearance.WORLD_WIDTH - 270, 990);
                comboShown = true;
            }if(b >= 3){
                MyWorld.theWorld.addObject(fortressButton, Appearance.WORLD_WIDTH - 360, 990);
                comboShown = true;
            }if(c >= 3){
                MyWorld.theWorld.addObject(battlecryButton, Appearance.WORLD_WIDTH - 450, 990);
                comboShown = true;
            }if(a > 0 && b > 0 && c > 0){
                MyWorld.theWorld.addObject(recruitButton, Appearance.WORLD_WIDTH - 540, 990);
                comboShown = true;
            }
            
        }
        ComboDisplayer.display();
        
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
