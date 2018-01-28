package mainObjects;

import base.GColor;

public class Player {
    
    private final String name;
    private final GColor color;
    private int armiesInHand = 0;
    
    private Combo combos = new Combo();
    
    public Player(String playerName, GColor c){
        name = playerName;
        color = c;
        
    }
    
    public void startTurn(){
        
        if(!aPlayerIsDead()){
            getArmies();
            
        }else{
            //SOMEWHERE endGame();
        }
        
    }
    
    private void getArmies(){
        
        int n = 0;
        
        /*
        
        for(Territory t : SOMEWHERE territories.size()){
            
            if(t.owner == this){
                n++;
            }
            
        }
        
        */
        
        armiesInHand += Math.floor(n / 3);
        
    }
    
    static public boolean aPlayerIsDead(){
        
        /*
        
        ArrayList<Player> playersAlive = new ArrayList<Player>();
        
        for(Territory t : SOMEWHERE territories.size()){
            
            if(t.owner != null && !playersAlive.contains(t.owner())){
                playersAlive.add(t.owner());
            }
            
            if(playersAlive.size() != SOMEWHERE players.size()){
                SOMEWHERE endGame();
                return true;
            }
            
        }
        
        */
        
        return false;
    }
    
}
