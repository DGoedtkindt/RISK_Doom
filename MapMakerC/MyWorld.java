import greenfoot.*;

import java.awt.Color;
import java.util.*;

//Import les xml
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MyWorld extends World
{
    static final int WORLDX = 1920;
    static final int WORLDY = 1080;
    
    static MyWorld theWorld; //pour accéder au monde depuis un non-acteur
    
    static private int currentMode = Mode.DEFAULT;
    
    MouseInfo mouse = Greenfoot.getMouseInfo();
    
    Button lastClickedButton;
    
  
    public MyWorld()
    {    
        super(WORLDX, WORLDY, 1);
        
        //quelques trucs cosmétiques
        Greenfoot.setSpeed(60);
        getBackground().setColor(new Color(135,135,155));
        getBackground().fill();
        
        theWorld = this;
        
        placeHexagonInCollumnRow(29, 15);
        
        //test de création de territoire
        testTerritoryCreation();
        testContinentChange();
        
    }
    
    //////////////////////////
    
    HashSet<SingleHex> singleHexesCurrentlySelected = new HashSet<SingleHex>();
    
    public void selectSingleHex(SingleHex selectedHex)
    //rajoute un SingleHex à la sélection
    {
        
        singleHexesCurrentlySelected.add(selectedHex);
        
    }
    
    HashSet<Territory> territoriesCurrentlySelected = new HashSet<Territory>();
    
    //doit être changé pour avoir 
    public void selectTerritory(Territory selectedTerritory)
    //rajoute un Territory à la selection
    {
        
        territoriesCurrentlySelected.add(selectedTerritory);
        
    }
    
    ///////////////////////////////
    
    public void makeSingleHex(int x, int y)
    {
        
        SingleHex hex = new SingleHex(x,y);
        int[] rectCoord = hex.getCoord().getRectCoord();
        addObject(hex,rectCoord[0],rectCoord[1]);
        
    }
    
    
    
    private void placeHexagonInCollumnRow(int collumn, int row)
    {
        
        for(int x = 0; x < collumn; x++) {
            
            for(int y = 0; y < row; y++) {

                makeSingleHex(x, y);
                
            }
            
        }
        
    }
    
    
    
    private Button getPressedButton(){
        
        return (Button)(mouse.getActor());
        
    }
    
    //////////////////////////////////////////////////Zone de tests
    
    Territory testTerritory;
    
    private void testTerritoryCreation()
    {
        HashSet<Coordinates> hexs = new HashSet<Coordinates>();
        hexs.add(new Coordinates(new int[]{5,5}));
        hexs.add(new Coordinates(new int[]{5,4}));
        hexs.add(new Coordinates(new int[]{4,5}));
        hexs.add(new Coordinates(new int[]{4,4}));
        hexs.add(new Coordinates(new int[]{6,7}));
        hexs.add(new Coordinates(new int[]{6,5}));
        
        try {
            
            testTerritory = new Territory(hexs);
            
        }   catch(Exception e) {
            
            System.out.println(e);
        
        }
        
    }
    
    
    private void testContinentChange()
    {

        //testTerritory.setContinent(new Color(145,145,230));

    }
    
    ////////////////////////////////////////////////
    
    
    public void changeMode(int newMode){
        
        currentMode = newMode;
        
    }
    
    
    
    public void init(){
        
        
        
    }
    
    
    // Inspiré de https://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
    public void saveToXML(){
        
        List continentList = getObjects(Continent.class);
        
        try{
            
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("map");//Création de rootElement
            doc.appendChild(rootElement);
            
            
            for(Object cContinent : continentList){//While  : append des continents à la map (et caractéristiques)
                
                Continent currentContinent = (Continent)cContinent;
                
                Element continent = doc.createElement("continent");
                rootElement.appendChild(continent);
                
                List<Territory> territoriesInContinent = currentContinent.getContainedTerritories();
                
                for(Territory currentTerritory : territoriesInContinent){//append des territoires aux continents (et caractéristiques)
                    
                    Element territory = doc.createElement("territory");
                    continent.appendChild(territory);
                    
                    int hexNumber = 0;
                    
                    ArrayList<TerritoryHex> composingHexes = currentTerritory.getComposingHex();
                    
                    for(TerritoryHex currentHex : composingHexes){//append des hex aux territoires
                        
                        Element hex = doc.createElement("hex");
                        territory.appendChild(hex);
                        
                        Attr hexX = doc.createAttribute("hexX");
                        hexX.setValue("" + currentHex.getX());
                        hex.setAttributeNode(hexX);
                        
                        Attr hexY = doc.createAttribute("hexY");
                        hexY.setValue("" + currentHex.getY());
                        hex.setAttributeNode(hexY);
                        
                    }
                    
                    Attr capitalPoints = doc.createAttribute("capitalPoints");

                    capitalPoints.setValue("" + currentTerritory.getBonusPoints());

                    territory.setAttributeNode(capitalPoints);
                    
                    Attr territoryOwner = doc.createAttribute("territoryOwner");
                    territoryOwner.setValue("");
                    territory.setAttributeNode(territoryOwner);
                    
                    Attr armies = doc.createAttribute("territoryArmies");
                    armies.setValue("0");
                    territory.setAttributeNode(armies);
                    
                    Attr id = doc.createAttribute("territoryId");
                    armies.setValue("" + currentTerritory.getId());
                    territory.setAttributeNode(id);
                    
                    
                    

                    List<Territory> borderingTerritories = currentTerritory.getBorderTerritories();
                    
                    //int borderingNumber = 0;
                    
                    for(Territory currentBordering : borderingTerritories){//append des limitrophes aux territoires
                        
                        Element bordering = doc.createElement("borderingTerritory");
                        territory.appendChild(bordering);
                        
                        Attr borderingID = doc.createAttribute("borderingID");
                        borderingID.setValue("" + currentBordering.getId());
                        bordering.setAttributeNode(borderingID);
                        
                    }
                    
                }
                
                
                Attr continentPoints = doc.createAttribute("continentPoints");
                continentPoints.setValue("" + currentContinent.getContinentColor());
                continent.setAttributeNode(continentPoints);
                
                Attr rContinentColor = doc.createAttribute("rContinentColor");
                rContinentColor.setValue("" + (currentContinent.getContinentColor()).getRed());
                continent.setAttributeNode(rContinentColor);
                
                Attr gContinentColor = doc.createAttribute("gContinentColor");
                gContinentColor.setValue("" + (currentContinent.getContinentColor()).getGreen());
                continent.setAttributeNode(gContinentColor);
                
                Attr bContinentColor = doc.createAttribute("bContinentColor");
                bContinentColor.setValue("" + (currentContinent.getContinentColor()).getBlue());
                continent.setAttributeNode(bContinentColor);
                
            }
            
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("C:\\Users\\dervi\\Desktop\\PROJEEEEET\\RISK_Doom\\MapMakerC"));
            
        }catch(Exception e){
            
            System.out.println("Erreur xml" + e.getMessage());
            
        }

        
    }
    
    
    public void escape(){
        
        territoriesCurrentlySelected.clear();
        
        singleHexesCurrentlySelected.clear();
        
        changeMode(Mode.DEFAULT);
        
    }
    
    
    
    public void calcTerritoryLinks(){
        
        
        
    }
    
    
    
    public void act() 
    {
        
        mouse = Greenfoot.getMouseInfo();
        
        if(getPressedButton() != null){ // Si on a appuyé quelque part
            
            lastClickedButton = getPressedButton();
            
            lastClickedButton.clicked(currentMode);
            
        }
        
        lastClickedButton = null;
        
    }
    
    
}
