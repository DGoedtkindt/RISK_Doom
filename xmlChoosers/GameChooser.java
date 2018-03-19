package xmlChoosers;

import appearance.MessageDisplayer;
import base.Game;
import gameXML.GameXML;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/** 
 * XMLChooser for Games.
 */
public class GameChooser extends XMLChooser{
    private final static String DIRECTORY_NAME = "Games";

    public GameChooser() {
        super(DIRECTORY_NAME);
    }

    @Override
    protected String getDescription(String fileName) {
        String mapDescription = "";
        Document doc;
        
        try{
        
            File XMLFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(XMLFile);
            doc.getDocumentElement().normalize();
            
            Element mapElement = ((Element)(doc.getElementsByTagName("game").item(0)));
            
            if(mapElement.hasAttribute("description")){
                mapDescription = mapElement.getAttribute("description");
                
            }
            
        }catch(IOException | ParserConfigurationException | DOMException | SAXException ex){
            String message = "Couldn't get the Game's description";
            MessageDisplayer.showException(new Exception(message, ex));
            return null;
        }
        
        if(!mapDescription.equals("")){
            return mapDescription;
        }else{return "This Game has no description";}
        

    }

    public Game getSelectedGame() throws Exception{
        try{
                File gameFile = getCurrentFile();
                GameXML gameXML = new GameXML(gameFile);
                Game game = gameXML.getGame();
                return game;
                 
            } catch(Exception ex) {
                throw new Exception("Couldn't create GameXML from File",ex);
                
            }
    }

}
