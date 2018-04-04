package gameXML;

import appearance.MessageDisplayer;
import base.Game;
import java.io.File;
import java.io.FileNotFoundException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

/** Stores the XML Document representing a Game
 */
public class GameXML {
    private Document xml;
    private final XMLReader READER = new XMLReader();
    private final XMLBuilder BUILDER = new XMLBuilder();
    
    /**  Reads a File and creates a new GameXML from it.
     * @param gameFile the file containing the game's XML
     * @throws java.io.FileNotFoundException
    */
    public GameXML(File gameFile) throws Exception {
        if(gameFile.exists() && gameFile.isFile()) xml = BUILDER.build(gameFile);
        else throw new FileNotFoundException();
        
    }
    
    /** Creates a new GameXML from a Game object.
     * @param game The Game to save.
     * @throws java.lang.Exception
    */
    public GameXML(Game game) throws Exception{
        xml = BUILDER.build(game);
    }
    
    /** Reads the Map's XML to create a Map object.
     * @return the Map object representing this MapXML
     * @throws java.lang.Exception
     */
    public Game getGame() throws Exception {
        return READER.getGame(xml);
    
    }
    
    /**
     * Saves a Game.
     * @param gameName The name of the Game.
     * @param description The description of the Game.
     */
    public void write(String gameName, String description) {
        
        //Adds the description
        xml.getDocumentElement().setAttribute("description", description);
            
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(xml);
            
            File dir = new File("Games");
                if(!dir.exists()) System.err.println("The Games directory does not seem to exist." +
                        "Please make sure " + dir.getAbsolutePath() + " exists");
            
            StreamResult result = new StreamResult(new File(dir.getAbsolutePath() + "/" + gameName + ".xml"));
            transformer.transform(source, result);
            
            MessageDisplayer.showMessage("Game saved.");
            
        } catch (UnsupportedOperationException | TransformerException ex) {
            String message = "Map couldn't be saved";
            MessageDisplayer.showException(new Exception(message, ex));
            
        }
        
    }
    
}
