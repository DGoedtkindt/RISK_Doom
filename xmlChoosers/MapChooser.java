package xmlChoosers;

import appearance.MessageDisplayer;
import arrowable.Arrowable;
import base.Map;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import mapXML.MapXML;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class MapChooser extends XMLChooser implements Arrowable{
    
    public MapChooser(boolean isNewMapAChoice){
            super("Maps", "New Map",isNewMapAChoice);
    }

    @Override
    protected String getDescription(String fileName) {
        String mapDescription = "";
        Document doc;
        
        try{
        
            File XMLFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(XMLFile);
            doc.getDocumentElement().normalize();
            
            Element mapElement = ((Element)(doc.getElementsByTagName("map").item(0)));
            
            if(mapElement.hasAttribute("description")){
                mapDescription = mapElement.getAttribute("description");
                
            }
            
        }catch(IOException | ParserConfigurationException | DOMException | SAXException e){
            MessageDisplayer.showMessage(e.getMessage());
            System.err.println(e.getMessage());
        }
        
        if(!mapDescription.equals("")){
            return mapDescription;
        }else{return "This map has no description";}
        

    }
    
    public Map getSelectedMap() throws Exception {
        try{
                File mapFile = getCurrentFile();
                MapXML mapXML = new MapXML(mapFile);
                Map map = mapXML.getMap();
                return map;
                 
            } catch(Exception ex) {
                throw new Exception("Couldn't create MapXML from File");
                
            }
        
    }
    
    
}
