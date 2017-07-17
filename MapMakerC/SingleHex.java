import greenfoot.*; 
import java.awt.Color;

public class SingleHex extends Button
{
    private Coordinates coord = new Coordinates();
    
    public SingleHex(int xHCoord, int yHCoord)
    {
        setCoord(xHCoord,yHCoord);
        
        this.setImage(Hexagon.createHexagonImage(Color.WHITE));
    }
    
    
    
    
    
    private void setCoord(int x, int y)
    {
        coord.setHexCoord(new int[]{x,y});
    }
    
    
    
    
    
    public Coordinates getCoord()
    {
        return coord;
    }
    
    
    
    
    
    public void clicked(int mode){
        
        
        switch(mode){
            /*       il ne faut pas que SingleHex ne se supprime automatiquement 
             *       (et c'est myWorld qui devrait vérifier la validité de la sélection)         
            case Mode.SELECT_HEX : ((MyWorld)getWorld()).selectHex(this);
            
                                   TerritoryHex newHex = new TerritoryHex();
                                   newHex.coord = this.coord;
                                   newHex.setImage(Hexagon.createHexagonImage(Color.BLUE)); // Bleu provisoire
                                   
                                   getWorld().addObject(newHex, coord.getHexCoord()[0], coord.getHexCoord()[1]);
                                   
                                   
                                   getWorld().removeObject(this);
                                break;
                        */        
                                
            default : ((MyWorld)getWorld()).escape();
                                break;
                                
                                
        }
        
        
    }
    
    
    
    
    
    public void act() 
    {
        
    }    
    
    
    
}
