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
    static MyWorld theWorld; //pour acceder au monde depuis un non-acteur
    
    static public int mode = Mode.DEFAULT;
    
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
        Coordinates[] aArray = new Coordinates[4];
        //aArray[0] = new Coordinates(new int[]{11,1});
        //aArray[1] = new Coordinates(new int[]{10,2});
        aArray[2] = new Coordinates(new int[]{9,1});
        aArray[3] = new Coordinates(new int[]{10,1});
        new Territory(aArray);

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
    
    
    
    
    public void changeMode(int newMode){
        
        mode = newMode;
        
    }
    

    public void selectHex(SingleHex hex)
    //rajoute un SingleHex à la selection
    {
        
        
        
    }
    

    public void selectTerritory(Territory territory)
    //rajoute un Territory à la selection
    {
        
        
        
    }
    
    
    
    
    public void init(){
        
        
        
    }
    
    
    
    // Inspiré de https://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
    /*public void saveToXML(){
        
        int continentsNumber = 0;
        
        List continentList = getObjects(Continent.class);
        
        try{
            
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("map");
            doc.appendChild(rootElement);
            
            
            int territoriesNumber = 0;
            
            while(continentList.get(continentsNumber) != null){
                
                Continent currentContinent = (Continent)(continentList.get(continentsNumber));
                
                Element continent = doc.createElement("continent");
                rootElement.appendChild(continent);
                
                Territory[] territoriesInContinent = currentContinent.territoriesContained;
                
                while(territoriesInContinent[territoriesNumber] != null){
                    
                    Territory currentTerritory = territoriesInContinent[territoriesNumber];
                    
                    Element territory = doc.createElement("territory");
                    continent.appendChild(territory);
                    
                    
                    int hexNumber = 0;
                    
                    Coordinates[] hexCoordinates = currentTerritory.hexCoordinates;
                    
                    while(hexCoordinates[hexNumber] != null){
                        
                        Coordinates currentHex = hexCoordinates[hexNumber];
                        
                        
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
                    capitalPoints.setValue("" + currentTerritory.capitalPoints);
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
                    
                    
                    
                    Territory[] borderingTerritories = currentTerritory.borderingTerritories;
                    
                    int borderingNumber = 0;
                    
                    while(borderingTerritories[borderingNumber] != null){
                        
                        Territory currentBordering = borderingTerritories[borderingNumber];
                        
                        Attr bordering = doc.createAttribute("bordering");
                        bordering.setValue("" + currentBordering.id);
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

        
    }*/
    
    
    
    
    public void escape(){
        
        
        
    }
    
    
    
    
    public void calcTerritoryLinks(){
        
        
        
    }
    
    
    
    
    public void act() 
    {
        
        
        mouse = Greenfoot.getMouseInfo();
        
        lastClickedButton = getPressedButton();
        
        
        if(lastClickedButton != null){ // Si on a appuyé quelque part
            
            
            switch(mode){
                
                default : escape();
                                break;
                
                case Mode.SELECT_HEX : 
                                break;
                
                case Mode.SELECT_TERRITORY : 
                                break;
                                
                case Mode.CHOOSE_DISPLAY_ARMIES :
                                break;
                                
                case Mode.CHOOSE_CAPITAL_TERRITORY :
                                break;
                                
                case Mode.CHOOSE_DISPLAY_INFO : 
                                break;
                                
                case Mode.SET_LINKS :
                                break;
                                
            }
            
            
        }
        
        
        
        
        lastClickedButton = null;
        
        
    }
    
    
}
