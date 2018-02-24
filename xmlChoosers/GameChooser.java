package xmlChoosers;

import base.Game;
import gameXML.GameXML;
import java.io.File;

/** XMLChooser for Games.
 */
public class GameChooser extends XMLChooser{
    private final static String DIRECTORY_NAME = "Games";

    public GameChooser() {
        super(DIRECTORY_NAME);
    }

    @Override
    protected String getDescription(String fileName) {
        return "Method getDescription() in class GameChooser is not supported yet";
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
