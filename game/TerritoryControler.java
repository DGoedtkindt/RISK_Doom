package game;

import appearance.MessageDisplayer;
import base.MyWorld;
import input.Form;
import mode.Mode;
import territory.Attack;
import territory.Controler;
import territory.Move;
import territory.Territory;


public class TerritoryControler extends Controler {

    public TerritoryControler(Territory terrToControl) {
        super(terrToControl);
    }

    @Override
    public void clicked() {
        switch (Mode.mode()) {
            case ATTACK : 
                Attack.initialisingNext(territory);
                break;

            case MOVE : 
                Move.initialisingNext(territory);
                break;

            case CLEARING_HAND :
                if(territory.owner() == Turn.currentTurn.player){
                    Form.inputText("The number of armies you want to put on this territory.", (input)->{
                        if(input.get("inputedText").matches("\\d+")){

                            int newArmies = Integer.parseInt(input.get("inputedText"));

                            if(newArmies < 0){
                                MessageDisplayer.showMessage("This is a negative number.");
                            }else if(newArmies > territory.owner().armiesInHand()){
                                MessageDisplayer.showMessage("You don't have enough armies.");
                            }else{
                                territory.addArmies(newArmies);
                                territory.owner().addArmiesToHand(-newArmies);
                            }

                        }else{
                            MessageDisplayer.showMessage("Invalid entry.");
                        }

                    });

                }

                break;

            case SAP : 
                if(territory.owner() != Turn.currentTurn.player){
                    territory.owner().addArmiesToHand(territory.armies());
                    territory.setOwner(null);
                    Turn.currentTurn.player.combos().useSap();
                    MyWorld.theWorld.stateManager.escape();
                }
                break;

            default: break;
        
        }
        System.out.println("Method clicked() in class TerritoryControler is not supported yet");
    }

}
