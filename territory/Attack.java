package territory;

import appearance.MessageDisplayer;
import base.Action;
import base.MyWorld;
import game.Player;
import input.Form;
import input.TextInput;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import javafx.util.Pair;
import mode.Mode;
import selector.Selector;

/**
 * An Attack is an Object that controls the progress of an attack
 * An attack must be initialised first then the attack() method can be called
 * to execute it.
 */
public class Attack {
    private static Attack initialising;
    
    public Territory attacking;
    public Territory defending;
    public int armies = 0;
    
    private final Player attacker;
    private Action callback;
    
    static{
        //to make sure 'initialising' is null when not in Mode 'ATTACK'
        Mode.addModeChangeListener(()->{
            if(Mode.mode() != Mode.ATTACK & initialising != null) initialising = null;
        });
        
        
    }
    
    public static void initialisingNext(Territory selected) {
        if(null != initialising) {
            initialising.nextPart(selected);
        }
    
    }
    
    public Attack(Player attackingPlayer) {
        attacker = attackingPlayer;
    
    }
    
    /**
     * Initialises this Attack. Initialisation lets the user choose from where to 
     * where they want to attack and with how many armies.
     * There can only be one Attack being initialised at a time.
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
     * Makes the initialisation of this Attack proceed to the next part.
     * This will only work if this Attack is the current initialising Attack
     * @param selected the Territory that has been selected during the previous
     * part of the initialisation.
     */
    public void nextPart(Territory selected) {
        Selector.select(selected);
        if(this == initialising) {
            if(attacking == null) {
                attacking = selected;
                chooseTarget();
            }
            else if(defending == null) {
                defending = selected;
                chooseArmies();
            }
        }
    
    }
    
    /**
     * This is the first part of the initialisation of the Attack.
     * It makes the user choose from where they want to attack.
     * 
     */
    private void chooseSource() {
        Selector.setValidator(canAttack());
    
    }
    
    /**
     * This is the second part of the initialisation of the Attack.
     * It makes the user choose their target.
     */
    private void chooseTarget() {
        Selector.setValidator(isAttackable());
    
    }
    
    /** 
     * This is the last part of the initialisation of the Attack.
     * It makes the user choose with how many armies they want to attack.
     */
    private void chooseArmies() {
        Form form = new Form();
        TextInput armiesInput = new TextInput("Are you sure you want to attack? "
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
     * Executes the attack as it was initialised
     */
    public void attack() {
        Pair<Boolean,String> attackValid = attackValid();
        if(attackValid.getKey() != true) MessageDisplayer.showMessage(attackValid.getValue());
        else{
            attackingLooseArmies();
            defendingLooseArmies();
            manageAttackSuccess();
        }
    }
    
    public void randomAttack() {
        if(attacking == null) System.err.println("Attacking territory wasn't set for randomAttack()");
        else if(attacking.armies() < 3) System.err.println("Territory had not enough armies to attack");
        else {
            Random random = new Random();
            //randomly choose a target
            HashSet<Territory> neighbourEnnemySet = attacking.neighbours();
            neighbourEnnemySet.removeAll(attacker.territories());
            ArrayList<Territory> neighbourEnnemyList = new ArrayList<>(neighbourEnnemySet);
            int randomIndex = random.nextInt(neighbourEnnemyList.size());
            defending = neighbourEnnemyList.get(randomIndex);
            
            //randomly choose a number of armies
            armies = 2 + random.nextInt(attacking.armies() - 2);
            
            //attack
            attack();
        
        }
    
    }
    
    private Pair<Boolean,String> attackValid() {
        if(attacking == null | defending == null) return new Pair(false, "Attack wasn't initialised properly");
        if(armies<2) return new Pair(false,"You can't attack a territory without at least two armies.");
        if(armies>attacking.armies() + attacker.battlecryBonus) return new Pair(false, "You don't have enough armies.");
        else return new Pair(true, "Chaaaaaaaarge!");
    
    }
    
    private void attackingLooseArmies() {
        attacking.addArmies(-armies);
        
    }
    
    private void defendingLooseArmies() {
        int defenderBonus = defending.owner().battlecryBonus;
        defending.addArmies(Math.min(0,-armies + defenderBonus));
    
    }
    
    private void manageAttackSuccess() {
        if(defending.armies() > 0); //do nothing
        if(defending.armies() == 0) defending.setOwner(null);
        if(defending.armies() < 0) {
            defending.setArmies(-defending.armies());
            defending.setOwner(attacker);
            attacker.conqueredThisTurn = true;
        }
    
    
    }
    
    private Predicate canAttack() {
        return (Object o) -> {
            if(o instanceof Territory && ((Territory) o).owner() == attacker){
                Territory territory = (Territory) o;
                HashSet<Territory> neighbouringEnnemy = territory.neighbours();
                neighbouringEnnemy.removeAll(attacker.territories());
                return territory.armies() > 2 & !neighbouringEnnemy.isEmpty();
            }else return false;
        };
    
    };
    
    private Predicate isAttackable() {
        return (Object o) -> {
            if(o instanceof Territory & attacking != null){
                Territory territory = (Territory) o;
                if(territory.owner() != null && territory.owner().fortressProtection)
                    return false;
                else return territory.owner() != attacking.owner() 
                            && attacking.neighbours().contains(territory);
            }else return false;
        };
    
    };

}
