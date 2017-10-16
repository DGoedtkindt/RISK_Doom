package loadgamemenu;

import basepackage.MGChooser;

public class GameChooser extends MGChooser{
    private static final String FOLDER_NAME = "somename";
    
    protected GameChooser() {
        super(FOLDER_NAME);
        
    }

    @Override
    public void next() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void previous() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
