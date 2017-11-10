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


public class MyWorld extends World
{
    //Couleurs de base
    static final GColor WORLD_COLOR = new GColor(55, 40, 55);
    static final GColor SELECTION_COLOR = new GColor(0, 220, 0);
    
    //Transparence et opacité des acteurs
    static final int TRANSPARENT = 30;
    static final int OPAQUE = 255;
    
    //Taille du monde
    static final int WORLD_WIDTH = 1920;
    static final int WORLD_HEIGHT = 1080;
    
    //Pour la grille d'hexagones
    static final int COLLUMN_NUMBER = 37;
    static final int ROW_NUMBER = 19;
    
    //positions hexagonales pour dessiner le vide contenant l'affichage 
    //des bonus des continents
    static final int CONTINENT_BONUS_ZONE_WIDTH = 12;
    static final int CONTINENT_BONUS_ZONE_HEIGHT = 4;
    static final int CONTINENT_BONUS_X_POSITION = COLLUMN_NUMBER / 2 - CONTINENT_BONUS_ZONE_WIDTH / 2;
    static final int CONTINENT_BONUS_Y_POSITION = ROW_NUMBER - CONTINENT_BONUS_ZONE_HEIGHT;
    
    //pour accéder au monde depuis un non-acteur
    static MyWorld theWorld;
    
    //Détection de la souris
    private MouseInfo mouse;
    
    //Mode par défaut dans la partie du logiciel actuelle
    private Mode currentDefaultMode = Mode.MAIN_MENU;
    
    //Boutons dans le menu principal            (Manque le theme editor)
    PlayGameButton playGameButton       = new PlayGameButton();
    MapEditorButton mapEditorButton     = new MapEditorButton();
    
    //Boutons dans le menu du jeu
    NewGameButton newGameButton         = new NewGameButton();
    LoadGameButton loadGameButton       = new LoadGameButton();
    
    //Boutons dans le jeu
    
    //Boutons dans le menu du map editor
    MapChooser mapThumbnail             = new MapChooser();
    LeftArrow leftArrow                 = new LeftArrow(mapThumbnail,100,60);
    RightArrow rightArrow               = new RightArrow(mapThumbnail,100,60);
    
    //Boutons dans le map editor
    ModeButton createTerritory          = new ModeButton("createNewTerritory.png",    Mode.CREATE_TERRITORY,      Selector.IS_BLANKHEX);
    ModeButton createContinent          = new ModeButton("addNewContinent.png",       Mode.CREATE_CONTINENT,      Selector.IS_TERRITORY_NOT_IN_CONTINENT);
    ModeButton editContinentBonus       = new ModeButton("editContinentBonus.png",    Mode.EDIT_CONTINENT_BONUS,  Selector.IS_CONTINENT);
    ModeButton editContinentColor       = new ModeButton("editContinentColor.png",    Mode.EDIT_CONTINENT_COLOR,  Selector.IS_CONTINENT);
    ModeButton editTerritoryBonus       = new ModeButton("editTerritoryBonus.png",    Mode.EDIT_TERRITORY_BONUS,  Selector.IS_TERRITORY);
    ModeButton createLink               = new ModeButton("newLink.png",               Mode.SET_LINK,              Selector.IS_TERRITORY);
    ModeButton deleteTerritory          = new ModeButton("deleteTerritory.png",       Mode.DELETE_TERRITORY,      Selector.IS_TERRITORY);
    ModeButton deleteContinent          = new ModeButton("deleteContinent.png",       Mode.DELETE_CONTINENT,      Selector.IS_CONTINENT);
    OKButton okButton                   = new OKButton();
    MakeXML makeXMLButton               = new MakeXML();
    
    //Bouton retour
    BackButton backButton               = new BackButton();
    
    public MyWorld() {    
        super(WORLD_WIDTH, WORLD_HEIGHT, 1);
        theWorld = this;
        
        mapEditorMenu();
        
    }
    
    //Création des scènes pour le jeu et le map editor/////////////////////////////
    
    public void setupGameScene(){
        
        //A faire
        
    }
    
    public void setupMapEditorScene(){
        
        //d'abord clear tout les acteurs sur le monde
        List<Actor> allActors = this.getObjects(null);
        allActors.forEach((actor) -> this.removeObject(actor));
        getBackground().setColor(WORLD_COLOR);
        getBackground().fill();
        //créer les blankHexs
        placeHexagonInCollumnRow(COLLUMN_NUMBER, ROW_NUMBER);
        //trou pour les bonus de continent
        drawContinentBonusZone();
        
        // placement des boutons
        addObject(createTerritory, WORLD_WIDTH -100, 100);
        addObject(createLink, WORLD_WIDTH - 70, 160);
        addObject(editTerritoryBonus, WORLD_WIDTH - 130, 160);
        addObject(deleteTerritory, WORLD_WIDTH - 100, 220);
        addObject(createContinent, WORLD_WIDTH - 100, 300);
        addObject(editContinentBonus, WORLD_WIDTH - 70, 360);
        addObject(editContinentColor, WORLD_WIDTH - 130, 360);
        addObject(deleteContinent, WORLD_WIDTH - 100, 420);
        addObject(okButton, WORLD_WIDTH - 100, 510);
        addObject(makeXMLButton, WORLD_WIDTH - 100, 600);
        addObject(backButton, WORLD_WIDTH - 25, 27);
        
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
            
            if(bh.hexCoord()[0] > CONTINENT_BONUS_X_POSITION 
                    && bh.hexCoord()[0] < CONTINENT_BONUS_X_POSITION + CONTINENT_BONUS_ZONE_WIDTH 
                    && bh.hexCoord()[1] > ROW_NUMBER - CONTINENT_BONUS_ZONE_HEIGHT){
                
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
        Mode.changeMode(currentDefaultMode);

        if(Links.newLinks != null){
            Links.newLinks.destroy();
            
        }
        Links.newLinks = null;
        
    }   
    
    @Override
    public void act() {
        
        mouse = Greenfoot.getMouseInfo();
        if(mouse != null && Greenfoot.mouseClicked(null)){ // Si on a appuyé quelque part
            
            if(getPressedButton() != null){
                getPressedButton().clicked();
                
            }
            
        }
        
        if(Greenfoot.isKeyDown("Escape")){
            escape();
            
        }
        
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
            e.printStackTrace(System.out);
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
            new Territory(hexContained,infoHex,bonus, id);


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
        
        getBackground().setColor(WORLD_COLOR.brighter());
        getBackground().fill();
        
        GreenfootImage pineapple = new GreenfootImage("TheMightyPineappleOfJustice.png");
        pineapple.scale(400, 150);
        getBackground().drawImage(pineapple, 75, 75);
        
    }
    
    //Menus//////////////////////////////////////////////
    
    public void mainMenu(){
        
        currentDefaultMode = Mode.MAIN_MENU;
        Mode.changeMode(Mode.MAIN_MENU);
        
        resetWorldObjects();
        prepareBackgroundForMenu();
        
        //Pour dessiner un joli logo (Pas d'image pour l'instant)
        //getBackground().drawImage(new GreenfootImage("RISK.png"), WORLD_WIDTH / 2, WORLD_HEIGHT / 5 );
        
        addObject(playGameButton, WORLD_WIDTH / 4, WORLD_HEIGHT / 2);
        addObject(mapEditorButton, 3 * WORLD_WIDTH / 4, WORLD_HEIGHT / 2);
        
    }
    
    public void mapEditorMenu(){
        
        currentDefaultMode = Mode.MAP_EDITOR_MENU;
        Mode.changeMode(Mode.MAP_EDITOR_MENU);
        
        resetWorldObjects();
        prepareBackgroundForMenu();
        
        addObject(mapThumbnail, WORLD_WIDTH / 2, WORLD_HEIGHT / 2 );
        addObject(leftArrow, WORLD_WIDTH / 3,WORLD_HEIGHT / 2 - mapThumbnail.getImage().getHeight() / 5);
        addObject(rightArrow, 2 * WORLD_WIDTH / 3,WORLD_HEIGHT / 2 - mapThumbnail.getImage().getHeight() / 5);
        addObject(backButton, WORLD_WIDTH - 25, 27);

    }
    
    public void gameMenu(){
        
        currentDefaultMode = Mode.GAME_MENU;
        Mode.changeMode(Mode.GAME_MENU);
        
        resetWorldObjects();
        prepareBackgroundForMenu();
        
        addObject(newGameButton, WORLD_WIDTH / 4, WORLD_HEIGHT / 2);
        addObject(loadGameButton, 3 * WORLD_WIDTH / 4, WORLD_HEIGHT / 2);
        addObject(backButton, WORLD_WIDTH - 25, 27);
        
    }
    
    public void newGameMenu(){
        
        //A faire
        
    }
    
    public void loadGameMenu(){
        
        //A faire
        
    }
    
}
