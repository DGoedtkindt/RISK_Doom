package mainObjects;

import greenfoot.Greenfoot;

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
        switch(Greenfoot.getRandomNumber(3)){
            
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
    
}
