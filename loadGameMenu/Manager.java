package loadGameMenu;

import appearance.MessageDisplayer;
import base.Action;
import base.Game;
import base.NButton;
import base.StateManager;
import javax.swing.JOptionPane;
import xmlChoosers.GameChooser;

public class Manager extends StateManager {
    
    private GameChooser gameC;
    private NButton playGameB;

    public Manager() {
        this.playGameB = new NButton(playSelectedGame, "Play Game");
        this.gameC = new GameChooser();
    }

    @Override
    public void setupScene() {
        gameC.addToWorld(world().getWidth() / 2, world().getHeight() / 2 );
        world().addObject(playGameB, world().getWidth()/2, 3 * world().getHeight() / 4);
        
    }

    @Override
    public void clearScene() {
        gameC.destroy();
        world().removeObject(playGameB);
    }

    @Override
    public void escape() {
        int choice = JOptionPane.showConfirmDialog(null, "Do you want to return to the main menu?", 
                                                             "Returning to the menu", JOptionPane.YES_NO_CANCEL_OPTION);
            if(choice == JOptionPane.YES_OPTION){
                clearScene();
                world().load(new menu.Manager());

            }
    }

    private Action playSelectedGame = () -> {
        try{
            Game game = gameC.getSelectedGame();
            game.Manager newManager = new game.Manager(game);
            world().load(newManager);

        } catch(Exception ex) {
            String message = "Cannot play selected Game";
            MessageDisplayer.showException(new Exception(message, ex));

        }

    };

}
