package mapXML;

import appearance.MessageDisplayer;
import base.Map;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOError;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

/** Stores the XML Document representing a Map
 */
public class MapXML {
    
    
    private static final File DIR;
    
    private Document xml;
    private String name;
    private XMLReader reader = new XMLReader();
    private XMLBuilder builder = new XMLBuilder();
    
    static{
        DIR = new File("Maps");
        if(!DIR.exists()) {
            throw new IOError(new FileNotFoundException("The Maps directory does not seem to exist." +
                    "Please make sure " + DIR.getAbsolutePath() + " exists"));
        }
        
    }
    
    /**  Reads a File and creates a new MapXML from it
     * @param mapFile the file containing the Map's XML
     * @throws java.io.FileNotFoundException
    */
    public MapXML(File mapFile) throws Exception {
        if(mapFile.exists() && mapFile.isFile()) {
            xml = builder.build(mapFile);
            name = mapFile.getName().replace(".xml", "");
        } else throw new FileNotFoundException();
        
    }
    
    /** Gets the Map file with this name and creates a new MapXML from it
     * @param mapName the name of file containing the Map's XML.
    */
    public MapXML(String mapName) throws Exception{
        name = mapName;
        File mapFile = new File(DIR.getAbsolutePath() + "/" + name + ".xml");
        if(mapFile.exists() && mapFile.isFile()) {
            xml = builder.build(mapFile);
            name = mapFile.getName().replace(".xml", "");
        } else throw new FileNotFoundException();
        
    }
    
    /** Creates a new MapXML from a Map object
     * @throws java.lang.Exception
    */
    public MapXML(Map map) throws Exception{
        name = map.name;
        xml = builder.build(map);
    }
    
    /** Reads the Map's XML to create a Map object.
     * @return the Map object representing this MapXML
     * @throws java.lang.Exception
     */
    public Map getMap() throws Exception {
        Map map = reader.getMap(xml);
        map.name = name;
        return map;
    
    }
    
    /** writes the content of this MapXML onto a file
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
            
            System.out.println("Map was succesfully saved");
            
        } catch(HeadlessException | TransformerException e) {
            MessageDisplayer.showMessage("Map couldn't be saved   : " + e);

        }
    
    }
    
    /**  calculates the checksum of the document
     *  to check if the map's XML has been corrupted.
     * @return a int unique to the MapXML containing the same Map 
    */
    public int calculateChecksum() {
        throw new UnsupportedOperationException();
    
    }

}
