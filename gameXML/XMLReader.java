package gameXML;

import base.GColor;
import base.Game;
import base.Map;
import game.Difficulty;
import game.TurnStat;
import java.util.HashMap;
import mainObjects.Player;
import mapXML.MapXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XMLReader {
    private Game game = new Game();
    private Map map;
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
            getDifficulty();
            getPlayerAndArmies();
            getStats();
   
            return game;
    
    }

    private void getMap() throws Exception {
        rootElement = doc.getDocumentElement();
        String mapName = rootElement.getAttribute("mapName");
        MapXML mapXML = new MapXML(mapName + ".xml");
        map = mapXML.getMap();
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
            NodeList playerArmiesPairs = doc.getElementsByTagName("PlayerArmyPair");
            HashMap<Player, Integer> numberOfArmies  = new HashMap<>();
            for(int pap = 0; pap < playerArmiesPairs.getLength(); pap++) {
                Element playerArmiesPairNode = (Element) playerArmiesPairs.item(pap);
                int playerNumber = Integer.parseInt(playerArmiesPairNode.getAttribute("playerNumber"));
                int armies = Integer.parseInt(playerArmiesPairNode.getAttribute("armies"));
                Player player = game.players.get(playerNumber);
                numberOfArmies.put(player, armies);
                
            }
            
            //number of armies per turn
            NodeList playerArmyPerTurnPairs = doc.getElementsByTagName("PlayerArmyPerTurnPair");
            HashMap<Player, Integer> numberOfArmiesPerTurn = new HashMap<>();
            for(int pap = 0; pap < playerArmyPerTurnPairs.getLength(); pap++) {
                Element playerArmyPerTurnPairNode = (Element) playerArmyPerTurnPairs.item(pap);
                int playerNumber = Integer.parseInt(playerArmyPerTurnPairNode.getAttribute("playerNumber"));
                int armiesPerTurn = Integer.parseInt(playerArmyPerTurnPairNode.getAttribute("armiesPerTurn"));
                Player player = game.players.get(playerNumber);
                numberOfArmiesPerTurn.put(player, armiesPerTurn);
                
            }
            
            //number of continents
            NodeList playerContinentPairs = doc.getElementsByTagName("PlayerContinentsPair");
            HashMap<Player, Integer> numberOfContinents = new HashMap<>();
            for(int pap = 0; pap < playerContinentPairs.getLength(); pap++) {
                Element playerContinentPairNode = (Element) playerContinentPairs.item(pap);
                int playerNumber = Integer.parseInt(playerContinentPairNode.getAttribute("playerNumber"));
                int continents = Integer.parseInt(playerContinentPairNode.getAttribute("continents"));
                Player player = game.players.get(playerNumber);
                numberOfContinents.put(player, continents);
                
            }
            
        }

        System.out.println("Method getStats() in class XMLReader is not supported yet");
        
    }
    
    
    
}
