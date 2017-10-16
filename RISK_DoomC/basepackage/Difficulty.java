package basepackage;
import java.util.HashMap;

public class Difficulty {
    public HashMap<String,Difficulty> nameDiffMap = new HashMap<>();
    
    public final String NAME;
    public final int PROB_ATTACK;
    public final int N_TURN_RESUMMON;
    public final int FIRST_SUMMON_TURN;
    public final float INCREMENT; 
    public final boolean RESUMMON_WHEN_LAST_KILLED;
    
    public Difficulty(String name,int pAtt, int resummonTurn, int firstSummon, float incr, boolean resum) {
        NAME = name;
        PROB_ATTACK = pAtt;
        N_TURN_RESUMMON = resummonTurn;
        FIRST_SUMMON_TURN = firstSummon;
        INCREMENT = incr;
        RESUMMON_WHEN_LAST_KILLED = resum;
    
    }        
            
    public static void getDifficulties() {
    
    }
    
}
