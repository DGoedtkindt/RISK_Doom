package mapXML;

import base.GColor;
import base.Map;
import java.util.ArrayList;
import base.BlankHex;
import java.util.HashSet;
import territory.Continent;
import territory.Links;
import territory.Territory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLReader {
    
    private final Map MAP = new Map();
    private Document doc;
    
    /**
     * @param fromDoc Document from where you create the Map
     * @return the Map representation of the Document
     * @throws java.lang.Exception
     */
    protected Map getMap(Document fromDoc) throws Exception {
        try {
            doc = fromDoc;
            createTerritories();
            createContinents();
            createLinks();
            
            return MAP;
        } catch (Exception ex) {
            String message = "couldn't get Map from Document";
            throw new Exception(message, ex);
        }

    }
    
    private void createTerritories() throws Exception{
        NodeList terrNodeList = doc.getElementsByTagName("Territory");
        
        for(int i = 0; i < terrNodeList.getLength(); i++) {
            Element terrNode = (Element)terrNodeList.item(i);
            HashSet<BlankHex> hexContained = new HashSet<>();
            
            BlankHex infoHex = null;
            int bonus = Integer.parseInt(terrNode.getAttribute("bonus"));
            
            NodeList allChildren = terrNode.getChildNodes();
            for(int j=0; j<allChildren.getLength();j++) {
                Node child = allChildren.item(j);
                
                if(child.getNodeName().equals("Hex")) {
                   Element hex = (Element)child;
                   int hexX = Integer.parseInt(hex.getAttribute("hexX"));
                   int hexY = Integer.parseInt(hex.getAttribute("hexY"));
                   hexContained.add(BlankHex.blankHexAt(hexX,hexY));

                } else if(child.getNodeName().equals("InfoHex")) {
                    Element infoHexNode = (Element)child;
                    int hexX = Integer.parseInt(infoHexNode.getAttribute("hexX"));
                    int hexY = Integer.parseInt(infoHexNode.getAttribute("hexY"));
                    infoHex = BlankHex.blankHexAt(hexX,hexY);

                }

            }
            MAP.territories.add(new Territory(hexContained, infoHex, bonus));
            
            }
    
    }
    
    private void createContinents() throws Exception{
        NodeList contNodeList = doc.getElementsByTagName("Continent");
        
        for(int i = 0; i < contNodeList.getLength(); i++) {
            Element contNode = (Element)contNodeList.item(i);
            
            ArrayList<Territory> terrContained = new ArrayList<>();
            int bonus = Integer.parseInt(contNode.getAttribute("bonus"));
            String colorString = contNode.getAttribute("color");
            GColor color = GColor.fromRGB(colorString);
            
            NodeList allTerrIDs = contNode.getChildNodes();
            for(int j=0; j<allTerrIDs.getLength();j++) {
                Element terrIdNode = (Element)allTerrIDs.item(j);
                int terrId = Integer.parseInt(terrIdNode.getAttribute("id"));
                Territory terr = MAP.territories.get(terrId);
                terrContained.add(terr);
            
            }
            
            MAP.continents.add(new Continent(terrContained,color,bonus));
            
        }
    
    }
    
    private void createLinks() throws Exception{
        NodeList linksNodeList = doc.getElementsByTagName("Links");
        
        for(int i = 0; i<linksNodeList.getLength();i++) {
            Element linksNode = (Element)linksNodeList.item(i);

            String colorString = linksNode.getAttribute("color");
            GColor color = GColor.fromRGB(colorString);
            Links newLinks = new Links(color);
            
            NodeList linkNodesList = linksNode.getElementsByTagName("Link");
            for (int j=0; j<linkNodesList.getLength();j++) {
                Element linkNode = (Element)linkNodesList.item(j);
                int xPos = Integer.parseInt(linkNode.getAttribute("xPos"));
                int yPos = Integer.parseInt(linkNode.getAttribute("yPos"));
                int terrId = Integer.parseInt(linkNode.getAttribute("terrId"));
                Territory terr = MAP.territories.get(terrId);
                newLinks.addlink(terr, new int[]{xPos,yPos});
            
            }
        
        }
    
    }
    
}
