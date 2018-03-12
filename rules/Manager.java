package rules;

import appearance.Appearance;
import appearance.InputPanel;
import appearance.Theme;
import arrowable.Arrowable;
import arrowable.LeftArrow;
import arrowable.RightArrow;
import base.MyWorld;
import base.StateManager;
import greenfoot.Actor;
import greenfoot.GreenfootImage;
import java.awt.FontMetrics;

/**
 * A StateManager that displays the rules.
 * 
 */
public class Manager extends StateManager{
    
    private RulesDisplayer displayer;
    
    /**
     * Creates a Manager.
     */
    public Manager(){
        displayer = new RulesDisplayer();
    }
    
    @Override
    public void setupScene() {
        world().makeSureSceneIsClear();
        displayer.addToWorld(Appearance.WORLD_WIDTH / 2, Appearance.WORLD_HEIGHT / 2);
        world().addObject(world().backButton, Appearance.WORLD_WIDTH - 40, 40);
    }

    @Override
    public void clearScene() {
        world().removeObjects(world().getObjects(Actor.class));
    }

    @Override
    public void escape() {
        InputPanel.showConfirmPanel("Do you want to return to the main Menu?", 100, "escape", this, Appearance.WORLD_WIDTH / 2, Appearance.WORLD_HEIGHT / 2);
        
    }

    @Override
    public void useInformations(String information, String type) throws Exception {
        
        if(type.equals("escape")){
            
            if(information.equals(InputPanel.YES_OPTION)){
                MyWorld.theWorld.load(new menu.Manager());

            }
            
        }
        
    }
    
}

/**
 * The Actor that displays the rules.
 * 
 */
class RulesDisplayer extends Actor implements Arrowable{
    
    private int x;
    private int y;
    
    private RightArrow rightArrow;
    private LeftArrow leftArrow;
    private int slideNumber = 0;
    private int linesNumber = 1;
    
    private static final int TOTAL_WIDTH = 1500;
    private static FontMetrics fm;
    
    /**
     * Creates a RulesDisplayer.
     * 
     */
    public RulesDisplayer(){
        rightArrow = new RightArrow(this);
        leftArrow = new LeftArrow(this);
        updateImage();
    }
    
    /**
     * Adds the RulesDisplayer and its Arrows to the world.
     * 
     */
    public void addToWorld(int xPos, int yPos){
        
        x = xPos;
        y = yPos;
        
        MyWorld.theWorld.addObject(this, x, y);
        MyWorld.theWorld.addObject(leftArrow, x - this.getImage().getWidth() / 2 - 30, y);
        MyWorld.theWorld.addObject(rightArrow, x + this.getImage().getWidth() / 2 + 30, y);
    }
    
    @Override
    public void next() {
        if(slideNumber < RULES.length - 1){
            slideNumber++;
        }else{
            slideNumber = 0;
        }
        updateImage();
    }

    @Override
    public void previous() {
        if(slideNumber == 0){
            slideNumber = RULES.length - 1;
        }else{
            slideNumber--;
        }
        updateImage();
    }
    
    /**
     * Creates a custom image for the RulesDisplayer, showing a ceetain rule.
     * 
     */
    private void updateImage(){
        
        String rule = RULES[slideNumber];
        GreenfootImage img = new GreenfootImage(1, 1);
        img.setFont(Appearance.GREENFOOT_FONT);
        fm = img.getAwtImage().getGraphics().getFontMetrics(Appearance.AWT_FONT);
        
        int width = fm.stringWidth(rule);
        if(width > TOTAL_WIDTH){width = TOTAL_WIDTH;}
        int height = fm.getMaxAscent() + fm.getMaxDescent();
        
        rule = Appearance.standardTextWrapping(rule, TOTAL_WIDTH);
        
        linesNumber = rule.split("\n").length;
        
        img.scale(width, height * linesNumber);
        img.setColor(Theme.used.backgroundColor.brighter());
        img.fill();
        img.setColor(Theme.used.textColor);
        img.drawString(rule, 0, fm.getMaxAscent());
        setImage(img);
        
        leftArrow.setLocation(x - this.getImage().getWidth() / 2 - 30, y);
        rightArrow.setLocation(x + this.getImage().getWidth() / 2 + 30, y);
        
    }
    
    private static final String[] RULES = 
            {"Hi! We're the Developpers! If you're here, you probably want to know how this Game can be played. "
            + "The good news is we can help you. That's why we wrote these Rules anyway.", 
                
            "The Map \nA Map is the bunch of Hexagons that you see if you launch either the Map Editor or the Game. "
            + "It can contain Territories and/or Continents, which are the main Objects in the Game (Especially the "
            + "Territories) (That means you're gonna see a lot of them).",
            
            "The Blank Hexes \nA Blank Hex is a Hexagon which has the special and rare property of being completely "
            + "useless during the Game. However, they're an important part of the Map Editor, since they represent the positions "
            + "that a Territory can fit in. In the Map Editor, you can indeed click the Button that creates a Territory, "
            + "choose a bunch of Blank Hexes, validate your choice, select one of those to hold an information displayer (We'll "
            + "talk about it later) and watch a Territory appear on the Map!",
            
            "The Territories \nDo you remember the moment we said the Territories and the Continents were "
            + "the core of the Game? We didn't lie to you. The Territory is the single most important Object in the Game in the eyes of a Player."
            + "\nThe Territories are the Objects you own. During the Game, you will use them to attack adjacent Territories, move armies, use combos on your "
            + "opponents, place armies. In the Map Editor, you can create them, change the bonus they give (Yes, they give a bonus), destroy them, use them to "
            + "obtain every password of this neighbour you hate and steal all his money. "
            + "\nYou understood us, Territories are cool.",
            
            "Territory informations \nA weird Hexagon can appear on your Territories. It displays informations about them. In the Map Editor, it shows "
            + "the bonus given by a Territory. In the Game, it displays the number of armies on a Territory (White), the bonus given by it "
            + "(Yellow) and the army bonus of this Territory (Green during your Turn, Orange during your opponent's). If the Territory is the "
            + "capital of a Player, it shows a golden star. If the owner of the Territory has an active Fortress Combo, it shows a blue shield.",
            
            "Armies \nYou knew this was a conquest Game, right? \nYou gain armies at the start of your Turn (One army for each three "
            + "Territories you own, plus the bonus of your capital (Your Territory with "
            + "the highest bonus), plus the bonus of each Continent you own), or when you use a certain Combo (We'll talk about Combos later), "
            + "or when this opponent (The one you just hit with a cushion, or a plate, we're not sure) used another Combo on you. ",
            
            "The Continents \nContinents are useful, unlike Blank Hexes. They give an army bonus if you own each of their Territories, "
            + "which is a good thing to know when you play the Game. When you use the Map Editor, you are able to create them, destroy them, "
            + "change their color and bonus. You can even add cheat codes if you- \nI shouldn't have said that.",
            
            "The Combos \nIf you invade an ennemy Territory, you will gain a Combo piece at the end of the Turn (Unless you already have five pieces). "
            + "They're called A, B and C, and they're, like, super powerful sci-fi stuff, you know. A is kinda explosive, B is "
            + "more or less rock and C is propaganda. That gives those funny Combos. \nThree A - Sap : \nYou remove the armies from an adjacent ennemy Territory. "
            + "Your opponent will be able to replace them at the start of his Turn. \nThree B - Fortress : \nYou will build a wall, a huge wall, around your "
            + "Territories, and your opponents won't be able to attack you until your next Turn. \nThree C - Battlecry : \nYou gain an attack and defense "
            + "bonus of two armies on each of your Territories until your next Turn. \nOne of each - Recruit : \nYou can place five new armies on your Territories.",
            
            "The Options \nIn the Options, you can change the Theme of the Game. \nYou will only see a finite number of Themes, but an ancient legend says "
            + "that they're more Themes in this Game than atoms in the universe. We're totally serious. Not kidding. Believe us.",
            
            "Anyway, thank you for playing, we hope you had fun during those four games you played before even noticing the \"Rules\" Button, "
            + "and the ones you'll play after. \n \nThe Developpers"
            };
    
}
