package basepackage;

import greenfoot.Actor;
import java.util.ArrayList;

/**
 * abstract class provides the basis for a map/game chooser
 */
public abstract class MGChooser extends Actor implements Arrowable {
    private final String folderName;
    private final ArrayList<String> fileNames;
    //devrait contenir le code pour chercher les XML et PNG et afficher
    //la première combinaison thumbnail-description
    public MGChooser(String folderName) {
        this.folderName = folderName; 
        fileNames = new ArrayList<>();
    
    }
    
    
}
