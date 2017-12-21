package gameXML;

import base.Game;
import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLBuilder {
    
    private Game game;
    private Document doc;
    private Element rootElement;
    
    /**builds a XML Document from a Game Object
     *
     * @param fromGame
     * @throws java.lang.Exception
     */
    Document build(Game fromGame) {
        throw new UnsupportedOperationException("Not supported yet."); 

    }
    
    /** Builds the Document from a XML File
     * 
     * @throws java.lang.Exception
     */
    Document build(File gameFile) {
        throw new UnsupportedOperationException("Not supported yet.");
    
    }
    
}
