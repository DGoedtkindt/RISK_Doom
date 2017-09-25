import java.awt.Color;
import java.util.HashMap;
import java.util.Set;
import javax.swing.JOptionPane;

public class Links {
    public static Links newLinks;
    private Color color;
    private HashMap<LinkIndic,Territory> indicTerrDico = new HashMap<>();
    
    public Links() {
        int rColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de rouge (int)"));
        int gColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de vert (int)"));
        int bColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de bleu (int)"));
        color = new Color(rColor,gColor,bColor);
        newLinks = this;
    }
    
    public void addlink(LinkIndic linkIndic, Territory linkedTerr ) {
        if(!indicTerrDico.containsValue(linkedTerr)){
            indicTerrDico.put(linkIndic, linkedTerr);
        } else{
            linkIndic.destroy();
        } 
    }
    
    public void removelink(LinkIndic linkToRemove) {
        MyWorld.theWorld.removeObject(linkToRemove);
        indicTerrDico.remove(linkToRemove);
    
    }
    
    public void destroy() {
        Set<LinkIndic> keySet = indicTerrDico.keySet();
        for(LinkIndic key : keySet) {
            key.destroy();
        
        }
        newLinks = null;
    
    }
    public Color color() {return color;}
    
}
