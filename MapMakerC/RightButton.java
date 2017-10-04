public class RightButton extends Button {
    MapChooser linkedChooser;
    public RightButton(MapChooser linkedMapChooser) {
        linkedChooser = linkedMapChooser;
    }
    
    public void clicked() {
        linkedChooser.next();
    }
    
}
