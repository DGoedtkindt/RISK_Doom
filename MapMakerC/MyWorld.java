import greenfoot.World;
import greenfoot.Greenfoot;
import greenfoot.MouseInfo;
import greenfoot.GreenfootImage;
import java.awt.Color;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.List;

public class MyWorld extends World
{
    
    static final Color BASE_WORLD_COLOR = new Color(135, 135, 155);
    static final Color SELECTION_COLOR = Color.GREEN;
    static final Color MENU_COLOR = new Color(121, 163, 200);
    
    static final int TRANSPARENT = 30;
    static final int OPAQUE = 255;
    
    static final int WORLD_WIDTH = 1920;
    static final int WORLD_HEIGHT = 1080;
    
    static final int CONTINENT_BONUS_X_LEFT = 550;
    static final int CONTINENT_BONUS_X_RIGHT = 1200;
    static final int CONTINENT_BONUS_Y_UP = 900;
    
    static final int COLLUMN_NUMBER = 29;
    static final int ROW_NUMBER = 15;
    
    static final SingleHex[][] SINGLE_HEX_ARRAY = new SingleHex[COLLUMN_NUMBER + 10][ROW_NUMBER + 10]; 
    
    static MyWorld theWorld; //pour accéder au monde depuis un non-acteur
    
    private MouseInfo mouse = Greenfoot.getMouseInfo();

    ModeButton createTerritory      = new ModeButton("createNewTerritory.png",    Mode.CREATE_TERRITORY,      Selector.IS_SINGLEHEX);
    ModeButton createContinent      = new ModeButton("addNewContinent.png",       Mode.CREATE_CONTINENT,      Selector.IS_TERRITORY_NOT_IN_CONTINENT);
    ModeButton editContinentBonus   = new ModeButton("editContinentBonus.png",    Mode.EDIT_CONTINENT_BONUS,  Selector.IS_CONTINENT);
    ModeButton editContinentColor   = new ModeButton("editContinentColor.png",    Mode.EDIT_CONTINENT_COLOR,  Selector.IS_CONTINENT);
    ModeButton editTerritoryBonus   = new ModeButton("editTerritoryBonus.png",    Mode.EDIT_TERRITORY_BONUS,  Selector.IS_TERRITORY);
    ModeButton createLink           = new ModeButton("newLink.png",               Mode.SET_LINK,              Selector.IS_TERRITORY);
    ModeButton deleteTerritory      = new ModeButton("deleteTerritory.png",       Mode.DELETE_TERRITORY,      Selector.IS_TERRITORY);
    ModeButton deleteContinent      = new ModeButton("deleteContinent.png",       Mode.DELETE_CONTINENT,      Selector.IS_CONTINENT);
    OKButton okButton               = new OKButton();
    MakeXML makeXMLButton           = new MakeXML();
    ReadXMLButton readXMLButton     = new ReadXMLButton();
    
    public MyWorld()
    {    
        super(WORLD_WIDTH, WORLD_HEIGHT, 1);
        theWorld = this;
        
        //quelques trucs cosmétiques
        Greenfoot.setSpeed(60);
        getBackground().setColor(BASE_WORLD_COLOR);
        getBackground().fill();
        
        //Hexagones bruns sur le côté
        for(int i = 29; i < 34;i++){
            for(int j = -1; j <= 15; j++){
                GreenfootImage hex;
                hex = Hexagon.createImage(MENU_COLOR);
                int[] rectCoord = Coordinates.hexToRectCoord(new int[]{i,j});
                int size = Hexagon.RADIUS;
                
                getBackground().drawImage(hex,rectCoord[0]-size,rectCoord[1]-size);
            }
        }
        
        // placement des boutons
        addObject(createTerritory, MyWorld.WORLD_WIDTH -100, 100);
        addObject(createLink, MyWorld.WORLD_WIDTH - 70, 160);
        addObject(editTerritoryBonus, MyWorld.WORLD_WIDTH - 130, 160);
        addObject(deleteTerritory, MyWorld.WORLD_WIDTH - 100, 220);
        addObject(createContinent, MyWorld.WORLD_WIDTH - 100, 300);
        addObject(editContinentBonus, MyWorld.WORLD_WIDTH - 70, 360);
        addObject(editContinentColor, MyWorld.WORLD_WIDTH - 130, 360);
        addObject(deleteContinent, MyWorld.WORLD_WIDTH - 100, 420);
        addObject(okButton, MyWorld.WORLD_WIDTH - 100, 510);
        addObject(makeXMLButton, MyWorld.WORLD_WIDTH - 100, 600);
        addObject(readXMLButton, MyWorld.WORLD_WIDTH - 100, 700);
        
        // placement des hexagones
        placeHexagonInCollumnRow(COLLUMN_NUMBER, ROW_NUMBER);
        
        // zone des bonus de continent
        for(SingleHex sh : getObjects(SingleHex.class)){
            
            if(sh.getX() > CONTINENT_BONUS_X_LEFT && sh.getX() < CONTINENT_BONUS_X_RIGHT && sh.getY() > CONTINENT_BONUS_Y_UP){
                
                removeObject(sh);
                
            }
            
        }
        
        Mode.changeMode(Mode.DEFAULT);
    }
    
    ///////////////////////////////
    
    public void makeSingleHex(int x, int y) {
        SingleHex hex = new SingleHex(x,y);
        int[] rectCoord = hex.coordinates().rectCoord();
        addObject(hex,rectCoord[0],rectCoord[1]);
        
    }
    
    private void placeHexagonInCollumnRow(int collumn, int row){
        for(int x = 0; x < collumn; x++) {
            
            for(int y = 0; y < row; y++) {

                makeSingleHex(x, y);
                
            }
            
        }
        
    }
    
    private Button getPressedButton(){
        if(mouse.getActor() instanceof Button){
            return (Button)(mouse.getActor());
            
        }else{
            return null;
            
        }
        
    }
    
    //////////////////////////////////////////////////
    
    public void escape(){
        Selector.clear();
        Mode.changeMode(Mode.DEFAULT);
        
        for(Territory t : Territory.allTerritories()){
            
            if(t.links.contains(Link.currentLink)){
                
                t.removeLink(Link.currentLink);
                
            }
            
        }
        
        Link.allLinks.remove(Link.currentLink);
        Link.currentLink = null;
        
    }   
    
    public void act() {
        mouse = Greenfoot.getMouseInfo();
        if(mouse != null && Greenfoot.mouseClicked(null)){ // Si on a appuyé quelque part
            
            if(getPressedButton() != null){
                getPressedButton().clicked();
                
            }else{
                escape();
                
            }
            
        }
        
        if(Greenfoot.isKeyDown("Escape")){
            escape();
            
        }
        
    }
    
    /////////////////////////////////////////////////
    
    private void createNewMap(){
        
        
    }
    
    public static void readXMLMap(String fileName){
        
        try{
            
            File XMLFile = new File(fileName);
            if(XMLFile.exists()){
                
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(XMLFile);
                doc.getDocumentElement().normalize();
                
                //getting links
                NodeList linkList = doc.getElementsByTagName("link");
                for(int i = 0; i <linkList.getLength(); i++){
                    
                    Node linkNode = linkList.item(i);
                    if(linkNode.getNodeType() == Node.ELEMENT_NODE){
                        
                        Element currentLink = (Element)linkNode;
                        
                        int rColor = Integer.parseInt(currentLink.getAttribute("rLinkColor"));
                        int gColor = Integer.parseInt(currentLink.getAttribute("gLinkColor"));
                        int bColor = Integer.parseInt(currentLink.getAttribute("bLinkColor"));
                        new Link(new Color(rColor, gColor, bColor));
                        
                    }
                    
                }
                
                ArrayList<LinkSpot> allLinkSpots = new ArrayList<LinkSpot>();
                
                //getting continents
                NodeList continentList = doc.getElementsByTagName("continent");
                for(int i = 0; i < continentList.getLength(); i++){
                
                    Node continentNode = continentList.item(i);
                    if(continentNode.getNodeType() == Node.ELEMENT_NODE){
                        
                        Element currentContinent = (Element)continentNode;
                    
                        int rColor = Integer.parseInt(currentContinent.getAttribute("rContinentColor"));
                        int gColor = Integer.parseInt(currentContinent.getAttribute("gContinentColor"));
                        int bColor = Integer.parseInt(currentContinent.getAttribute("bContinentColor"));
                        int continentBonus = Integer.parseInt(currentContinent.getAttribute("continentPoints"));
                        
                        ArrayList<Territory> territoriesInContinent = new ArrayList<Territory>();
                        
                        NodeList territoryList = currentContinent.getChildNodes();

                        for(int j = 0; j < territoryList.getLength(); j++){
                            
                            Node territoryNode = territoryList.item(j);
                            
                            if(territoryNode.getNodeType() == Node.ELEMENT_NODE){
                                
                                Element currentTerritory = (Element)territoryNode;

                                int territoryBonus = Integer.parseInt(currentTerritory.getAttribute("territoryPoints"));
                                SingleHex infoHex = null;

                                ArrayList<SingleHex> hexesInTerritory = new ArrayList<SingleHex>();

                                NodeList hexList = currentTerritory.getChildNodes();

                                for(int k = 0; k < hexList.getLength(); k++){

                                    Node hexNode = hexList.item(k);
                                    if(hexNode.getNodeType() == Node.ELEMENT_NODE){
                                        
                                        Element currentHex = (Element)hexNode;

                                        if(currentHex.hasAttribute("infoHexX")){

                                            int infoHexX = Integer.parseInt(currentHex.getAttribute("infoHexX"));
                                            int infoHexY = Integer.parseInt(currentHex.getAttribute("infoHexY"));

                                            infoHex = SINGLE_HEX_ARRAY[infoHexX][infoHexY];

                                        }else if(currentHex.hasAttribute("hexX")){

                                            int xCoord = Integer.parseInt(currentHex.getAttribute("hexX"));
                                            int yCoord = Integer.parseInt(currentHex.getAttribute("hexY"));
                                            
                                            if(currentHex.hasChildNodes()){
                                                
                                                NodeList linkSpotList = currentHex.getElementsByTagName("linkSpot");
                                            
                                                for(int v = 0; v < linkSpotList.getLength(); v++){
                                                    
                                                    Element linkSpot = (Element)linkSpotList.item(v);
                                                    
                                                    int rSpotColor = Integer.parseInt(linkSpot.getAttribute("rLinkColor"));
                                                    int gSpotColor = Integer.parseInt(linkSpot.getAttribute("gLinkColor"));
                                                    int bSpotColor = Integer.parseInt(linkSpot.getAttribute("bLinkColor"));
                                                    int relativeX  = Integer.parseInt(linkSpot.getAttribute("linkRelX"));
                                                    int relativeY  = Integer.parseInt(linkSpot.getAttribute("linkRelY"));
                                                    
                                                    Coordinates hexCoord = new Coordinates(new int[]{xCoord, yCoord});
                                                    Color linkColor = new Color(rSpotColor, gSpotColor, bSpotColor);
                                                    int[] relativePos = {relativeX, relativeY};
                                                    
                                                    LinkSpot ls = new LinkSpot(linkColor, hexCoord, relativePos);
                                                    allLinkSpots.add(ls);
                                                    
                                                }
                                                
                                            }
                                            
                                            hexesInTerritory.add(SINGLE_HEX_ARRAY[xCoord][yCoord]);

                                        }else if(currentHex.hasAttribute("borderingID")){

                                        }

                                    }

                                }
                                
                                Territory t = new Territory(hexesInTerritory, infoHex, territoryBonus);
                                territoriesInContinent.add(t);
                            }

                        }

                        Color continentColor = new Color(rColor, gColor, bColor);

                        new Continent(territoriesInContinent, continentColor, continentBonus);
                        
                    }
                    
                }
            
                
                NodeList unoccupiedTerritories = doc.getElementsByTagName("unoccupiedTerritory");
            
                for(int n = 0; n < unoccupiedTerritories.getLength(); n++){
                
                    Node territoryNode = unoccupiedTerritories.item(n);
                    
                    if(territoryNode.getNodeType() == Node.ELEMENT_NODE){
                        
                        Element currentTerritory = (Element)territoryNode;
                    
                        int territoryBonus = Integer.parseInt(currentTerritory.getAttribute("territoryPoints"));
                        SingleHex infoHex = null;

                        ArrayList<SingleHex> hexesInTerritory = new ArrayList<SingleHex>();

                        NodeList hexList = currentTerritory.getChildNodes();

                        for(int k = 0; k < hexList.getLength(); k++){

                            Node hexNode = hexList.item(k);
                            if(hexNode.getNodeType() == Node.ELEMENT_NODE){

                                Element currentHex = (Element)hexNode;

                                if(currentHex.hasAttribute("infoHexX")){

                                    int infoHexX = Integer.parseInt(currentHex.getAttribute("infoHexX"));
                                    int infoHexY = Integer.parseInt(currentHex.getAttribute("infoHexY"));

                                    infoHex = SINGLE_HEX_ARRAY[infoHexX][infoHexY];

                                }else if(currentHex.hasAttribute("hexX")){

                                    int xCoord = Integer.parseInt(currentHex.getAttribute("hexX"));
                                    int yCoord = Integer.parseInt(currentHex.getAttribute("hexY"));

                                    if(currentHex.hasChildNodes()){
                                                
                                        NodeList linkSpotList = currentHex.getElementsByTagName("linkSpot");

                                        for(int v = 0; v < linkSpotList.getLength(); v++){

                                            Element linkSpot = (Element)linkSpotList.item(v);

                                            int rSpotColor = Integer.parseInt(linkSpot.getAttribute("rLinkColor"));
                                            int gSpotColor = Integer.parseInt(linkSpot.getAttribute("gLinkColor"));
                                            int bSpotColor = Integer.parseInt(linkSpot.getAttribute("bLinkColor"));
                                            int relativeX  = Integer.parseInt(linkSpot.getAttribute("linkRelX"));
                                            int relativeY  = Integer.parseInt(linkSpot.getAttribute("linkRelY"));

                                            Coordinates hexCoord = new Coordinates(new int[]{xCoord, yCoord});
                                            Color linkColor = new Color(rSpotColor, gSpotColor, bSpotColor);
                                            int[] relativePos = {relativeX, relativeY};
                                            
                                            LinkSpot ls = new LinkSpot(linkColor, hexCoord, relativePos);
                                            allLinkSpots.add(ls);

                                        }
                                                
                                    }
                                    
                                    hexesInTerritory.add(SINGLE_HEX_ARRAY[xCoord][yCoord]);

                                }else if(currentHex.hasAttribute("borderingID")){

                                }
                                
                            }
                            
                        }
                        
                        new Territory(hexesInTerritory, infoHex, territoryBonus);
                    }
                    
                }
                
                List<TerritoryHex> territoryHexes= theWorld.getObjects(TerritoryHex.class);
                
                for(LinkSpot ls : allLinkSpots){
                    
                    for(TerritoryHex th : territoryHexes){
                        
                        if(ls.terrHexCoordinates.hexCoord[0] == th.coordinates().hexCoord[0] 
                           && ls.terrHexCoordinates.hexCoord[1] == th.coordinates().hexCoord[1]){
                            
                            th.linksPlacedInIt.add(ls);
                            ls.specificHex = th;
                            theWorld.addObject(ls, ls.terrHexCoordinates.rectCoord()[0] + ls.relativePosition[0], ls.terrHexCoordinates.rectCoord()[1] + ls.relativePosition[1]);
                        }
                        
                    }
                    
                }
                
            }
                
        }catch(Exception e){e.printStackTrace();}
        Mode.changeMode(Mode.DEFAULT);
    }
    
    private void init(){
        
        
    }
    
}
