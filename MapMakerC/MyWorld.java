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
    
    SingleHex[][] singleHex2DArray = new SingleHex[50][30];
    
    static MyWorld theWorld; //pour accéder au monde depuis un non-acteur
    
    static public int currentMode = Mode.DEFAULT;
    
    MouseInfo mouse = Greenfoot.getMouseInfo();
    
    Button lastClickedButton;
    
    
  
    public MyWorld()
    {    
        super(WORLDX, WORLDY, 1);
        
        //quelques trucs cosmétiques
        Greenfoot.setSpeed(60);
        getBackground().setColor(new Color(150,20,70));
        getBackground().fill();
        
        theWorld = this;
        
        placeHexagonInCollumnRow(29, 15);
        
        //test de création de territoire
        testTerritoryCreation();
        
    }
    
    
    
    
    
        private void makeSingleHex(int x, int y)
    {
        SingleHex hex = new SingleHex(x,y);
        int[] rectCoord = hex.getCoord().getRectCoord();
        addObject(hex,rectCoord[0],rectCoord[1]);
        singleHex2DArray[x][y] = hex;
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
    
    
    
    
    
    private void testTerritoryCreation()
    {
        ArrayList<Coordinates> hexs = new ArrayList<Coordinates>();
        hexs.add(new Coordinates(new int[]{5,5}));
        hexs.add(new Coordinates(new int[]{5,4}));
        hexs.add(new Coordinates(new int[]{4,5}));
        hexs.add(new Coordinates(new int[]{4,4}));
        hexs.add(new Coordinates(new int[]{6,7}));
        hexs.add(new Coordinates(new int[]{6,5}));
        
        try {
            
            new Territory(hexs);
            
        }   catch(Exception e) {
            
            System.out.println(e);
        
        }
        
    }
    
    
    
    
    public void changeMode(int newMode){
        
        currentMode = newMode;
        
    }
    
    
    
    
    
    
    
    
    SingleHex[] singleHexesCurrentlySelected = new SingleHex[50];
    
    int singleHexesCurrentlySelectedNumber = 0;
    

    public void selectHex(SingleHex hex)
    //rajoute un SingleHex à la selection
    {
        
        singleHexesCurrentlySelected[singleHexesCurrentlySelectedNumber] = hex;
        
        singleHexesCurrentlySelectedNumber++;
        
    }
    
    
    
    
    
    
    
    
    Territory[] territoriesCurrentlySelected = new Territory[30];
    
    int territoriesCurrentlySelectedNumber = 0;
    

    public void selectTerritory(Territory territory)
    //rajoute un Territory à la selection
    {
        
        territoriesCurrentlySelected[territoriesCurrentlySelectedNumber] = territory;
        
        territoriesCurrentlySelectedNumber++;
        
    }
    
    
    
    
    public void init(){
        
        
        
    }
    
    
    
    // Inspiré de https://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
    public void saveToXML(){
        
        int continentsNumber = 0;
        
        List continentList = getObjects(Continent.class);
        
        try{
            
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("map");//Création de rootElement
            doc.appendChild(rootElement);
            
            
            int territoriesNumber = 0;
            
            while(continentList.get(continentsNumber) != null){//While 1 : append des continents à la map (et caractéristiques)
                
                Continent currentContinent = (Continent)(continentList.get(continentsNumber));
                
                Element continent = doc.createElement("continent");
                rootElement.appendChild(continent);
                
                Territory[] territoriesInContinent = currentContinent.territoriesContained;
                
                for(Territory currentTerritory : territoriesInContinent){//append des territoires aux continents (et caractéristiques)
                    
                    
                    Element territory = doc.createElement("territory");
                    continent.appendChild(territory);
                    
                    
                    int hexNumber = 0;
                    
                    Coordinates[] hexCoordinates = currentTerritory.getComposingHex();

                    
                    for(Coordinates currentHex : hexCoordinates){//append des hex aux territoires
                        
                        
                        Element hex = doc.createElement("hex");
                        territory.appendChild(hex);
                        
                        Attr hexX = doc.createAttribute("hexX");
                        hexX.setValue("" + currentHex.getHexCoord()[0]);
                        hex.setAttributeNode(hexX);
                        
                        Attr hexY = doc.createAttribute("hexY");
                        hexY.setValue("" + currentHex.getHexCoord()[1]);
                        hex.setAttributeNode(hexY);
                        
                        
                    }
                    
                    
                    Attr capitalPoints = doc.createAttribute("capitalPoints");

                    capitalPoints.setValue("" + currentTerritory.getCapitalBonus());

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
                    
                    
                    

                    Territory[] borderingTerritories = currentTerritory.getBorderingTerritories();

                    int borderingNumber = 0;
                    
                    for(Territory currentBordering : borderingTerritories){
                        

                        Attr borderingID = doc.createAttribute("borderingID");
                        borderingID.setValue("" + currentBordering.getId());
                        territory.setAttributeNode(borderingID);

                        Attr bordering = doc.createAttribute("bordering");
                        bordering.setValue("" + currentBordering.getId());
                        territory.setAttributeNode(bordering);

                        
                        
                    }
                    
                    
                    territoriesNumber++;
                    
                }
                
                
                Attr continentPoints = doc.createAttribute("continentPoints");
                continentPoints.setValue("" + currentContinent.continentBonus);
                continent.setAttributeNode(continentPoints);
                
                Attr rContinentColor = doc.createAttribute("rContinentColor");
                rContinentColor.setValue("" + (currentContinent.continentColor).getRed());
                continent.setAttributeNode(rContinentColor);
                
                Attr gContinentColor = doc.createAttribute("gContinentColor");
                gContinentColor.setValue("" + (currentContinent.continentColor).getGreen());
                continent.setAttributeNode(gContinentColor);
                
                Attr bContinentColor = doc.createAttribute("bContinentColor");
                bContinentColor.setValue("" + (currentContinent.continentColor).getBlue());
                continent.setAttributeNode(bContinentColor);
                
                continentsNumber++;
                
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
        
        
        
    }
    
    
    
    
    public void calcTerritoryLinks(){
        
        
        
    }
    
    
    
    
    public void act() 
    {
        
        
        mouse = Greenfoot.getMouseInfo();
        
        
        if(getPressedButton() != null){ // Si on a appuyé quelque part
            

            lastClickedButton.clicked(currentMode);

            lastClickedButton = getPressedButton();
            
            lastClickedButton.clicked(currentMode);
            

            
        }
        
        
        
        lastClickedButton = null;
        
        
    }
    
    
}
