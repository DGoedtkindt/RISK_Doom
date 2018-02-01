package mainObjects;

import base.GColor;

public class Player {
    
    private static final String ZOMBIE_NAME = "zombie";
    
    private final String name;
    private final GColor color;
    private int armiesInHand = 0;
    
    private Combo combos = new Combo();
    
    public Player(String playerName, GColor c){
        name = playerName;
        color = c;
        
    }
    
    static public void nextPlayer(){
        
        if(!aPlayerIsDead()){
            
            // SOMEWHERE on change de joueur
            
            // IF player.name != zombie (un truc comme ça, sauf si class Zombie extends Player)
            //      showNextTurnPanel();
            // ELSE play zombie turn
            
        }
        /*else{                 C'est en commentaire parce que c'est déjà géré dans aPlayerIsDead(), à voir comment on fait
            Game.end();
        }*/
        
    }
    
    static public void showNextTurnPanel(){
        
        
        
    }
    
    public void startTurn(){
        
        getArmies();
        
    }
    
    public void endTurn(){
        throw new UnsupportedOperationException("Not supported yet.");
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
                Game.end();
                return true;
            }
            
        }
        
        */
        
        return false;
    }
    
}
