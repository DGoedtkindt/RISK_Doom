import greenfoot.*; 
import java.awt.*;

public class MyWorld extends World
{
    
    int turnsCount = 0;
    int playersNumber;
    int activePlayer;   // = turnsCount % (playersNumber + 1)
    int currentMode;
    Player[] playersPlaying = new Player[7];
    
    
    public MyWorld()
    {    
        super(600, 400, 1); 
    }
    
    public void act()
    {
    
    }
    
    void StartOfGame()
    {
    
    }
    
    void StartOfTurn()
    {
    
    }
    
    void EndOfTurn()
    {
    
    }
    
    void ExtractXML() //to be renamed
    {
    
    }
    
    void SaveGame()
    {
    
    }
    
    void drawMap()
    {
    
    }
    
    void setPlayers()
    {
    
    }
    
    void giveTerritories()
    {
    
    }
    
    void setMission()
    {
        
    }
    
    boolean hasPlayerWon()
    {
        return false;
    }
    
    boolean isPlayerDead()
    {
        return false;
    }
    
    void setZombies()
    {
    
    }
    
    void calculateArmies()
    {
    
    }
    
    void removePlayerBonuses()
    {
    
    }
    
    void changeActivePlayer()
    {
    
    }
    
    Button ButtonPressed()
    {
        return  null;
    }
    
    void Escape() //en cas d'action illégale ou que 'esc' a été press. annuller les actions et revenir à la normale
    {
    
    }
}
