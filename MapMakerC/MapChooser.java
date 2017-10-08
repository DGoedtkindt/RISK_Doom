import greenfoot.GreenfootImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.awt.Color;
import java.awt.Font;

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
    
    private void updateImage() {
        String name = directory.getName() + "/" + fileList.get(mapNumber).replace(".xml", "");
        try{
            GreenfootImage returnImage = new GreenfootImage(500,400);
            GreenfootImage mapImage = new GreenfootImage(name + ".png");
            mapImage.scale(500, 300);
            returnImage.drawImage(mapImage, 0, 0);
            returnImage.setFont(new Font("Monospaced", 0, 20));
            returnImage.setColor(Color.WHITE);
            returnImage.drawString(name.replace(directory.getName() + "/", ""), 190, 350);
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
    
}
