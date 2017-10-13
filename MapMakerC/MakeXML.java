import greenfoot.GreenfootImage;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.awt.image.BufferedImage;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MakeXML extends Button
{   
    Document doc;
    Element rootElement;
    
    private static MyWorld world() {return MyWorld.theWorld;}
    
    public MakeXML(){
        try {
            GreenfootImage image = new GreenfootImage("MakeXML.png");
            image.scale(80, 80);
            this.setImage(image);
            
            createNewDocument();
        } catch (IllegalArgumentException| DOMException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public void clicked(){
        if(Mode.currentMode() == Mode.DEFAULT){
            
            createTerritoryNodes();
            createContinentNodes();
            createLinksNodes();
            saveFile();
            
        }
    }
    
    public void createTerritoryNodes(){
        ArrayList<Territory> allTerrList = Territory.allTerritories();
        for(Territory terr: allTerrList) {
            Element terrNode = doc.createElement("Territory");
            rootElement.appendChild(terrNode);
            
            //rajoute les coordonées du infoHex
            Element infoHexNode = doc.createElement("InfoHex");
            terrNode.appendChild(infoHexNode);
            TerritoryHex infoHex = terr.infoHex();
            infoHexNode.setAttribute("hexX", "" + infoHex.coordinates().hexCoord[0]);
            infoHexNode.setAttribute("hexY", "" + infoHex.coordinates().hexCoord[1]);
            
            //rajouter tous les hexs
            ArrayList<TerritoryHex> hexList = terr.composingHex();
            for(TerritoryHex hex: hexList) {
                Element HexNode = doc.createElement("Hex");
                terrNode.appendChild(HexNode);
                HexNode.setAttribute("hexX", "" + hex.coordinates().hexCoord[0]);
                HexNode.setAttribute("hexY", "" + hex.coordinates().hexCoord[1]);
            
            }
            
            //rajouter l'id
            int terrID = terr.id();
            terrNode.setAttribute("id", "" + terrID);
            
            //rajouter le bonus
            int terrBonus = terr.bonus();
            terrNode.setAttribute("bonus", "" + terrBonus);

        }
    }
    
    private void createContinentNodes(){
        ArrayList<Continent> allContList = Continent.continentList();
        for(Continent cont : allContList) {
            Element contNode = doc.createElement("Continent");
            rootElement.appendChild(contNode);
            
            ArrayList<Territory> terrContained = cont.containedTerritories();
            for(Territory terr : terrContained) {
                Element terrInCont = doc.createElement("TerrInCont");
                terrInCont.setAttribute("id", "" + terr.id());
                contNode.appendChild(terrInCont);
            
            }
            String contColor = "" + cont.color().getRGB();
            contNode.setAttribute("color", contColor);
            contNode.setAttribute("bonus", "" + cont.bonus());
        
        }
    
    }

    private void createLinksNodes() {
        ArrayList<Links> allLinksList = Links.allLinks();
        for(Links links : allLinksList) {
            Element linksNode = doc.createElement("Links");
            rootElement.appendChild(linksNode);
            ArrayList<LinkIndic> linksContained = links.LinkIndicsList();
            String linksColor = "" + links.color().getRGB();
            linksNode.setAttribute("color", linksColor);
            for(LinkIndic link : linksContained) {
                Element linkNode = doc.createElement("Link");
                linksNode.appendChild(linkNode);
                int xPos = link.getX();
                int yPos = link.getY();
                int linkedTerrId = link.linkedTerr().id();
                linkNode.setAttribute("xPos", ""+xPos);
                linkNode.setAttribute("yPos", ""+yPos);
                linkNode.setAttribute("terrId", ""+linkedTerrId);
            
            }
        
        
        }
    
    }
    
    private void saveFile() {
        try{
            
            String mapName = "";
            String mapDescription = "";
            
            JTextField nameField = new JTextField(25);
            JTextField descriptionField = new JTextField(65);

            JPanel questionPanel = new JPanel();
            questionPanel.add(new JLabel("Name : "));
            questionPanel.add(nameField);
            questionPanel.add(Box.createHorizontalStrut(20));
            questionPanel.add(new JLabel("Description : "));
            questionPanel.add(descriptionField);

            int output = JOptionPane.showConfirmDialog(null, questionPanel, "Give your map a name and a description", JOptionPane.OK_CANCEL_OPTION);
            if (output == JOptionPane.OK_OPTION) {
               mapName = nameField.getText();
               mapDescription = descriptionField.getText();
            }
            
            if(mapName != null && !mapName.isEmpty() && mapName != "New Map"){
                
                int choice = JOptionPane.YES_OPTION;
                
                if(world().mapThumbnail.fileList().contains(mapName + ".xml")){
                    
                    choice = JOptionPane.showConfirmDialog(null, "Do you want to replace the existing map '" + mapName + "' with this one?", 
                                                                 "Replacing an existing map", JOptionPane.YES_NO_OPTION);
                    
                }
                
                if(choice == JOptionPane.YES_OPTION){
                        
                        rootElement.setAttribute("description", mapDescription);
                
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        DOMSource source = new DOMSource(doc);
                        File dir = new File(".");
                        try{ 
                            dir = new File("Maps");
                            if(!dir.exists()) throw new Exception("The Maps directory does not seem to exist." +
                                    "Please make sure " + dir.getAbsolutePath() + " exists");

                        }catch(Exception e) {System.err.println(e);}
                        StreamResult result = new StreamResult(new File(dir.getAbsolutePath() + "/" + mapName + ".xml"));
                        transformer.transform(source, result);

                        BufferedImage mapImage = world().createMapImage();
                        File out = new File(dir.getAbsolutePath() + "/" + mapName + ".png");

                        ImageIO.write(mapImage, "PNG", out);}
                
            }else{
                System.err.println("You can't save a map if it has no name or if its name is 'New Map'");
                createNewDocument();
                
            }
            
        } catch(IOException | HeadlessException | TransformerException e) {
            System.err.println(e);
        }
        
    }
    
    private void createNewDocument(){
        
        try {
            
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();
            rootElement = doc.createElement("map");//Création de rootElement
            doc.appendChild(rootElement);
            
        } catch (IllegalArgumentException | ParserConfigurationException | DOMException ex) {
            ex.printStackTrace(System.out);
        }
        
    }
    
    public void makeTransparent() {
        getImage().setTransparency(MyWorld.TRANSPARENT);
    
    }
    
    public void makeOpaque() {
        getImage().setTransparency(MyWorld.OPAQUE);
    
    }
}
