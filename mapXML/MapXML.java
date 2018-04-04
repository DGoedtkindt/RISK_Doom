package mapXML;

import appearance.MessageDisplayer;
import base.Map;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

/** 
 * Stores the XML Document representing a Map
 */
public class MapXML {
    
    private static final File DIR;
    
    private Document xml;
    private String name;
    private final XMLReader READER = new XMLReader();
    private final XMLBuilder BUILDER = new XMLBuilder();
    
    static{
        DIR = new File("Maps");
        if(!DIR.exists()) {
            MessageDisplayer.showException(new FileNotFoundException("The Maps directory does not seem to exist." +
                    "Please make sure " + DIR.getAbsolutePath() + " exists"));
        }
        
    }
    
    /**  
     * Reads a File and creates a new MapXML from it
     * @param mapFile the file containing the Map's XML
     * @throws java.io.FileNotFoundException
    */
    public MapXML(File mapFile) throws Exception {
        if(mapFile.exists() && mapFile.isFile()) {
            xml = BUILDER.build(mapFile);
            name = mapFile.getName().replace(".xml", "");
        } else throw new FileNotFoundException();
        
    }
    
    /** 
     * Gets the Map file with this name and creates a new MapXML from it
     * @param mapName the name of file containing the Map's XML. (without the ".xml" suffix)
    */
    public MapXML(String mapName) throws Exception{
        name = mapName;
        File mapFile = new File(DIR.getAbsolutePath() + "/" + name + ".xml");
        if(mapFile.exists() && mapFile.isFile()) {
            xml = BUILDER.build(mapFile);
            name = mapFile.getName().replace(".xml", "");
        } else throw new FileNotFoundException("file " + mapFile.getName() + " was not found");
        
    }
    
    /** 
     * Creates a new MapXML from a Map object
     * @param map The Map.
     * @throws java.lang.Exception
    */
    public MapXML(Map map) throws Exception{
        name = map.name;
        xml = BUILDER.build(map);
    }
    
    /** 
     * Reads the Map's XML to create a Map object.
     * @return the Map object representing this MapXML
     * @throws java.lang.Exception
     */
    public Map getMap() throws Exception {
        Map map = READER.getMap(xml);
        map.name = name;
        return map;
    
    }
    
    /** Writes the content of this MapXML onto a file
     * @param mapName The name of this Map.
     * @param description The description of this Map.
    */
    public void write(String mapName, String description) {
        try{
            name = mapName;
            //rajouter la description
            xml.getDocumentElement().setAttribute("description", description);

            //créer le fichier
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(xml);
            

            //écrire le fichier
            StreamResult result = new StreamResult(new File(DIR.getAbsolutePath() + "/" + name + ".xml"));
            transformer.transform(source, result);
            
            MessageDisplayer.showMessage("Map saved.");
            
        } catch(HeadlessException | TransformerException ex) {
            String message = "Map Couldn't be saved";
            MessageDisplayer.showException(new Exception(message, ex));

        }
    
    }
    
}
