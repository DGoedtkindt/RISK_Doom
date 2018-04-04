package mapXML;

import base.Map;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import base.BlankHex;
import java.util.Collection;
import territory.Continent;
import territory.Links;
import territory.Territory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import territory.LinkIndic;

/**
 * This Class creates a XML Document to save a Map.
 * 
 */
public class XMLBuilder {
    
    private Map map;
    private Document doc;
    private Element rootElement;
    
    /**
     * Builds a XML Document from a Map Object.
     *
     * @param fromMap
     * @return The generated Document.
     * @throws java.lang.Exception
     */
    protected Document build(Map fromMap) throws Exception{
        try {
            map = fromMap;
            createNewDocument();
            createTerritoryNodes();
            createContinentNodes();
            createLinksNodes();
            
            return doc;
        } catch (Exception ex) {
            String message = "Couldn't create Document from Map";
            throw new Exception(message, ex);
        }
    }
    
    /** 
     * Builds the Document from a XML File.
     * 
     * @param mapXML The XML File.
     * @return The generated Document.
     * @throws java.lang.Exception
     */
    protected Document build(File mapXML) throws Exception {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(mapXML);
            doc.getDocumentElement().normalize();
            
            return doc;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            String message = "Document couln't be created from File";
            throw new Exception(message, ex);
        }

    }

    
    private void createNewDocument() throws Exception{
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();
            rootElement = doc.createElement("map");
            doc.appendChild(rootElement);
        
    }
    
    private void createTerritoryNodes(){
        for(Territory terr: map.territories) {
            Element terrNode = doc.createElement("Territory");
            rootElement.appendChild(terrNode);
            
            //rajoute les coordonées du infoHex
            Element infoHexNode = doc.createElement("InfoHex");
            terrNode.appendChild(infoHexNode);
            BlankHex infoHex = terr.infoHex;
            infoHexNode.setAttribute("hexX", "" + infoHex.hexCoord()[0]);
            infoHexNode.setAttribute("hexY", "" + infoHex.hexCoord()[1]);
            
            //rajouter tous les hexs
            for(BlankHex hex: terr.blankHexSet) {
                Element HexNode = doc.createElement("Hex");
                terrNode.appendChild(HexNode);
                HexNode.setAttribute("hexX", "" + hex.hexCoord()[0]);
                HexNode.setAttribute("hexY", "" + hex.hexCoord()[1]);
            
            }
            
            //rajouter le bonus
            int terrBonus = terr.bonus();
            terrNode.setAttribute("bonus", "" + terrBonus);

        }
    }
    
    private void createContinentNodes(){
        for(Continent cont : map.continents) {
            Element contNode = doc.createElement("Continent");
            rootElement.appendChild(contNode);
            
            Collection<Territory> terrContained = cont.territoriesContained;
            for(Territory terr : terrContained) {
                Element terrInCont = doc.createElement("TerrInCont");
                terrInCont.setAttribute("id", "" + terr.id());
                contNode.appendChild(terrInCont);
            
            }
            String contColor = cont.color().toRGB();
            contNode.setAttribute("color", contColor);
            contNode.setAttribute("bonus", "" + cont.bonus());
        
        }
    
    }

    private void createLinksNodes() {
        for(Links links : map.links()) {
            Element linksNode = doc.createElement("Links");
            rootElement.appendChild(linksNode);
            HashMap<Territory,LinkIndic> terrCoordMap = links.terrIndicMap();
            String linksColor = links.COLOR.toRGB();
            linksNode.setAttribute("color", linksColor);
            
            //iterate through the Map to save each link
            for(java.util.Map.Entry<Territory, LinkIndic> terrCoord : terrCoordMap.entrySet()) {
                Territory terr = terrCoord.getKey();
                int[] coord = terrCoord.getValue().coordinate();
                Element linkNode = doc.createElement("Link");
                linksNode.appendChild(linkNode);
                int linkedTerrId = terr.id();
                linkNode.setAttribute("xPos", ""+coord[0]);
                linkNode.setAttribute("yPos", ""+coord[1]);
                linkNode.setAttribute("terrId", ""+linkedTerrId);
                
                
            }
        
        
        }
    
    }
   
}
