package base;

import selector.Selector;
import java.awt.event.ActionEvent;
import appearance.Appearance;
import appearance.ThemeChooser;
import appearance.Theme;
import mainObjects.BlankHex;
import mainObjects.Links;
import mapEditor.OKButton;
import mainObjects.Territory;
import xmlChoosers.MapChooser;
import greenfoot.World;
import greenfoot.Greenfoot;
import greenfoot.MouseInfo;
import greenfoot.Actor;
import greenfoot.GreenfootImage;
import java.io.File;
import java.util.List;
import javax.swing.JOptionPane;
import mainObjects.Continent;
import mapXML.MapXML;


public class MyWorld extends World {
    
    //pour accéder au monde depuis un non-acteur
    public static MyWorld theWorld;
    
    //Détection de la souris
    private MouseInfo mouse;
    
    //Détecteur d'escape
    private CheckEscape escapeTester = new CheckEscape();
    
    //Mode par défaut dans la partie du logiciel actuelle
    private Mode currentDefaultMode = Mode.MAIN_MENU;
    
    //Objet Map et Game
    public Map map = new Map();
    public Game game = new Game();
    
    //Boutons dans le menu principal
    public NButton playGameButton           = new NButton((ActionEvent ae) -> {gameMenu();}     , "Play");
    public NButton mapEditorButton          = new NButton((ActionEvent ae) -> {mapEditorMenu();}, "Map Editor");
    public NButton optionsButton            = new NButton((ActionEvent ae) -> {optionsMenu();}  , "Options");
    
    //Boutons dans le menu du jeu
    public NButton newGameButton            = new NButton((ActionEvent ae) -> {newGameMenu();}  , "New Game");
    public NButton loadGameButton           = new NButton((ActionEvent ae) -> {loadGameMenu();} , "Load Game");
    
    //Boutons dans le map editor
    public ModeButton createTerritory       = new ModeButton("createNewTerritory.png",    Mode.CREATE_TERRITORY,      Selector.IS_BLANKHEX);
    public ModeButton createContinent       = new ModeButton("addNewContinent.png",       Mode.CREATE_CONTINENT,      Selector.IS_TERRITORY_NOT_IN_CONTINENT);
    public ModeButton editContinentBonus    = new ModeButton("editContinentBonus.png",    Mode.EDIT_CONTINENT_BONUS,  Selector.IS_CONTINENT);
    public ModeButton editContinentColor    = new ModeButton("editContinentColor.png",    Mode.EDIT_CONTINENT_COLOR,  Selector.IS_CONTINENT);
    public ModeButton editTerritoryBonus    = new ModeButton("editTerritoryBonus.png",    Mode.EDIT_TERRITORY_BONUS,  Selector.IS_TERRITORY);
    public ModeButton createLink            = new ModeButton("newLink.png",               Mode.SET_LINK,              Selector.IS_TERRITORY);
    public ModeButton deleteTerritory       = new ModeButton("deleteTerritory.png",       Mode.DELETE_TERRITORY,      Selector.IS_TERRITORY);
    public ModeButton deleteContinent       = new ModeButton("deleteContinent.png",       Mode.DELETE_CONTINENT,      Selector.IS_CONTINENT);
    public OKButton okButton                = new OKButton();
    public NButton makeXMLButton            = new NButton(NButton.saveFile, new GreenfootImage("MakeXML.png"));
    
    //Bouton retour
    public NButton backButton            = new NButton((ActionEvent ae) -> {backToMenu();}, new GreenfootImage("backToHome.png"),30,30);
    
    
    ////////////////Contructor
    ///////////////
    
    public MyWorld() {    
        super(Appearance.WORLD_WIDTH, Appearance.WORLD_HEIGHT, 1);
        theWorld = this;
        
        mainMenu();
        
    }
    ////////////////////////
    ///////////////////////
    
    
    
    //Création des scènes pour le jeu et le map editor/////////////////////////////
    
    public void setupGameScene(){
        
        //A faire
        
    }
    
    public void setupMapEditorScene(){
        
        //d'abord clear tout les acteurs sur le monde
        List<Actor> allActors = this.getObjects(null);
        allActors.forEach((actor) -> this.removeObject(actor));
        getBackground().setColor(Theme.used.backgroundColor);
        getBackground().fill();
        //créer les blankHexs
        placeHexagonInCollumnRow(Appearance.COLLUMN_NUMBER, Appearance.ROW_NUMBER);
        //trou pour les bonus de continent
        drawContinentBonusZone();
        
        // placement des boutons
        addObject(createTerritory, getWidth() -100, 100);
        addObject(createLink, getWidth() - 70, 160);
        addObject(editTerritoryBonus, getWidth() - 130, 160);
        addObject(deleteTerritory, getWidth() - 100, 220);
        addObject(createContinent, getWidth() - 100, 300);
        addObject(editContinentBonus, getWidth() - 70, 360);
        addObject(editContinentColor, getWidth() - 130, 360);
        addObject(deleteContinent, getWidth() - 100, 420);
        addObject(okButton, getWidth() - 100, 510);
        addObject(makeXMLButton, getWidth() - 100, 600);
        addObject(backButton, getWidth() - 25, 27);
        
        currentDefaultMode = Mode.MAP_EDITOR_DEFAULT;
        Mode.changeMode(Mode.MAP_EDITOR_DEFAULT);
        
    }
    
    private void placeHexagonInCollumnRow(int collumn, int row) {
        for(int x = 0; x < collumn; x++) {
            
            for(int y = 0; y < row; y++) {
                BlankHex hexToPlace = BlankHex.blankHexAt(x, y);
                int[] rectCoords = hexToPlace.rectCoord();
                addObject(hexToPlace,rectCoords[0],rectCoords[1]);
                
            }
            
        }
        
    }

    private void drawContinentBonusZone(){
        
        for(BlankHex bh : getObjects(BlankHex.class)){

            if(bh.hexCoord()[0] > Appearance.CONTINENT_BONUS_X_POSITION 
                    && bh.hexCoord()[0] < Appearance.CONTINENT_BONUS_X_POSITION + Appearance.CONTINENT_BONUS_ZONE_WIDTH 
                    && bh.hexCoord()[1] > Appearance.ROW_NUMBER - Appearance.CONTINENT_BONUS_ZONE_HEIGHT){
                
                removeObject(bh);
                
            }
            
        }
        
    }
    
    /////////////////////////////////////////////////
    
    private Button getPressedButton(){
        if(mouse.getActor() instanceof Button){
            return (Button)(mouse.getActor());
            
        }else{
            return null;
            
        }
        
    }
    
    //Méthodes courantes////////////////////////////////////////////////
    
    public void escape(){
        Selector.clear();
        
        if(Links.newLinks != null){
            Links.newLinks.destroy();
            Links.newLinks = null;
        }
        
        Mode.changeMode(currentDefaultMode);

    }   
    
    @Override
    public void act() {
        
        mouse = Greenfoot.getMouseInfo();
        if(mouse != null && Greenfoot.mouseClicked(null)){ // Si on a appuyé quelque part
            
            if(getPressedButton() != null){
                getPressedButton().clicked();
                
            }
            
        }
        
        escapeTester.testForEscape();
        
    }
    
    public void backToMenu(){
        
        int choice;
        
        switch(Mode.currentMode()){
            
            case MAP_EDITOR_DEFAULT : 
                
                choice = JOptionPane.showConfirmDialog(null, "Do you want to return to the map editor menu? Unsaved changes will be lost.", 
                                                             "Returning to the menu", JOptionPane.YES_NO_CANCEL_OPTION);
                if(choice == JOptionPane.YES_OPTION){
                    mapEditorMenu();

                }
                break;
                
            case GAME_DEFAULT : 
                
                choice = JOptionPane.showConfirmDialog(null, "Do you want to return to the game menu? Your game will be lost if it is not saved.", 
                                                             "Returning to the menu", JOptionPane.YES_NO_CANCEL_OPTION);
                if(choice == JOptionPane.YES_OPTION){
                    gameMenu();

                }
                break;
            
            case MAP_EDITOR_MENU :
            case GAME_MENU :
            case OPTIONS :
                
                choice = JOptionPane.showConfirmDialog(null, "Do you want to return to the main menu?", 
                                                             "Returning to the menu", JOptionPane.YES_NO_CANCEL_OPTION);
                if(choice == JOptionPane.YES_OPTION){
                    mainMenu();

                }
                break;
                
            default : escape();
            
        }
        
    }
    
    private class CheckEscape{
        boolean escapeWasClicked = false;
        
        public void testForEscape(){ 
            if(escapeReleased()){
                backToMenu();
                
            }
            if(Greenfoot.isKeyDown("Escape") != escapeWasClicked){
                escapeWasClicked = !escapeWasClicked;
                
            }
            
        }
        
        private boolean escapeReleased(){
            return (!Greenfoot.isKeyDown("Escape") && escapeWasClicked);
            
        }
        
    }
    
    //Préparation des menus//////////////////////////////////////////////
    
    //cette méthode est risquée car elle risque de ne pas supprimmer des 
    //acteurs correctement (destroy() devrais être utilisé)
    private void resetWorldObjects(){
        map = new Map();
        game = new Game();
        
        for(Actor a : getObjects(Actor.class)){
            removeObject(a);
            
        }
        
    }
    
    private void prepareBackgroundForMenu(){
        getBackground().setColor(Theme.used.backgroundColor.brighter());
        getBackground().fill();
        
    }
    
    //Menus//////////////////////////////////////////////
    
    public void mainMenu(){
        
        currentDefaultMode = Mode.MAIN_MENU;
        Mode.changeMode(Mode.MAIN_MENU);
        
        resetWorldObjects();
        prepareBackgroundForMenu();
        
        GreenfootImage pineapple = new GreenfootImage("TheMightyPineappleOfJustice.png");
        pineapple.scale(400, 150);
        getBackground().drawImage(pineapple, 75, 75);
        
        //Pour dessiner un joli logo (Pas d'image pour l'instant)
        //getBackground().drawImage(new GreenfootImage("RISK.png"), WORLD_WIDTH / 2, WORLD_HEIGHT / 5 );
        
        addObject(playGameButton, getWidth() / 2, getHeight() / 2);
        addObject(mapEditorButton, getWidth() / 4, getHeight() / 2);
        addObject(optionsButton, 3 * getWidth() / 4, getHeight() / 2);
        
    }
    
    public void mapEditorMenu(){
        
        currentDefaultMode = Mode.MAP_EDITOR_MENU;
        Mode.changeMode(Mode.MAP_EDITOR_MENU);
        
        resetWorldObjects();
        prepareBackgroundForMenu();
        
        MapChooser mapC = new MapChooser();
        mapC.addToWorld(this, getWidth() / 2, getHeight() / 2 );
        
        NButton editMapB = new NButton((ActionEvent ae) -> {
            try{
                File mapFile = mapC.getCurrentFile();
                MapXML mapXML = new MapXML(mapFile);
                Map map = mapXML.getMap();
                setupMapEditorScene();
                loadMap(map);
                 
            } catch(Exception ex) {
                System.err.println("couldn't create MapXML from File");
                ex.printStackTrace(System.err);
                
            }
            
        }, "Edit Map");
        
        addObject(editMapB, getWidth()/2, 3 * getHeight() / 4);
        
        
        addObject(backButton, getWidth() - 25, 27);

    }
    
    public void gameMenu(){
        
        currentDefaultMode = Mode.GAME_MENU;
        Mode.changeMode(Mode.GAME_MENU);
        
        resetWorldObjects();
        prepareBackgroundForMenu();
        
        addObject(newGameButton, getWidth() / 4, getHeight() / 2);
        addObject(loadGameButton, 3 * getWidth() / 4, getHeight() / 2);
        addObject(backButton, getWidth() - 25, 27);
        
    }
    
    public void newGameMenu(){
        
        //A faire
        
    }
    
    public void loadGameMenu(){
        
        //A faire
        
    }
    
    public void optionsMenu(){
        
        currentDefaultMode = Mode.OPTIONS;
        Mode.changeMode(Mode.OPTIONS);
        
        resetWorldObjects();
        prepareBackgroundForMenu();
        
        ThemeChooser themeC = new ThemeChooser();
        themeC.addToWorld(this, getWidth() / 2, getHeight() / 2);
        addObject(backButton, getWidth() - 25, 27);
        
    }
    
    //Ouverture d'une map pour le map editor///////////////////////////////////////////////
    
    public void loadMap(Map mapToLoad) {
        mapToLoad.territories.forEach(Territory::addToWorld);
        mapToLoad.continents.forEach(Continent::addToWorld);
        mapToLoad.links.forEach(Links::addToWorld);
        map = mapToLoad;
    
    }

}
