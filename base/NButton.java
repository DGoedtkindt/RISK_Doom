package base;

import appearance.Appearance;
import greenfoot.GreenfootImage;
import greenfoot.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import mapXML.MapXML;

public class NButton extends Button{
    
    private ActionListener action;
    
    /**
     * NButton with no image
     * @param al the action the button performs when clicked
     */
    public NButton(ActionListener al) {
        action = al;
        
    }
    
    /**
     * NButton with a custom image and scale
     * 
     * @param al the action the button performs when clicked
     * @param img the image of the Button
     * @param width the width to which the image will be scaled
     * @param height the height to which the image will be scaled
     */
    public NButton(ActionListener al, GreenfootImage img, int width, int height){
        this.setImage(img);
        this.getImage().scale(width, height);
        action = al;
        
    }
    
    /**
     * NButton initialized with an image scaled to default size : 80x80 
     * 
     * @param al the action the button performs when clicked
     * @param img the image of the button
     */
    public NButton(ActionListener al, GreenfootImage img){
        this.setImage(img);
        this.getImage().scale(80, 80);
        action = al;
        
    }
    
    /**
     * NButton with a long image background (175x80) and a small text 
     * 
     * @param al the action the button performs when clicked
     * @param txt the text displayed on the button
     */
    public NButton(ActionListener al, String txt){
        GreenfootImage img = new GreenfootImage("button11-5.png");
        this.setImage(img);
        this.getImage().scale(175, 80);
        GreenfootImage txtImg = new GreenfootImage(txt,20,Color.BLACK,new Color(0,0,0,0));
        int xPos = (175-txtImg.getWidth())/2;
        int yPos = (80-txtImg.getHeight())/2;
        this.getImage().drawImage(txtImg, xPos, yPos);
        action = al;
        
    }
    
    @Override
    public void clicked() {
        if(action != null) action.actionPerformed(null);
        
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
            System.err.println("You can't save a map if it has no name or if its name is 'New Map'");
            writeQ = JOptionPane.NO_OPTION;

        } else if(new File("Maps/"+mapName+".xml").exists()){
                writeQ = JOptionPane.showConfirmDialog(
                        null, "Do you want to replace the existing map '" + mapName + "' with this one?", 
                        "Replacing an existing map", JOptionPane.YES_NO_OPTION);

        }

        //si les conditions sont accomplies: sauver
        if(writeQ == JOptionPane.YES_OPTION){
            try {
                MapXML xml = new MapXML(MyWorld.theWorld.stateManager.map());
                xml.write(mapName, mapDescription);
                
                //écrivre l'image du thumbnail (solution temporaire)
                try{
                    BufferedImage mapImage = MyWorld.theWorld.getBackground().getAwtImage();
                    File out = new File(new File("Maps").getAbsolutePath() + "/" + mapName + ".png");

                    ImageIO.write(mapImage, "PNG", out);
                    System.out.println("done");

                } catch(IOException ex) {
                    System.err.println("thumbnail couldn't be saved : " + ex);

                }

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