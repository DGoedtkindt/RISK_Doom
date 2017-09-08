import greenfoot.GreenfootImage;
import java.awt.Color;
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
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MakeXML extends Button
{
    public MakeXML(){
        
        GreenfootImage image = new GreenfootImage("Make XML", 25, Color.BLACK, Color.WHITE);
        this.setImage(image);
        
    }
    
    public void clicked(){
        
        saveToXML();
        
    }
    
    // Inspiré de https://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
    private void saveToXML(){
        
        ArrayList<Continent> continentList = Continent.continentList();
        
        try{
            
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("map");//Création de rootElement
            doc.appendChild(rootElement);
            
            
            for(Continent currentContinent : continentList){//append des continents à la map (et caractéristiques)
                
                Element continent = doc.createElement("continent");
                rootElement.appendChild(continent);
                
                ArrayList<Territory> territoriesInContinent = currentContinent.ContainedTerritories();
                
                for(Territory currentTerritory : territoriesInContinent){//append des territoires aux continents (et caractéristiques)
                    
                    Element territory = doc.createElement("territory");
                    continent.appendChild(territory);
                    
                    ArrayList<TerritoryHex> composingHexes = currentTerritory.getComposingHex();
                    
                    for(TerritoryHex currentHex : composingHexes){//append des hex aux territoires
                        
                        Element hex = doc.createElement("hex");
                        territory.appendChild(hex);
                        
                        Attr hexX = doc.createAttribute("hexX");
                        hexX.setValue("" + currentHex.getX());
                        hex.setAttributeNode(hexX);
                        
                        Attr hexY = doc.createAttribute("hexY");
                        hexY.setValue("" + currentHex.getY());
                        hex.setAttributeNode(hexY);
                        
                    }
                    
                    Attr territoryPoints = doc.createAttribute("territoryPoints");
                    territoryPoints.setValue("" + currentTerritory.bonus());
                    territory.setAttributeNode(territoryPoints);
                    
                    Attr territoryOwner = doc.createAttribute("territoryOwner");
                    territoryOwner.setValue("");
                    territory.setAttributeNode(territoryOwner);
                    
                    Attr armies = doc.createAttribute("territoryArmies");
                    armies.setValue("0");
                    territory.setAttributeNode(armies);
                    
                    Attr id = doc.createAttribute("territoryID");
                    id.setValue("" + currentTerritory.id());
                    territory.setAttributeNode(id);
                    
                    ArrayList<Territory> borderingTerritories = currentTerritory.getBorderTerritories();
                    
                    for(Territory currentBordering : borderingTerritories){//append des limitrophes aux territoires
                        
                        Element bordering = doc.createElement("borderingTerritory");
                        territory.appendChild(bordering);
                        
                        Attr borderingID = doc.createAttribute("borderingID");
                        borderingID.setValue("" + currentBordering.id());
                        bordering.setAttributeNode(borderingID);
                        
                    }
                    
                }
                
                
                Attr continentPoints = doc.createAttribute("continentPoints");
                continentPoints.setValue("" + currentContinent.bonus);
                continent.setAttributeNode(continentPoints);
                
                Attr rContinentColor = doc.createAttribute("rContinentColor");
                rContinentColor.setValue("" + (currentContinent.color()).getRed());
                continent.setAttributeNode(rContinentColor);
                
                Attr gContinentColor = doc.createAttribute("gContinentColor");
                gContinentColor.setValue("" + (currentContinent.color()).getGreen());
                continent.setAttributeNode(gContinentColor);
                
                Attr bContinentColor = doc.createAttribute("bContinentColor");
                bContinentColor.setValue("" + (currentContinent.color()).getBlue());
                continent.setAttributeNode(bContinentColor);
                
            }
            
            for(Territory t : Territory.territoryList){
                
                if(t.continent() == null){
                    
                    Element unoccupiedTerritory = doc.createElement("unoccupiedTerritory");
                    rootElement.appendChild(unoccupiedTerritory);
                    
                    Attr territoryPoints = doc.createAttribute("territoryPoints");
                    territoryPoints.setValue("" + t.bonus());
                    unoccupiedTerritory.setAttributeNode(territoryPoints);
                    
                    Attr territoryOwner = doc.createAttribute("territoryOwner");
                    territoryOwner.setValue("");
                    unoccupiedTerritory.setAttributeNode(territoryOwner);
                    
                    Attr armies = doc.createAttribute("territoryArmies");
                    armies.setValue("0");
                    unoccupiedTerritory.setAttributeNode(armies);
                    
                    Attr id = doc.createAttribute("territoryID");
                    id.setValue("" + t.id());
                    unoccupiedTerritory.setAttributeNode(id);
                    
                    ArrayList<Territory> borderingTerritories = t.getBorderTerritories();
                    
                    for(Territory currentBordering : borderingTerritories){//append des limitrophes aux territoires
                        
                        Element bordering = doc.createElement("borderingTerritory");
                        unoccupiedTerritory.appendChild(bordering);
                        
                        Attr borderingID = doc.createAttribute("borderingID");
                        borderingID.setValue("" + currentBordering.id());
                        bordering.setAttributeNode(borderingID);
                        
                    }
                    
                }
                
            }
            
            
            String fileName = JOptionPane.showInputDialog("Entrez le nom du fichier");
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);
            
        }catch(HeadlessException | IllegalStateException | ParserConfigurationException | TransformerException | DOMException e){
            e.printStackTrace(System.out);
            MyWorld.theWorld.escape();
            
        }
   
    }
    
    public void makeTransparent() {
        getImage().setTransparency(MyWorld.TRANSPARENT);
    
    }
    
    public void makeOpaque() {
        getImage().setTransparency(MyWorld.OPAQUE);
    
    }
}
