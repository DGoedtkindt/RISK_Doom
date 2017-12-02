package base;

import greenfoot.GreenfootImage;

public class BackButton extends Button{

    public BackButton(){
        GreenfootImage img = new GreenfootImage("backToHome.png");
        img.scale(30,30);
        this.setImage(img);

    }
    
    @Override
    public void clicked() {
        MyWorld.theWorld.backToMenu();
        
    }
    
}
