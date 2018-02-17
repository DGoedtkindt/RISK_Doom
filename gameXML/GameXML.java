package gameXML;

import appearance.MessageDisplayer;
import org.w3c.dom.Document;
import java.io.FileNotFoundException;
import java.io.File;
import base.Game;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/** Stores the XML Document representing a Game
 */
public class GameXML {
    private Document xml;
    private XMLReader reader = new XMLReader();
    private XMLBuilder builder = new XMLBuilder();
    
    /**  Reads a File and creates a new GameXML from it.
     * @param gameFile the file containing the game's XML
     * @throws java.io.FileNotFoundException
    */
    public GameXML(File gameFile) throws Exception {
        if(gameFile.exists() && gameFile.isFile()) xml = builder.build(gameFile);
        else throw new FileNotFoundException();
        
    }
    
    /** Creates a new GameXML from a Game object.
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
    
    public void write(String gameName, String description) {
        //add The description
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
            
            System.out.println("Game was succesfully saved");
            
        } catch (UnsupportedOperationException | TransformerException ex) {
            ex.printStackTrace();
            MessageDisplayer.showMessage("Map couldn't be saved   : " + ex);
            
        }
        
    }
    
}
