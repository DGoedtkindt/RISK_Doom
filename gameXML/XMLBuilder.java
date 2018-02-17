package gameXML;

import appearance.MessageDisplayer;
import base.Game;
import game.TurnStat;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
     * @return a xml Document representing fromGame
     */
    protected Document build(Game fromGame) throws Exception {
        try {
            game = fromGame;
            createNewDocument();
            addMapName();
            addDifficulty();
            addMapName();
            createPlayerNodes();
            if(game.stats != null) {
                createStatsNode();
            }
            return doc;
        } catch (Exception ex) {
            MessageDisplayer.showMessage("Couldn't create Document from Game");
            throw ex;
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
            MessageDisplayer.showException(ex);
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
            Set<Map.Entry<Player, Integer>> numArmies = stat.numberOfArmies.entrySet();
            for(Map.Entry<Player, Integer> entry : numArmies) {
                Player p = entry.getKey();
                int playerNumber = game.players.indexOf(p);
                int armies = entry.getValue();
                Element pairNode = doc.createElement("PlayerArmyPair");
                statNode.appendChild(pairNode);
                pairNode.setAttribute("playerNumber", playerNumber + "");
                pairNode.setAttribute("armies", armies + "");
            
            }
            
            //number of armies per turn
            Set<Map.Entry<Player, Integer>> numArmiesPerTurn = stat.numberOfArmiesPerTurn.entrySet();
            for(Map.Entry<Player, Integer> entry : numArmiesPerTurn) {
                Player p = entry.getKey();
                int playerNumber = game.players.indexOf(p);
                int armiesPerTurn = entry.getValue();
                Element pairNode = doc.createElement("PlayerArmyPerTurnPair");
                statNode.appendChild(pairNode);
                pairNode.setAttribute("playerNumber", playerNumber + "");
                pairNode.setAttribute("armiesPerTurn", armiesPerTurn + "");
            
            }
            
            //number of Continents
            Set<Map.Entry<Player, Integer>> numContinents = stat.numberOfContinents.entrySet();
            for(Map.Entry<Player, Integer> entry : numContinents) {
                Player p = entry.getKey();
                int playerNumber = game.players.indexOf(p);
                int continents = entry.getValue();
                Element pairNode = doc.createElement("PlayerContinents");
                statNode.appendChild(pairNode);
                pairNode.setAttribute("playerNumber", playerNumber + "");
                pairNode.setAttribute("continents", continents + "");
            
            }
            
            //number of armies
            Set<Map.Entry<Player, Integer>> numPoints = stat.numberOfPoints.entrySet();
            for(Map.Entry<Player, Integer> entry : numPoints) {
                Player p = entry.getKey();
                int playerNumber = game.players.indexOf(p);
                int points = entry.getValue();
                Element pairNode = doc.createElement("PlayerPointsPair");
                statNode.appendChild(pairNode);
                pairNode.setAttribute("playerNumber", playerNumber + "");
                pairNode.setAttribute("points", points + "");
            
            }
            
            //number of armies
            Set<Map.Entry<Player, Integer>> numTerritories = stat.numberOfTerritories.entrySet();
            for(Map.Entry<Player, Integer> entry : numTerritories) {
                Player p = entry.getKey();
                int playerNumber = game.players.indexOf(p);
                int territories = entry.getValue();
                Element pairNode = doc.createElement("PlayerTerritoriesPair");
                statNode.appendChild(pairNode);
                pairNode.setAttribute("playerNumber", playerNumber + "");
                pairNode.setAttribute("territories", territories + "");
            
            }
        
        }
    }
    
}
