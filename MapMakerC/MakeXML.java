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
            
            //Appends continents to the map
            for(Continent currentContinent : continentList){
                
                Element continent = doc.createElement("continent");
                rootElement.appendChild(continent);
                
                ArrayList<Territory> territoriesInContinent = currentContinent.ContainedTerritories();
                
                //Appends territories to continent
                for(Territory currentTerritory : territoriesInContinent){
                    
                    Element territory = doc.createElement("territory");
                    continent.appendChild(territory);
                    
                    ArrayList<TerritoryHex> composingHexes = currentTerritory.getComposingHex();
                    
                    //Appends hexes to territory
                    for(TerritoryHex currentHex : composingHexes){
                        
                        Element hex = doc.createElement("hex");
                        territory.appendChild(hex);
                        
                        //Gives attributes to hexes
                        Attr hexX = doc.createAttribute("hexX");
                        hexX.setValue("" + currentHex.getX());
                        hex.setAttributeNode(hexX);
                        
                        Attr hexY = doc.createAttribute("hexY");
                        hexY.setValue("" + currentHex.getY());
                        hex.setAttributeNode(hexY);
                        
                    }
                    //Appends infoHex to territory
                    Element infoHex = doc.createElement("infoHex");
                    territory.appendChild(infoHex);

                    Attr infoHexX = doc.createAttribute("infoHexX");
                    infoHexX.setValue("" + currentTerritory.terrInfo().getX());
                    infoHex.setAttributeNode(infoHexX);

                    Attr infoHexY = doc.createAttribute("infoHexY");
                    infoHexY.setValue("" + currentTerritory.terrInfo().getY());
                    infoHex.setAttributeNode(infoHexY);
                    
                    //Gives attributes to territory
                    Attr territoryPoints = doc.createAttribute("territoryPoints");
                    territoryPoints.setValue("" + currentTerritory.bonus());
                    territory.setAttributeNode(territoryPoints);
                    
                    Attr id = doc.createAttribute("territoryID");
                    id.setValue("" + currentTerritory.id());
                    territory.setAttributeNode(id);
                    
                    ArrayList<Territory> borderingTerritories = currentTerritory.getBorderTerritories();
                    
                    //Appends borderings to territory
                    for(Territory currentBordering : borderingTerritories){
                        
                        Element bordering = doc.createElement("borderingTerritory");
                        territory.appendChild(bordering);
                        
                        Attr borderingID = doc.createAttribute("borderingID");
                        borderingID.setValue("" + currentBordering.id());
                        bordering.setAttributeNode(borderingID);
                        
                    }
                    
                }
                
                //Gives attributes to continent
                Attr continentPoints = doc.createAttribute("continentPoints");
                continentPoints.setValue("" + currentContinent.bonus());
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
            
            //Appends unoccupied territories to the map
            for(Territory t : Territory.allTerritories()){
                
                if(t.continent() == null){
                    
                    Element unoccupiedTerritory = doc.createElement("unoccupiedTerritory");
                    rootElement.appendChild(unoccupiedTerritory);
                    
                    //Gives unoccupied territory attributes
                    Attr territoryPoints = doc.createAttribute("territoryPoints");
                    territoryPoints.setValue("" + t.bonus());
                    unoccupiedTerritory.setAttributeNode(territoryPoints);
                    
                    Attr id = doc.createAttribute("territoryID");
                    id.setValue("" + t.id());
                    unoccupiedTerritory.setAttributeNode(id);
                    
                    ArrayList<Territory> borderingTerritories = t.getBorderTerritories();
                    
                    //Appends borderings to unoccupied territory
                    for(Territory currentBordering : borderingTerritories){
                        
                        Element bordering = doc.createElement("borderingTerritory");
                        unoccupiedTerritory.appendChild(bordering);
                        
                        Attr borderingID = doc.createAttribute("borderingID");
                        borderingID.setValue("" + currentBordering.id());
                        bordering.setAttributeNode(borderingID);
                        
                    }
                    
                }
                
            }
            
            //Saves
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
