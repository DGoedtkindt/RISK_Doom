package mapXML;

import base.Map;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import mainObjects.Continent;
import mainObjects.LinkIndic;
import mainObjects.Links;
import mainObjects.Territory;
import mainObjects.TerritoryHex;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

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
            TerritoryHex infoHex = terr.infoHex();
            infoHexNode.setAttribute("hexX", "" + infoHex.hexCoord()[0]);
            infoHexNode.setAttribute("hexY", "" + infoHex.hexCoord()[1]);
            
            //rajouter tous les hexs
            List<TerritoryHex> hexList = terr.composingHex();
            for(TerritoryHex hex: hexList) {
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
            
            List<Territory> terrContained = cont.containedTerritories();
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
        for(Links links : map.links) {
            Element linksNode = doc.createElement("Links");
            rootElement.appendChild(linksNode);
            List<LinkIndic> linksContained = links.LinkIndicsList();
            String linksColor = links.color().toRGB();
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
   
}
