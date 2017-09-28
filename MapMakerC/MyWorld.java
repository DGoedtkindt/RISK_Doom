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
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

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
        try{
            placeHexagonInCollumnRow(COLLUMN_NUMBER, ROW_NUMBER);
        }catch(Exception e) {e.printStackTrace(System.out);}
        
        // zone des bonus de continent
        for(SingleHex sh : getObjects(SingleHex.class)){
            
            if(sh.getX() > CONTINENT_BONUS_X_LEFT && sh.getX() < CONTINENT_BONUS_X_RIGHT && sh.getY() > CONTINENT_BONUS_Y_UP){
                
                removeObject(sh);
                
            }
            
        }
        
        Mode.changeMode(Mode.DEFAULT);
    }
    
    ///////////////////////////////
    
    public void makeSingleHex(int x, int y) throws Exception {
        SingleHex hex = new SingleHex(x,y);
        int[] rectCoord = hex.coordinates().rectCoord();
        addObject(hex,rectCoord[0],rectCoord[1]);
        
    }
    
    private void placeHexagonInCollumnRow(int collumn, int row) throws Exception{
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
        Links.newLinks = null;
        
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
            ArrayList<SingleHex> hexContained = new ArrayList<>();
            SingleHex infoHex = null;
            int id = Integer.parseInt(terrNode.getAttribute("id"));
            int bonus = Integer.parseInt(terrNode.getAttribute("bonus"));
            NodeList allChildren = terrNode.getChildNodes();
            for(int j=0; j<allChildren.getLength();j++) {
                Node child = allChildren.item(j);
                if(child.getNodeName() == "Hex") {
                   Element hex = (Element)child;
                   int hexX = Integer.parseInt(hex.getAttribute("hexX"));
                   int hexY = Integer.parseInt(hex.getAttribute("hexY"));
                   hexContained.add(SingleHex.SINGLE_HEX_ARRAY[hexX][hexY]);

                } else if(child.getNodeName() == "InfoHex") {
                    Element infoHexNode = (Element)child;
                    int hexX = Integer.parseInt(infoHexNode.getAttribute("hexX"));
                    int hexY = Integer.parseInt(infoHexNode.getAttribute("hexY"));
                    infoHex = SingleHex.SINGLE_HEX_ARRAY[hexX][hexY];

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
            int rColor = Integer.parseInt(contNode.getAttribute("rColor"));
            int gColor = Integer.parseInt(contNode.getAttribute("gColor"));
            int bColor = Integer.parseInt(contNode.getAttribute("bColor"));
            Color color = new Color(rColor,gColor,bColor);
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
            Element colorNode = (Element)(linksNode.getElementsByTagName("Color")).item(0);
            int rColor = Integer.parseInt(colorNode.getAttribute("rColor"));
            int gColor = Integer.parseInt(colorNode.getAttribute("gColor"));
            int bColor = Integer.parseInt(colorNode.getAttribute("bColor"));
            Color color = new Color(rColor,gColor,bColor);
            Links newLinks = new Links(color);
            Links.newLinks = newLinks;
            NodeList linkNodesList = linksNode.getElementsByTagName("Link");
            for (int j=0; j<linkNodesList.getLength();j++) {
                Element linkNode = (Element)linkNodesList.item(j);
                int xPos = Integer.parseInt(linkNode.getAttribute("xPos"));
                int yPos = Integer.parseInt(linkNode.getAttribute("yPos"));
                int terrId = Integer.parseInt(linkNode.getAttribute("terrId"));
                Territory terr = Territory.allTerritories().get(terrId);
                LinkIndic link = new LinkIndic(terr);
                theWorld.addObject(link,xPos,yPos);
                
                newLinks.addlink(link, terr);
            
            }
        
        }
    
    
    }
  
    
    private void init(){
        
        
    }
    
}
