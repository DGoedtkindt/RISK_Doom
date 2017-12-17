package mapXML;

import base.Map;
import java.io.File;
import org.w3c.dom.Document;

public class MapXML {
    protected Document xml;
    protected XMLReader reader;
    protected XMLBuilder builder;
    
    /**  Reads a File and creates a new MapXML from it
    */
    public MapXML(String mapName) {
        throw new UnsupportedOperationException("Not supported yet.");
        
    }
    
    /** Creates a new MapXML from a Map object
    */
    public MapXML(Map map) {
        throw new UnsupportedOperationException("Not supported yet.");
        
    }
    
    /** writtes the content of this MapXML onto a file
     *
     * @return 1 if it succeded, and 0 if it failed
    */
    public int writte(String mapName, String description) {
        throw new UnsupportedOperationException("Not supported yet.");
    
    }
    
    /**  calculates the checksum of the document.
     *  to check if the map's XML has been corrupted.
    */
    public int calculateChecksum() {
        throw new UnsupportedOperationException("Not supported yet.");
    
    }
}
