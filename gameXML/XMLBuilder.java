package gameXML;

import appearance.MessageDisplayer;
import base.Game;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import mainObjects.Player;
import mainObjects.Territory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XMLBuilder {
    
    private Game game;
    private Document doc;
    private Element rootElement;
    
    /**builds a XML Document from a Game Object
     *
     * @param fromGame
     */
    protected Document build(Game fromGame) throws Exception {
        try {
            game = fromGame;
            createNewDocument();
            addMapName();
            addActivePlayer();
            addDifficulty();
            addMapName();
            createPlayerNodes();
            return doc;
        } catch (Exception ex) {
            MessageDisplayer.showMessage("Couldn't create Document from Game");
            throw ex;
        }

    }
    
    /** Builds the Document from a XML File
     * 
     * @param gameFile
     */
    protected Document build(File gameFile) throws Exception {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(gameFile);
            doc.getDocumentElement().normalize();
            
            return doc;
        } catch(IOException | ParserConfigurationException | SAXException ex) {
            MessageDisplayer.showMessage(ex.getMessage());
            throw ex;
        
        }
    }
    
    private void createNewDocument() throws Exception{
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();
            rootElement = doc.createElement("game");//Création de rootElement
            doc.appendChild(rootElement);
        
    }
    
    private void addMapName() {
        rootElement.setAttribute("mapName", game.map.name);
    
    }
    
    private void addActivePlayer() {
        System.err.println("Active player can't be saved yet");
        
    }
    
    private void addDifficulty() {
        rootElement.setAttribute("difficulty", game.difficulty.toString());
    
    }
    
    private void createPlayerNodes() {
        for(Player player : game.players) {
            Element playerNode = doc.createElement("Player");
            rootElement.appendChild(playerNode);
            
            //player's attributes
            playerNode.setAttribute("name", player.name());
            playerNode.setAttribute("color", player.color().toRGB());
            playerNode.setAttribute("points", player.points() + "");
            
            //player's territories
            ArrayList<Territory> ctrlTerrs = player.territories();
            for(Territory terr : ctrlTerrs) {
                Element terrNode = doc.createElement("Territory");
                playerNode.appendChild(terrNode);
                terrNode.setAttribute("armies", terr.armies() + "");
            
            }
        
        }
        
    
    }
    
    private void addStats() {
        throw new UnsupportedOperationException("not supported yet");
    }
    
}
