package base;

import appearance.Appearance;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import mapXML.MapXML;

public class NButton extends Button{
    
    private ActionListener action;
    
    static private MyWorld world(){return MyWorld.theWorld;}
    
    public NButton(ActionListener al, String s){
        
        action = al;
        
    }
    
    @Override
    public void clicked() {
        
        action.actionPerformed(null);
        
    }
    
    //Action pour sauver le monde
    
    public static ActionListener saveFile = (ActionEvent al) -> {
        
        String mapName = "";
        String mapDescription = "";
        
        //créer la boite de dialogue
        JTextField nameField = new JTextField(15);
        JTextField descriptionField = new JTextField(65);
        JPanel questionPanel = new JPanel();
        questionPanel.add(new JLabel("Name : "));
        questionPanel.add(nameField);
        questionPanel.add(Box.createHorizontalStrut(20));
        questionPanel.add(new JLabel("Description : "));
        questionPanel.add(descriptionField);

        //get le résultat de la boite de dialogue
        int output = JOptionPane.showConfirmDialog(null, questionPanel, "Give your map a name and a description", JOptionPane.OK_CANCEL_OPTION);
        if (output == JOptionPane.OK_OPTION) {
           mapName = nameField.getText();
           mapDescription = descriptionField.getText();
        }

        //vérifier quelques conditions
        int writeQ = JOptionPane.YES_OPTION;

        if(mapName == null || mapName.isEmpty() || mapName == "New Map") {
            System.out.println("You can't save a map if it has no name or if its name is 'New Map'");
            writeQ = JOptionPane.NO_OPTION;

        } else if(new File("Maps/"+mapName+".xml").exists()){
                writeQ = JOptionPane.showConfirmDialog(
                        null, "Do you want to replace the existing map '" + mapName + "' with this one?", 
                        "Replacing an existing map", JOptionPane.YES_NO_OPTION);

        }

        //si les conditions sont accomplies: sauver
        if(writeQ == JOptionPane.YES_OPTION){
            try {
                MapXML xml = new MapXML(MyWorld.theWorld.map);
                xml.write(mapName, mapDescription);

            } catch (Exception ex) {
                System.err.println("Map could not be saved : "+ ex);
            }

        }
    };
    
    @Override
    public void makeTransparent() {
        getImage().setTransparency(Appearance.TRANSPARENT);
    
    }
    
    @Override
    public void makeOpaque() {
        getImage().setTransparency(Appearance.OPAQUE);
    
    }
    
}