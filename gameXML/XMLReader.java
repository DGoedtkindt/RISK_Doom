package gameXML;

import appearance.MessageDisplayer;
import base.Game;
import base.Map;
import game.Difficulty;
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
            Element player = (Element)players.item(p);
            
        }
          
        throw new UnsupportedOperationException("Not supported yet");  
    }
    
    
    
}
