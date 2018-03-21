package gameXML;

import base.GColor;
import base.Game;
import game.Difficulty;
import game.Player;
import game.Zombie;
import mapXML.MapXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * The Class able to read a XML File and to reproduce a Game from the informations it gets.
 * 
 */
public class XMLReader {
    private final Game GAME = new Game();
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
        getTurnNumber();

        return GAME;
    
    }
    
    /**
     * Gets the Map.
     */
    private void getMap() throws Exception {
        rootElement = doc.getDocumentElement();
        String mapName = rootElement.getAttribute("mapName");
        MapXML mapXML = new MapXML(mapName);
        GAME.map = mapXML.getMap();
    }
    
    /**
     * Gets the Difficulty.
     */
    private void getDifficulty() {
        String difficultyName = rootElement.getAttribute("difficulty");
        GAME.difficulty = Difficulty.valueOf(difficultyName);
        
    }
    
    /**
     * Gets the Players and their attributes.
     */
    private void getPlayerAndArmies() {
        NodeList players = doc.getElementsByTagName("Player");
        
        for(int p = 0; p < players.getLength(); p++) {
            Element playerNode = (Element)players.item(p);
            String colorString = playerNode.getAttribute("color");
            GColor color = GColor.fromRGB(colorString);
            String name = playerNode.getAttribute("name");
            
            Player player;
            if(color.equals(Zombie.ZOMBIE_COLOR)){
                player = new Zombie(GAME.difficulty);
            }else{
                player = new Player(name,color);
            }
            
            player.setArmiesInHand(Integer.parseInt(playerNode.getAttribute("armiesInHand")));
            GAME.players.add(player);
            
            NodeList territories = playerNode.getElementsByTagName("Territory");
            for(int t = 0; t < territories.getLength(); t++) {
                Element territory = (Element)territories.item(t);
                int terrID = Integer.parseInt(territory.getAttribute("terrID"));
                int armies = Integer.parseInt(territory.getAttribute("armies"));
                GAME.map.territories.get(terrID).setArmies(armies);
                GAME.map.territories.get(terrID).setOwnerWithoutDrawing(player);
                
            }
            
        }
        
    }
    
    /**
     * Gets the State of the Game.
     */
    private void getGameState() {
        String gameStateName = rootElement.getAttribute("gameState");
        Game.State gameState = Game.State.valueOf(gameStateName);
        GAME.gameState = gameState;
    }

    private void getTurnNumber() {
        
        String turnNumberString = rootElement.getAttribute("turnNumber");
        
        int turnNumber = 0;
        
        if(!turnNumberString.equals("") && !turnNumberString.matches("\\s+")){
            turnNumber = Integer.parseInt(rootElement.getAttribute("turnNumber"));
        }
        
        GAME.turnNumber = turnNumber;
        
    }
    
}
