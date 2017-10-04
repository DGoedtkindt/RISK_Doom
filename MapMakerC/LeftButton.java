
public class LeftButton extends Button{
    MapChooser linkedChooser;
    public LeftButton(MapChooser linkedMapChooser) {
        linkedChooser = linkedMapChooser;
    }
    
    public void clicked() {
        linkedChooser.previous();
    }
    
}
