import greenfoot.GreenfootImage;

/**objet rataché à un MapChooser.
Permet de changer la Map de celui-ci vers la gauche*/

public class LeftButton extends Button {
    MapChooser linkedChooser;
    public LeftButton(MapChooser linkedMapChooser) {
        linkedChooser = linkedMapChooser;
        
        GreenfootImage img = new GreenfootImage("leftButton.png");
        this.setImage(img);
    }
    
    @Override
    public void clicked() {
        linkedChooser.previous();
    }
    
}
