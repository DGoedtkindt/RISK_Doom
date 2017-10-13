import greenfoot.GreenfootImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class MapChooser extends Button{
    
    private int mapNumber = 0;
    private File directory;
    private String[] fileArray;
    private ArrayList<String> fileList = new ArrayList<>();
    
    private static MyWorld world() {return MyWorld.theWorld;}
    
    public MapChooser() {
        directory = new File("Maps");
        if(directory.isDirectory()) {
            fileArray = directory.list((File file, String name) -> {
                return name.endsWith(".xml");
            });
        } else System.err.println("The Map directory was not found. please make sure it is placed there: " + directory.getAbsolutePath());
       
        fileList.addAll(Arrays.asList(fileArray));
        fileList.remove("New Map.xml");
        fileList.add(0,"New Map.xml");
        
        updateImage();
        
    }
    
    public void clicked() {
        world().setupScene();
        MyWorld.readXMLMap(directory.getName() + "/" +fileList.get(mapNumber));
        MyWorld.escape();
    }
    
    public void next() {
        if(mapNumber < fileList.size()-1) mapNumber++;
        else mapNumber = 0;
        updateImage();
    
    }
    
    public void previous() {
        if(mapNumber > 0) mapNumber--;
        else mapNumber = fileList.size()-1;
        updateImage();

    }
    
    public ArrayList<String> fileList(){return fileList;}
    
    ///Private methods///////////////////////////////////
    
    private void updateImage() {
        String name = directory.getName() + "/" + fileList.get(mapNumber).replace(".xml", "");
        try{
            GreenfootImage returnImage = new GreenfootImage(500,500);
            GreenfootImage mapImage = new GreenfootImage(name + ".png");
            mapImage.scale(500, 300);
            returnImage.drawImage(mapImage, 0, 0);
            returnImage.setFont(new Font("Monospaced", 0, 20));
            returnImage.setColor(Color.WHITE);
            
            String completeMessage = "";
            completeMessage += wrapText(name.replace(directory.getName() + "/", ""), 40, "Map Name :");
            completeMessage += wrapText(getMapDescription(directory.getName() + "/" +fileList.get(mapNumber)), 40, "Description :");
            completeMessage += "\n";
            completeMessage += "Map " + (mapNumber + 1) + " out of " + fileArray.length;
            
            returnImage.drawString(completeMessage, 10, 300);
            
            this.setImage(returnImage);
            
        } catch(IllegalArgumentException e) {
            
            GreenfootImage thumbnailNotFound = new GreenfootImage(500,300);
            thumbnailNotFound.setColor(new Color(150,150,150));
            thumbnailNotFound.fill();
            thumbnailNotFound.setColor(new Color(0,0,0));
            thumbnailNotFound.drawString("THUMBNAIL NOT FOUND", 180, 170);
            thumbnailNotFound.setFont(new Font("Monospaced", 0, 20));
            thumbnailNotFound.drawString(name.replace(directory.getName() + "/", ""), 190, 250);
            System.err.println("The thumbnail for map " + name.replace(directory.getName() + "/", "") +
                                " is missing. Make sure the thumbnails are placed in this directory: " + directory.getAbsolutePath());
            this.setImage(thumbnailNotFound);
        } 
        
    }
    
    private String getMapDescription(String fileName){
        
        String mapDescription = "";
        Document doc;
        
        try{
        
            File XMLFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(XMLFile);
            doc.getDocumentElement().normalize();
            
            Element mapElement = ((Element)(doc.getElementsByTagName("map").item(0)));
            
            if(mapElement.hasAttribute("description")){
                mapDescription = mapElement.getAttribute("description");
                
            }
            
        }catch(IOException | ParserConfigurationException | DOMException | SAXException e){System.err.println(e.getMessage());}
        
        if(!mapDescription.equals("")){
            return mapDescription;
        }else{return "This map has no description";}
        
    }
    
    private static String wrapText(String strToWrap, int maxLineLength, String entryName) {
        
        String nextEntry = "";
        
        for(char c : entryName.toCharArray()){
            
            nextEntry += " ";
            
        }
        
        String[] words = strToWrap.split(" ");
        String finalString = "\n";
        
        String currentLine = entryName;
        for(String currentWord : words){
            if((currentLine + currentWord).length() > maxLineLength){
                finalString += currentLine + "\n";
                currentLine = nextEntry;
                
            }
            currentLine += " " + currentWord;
            
        }
        finalString += currentLine;
        
        return finalString;
    
    }
    
}
