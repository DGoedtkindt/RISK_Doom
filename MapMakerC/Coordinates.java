//pour éviter les duplicates, les méthodes relatives aux coordonées Hexagonales/Rectangulaires sont ici

public class Coordinates  
{
    public int[] hexCoord = new int[2];
    
    public Coordinates() {}
    
    public Coordinates(int[] hexCoordinates) {
        hexCoord = hexCoordinates;
    }
    
    
    public static int[] hexToRectCoord(int[] hexCoord){
        int[] converted = new int[2];  //Tableau de retour
        
        double h = Math.sin(Math.PI/3) * (Hexagon.RADIUS-1); // Hauteur d'un Hex
        
        int DECALAGE_Y = 15; // Décalage au sommet
        
        converted[0] = (Hexagon.RADIUS-1) + (int)(1.5 * hexCoord[0] * (Hexagon.RADIUS-1));
        converted[1] = DECALAGE_Y + (int)(h + 2 * hexCoord[1] * h);            //X et Y du centre
        
        if(hexCoord[0] % 2 == 1) converted[1] += h; //Décalage vertical une fois sur deux
        
        return converted;
        
    }
    
    public int[] rectCoord(){
        return  hexToRectCoord(hexCoord);
    }
    
}
