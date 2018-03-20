package gameXML;

import base.Game;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import game.Player;
import java.util.List;
import mainObjects.Territory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * The Class that represents the Objects that builds XML Documents.
 * 
 */
public class XMLBuilder {
    
    private Game game;
    private Document doc;
    private Element rootElement;
    
    /**
     * Builds a XML Document from a Game Object
     * @param fromGame
     * @return a xml Document representing fromGame
     * @throws Exception
     */
    protected Document build(Game fromGame) throws Exception {
        try {
            game = fromGame;
            createNewDocument();
            addMapName();
            addDifficulty();
            createPlayerNodes();
            addGameState();
            addTurnNumber();
            return doc;
        } catch (Exception ex) {
            String message = "Couldn't create Document from Game";
            throw new Exception(message, ex);
        }

    }
    
    /** Builds the Document from a XML File
     * 
     * @param gameFile
     * @return the xml Document saved in gameFile
     */
    protected Document build(File gameFile) throws Exception {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(gameFile);
            doc.getDocumentElement().normalize();
            return doc;
        } catch(IOException | ParserConfigurationException | SAXException ex) {
            String message = "Couldn't create Document from File : \n";
            throw new Exception(message, ex);
        
        }
    }
    
    /**
     * Creates a new Document for the Game.
     */
    private void createNewDocument() throws Exception{
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();
            rootElement = doc.createElement("game");
            doc.appendChild(rootElement);
        
    }
    
    /**
     * Saves the name of the Map.
     */
    private void addMapName() {
        rootElement.setAttribute("mapName", game.map.name);
    
    }
    
    /**
     * Saves the Difficulty of the Game.
     */
    private void addDifficulty() {
        rootElement.setAttribute("difficulty", game.difficulty.toString());
    
    }
    
    /**
     * Saves the Players.
     */
    private void createPlayerNodes() {
        for(Player player : game.players) {
            Element playerNode = doc.createElement("Player");
            playerNode.setAttribute("armiesInHand", player.armiesInHand() + "");
            rootElement.appendChild(playerNode);
            
            //Player's attributes
            playerNode.setAttribute("name", player.name());
            playerNode.setAttribute("color", player.color().toRGB());
            playerNode.setAttribute("points", player.points + "");
            
            //Player's Territories
            List<Territory> ctrlTerrs = player.territories();
            for(Territory terr : ctrlTerrs) {
                Element terrNode = doc.createElement("Territory");
                playerNode.appendChild(terrNode);
                terrNode.setAttribute("armies", terr.armies() + "");
                terrNode.setAttribute("terrID", terr.id() + "");
            
            }
        
        }
        
    
    }
    
    /**
     * Saves the State of the Game.
     */
    private void addGameState() {
        rootElement.setAttribute("gameState", game.gameState.name());
        
    }

    private void addTurnNumber() {
        rootElement.setAttribute("turnNumber", game.turnNumber + "");
    }
    
}
