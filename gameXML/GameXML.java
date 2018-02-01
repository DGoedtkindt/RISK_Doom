package gameXML;

import org.w3c.dom.Document;
import java.io.FileNotFoundException;
import java.io.File;
import base.Game;

/** Stores the XML Document representing a Game
 */
public class GameXML {
    private Document xml;
    private XMLReader reader = new XMLReader();
    private XMLBuilder builder = new XMLBuilder();
    
    /**  Reads a File and creates a new GameXML from it
     * @param gameFile the file containing the game's XML
     * @throws java.io.FileNotFoundException
    */
    public GameXML(File gameFile) throws Exception {
           if(gameFile.exists() && gameFile.isFile()) xml = builder.build(gameFile);
           else throw new FileNotFoundException();
        
    }
    
    /** Creates a new GameXML from a Game object
     * @throws java.lang.Exception
    */
    public GameXML(Game game) throws Exception{
            xml = builder.build(game);
    }
    
    /** Reads the Map's XML to create a Map object.
     * @return the Map object representing this MapXML
     * @throws java.lang.Exception
     */
    public Game getGame() throws Exception {
        return reader.getGame(xml);
    
    }
    
    public void write(String mapName, String description) { 
        throw new UnsupportedOperationException("Not supported yet.");
        
    }
    
}
