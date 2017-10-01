import greenfoot.GreenfootImage;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.io.File;
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

public class MakeXML extends Button
{   
    Document doc;
    Element rootElement;
    
    public MakeXML(){
        try {
            GreenfootImage image = new GreenfootImage("MakeXML.png");
            image.scale(80, 80);
            this.setImage(image);
            
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();
            rootElement = doc.createElement("map");//Création de rootElement
            doc.appendChild(rootElement);
        } catch (IllegalArgumentException | ParserConfigurationException | DOMException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public void saveToXML(){
        createTerritoryNodes();
        createContinentNodes();
        createLinksNodes();
        saveFile();
        
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
            String fileName = JOptionPane.showInputDialog("Enter file name");

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName + ".xml"));
            transformer.transform(source, result);
            
        } catch(HeadlessException | TransformerException e) {
            e.printStackTrace(System.out);
        }
        
    }
    
    public void clicked(){
        
        saveToXML();
        
    }
   
    public void makeTransparent() {
        getImage().setTransparency(MyWorld.TRANSPARENT);
    
    }
    
    public void makeOpaque() {
        getImage().setTransparency(MyWorld.OPAQUE);
    
    }
}
