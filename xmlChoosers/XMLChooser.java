package xmlChoosers;

import appearance.MessageDisplayer;
import appearance.Theme;
import arrowable.Arrowable;
import arrowable.LeftArrow;
import arrowable.RightArrow;
import base.GColor;
import base.MyWorld;
import greenfoot.GreenfootImage;
import greenfoot.Actor;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import greenfoot.Font;
import java.io.FileNotFoundException;

/**
 * A XMLChooser is a Group of Actors that enables the user to choose between
 * .xml files. It being a group of actor means that it should not be
 * added/removed from a World using World.addObject()/removeObject(), but rather
 * with addToWorld()/destroy().
 */

public abstract class XMLChooser implements Arrowable {
    
    private int mapNumber = 0;
    private File directory;
    private String[] fileArray;
    private ArrayList<String> fileList = new ArrayList<>();
    private RightArrow rightArrow;
    private LeftArrow leftArrow;
    private Thumbnail thumbnail;
    protected MyWorld world() {return MyWorld.theWorld;}
    
    private class Thumbnail extends Actor {}
    
    /**
     * @param directoryName the name of the directory where the XMLChooser fetches
     * the .XML files
     * @param specialFile the name of the .XML file which will either be shown 
     * by default or hidden. null for no special .XML file.
     * @param defaultOrHide true: specialFile is shown by default
     * false: special file is hidden
     */
    public XMLChooser(String directoryName, String specialFile, boolean defaultOrHide){
        directory = new File(directoryName);
        if(directory.isDirectory()) {
            fileArray = directory.list((File file, String name) -> {
                return name.endsWith(".xml");
            });
            
            fileList.addAll(Arrays.asList(fileArray));
            
            if(specialFile == null) {}
            else if(fileList.contains(specialFile + ".xml")) {
                
                fileList.remove(specialFile + ".xml");
                if(defaultOrHide) fileList.add(0,specialFile + ".xml");
                
            } else {
                MessageDisplayer.showMessage(specialFile + ".xml was not found. please make sure it is placed there: " + directory.getAbsolutePath());
                System.err.println(specialFile + ".xml was not found. please make sure it is placed there: " + directory.getAbsolutePath());
            
            }
            
        } else {
            MessageDisplayer.showException(new FileNotFoundException("The " 
                    + directoryName + " directory was not found."
                    + " please make sure it is placed there: " 
                    + directory.getAbsolutePath()));
            
        }
        
        thumbnail = new Thumbnail();
        rightArrow = new RightArrow(this);
        leftArrow = new LeftArrow(this);
        updateImage();
        
    }
    
    /**
     * @param directoryName the name of the directory where the XMLChooser fetches
     * the .XML files
     */
    public XMLChooser(String directoryName) {
        this(directoryName, null, true);
    
    }
    
    public void addToWorld(int xPos, int yPos) {
        world().addObject(thumbnail, xPos, yPos);
        world().addObject(leftArrow, xPos - 310, yPos - 70);
        world().addObject(rightArrow, xPos + 310, yPos -70);
    
    }
    
    public void destroy() {
        world().removeObject(thumbnail);
        world().removeObject(rightArrow);
        world().removeObject(leftArrow);
                
    }
    
    public File getCurrentFile() {
        return new File(currentFile());
                
    }
    
    protected String currentFile() {
        return directory.getName() + "/" + fileList.get(mapNumber);
    
    }
    
    @Override
    public void next() {
        if(mapNumber < fileList.size()-1) mapNumber++;
        else mapNumber = 0;
        updateImage();
    
    }
    
    @Override
    public void previous() {
        if(mapNumber > 0) mapNumber--;
        else mapNumber = fileList.size()-1;
        updateImage();

    }
    
    ///Private methods///////////////////////////////////
    
    private void updateImage() {
        String name = directory.getName() + "/" + fileList.get(mapNumber).replace(".xml", "");
        
        //The image of this Object
        GreenfootImage returnImage = new GreenfootImage(500, 500);
        
        try{
            //Draws the image of the Map
            GreenfootImage mapImage = new GreenfootImage(name + ".png");
            mapImage.scale(500, 300);
            returnImage.drawImage(mapImage, 0, 0);
            
        } catch(IllegalArgumentException e) {

            //If the Map image doesn't exist, creates a 
            //'THUMBNAIL NOT FOUND' image
            GreenfootImage thumbnailNotFound = new GreenfootImage(500,300);

            thumbnailNotFound.setColor(new GColor(150,150,150));
            thumbnailNotFound.fill();
            thumbnailNotFound.setColor(new GColor(0,0,0));

            thumbnailNotFound.setFont(new Font("Monospaced", 20));
            thumbnailNotFound.drawString("THUMBNAIL NOT FOUND", 180, 170);
            thumbnailNotFound.drawString(name.replace(directory.getName() + "/", ""), 190, 250);
            System.err.println("The thumbnail for '" + name.replace(directory.getName() + "/", "") +
                                "' is missing. Make sure the thumbnails are placed in this directory: " + directory.getAbsolutePath());
            MessageDisplayer.showMessage("The thumbnail for '" + name.replace(directory.getName() + "/", "") +
                                        "' is missing. Make sure the thumbnails are placed in this directory: " + directory.getAbsolutePath());
            returnImage.drawImage(thumbnailNotFound, 0, 0);
        } 

        //Font and Colors
        returnImage.setFont(new Font("Monospaced", 20));
        returnImage.setColor(Theme.used.textColor);

        //Obtains a description
        String completeMessage = "";
        completeMessage += wrapText(name.replace(directory.getName() + "/", ""), 40, "Name :");
        completeMessage += wrapText(getDescription(directory.getName() + "/" +fileList.get(mapNumber)), 40, "Description :");
        completeMessage += "\n";
        completeMessage += (mapNumber + 1) + " out of " + fileList.size();

        //Draws the description
        returnImage.drawString(completeMessage, 10, 300);

        thumbnail.setImage(returnImage);
            
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
