import greenfoot.GreenfootImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.IOException;
import greenfoot.Font;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;


public class MapChooser extends Button implements Arrowable{
    
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
    
    /**
     *Utiliser seulement à travers MyWorld.act()
     */
    @Override
    public void clicked() {
        world().setupMapEditorScene();
        MyWorld.readXMLMap(directory.getName() + "/" +fileList.get(mapNumber));
        world().escape();
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
        //l'image qui sera set comme l'image de l'objet
        GreenfootImage returnImage = new GreenfootImage(500,500);
        try{
            //rajoute l'image de la map (tirée du .png) au returnImage
            GreenfootImage mapImage = new GreenfootImage(name + ".png");
            mapImage.scale(500, 300);
            returnImage.drawImage(mapImage, 0, 0);
            
            } catch(IllegalArgumentException e) {
                //Si le .png de la map est absent
                //créer une image affichant "THUMBNAIL NOT FOUND"
                GreenfootImage thumbnailNotFound = new GreenfootImage(500,300);
                
                thumbnailNotFound.setColor(new GColor(150,150,150));
                thumbnailNotFound.fill();
                thumbnailNotFound.setColor(new GColor(0,0,0));
                
                thumbnailNotFound.setFont(new Font("Monospaced", 20));
                thumbnailNotFound.drawString("THUMBNAIL NOT FOUND", 180, 170);
                thumbnailNotFound.drawString(name.replace(directory.getName() + "/", ""), 190, 250);
                System.err.println("The thumbnail for map " + name.replace(directory.getName() + "/", "") +
                                    " is missing. Make sure the thumbnails are placed in this directory: " + directory.getAbsolutePath());
                returnImage.drawImage(thumbnailNotFound, 0, 0);
        } 
            
            //quelques settings d'apparence
            returnImage.setFont(new Font("Monospaced", 20));
            returnImage.setColor(GColor.WHITE);
            
            //créer le String de la description
            String completeMessage = "";
            completeMessage += wrapText(name.replace(directory.getName() + "/", ""), 40, "Map Name :");
            completeMessage += wrapText(getMapDescription(directory.getName() + "/" +fileList.get(mapNumber)), 40, "Description :");
            completeMessage += "\n";
            completeMessage += "Map " + (mapNumber + 1) + " out of " + fileArray.length;
            
            //dessiner la description
            returnImage.drawString(completeMessage, 10, 300);
            
            this.setImage(returnImage);
            
        
        
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
