package game;

public enum Difficulty {

    ZOMBIELESS("Zombieless", 0, 0, 0, -1, 0),
    ZOMBIENESS("Zombieness", 0, 0, 0, -1, 0);

    public final String NAME;
    public final int ZOMBIES_SPAWNING;
    public final int INCREMENT;
    public final int TURNS_BEFORE_ZOMBIES;
    public final int ZOMBIES_TURN_LIMIT;
    public final double ATTACK_CHANCE;

    public int zombiesNextWave;
    public int turnsBeforeNextWave;

    /**
    * Creates a Difficulty object, wich contains informations about the difficulty it represents.
    * 
    * @param name The name of this Difficulty.
    * @param zombiesSpawning The starting number of zombie territories.
    * @param increment Increments the number of zombie territories appearing at each wave.
    * @param turnsBeforeZombies The number of turns before the first wave of zombies.
    * @param constantSpawn The maximum number of turns before a new zombie wave. 
    *                      This argument takes the value -1 if the only time when new zombies spawn is
    *                      at the start of the zombie turn.
    * @param attackChance The probability that any zombie territory will attack a random adjacent territory.
    */
    Difficulty(String name, int zombiesSpawning, int increment, int turnsBeforeZombies, int constantSpawn, double attackChance){
        NAME = name;
        ZOMBIES_SPAWNING = zombiesSpawning;
        INCREMENT = increment;
        TURNS_BEFORE_ZOMBIES = turnsBeforeZombies;
        ZOMBIES_TURN_LIMIT = constantSpawn;
        ATTACK_CHANCE = attackChance;

        zombiesNextWave = ZOMBIES_SPAWNING;
        turnsBeforeNextWave = ZOMBIES_TURN_LIMIT;
    }
    
    public void countdown(){
        turnsBeforeNextWave --;
        if(turnsBeforeNextWave == -1){
            turnsBeforeNextWave = ZOMBIES_TURN_LIMIT;
        }
    }
    
    public void incrementNextWave(){
        zombiesNextWave += INCREMENT;
    }

    public void reset(){
        zombiesNextWave = ZOMBIES_SPAWNING;
    }
    
}