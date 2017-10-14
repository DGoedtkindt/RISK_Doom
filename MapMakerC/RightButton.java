/**objet rataché à un MapChooser. 
Permet de changer la Map de celui-ci vers la droite*/

public class RightButton extends Button {
    MapChooser linkedChooser;
    public RightButton(MapChooser linkedMapChooser) {
        linkedChooser = linkedMapChooser;
    }
    
    @Override
    public void clicked() {
        linkedChooser.next();
    }
    
}
