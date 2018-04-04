package mapEditor;

import base.MyWorld;
import greenfoot.Greenfoot;
import greenfoot.MouseInfo;
import input.Form;
import input.FormAction;
import mode.Mode;
import selector.Selector;
import territory.Controler;
import territory.Links;
import territory.Territory;


public class TerritoryControler extends Controler {
    private MyWorld world() {return MyWorld.theWorld;}

    public TerritoryControler(Territory terrToControl) {
        super(terrToControl);
    }

    @Override
    public void clicked() {
        switch (Mode.mode()) {
            case CREATE_CONTINENT :
            case DELETE_TERRITORY :
                Selector.select(territory);
                break;
                    
            case EDIT_TERRITORY_BONUS :
                Selector.select(territory);
                Selector.setValidator(Selector.NOTHING);
                world().repaint(); //pour forcer l'actualisation des images
                askBonus();
                break;

            case EDIT_CONTINENT_COLOR :
                if(territory.continent() != null){
                    Selector.select(territory.continent());
                    Selector.setValidator(Selector.NOTHING);
                    world().repaint(); //pour forcer l'actualisation des images
                    territory.continent().editColor();
                }
                break;

            case EDIT_CONTINENT_BONUS :
                if(territory.continent() != null){
                    Selector.select(territory.continent());
                    Selector.setValidator(Selector.NOTHING);
                    world().repaint(); //pour forcer l'actualisation des images
                    territory.continent().editBonus();
                }
                break;

            case DELETE_CONTINENT :
                Selector.select(territory.continent());
                break;

            case SET_LINK :
                MouseInfo mouse = Greenfoot.getMouseInfo();
                int mouseX = mouse.getX();
                int mouseY = mouse.getY();
                Links.modifying().addlink(territory, new int[]{mouseX,mouseY});
                break;
        
        
        }
        
    }
    
    private void askBonus() {
        Form.inputText("Enter the new bonus for this territory.", changeBonus);
    
    }
    
    private FormAction changeBonus = (java.util.Map<String,String> input) -> { 
        if(input.get("inputedText").matches("\\d+")){    
            territory.setBonus(Integer.parseInt(input.get("inputedText")));
        }else{
            appearance.MessageDisplayer.showMessage("Invalid Entry.");
        }
        world().stateManager.escape();
            
    };

}
