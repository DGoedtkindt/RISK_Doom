package territory;

import appearance.MessageDisplayer;
import base.Action;
import base.MyWorld;
import game.Player;
import input.Form;
import input.TextInput;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import javafx.util.Pair;
import mode.Mode;
import selector.Selector;

/**
 * A Move is an Object that controls the progress of an army movement
 * A Move must be initialised first then the move() method can be called
 * to execute it.
 */
public class Move {
    
    private static Move initialising;
    
    static{
        //to make sure 'initialising' is null when not in Mode 'MOVE'
        Mode.addModeChangeListener(()->{
            if(Mode.mode() != Mode.MOVE & initialising != null) initialising = null;
        });
        
        
    }
    
     public static void initialisingNext(Territory selected) {
        if(null != initialising) {
            initialising.nextPart(selected);
        }
    
    }
    
    public Territory from;
    public Territory to;
    public int armies = 0;
    
    private final Player mover;
    private Action callback;
    
    
    
    public Move(Player movingPlayer) {
        mover = movingPlayer;
    
    }
    
    /**
     * Initialises this Move. Initialisation lets the user choose from where to 
     * where they want to move and with how many armies.
     * There can only be one Move being initialised at a time.
     * @param callback the action to be executed once the initialisation
     * successfully complete.
     */
    public void init(Action callback) {
        if(initialising == null) {
            initialising = this;
            this.callback = callback;
            Mode.setMode(Mode.ATTACK);
            chooseSource();
        } else System.err.println("There already is an Attack being initialised");
    } 
    
    /**
     * Makes the initialisation of this Move proceed to the next part.
     * This will only work if this Move is the current initialising Move
     * @param selected the Territory that has been selected during the previous
     * part of the initialisation.
     */
    public void nextPart(Territory selected) {
        Selector.select(selected);
        if(this == initialising) {
            if(from == null) {
                from = selected;
                chooseTarget();
            }
            else if(to == null) {
                to = selected;
                chooseArmies();
            }
        }
    
    }
    
    /**
     * This is the first part of the initialisation of the Move.
     * It makes the user choose from where they want to move.
     * 
     */
    private void chooseSource() {
        Selector.setValidator(canMove());
    
    }
    
    /**
     * This is the second part of the initialisation of the Move.
     * It makes the user choose their target.
     */
    private void chooseTarget() {
        Selector.setValidator(isTargetable());
    
    }
    
    /** 
     * This is the last part of the initialisation of the Move.
     * It makes the user choose with how many armies they want to move.
     */
    private void chooseArmies() {
        Form form = new Form();
        TextInput armiesInput = new TextInput("Are you sure you want to move armies?\n"
                + "How many armies do you want to use?");
        form.addInput("armies", armiesInput, false);
        form.submitAction = (Map<String,String> input) -> { 
            if(input.get("armies").matches("\\d+")){    
                armies = Integer.parseInt(input.get("inputedText"));
                callback.act();
                MyWorld.theWorld.stateManager.escape();
            }else{
                appearance.MessageDisplayer.showMessage("Invalid Entry.");
            }
        };
        form.cancelAction = (String c) -> {
            MyWorld.theWorld.stateManager.escape();
        
        };
        form.addToWorld();
        
    }
    
    /**
     * Executes the move as it was initialised.
     */
    public void move() {
        Pair<Boolean,String> moveValid = moveValid();
        if(moveValid.getKey() != true) MessageDisplayer.showMessage(moveValid.getValue());
        else{
            fromLooseArmies();
            toGainArmies();
        }
    }
    
    private Pair<Boolean,String> moveValid() {
        if(from == null | to == null) return new Pair(false, "Move wasn't initialised properly");
        if(armies<1) return new Pair(false,"You can't move no armies");
        if(armies >= from.armies()) return new Pair(false, "You don't have enough armies.");
        else return new Pair(true, "March !!!");
    
    }
    
    private void fromLooseArmies() {
        from.addArmies(-armies);
        
    }
    
    private void toGainArmies() {
        to.addArmies(armies);
    
    }
    
    private Predicate canMove() {
        return (Object o) -> {
            if(o instanceof Territory && ((Territory) o).owner() == mover){
                Territory territory = (Territory) o;
                List<Territory> allies = territory.owner().territories();
                allies.remove(territory);
                return territory.armies() > 1 & !allies.isEmpty();
            }else return false;
        };
    
    };
    
    private Predicate isTargetable() {
        return (Object o) -> {
            if(o instanceof Territory & from != null){
                return ((Territory) o).owner() == from.owner() ;
            }else return false;
        };
    
    };

}
