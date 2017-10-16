import greenfoot.GreenfootImage;

/**objet rataché à un MapChooser. 
Permet de changer la Map de celui-ci vers la droite*/

public class RightButton extends Button {
    MapChooser linkedChooser;
    public RightButton(MapChooser linkedMapChooser) {
        linkedChooser = linkedMapChooser;
        
        GreenfootImage img = new GreenfootImage("rightButton.png");
        this.setImage(img);
    }
    
    @Override
    public void clicked() {
        linkedChooser.next();
    }
    
}
