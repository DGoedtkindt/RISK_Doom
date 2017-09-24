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
    public MakeXML(){
        
        GreenfootImage image = new GreenfootImage("MakeXML.png");

        image.scale(80, 80);
        this.setImage(image);
        
    }
    
    public void clicked(){
        
        saveToXML();
        
    }
    
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
                    
                    ArrayList<TerritoryHex> composingHexes = currentTerritory.composingHex();
                    
                    //Appends hexes to territory
                    for(TerritoryHex currentHex : composingHexes){
                        
                        Element hex = doc.createElement("hex");
                        territory.appendChild(hex);
                        
                        for(LinkSpot currentSpot : currentHex.linksPlacedInIt){
                            
                            Element linkSpot = doc.createElement("linkSpot");
                            hex.appendChild(linkSpot);
                            
                            linkSpot.setAttribute("rLinkColor", "" + currentSpot.specificLink().color().getRed());
                            linkSpot.setAttribute("gLinkColor", "" + currentSpot.specificLink().color().getGreen());
                            linkSpot.setAttribute("bLinkColor", "" + currentSpot.specificLink().color().getBlue());
                            linkSpot.setAttribute("linkRelX", "" + currentSpot.relativePosition[0]);
                            linkSpot.setAttribute("linkRelY", "" + currentSpot.relativePosition[1]);
                        }
                        
                        //Gives attributes to hexes
                        hex.setAttribute("hexX", "" + currentHex.coordinates().hexCoord[0]);
                        hex.setAttribute("hexY", "" + currentHex.coordinates().hexCoord[1]);
                        
                    }
                    //Appends infoHex to territory
                    Element infoHex = doc.createElement("infoHex");
                    territory.appendChild(infoHex);

                    infoHex.setAttribute("infoHexX", "" + currentTerritory.terrInfo().linkedTerrHex().coordinates().hexCoord[0]);
                    infoHex.setAttribute("infoHexY", "" + currentTerritory.terrInfo().linkedTerrHex().coordinates().hexCoord[1]);
                    
                    //Gives attributes to territory
                    territory.setAttribute("territoryPoints", "" + currentTerritory.bonus());
                    
                //Gives attributes to continent
                continent.setAttribute("continentPoints", "" + currentContinent.bonus());
                
                continent.setAttribute("rContinentColor", "" + currentContinent.color().getRed());
                continent.setAttribute("gContinentColor", "" + currentContinent.color().getGreen());
                continent.setAttribute("bContinentColor", "" + currentContinent.color().getBlue());
                
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
                        
                        for(LinkSpot currentSpot : currentHex.linksPlacedInIt){
                            
                            Element linkSpot = doc.createElement("linkSpot");
                            hex.appendChild(linkSpot);
                            
                            linkSpot.setAttribute("rLinkColor", "" + currentSpot.specificLink().color().getRed());
                            linkSpot.setAttribute("gLinkColor", "" + currentSpot.specificLink().color().getGreen());
                            linkSpot.setAttribute("bLinkColor", "" + currentSpot.specificLink().color().getBlue());
                            linkSpot.setAttribute("linkRelX", "" + currentSpot.relativePosition[0]);
                            linkSpot.setAttribute("linkRelY", "" + currentSpot.relativePosition[1]);
                        }
                        
                        //Gives attributes to hexes
                        hex.setAttribute("hexX", "" + currentHex.coordinates().hexCoord[0]);
                        hex.setAttribute("hexY", "" + currentHex.coordinates().hexCoord[1]);
                        
                    }
                    
                    //Appends infoHex to territory
                    Element infoHex = doc.createElement("infoHex");
                    unoccupiedTerritory.appendChild(infoHex);
                    
                    infoHex.setAttribute("infoHexX", "" + currentTerritory.terrInfo().linkedTerrHex().coordinates().hexCoord[0]);
                    infoHex.setAttribute("infoHexY", "" + currentTerritory.terrInfo().linkedTerrHex().coordinates().hexCoord[1]);
                    
                    //Gives unoccupied territory attributes
                    unoccupiedTerritory.setAttribute("territoryPoints", "" + currentTerritory.bonus());
                    
                }
                    
                }
                
            }
            
            //appends links to the map
            for(Link currentLink : Link.allLinks){
                
                Element link = doc.createElement("link");
                rootElement.appendChild(link);
                
                link.setAttribute("rLinkColor", "" + currentLink.color().getRed());
                link.setAttribute("gLinkColor", "" + currentLink.color().getGreen());
                link.setAttribute("bLinkColor", "" + currentLink.color().getBlue());
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
   
    }
    
    public void makeTransparent() {
        getImage().setTransparency(MyWorld.TRANSPARENT);
    
    }
    
    public void makeOpaque() {
        getImage().setTransparency(MyWorld.OPAQUE);
    
    }
}
