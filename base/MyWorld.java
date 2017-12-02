package base;

import appearance.Appearance;
import appearance.ThemeChooser;
import appearance.Theme;
import mainObjects.BlankHex;
import mainObjects.Continent;
import links.Links;
import links.LinkIndic;
import menuButtons.LoadGameButton;
import menuButtons.NewGameButton;
import menuButtons.MapEditorButton;
import menuButtons.PlayGameButton;
import Selection.Selector;
import mapEditor.OKButton;
import mapEditor.MakeXML;
import mainObjects.Territory;
import xmlChoosers.MapChooser;
import greenfoot.World;
import greenfoot.Greenfoot;
import greenfoot.MouseInfo;
import greenfoot.Actor;
import greenfoot.GreenfootImage;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import javax.swing.JOptionPane;


public class MyWorld extends World
{
    //Thème utilisé
    static public Theme usedTheme = Theme.BASIC_DARK;
    
    //pour accéder au monde depuis un non-acteur
    public static MyWorld theWorld;
    
    //Détection de la souris
    private MouseInfo mouse;
    
    //Détecteur d'escape
    private CheckEscape escapeTester = new CheckEscape();
    
    //Mode par défaut dans la partie du logiciel actuelle
    private Mode currentDefaultMode = Mode.MAIN_MENU;
    
    //Boutons dans le menu principal
    public PlayGameButton playGameButton    = new PlayGameButton();
    public MapEditorButton mapEditorButton  = new MapEditorButton();
    public OptionsButton optionsButton      = new OptionsButton();
    
    //Boutons dans le menu du jeu
    public NewGameButton newGameButton      = new NewGameButton();
    public LoadGameButton loadGameButton    = new LoadGameButton();
    
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
    public MakeXML makeXMLButton            = new MakeXML();
    
    //Boutons dans les options
    public ThemeChooser themeChooser        = new ThemeChooser();
    
    //Bouton retour
    public BackButton backButton            = new BackButton();
    
    
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
        getBackground().setColor(usedTheme.backgroundColor);
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
    
    private void resetWorldObjects(){
        
        for(Continent c : Continent.continentList()){
            c.destroy();
            
        }
        
        for(Territory t : Territory.allTerritories()){
            t.destroy();
            
        }
        
        for(Button b : getObjects(Button.class)){
            removeObject(b);
            
        }
        
    }
    
    private void prepareBackgroundForMenu(){
        getBackground().setColor(usedTheme.backgroundColor.brighter());
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
    
    public void OptionsMenu(){
        
        currentDefaultMode = Mode.OPTIONS;
        Mode.changeMode(Mode.OPTIONS);
        
        resetWorldObjects();
        prepareBackgroundForMenu();
        
        addObject(themeChooser, getWidth() / 2, getHeight() / 2);
        addObject(backButton, getWidth() - 25, 27);
        
    }
    
    //Ouverture d'une map pour le map editor///////////////////////////////////////////////
    
    private static Document doc;
    
    public static void readXMLMap(String fileName) {
        try{
            getDocument(fileName);
            createTerritories();
            createContinents();
            createLinks();
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
        
        
    }
    
    private static void getDocument(String fileName) throws Exception {
        File XMLFile = new File(fileName);
                if(XMLFile.exists()){
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    doc = dBuilder.parse(XMLFile);
                    doc.getDocumentElement().normalize();
                } else {throw new Exception("File does not exist");}
    }
    
    private static void createTerritories() throws Exception{
        NodeList terrNodeList = doc.getElementsByTagName("Territory");
        for(int i = 0; i < terrNodeList.getLength(); i++) {
            Element terrNode = (Element)terrNodeList.item(i);
            ArrayList<BlankHex> hexContained = new ArrayList<>();
            BlankHex infoHex = null;
            int id = Integer.parseInt(terrNode.getAttribute("id"));
            int bonus = Integer.parseInt(terrNode.getAttribute("bonus"));
            NodeList allChildren = terrNode.getChildNodes();
            for(int j=0; j<allChildren.getLength();j++) {
                Node child = allChildren.item(j);
                if(child.getNodeName() == "Hex") {
                   Element hex = (Element)child;
                   int hexX = Integer.parseInt(hex.getAttribute("hexX"));
                   int hexY = Integer.parseInt(hex.getAttribute("hexY"));
                   hexContained.add(BlankHex.blankHexAt(hexX,hexY));

                } else if(child.getNodeName() == "InfoHex") {
                    Element infoHexNode = (Element)child;
                    int hexX = Integer.parseInt(infoHexNode.getAttribute("hexX"));
                    int hexY = Integer.parseInt(infoHexNode.getAttribute("hexY"));
                    infoHex = BlankHex.blankHexAt(hexX,hexY);

                }

            }
            new Territory(hexContained, infoHex, bonus, id);


            }
    
    }
    
    private static void createContinents() throws Exception{
        NodeList contNodeList = doc.getElementsByTagName("Continent");
        for(int i = 0; i < contNodeList.getLength(); i++) {
            Element contNode = (Element)contNodeList.item(i);
            ArrayList<Territory> terrContained = new ArrayList<>();
            int bonus = Integer.parseInt(contNode.getAttribute("bonus"));
            String colorString = contNode.getAttribute("color");
            GColor color = GColor.fromRGB(colorString);
            NodeList allTerrIDs = contNode.getChildNodes();
            for(int j=0; j<allTerrIDs.getLength();j++) {
                Element terrIdNode = (Element)allTerrIDs.item(j);
                int terrId = Integer.parseInt(terrIdNode.getAttribute("id"));
                Territory terr = Territory.allTerritories().get(terrId);
                terrContained.add(terr);
            
            }
            
            new Continent(terrContained,color,bonus);
            
        }
    
    }
    
    private static void createLinks() throws Exception{
        NodeList linksNodeList = doc.getElementsByTagName("Links");
        for(int i = 0; i<linksNodeList.getLength();i++) {
            Element linksNode = (Element)linksNodeList.item(i);

            String colorString = linksNode.getAttribute("color");
            GColor color = GColor.fromRGB(colorString);
            Links newLinks = new Links(color);
            Links.newLinks = newLinks;
            NodeList linkNodesList = linksNode.getElementsByTagName("Link");
            for (int j=0; j<linkNodesList.getLength();j++) {
                Element linkNode = (Element)linkNodesList.item(j);
                int xPos = Integer.parseInt(linkNode.getAttribute("xPos"));
                int yPos = Integer.parseInt(linkNode.getAttribute("yPos"));
                int terrId = Integer.parseInt(linkNode.getAttribute("terrId"));
                Territory terr = Territory.allTerritories().get(terrId);
                new LinkIndic(terr, xPos, yPos);
            
            }
        
        }
        Links.newLinks = null;
    
    }

    //Création d'une image du monde//////////////////////////////////////////////
    
    public BufferedImage createMapImage(){
        
        GreenfootImage mapImage = new GreenfootImage(1920, 1080);
        mapImage.drawImage(getBackground(), 0, 0);
        
        return mapImage.getAwtImage();
        
    }

}
