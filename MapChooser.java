import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class MapChooser extends XMLChooser {
    private static MyWorld world() {return MyWorld.theWorld;}
    
    public MapChooser(){
            super("Maps", "New Map");
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
            
        }catch(IOException | ParserConfigurationException | DOMException | SAXException e){System.err.println(e.getMessage());}
        
        if(!mapDescription.equals("")){
            return mapDescription;
        }else{return "This map has no description";}
        

    }

    @Override
    public void clicked() {
        world().setupMapEditorScene();
        MyWorld.readXMLMap(currentFile());
        world().escape();
    }
    
    
    
    
}
