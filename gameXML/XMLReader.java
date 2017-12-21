package gameXML;

import base.Game;
import org.w3c.dom.Document;

public class XMLReader {
    private Game game = new Game();
    private Document doc;

    /**
     * @param fromDoc Document from where you create the Game
     * @return the Game representation of the Document
     * @throws java.lang.Exception
     */
    Game getGame(Document xml) {
        throw new UnsupportedOperationException("Not supported yet.");
    
    }
    
}
