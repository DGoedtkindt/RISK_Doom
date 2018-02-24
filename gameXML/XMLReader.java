package gameXML;

import base.GColor;
import base.Game;
import game.Difficulty;
import game.Player;
import game.TurnStat;
import mapXML.MapXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XMLReader {
    private Game game = new Game();
    private Document doc;
    private Element rootElement;

    /**
     * @param fromDoc Document from where you create the Game
     * @return the Game representation of the Document
     * @throws java.lang.Exception
     */
    protected Game getGame(Document fromDoc) throws Exception {
            doc = fromDoc;
            getMap();
            getGameState();
            getDifficulty();
            getPlayerAndArmies();
            getStats();
   
            return game;
    
    }

    private void getMap() throws Exception {
        rootElement = doc.getDocumentElement();
        String mapName = rootElement.getAttribute("mapName");
        MapXML mapXML = new MapXML(mapName);
        game.map = mapXML.getMap();
    }

    private void getDifficulty() {
        String difficultyName = rootElement.getAttribute("difficulty");
        game.difficulty = Difficulty.valueOf(difficultyName);
        
    }

    private void getPlayerAndArmies() {
        NodeList players = doc.getElementsByTagName("Player");
        
        for(int p = 0; p < players.getLength(); p++) {
            Element playerNode = (Element)players.item(p);
            String colorString = playerNode.getAttribute("color");
            GColor color = GColor.fromRGB(colorString);
            String name = playerNode.getAttribute("name");
            Player player = new Player(name,color);
            game.players.add(player);
            NodeList territories = playerNode.getElementsByTagName("Territory");
            
            for(int t = 0; t < territories.getLength(); t++) {
                Element territory = (Element)territories.item(t);
                int terrID = Integer.parseInt(territory.getAttribute("terrID"));
                int armies = Integer.parseInt(territory.getAttribute("armies"));
                game.map.territories.get(terrID).armies = armies;
                game.map.territories.get(terrID).setOwnerWithoutDrawing(player);
                
            }
            
        }
        
    }

    private void getStats() {
        NodeList turnStats = doc.getElementsByTagName("TurnStat");
        for(int ts = 0; ts < turnStats.getLength(); ts++) {
            Element turnStatNode = (Element)turnStats.item(ts);
            TurnStat turnStat = new TurnStat();
            turnStat.turnNumber = Integer.parseInt(turnStatNode.getAttribute("turnNumber"));
            
            //number of armies
            storePlayerStatMap(turnStat.numberOfArmies, "PlayerArmyPair", "armies");
            
            //number of armies per turn
            storePlayerStatMap(turnStat.numberOfArmiesPerTurn, "PlayerArmyPerTurnPair", "armiesPerTurn");
            
            //number of continents
            storePlayerStatMap(turnStat.numberOfContinents, "PlayerContinentsPair", "continents");
            
            //number of points
            storePlayerStatMap(turnStat.numberOfPoints, "PlayerPointsPair", "points");
            
            //number of Territories
            storePlayerStatMap(turnStat.numberOfTerritories, "PlayerTerritoriesPair", "territories");
            
            game.stats.add(turnStat);
        }
        
    }
    
    private void storePlayerStatMap(java.util.Map<Player,Integer> storeIn, String elementName, String valueName) {
        NodeList playerValuePairs = doc.getElementsByTagName(elementName);
        for(int pvp = 0; pvp < playerValuePairs.getLength(); pvp++) {
            Element playerValuePairNode = (Element) playerValuePairs.item(pvp);
            int playerNumber = Integer.parseInt(playerValuePairNode.getAttribute("playerNumber"));
            int value = Integer.parseInt(playerValuePairNode.getAttribute(valueName));
            Player player = game.players.get(playerNumber);
            storeIn.put(player, value);

        }
    
    }

    private void getGameState() {
        String gameStateName = rootElement.getAttribute("gameState");
        Game.State gameState = Game.State.valueOf(gameStateName);
        game.gameState = gameState;
    }
    
    
    
}
