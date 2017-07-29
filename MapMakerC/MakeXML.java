import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.util.*;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MakeXML extends Button
{
    public void clicked(int mode){
        
        saveToXML();
        
    }
    
    // Inspiré de https://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
    private void saveToXML(){
        
        ArrayList continentList = MyWorld.theWorld.getContinentList();
        
        try{
            
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("map");//Création de rootElement
            doc.appendChild(rootElement);
            
            
            for(Object cContinent : continentList){//append des continents à la map (et caractéristiques)
                
                Continent currentContinent = (Continent)cContinent;
                
                Element continent = doc.createElement("continent");
                rootElement.appendChild(continent);
                
                List<Territory> territoriesInContinent = currentContinent.getContainedTerritories();
                
                for(Territory currentTerritory : territoriesInContinent){//append des territoires aux continents (et caractéristiques)
                    
                    Element territory = doc.createElement("territory");
                    continent.appendChild(territory);
                    
                    int hexNumber = 0;
                    
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
                    
                    Attr capitalPoints = doc.createAttribute("capitalPoints");

                    capitalPoints.setValue("" + currentTerritory.getBonusPoints());

                    territory.setAttributeNode(capitalPoints);
                    
                    Attr territoryOwner = doc.createAttribute("territoryOwner");
                    territoryOwner.setValue("");
                    territory.setAttributeNode(territoryOwner);
                    
                    Attr armies = doc.createAttribute("territoryArmies");
                    armies.setValue("0");
                    territory.setAttributeNode(armies);
                    
                    Attr id = doc.createAttribute("territoryId");
                    id.setValue("" + currentTerritory.getId());
                    territory.setAttributeNode(id);
                    
                    
                    

                    List<Territory> borderingTerritories = currentTerritory.getBorderTerritories();
                    
                    for(Territory currentBordering : borderingTerritories){//append des limitrophes aux territoires
                        
                        Element bordering = doc.createElement("borderingTerritory");
                        territory.appendChild(bordering);
                        
                        Attr borderingID = doc.createAttribute("borderingID");
                        borderingID.setValue("" + currentBordering.getId());
                        bordering.setAttributeNode(borderingID);
                        
                    }
                    
                }
                
                
                Attr continentPoints = doc.createAttribute("continentPoints");
                continentPoints.setValue("" + currentContinent.getContinentColor());
                continent.setAttributeNode(continentPoints);
                
                Attr rContinentColor = doc.createAttribute("rContinentColor");
                rContinentColor.setValue("" + (currentContinent.getContinentColor()).getRed());
                continent.setAttributeNode(rContinentColor);
                
                Attr gContinentColor = doc.createAttribute("gContinentColor");
                gContinentColor.setValue("" + (currentContinent.getContinentColor()).getGreen());
                continent.setAttributeNode(gContinentColor);
                
                Attr bContinentColor = doc.createAttribute("bContinentColor");
                bContinentColor.setValue("" + (currentContinent.getContinentColor()).getBlue());
                continent.setAttributeNode(bContinentColor);
                
            }
            
            
            String fileName = JOptionPane.showInputDialog("Entrez le nom du fichier");
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);
            
        }catch(Exception e){
            
            System.out.println("Erreur xml" + e.getMessage());
            
        }

        
    }
}
