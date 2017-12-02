package xmlChoosers;

import base.*;
import arrowable.*;
import greenfoot.GreenfootImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import greenfoot.Font;
import greenfoot.World;

public abstract class XMLChooser extends Button implements Arrowable {
    
    //un XMLChooser a une image de taille 500x500
    
    private int mapNumber = 0;
    private File directory;
    private String[] fileArray;
    private ArrayList<String> fileList = new ArrayList<>();
    private RightArrow rightArrow;
    private LeftArrow leftArrow;
    
    public XMLChooser(String directoryName, String defaultFile){
        directory = new File(directoryName);
        if(directory.isDirectory()) {
            fileArray = directory.list((File file, String name) -> {
                return name.endsWith(".xml");
            });
            
            fileList.addAll(Arrays.asList(fileArray));
            
            if(fileList.contains(defaultFile + ".xml")) {
                fileList.remove(defaultFile + ".xml");
                fileList.add(0,defaultFile + ".xml");
                
            } else {
                System.err.println(defaultFile + ".xml was not found. please make sure it is placed there: " + directory.getAbsolutePath());
            
            }
            
        } else {
            System.err.println("The " + directoryName + " directory was not found. please make sure it is placed there: " + directory.getAbsolutePath());
            System.exit(0);
            
        }
        
        updateImage();
        
        rightArrow = new RightArrow(this);
        leftArrow = new LeftArrow(this);
        
    }
    
    public void addToWorld(World world, int xPos, int yPos) {
        world.addObject(this, xPos, yPos);
        world.addObject(leftArrow, xPos - 310, yPos - 70);
        world.addObject(rightArrow, xPos + 310, yPos -70);
    
    }
    
    public void removeFromWorld(World world) {
        world.removeObject(this);
        world.removeObject(rightArrow);
        world.removeObject(leftArrow);
                
    }
    
    protected String currentFile() {
        return directory.getName() + "/" + fileList.get(mapNumber);
    
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
                System.err.println("The thumbnail for " + name.replace(directory.getName() + "/", "") +
                                    " is missing. Make sure the thumbnails are placed in this directory: " + directory.getAbsolutePath());
                returnImage.drawImage(thumbnailNotFound, 0, 0);
        } 
            
            //quelques settings d'apparence
            returnImage.setFont(new Font("Monospaced", 20));
            returnImage.setColor(GColor.WHITE);
            
            //créer le String de la description
            String completeMessage = "";
            completeMessage += wrapText(name.replace(directory.getName() + "/", ""), 40, "Name :");
            completeMessage += wrapText(getDescription(directory.getName() + "/" +fileList.get(mapNumber)), 40, "Description :");
            completeMessage += "\n";
            completeMessage += (mapNumber + 1) + " out of " + fileArray.length;
            
            //dessiner la description
            returnImage.drawString(completeMessage, 10, 300);
            
            this.setImage(returnImage);
            
        
        
    }
    
    protected abstract String getDescription(String fileName);
    
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
