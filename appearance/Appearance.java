package appearance;

import base.GColor;

public class Appearance {
    //Taille du monde
    public static final int WORLD_WIDTH = 1920;
    public static final int WORLD_HEIGHT = 1080;
    
    //Pour la grille d'hexagones
    public static final int COLLUMN_NUMBER = 37;
    public static final int ROW_NUMBER = 19;
    
    //Couleurs de base
    public static final GColor WORLD_COLOR = new GColor(55, 40, 55);
    public static final GColor SELECTION_COLOR = new GColor(0, 220, 0);
    public static final GColor BASE_HEX_COLOR = new GColor(110, 80, 110);
    
    //Transparence et opacité des acteurs
    public static final int TRANSPARENT = 30;
    public static final int OPAQUE = 255;
    
    //positions hexagonales pour dessiner le vide contenant l'affichage 
    //des bonus des continents
    public static final int CONTINENT_BONUS_ZONE_WIDTH = 12;
    public static final int CONTINENT_BONUS_ZONE_HEIGHT = 4;
    public static final int CONTINENT_BONUS_X_POSITION = COLLUMN_NUMBER / 2 - CONTINENT_BONUS_ZONE_WIDTH / 2;
    public static final int CONTINENT_BONUS_Y_POSITION = ROW_NUMBER - CONTINENT_BONUS_ZONE_HEIGHT;
}
