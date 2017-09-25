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
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import java.util.HashMap;
import java.util.Set;

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
            
            contNode.setAttribute("rColor", "" + (cont.color()).getRed());
            contNode.setAttribute("gColor", "" + (cont.color()).getGreen());
            contNode.setAttribute("bColor", "" + (cont.color()).getBlue());
            
            contNode.setAttribute("bonus", "" + cont.bonus());
        
        }
    
    
    
    
    
    }

    private void saveFile() {
        try{
            String fileName = JOptionPane.showInputDialog("Enter file name");

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);
            
        } catch(HeadlessException | TransformerException e) {
            e.printStackTrace(System.out);
        }
    }
    
    
   
    
    public void clicked(){
        
        saveToXML();
        
    }
    
    /*private void saveToXML2(){
        
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
                    
                    ArrayList<TerritoryHex> composingHexes = currentTerritory.composingHex();
                    
                    //Appends hexes to territory
                    for(TerritoryHex currentHex : composingHexes){
                        
                        Element hex = doc.createElement("hex");
                        territory.appendChild(hex);
                        
                        //Gives attributes to hexes
                        Attr hexX = doc.createAttribute("hexX");
                        hexX.setValue("" + currentHex.coordinates().hexCoord[0]);
                        hex.setAttributeNode(hexX);
                        
                        Attr hexY = doc.createAttribute("hexY");
                        hexY.setValue("" + currentHex.coordinates().hexCoord[1]);
                        hex.setAttributeNode(hexY);
                        
                    }
                    //Appends infoHex to territory
                    Element infoHex = doc.createElement("infoHex");
                    territory.appendChild(infoHex);

                    Attr infoHexX = doc.createAttribute("infoHexX");

                    infoHexX.setValue("" + currentTerritory.infoHex().coordinates().hexCoord[0]);
                    infoHex.setAttributeNode(infoHexX);
                    
                    Attr infoHexY = doc.createAttribute("infoHexY");

                    infoHexY.setValue("" + currentTerritory.infoHex().coordinates().hexCoord[1]);
                    infoHex.setAttributeNode(infoHexY);
                    
                    //Gives attributes to territory
                    Attr territoryPoints = doc.createAttribute("territoryPoints");
                    territoryPoints.setValue("" + currentTerritory.bonus());
                    territory.setAttributeNode(territoryPoints);
                    
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
            for(Territory currentTerritory : Territory.allTerritories()){

                
                if(currentTerritory.continent() == null){
                    
                    Element unoccupiedTerritory = doc.createElement("unoccupiedTerritory");
                    rootElement.appendChild(unoccupiedTerritory);
                    
                    ArrayList<TerritoryHex> composingHexes = currentTerritory.composingHex();
                    
                    //Appends hexes to territory
                    for(TerritoryHex currentHex : composingHexes){
                        
                        Element hex = doc.createElement("hex");
                        unoccupiedTerritory.appendChild(hex);
                        
                        //Gives attributes to hexes
                        Attr hexX = doc.createAttribute("hexX");
                        hexX.setValue("" + currentHex.coordinates().hexCoord[0]);
                        hex.setAttributeNode(hexX);
                        
                        Attr hexY = doc.createAttribute("hexY");
                        hexY.setValue("" + currentHex.coordinates().hexCoord[1]);
                        hex.setAttributeNode(hexY);
                        
                    }
                    
                    //Appends infoHex to territory
                    Element infoHex = doc.createElement("infoHex");
                    unoccupiedTerritory.appendChild(infoHex);

                    Attr infoHexX = doc.createAttribute("infoHexX");
                    infoHexX.setValue("" + currentTerritory.terrInfo().linkedTerrHex().coordinates().hexCoord[0]);
                    infoHex.setAttributeNode(infoHexX);
                    
                    Attr infoHexY = doc.createAttribute("infoHexY");
                    infoHexY.setValue("" + currentTerritory.terrInfo().linkedTerrHex().coordinates().hexCoord[1]);
                    infoHex.setAttributeNode(infoHexY);
                    
                    //Gives unoccupied territory attributes
                    Attr territoryPoints = doc.createAttribute("territoryPoints");
                    territoryPoints.setValue("" + currentTerritory.bonus());
                    unoccupiedTerritory.setAttributeNode(territoryPoints);
                }
                    
                }
                
            }
            
            //Saves
            String fileName = JOptionPane.showInputDialog("Enter file name");
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);
            
        }catch(HeadlessException | IllegalStateException | ParserConfigurationException | TransformerException | DOMException e){
            e.printStackTrace(System.out);
            MyWorld.theWorld.escape();
            
        }
   
    }*/
    
    public void makeTransparent() {
        getImage().setTransparency(MyWorld.TRANSPARENT);
    
    }
    
    public void makeOpaque() {
        getImage().setTransparency(MyWorld.OPAQUE);
    
    }
}
