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
import javax.swing.JOptionPane;

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
            {"Hi! We're the Developpers! If you're here, you probably want to know how this game can be played. "
            + "The good news is we can help you. That's why we wrote these Rules, anyway, I guess.", 
            
            "THE GAME \n1. Goal \nThe goal? Well, I guess you have to win in order to... win. What? This does not help you?? Hmpf. "
            + "Ok then. \nThe first part is to survive "
            + "(Did we mention that your opponents might want to kill you? Oh, and I almost forgot those Zombies!). "
            + "The second part is to have more points than anyone else. If you reach 7 points, you win. If someone dies, the leading Player wins. "
            + "You gain points by killing zombies and invading their territories (Because it's actually a conquest game, you're going to invade a lot "
            + "of territories. If you're winning, at least). I think it's about saving the world from some kind of "
            + "Zombie plague, wich means you're doing something good by winning this game."
            + "\nEasy, right?",
            
            "THE GAME \n2. Starting a new Game \nOh, you want to start a Game? Go to the \"New Game\" Menu, then choose a Map with the first selector, and a Difficulty "
            + "with the second. After that, you can change the Players settings, add Players, remove them, change their names and colors. "
            + "\nUnderstood? Perfect! You're ready to start a new Game! "
            + "\nP.S. You should continue reading, if you want to learn how to actually play.",
            
            "THE GAME \n3. Loading a saved game \nIf you have a loaded game that you want to continue, just go to the \"Load Game\" Menu. There, you will find a "
            + "Game selector. Just choose your Game and continue playing!",
            
            "THE GAME \n4. The Map \nIf you start a game, you're gonna see a Map, or, at least, a bunch of colored hexagons with some weird "
            + "buttons next to those. Well, believe it or not, this is a Map. There are Continents (Represented by the background color of each hexagon), "
            + "and each Continent has Territories (Delimited by lines). \nThe flashy color on top of some of those is the color of a Player. "
            + "\nThe weird hexagon on each Territory is an indicator. It gives informations about the number of armies on the Territory (white), "
            + "the bonus given by it (yellow) and the attack and defense bonus of each Territory (green for you, red for your opponents). "
            + "\nNote that in the Map Editor, this indicator only shows the bonus given by the Territory, in white. "
            + "\nA map can contain Territories that belong to no Continent. They're, like, anywhere on the map, and their color is generally drab "
            + "(We'll be talking about colors and Themes later).",
            
            "THE GAME \n5. The funny Buttons at your right \nI think you know that Buttons usually do something. Well, we have good news for you : "
            + "you can click on a Button not only to use it, but also to know what it does, since it will display an informative message on the lower "
            + "right part of your screen! Isn't that great?",
            
            "THE GAME \n6. Zombies and Difficulty \nNo, you didn't misread us, there are Zombies in this game, and you should kill them (You read the \"Goal\" part, right?). "
            + "There are different Difficulties, and each Difficulty means a different kind of Zombie invasion. If there's no Zombie Territory left, "
            + "the Zombies will come back and take some Territories randomly. Each Difficulty has Options, like the number of turns before the Zombies get new Territories "
            + "by themselves, the probability that they attack another Territory randomly... Usual Zombie stuff, you know.",
            
            "THE GAME \n7. Armies \nIt's a conquest Game, you have armies. You gain those at the start of your turn, and you must place them then. You gain an army "
            + "for each three Territories you have, plus the bonus of every Continent you control (Down), plus the bonus of your capital "
            + "(The Territory with the highest bonus that you have). You can place the armies you have at the start of each Turn.",
            
            "THE GAME \n8. Combos \nEvery time you invade a Territory, you gain a Combo Piece (If you have less than five pieces already). "
            + "With three of those, you can play a Combo. Different pieces means different Combos. Got it? \nThere are four different Combos : "
            + "\nSap : Choose an ennemy Territory that you can attack. All the troops on it go in your ennemy's hand, and the Territory stays with one army and no owner. "
            + "\nFortress : You can't be attacked until your next turn. "
            + "\nBattlecry : You have an attack and defense bonus of two armies until your next turn. "
            + "\nRecruit : You add five armies to your hand.",
            
            "THE MAP EDITOR \n1. Launching the Map Editor \nClick the \"Map Editor\" Button, select a Map and edit it. Pretty straight forward, right?",
            
            "THE MAP EDITOR \n2. Buttons \nDo you remember the cool fact that clicking on a Button during a Game displays informations? The Map Editor has the same Feature!",
            
            "THE OPTIONS \nIn the Options, you can modify the Theme you use and add fancy colors to your Map \nYou can also add some cheat codes by clicking on the- Oh, "
            + "I shouldn't have said that.",
            
            "FUN FACT \n You can click on the \"Escape\" Button on your keyboard to stop an action or to go to the main Menu. \nUnless it was to crack every one of your "
            + "passwords. I don't remember.",
            
            "Anyway, thank you for playing, we hope you had fun during those four games you played before even noticing the \"Rules\" Button, "
            + "and the ones you'll play after. \n \nThe Developpers"
            };
    
}
