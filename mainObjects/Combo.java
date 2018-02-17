package mainObjects;

import appearance.ComboDisplayer;
import greenfoot.Greenfoot;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import mode.Mode;
import selector.Selector;

public class Combo {
    
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
    
    public void use(){
        
        boolean sap = false;
        boolean fortress = false;
        boolean battlecry = false;
        boolean recruit = false;
        
        int n = 1;
        
        if(a >= 3){
            sap = true;
            n++;
        }if(b >= 3){
            fortress = true;
            n++;
        }if(c >= 3){
            battlecry = true;
            n++;
        }if(a > 0 && b > 0 && c > 0){
            recruit = true;
            n++;
        }
        
        ArrayList<String> actionsStrings = new ArrayList<String>();
        
        actionsStrings.add("Nothing");
        if(sap){
            actionsStrings.add("Sap");
        }
        if(fortress){
            actionsStrings.add("Fortress");
        }
        if(battlecry){
            actionsStrings.add("Battlecry");
        }
        if(recruit){
            actionsStrings.add("Recruit");
        }
        
        String[] actions = new String[5];
        actions = actionsStrings.toArray(actions);
        
        //Marche pas je crois
        int response = JOptionPane.showOptionDialog(null, 
                                                    "Wich combo do you want to use?", 
                                                    "Combo", 
                                                    JOptionPane.DEFAULT_OPTION, 
                                                    JOptionPane.PLAIN_MESSAGE, 
                                                    null, 
                                                    actions, 
                                                    actions[0]);

        switch(response){
            
            case 1 : Mode.setMode(Mode.SAP);
                     Selector.setValidator(Selector.IS_NOT_OWNED_CLOSE_TO_OWNED_TERRITORY);
                     break;
                     
            case 2 : 
                     break;
            
            case 3 : 
                     break;
                     
            case 4 : 
                     break;
                     
            default : break;

        }
        
    }
    
    public void useSap(){
        a -= 3;
    }
    
}
