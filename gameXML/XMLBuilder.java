package gameXML;

import base.Game;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import game.Player;
import game.TurnStat;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
     * @return a xml Document representing fromGame
     */
    protected Document build(Game fromGame) throws Exception {
        try {
            game = fromGame;
            createNewDocument();
            addMapName();
            addDifficulty();
            createPlayerNodes();
            addGameState();
            if(game.stats != null) {
                createStatsNode();
            }
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
            List<Territory> ctrlTerrs = player.territories();
            for(Territory terr : ctrlTerrs) {
                Element terrNode = doc.createElement("Territory");
                playerNode.appendChild(terrNode);
                terrNode.setAttribute("armies", terr.armies() + "");
                terrNode.setAttribute("terrID", terr.id() + "");
            
            }
        
        }
        
    
    }

    private void createStatsNode() {
        for(TurnStat stat : game.stats) {
            Element statNode = doc.createElement("TurnStat");
            rootElement.appendChild(statNode);
            statNode.setAttribute("turnNumber", stat.turnNumber + "");
            
            //number of armies
            createPlayerStatElement(stat.numberOfArmies, "PlayerArmyPair", "armies", statNode);
            
            
            //number of armies per turn
            createPlayerStatElement(stat.numberOfArmiesPerTurn, "PlayerArmyPerTurnPair", "armiesPerTurn", statNode);
            
            //number of Continents
            createPlayerStatElement(stat.numberOfContinents, "PlayerContinentsPair", "continents", statNode);
            
            //number of points
            createPlayerStatElement(stat.numberOfPoints, "PlayerPointsPair", "points", statNode);
            
            //number of territories
            createPlayerStatElement(stat.numberOfTerritories, "PlayerTerritoriesPair", "territories", statNode);
            
        }
    }
    
    private void createPlayerStatElement(Map<Player,Integer> createFrom, String elementName, String valueName, Element statNode) { 
        Set<Map.Entry<Player, Integer>> playerValuePairSet = createFrom.entrySet();
        for(Map.Entry<Player, Integer> playerValuePair : playerValuePairSet) {
            Player player = playerValuePair.getKey();
            int playerNumber = game.players.indexOf(player);
            Integer value = playerValuePair.getValue();
            Element pairNode = doc.createElement(elementName);
            statNode.appendChild(pairNode);
            pairNode.setAttribute("playerNumber", playerNumber + "");
            pairNode.setAttribute(valueName, value + "");
            
        }
    
    }

    private void addGameState() {
        rootElement.setAttribute("gameState", game.gameState.name());
        
    }
    
}
