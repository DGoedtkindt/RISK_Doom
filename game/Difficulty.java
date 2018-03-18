package game;

public enum Difficulty {

    ZOMBIELESS("Zombieless", 0, 0, 999, 0),
    ZOMBIENESS("Zombieness", 4, 1, 10, 1),
    INTERMEDIATE("Intermediate", 2, 1, 10, 0.5);

    public final String NAME;
    public final int ZOMBIES_SPAWNING;
    public final int INCREMENT;
    public final int ZOMBIES_TURN_LIMIT;
    public final double ATTACK_CHANCE;

    /**
    * Creates a Difficulty object, wich contains informations about the difficulty it represents.
    * 
    * @param name The name of this Difficulty.
    * @param zombiesSpawning The starting number of zombie territories.
    * @param increment Increments the number of zombie territories appearing at each wave.
    * @param turnsBeforeZombies The number of turns before the first wave of zombies.
    * @param constantSpawn The maximum number of turns before a new zombie wave. 
    *                      This argument takes the value 999 if the only time when new zombies spawn is
    *                      at the start of the zombie turn.
    * @param attackChance The probability that any zombie territory will attack a random adjacent territory.
    */
    Difficulty(String name, int zombiesSpawning, int increment, int constantSpawn, double attackChance){
        NAME = name;
        ZOMBIES_SPAWNING = zombiesSpawning;
        INCREMENT = increment;
        ZOMBIES_TURN_LIMIT = constantSpawn;
        ATTACK_CHANCE = attackChance;
        
    }
    
}
