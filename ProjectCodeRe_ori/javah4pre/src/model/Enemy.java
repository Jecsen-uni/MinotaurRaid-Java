package model;

public abstract class Enemy extends Entity {
    protected double physicalDef;
    protected double magicDef;
    protected double spiritDef;
    protected double atk;

    public Enemy(String name) {
        super(name);
    }
    
    public double getPhysicalDef() { return physicalDef; }
    public double getMagicDef() { return magicDef; }
    public double getSpiritDef() { return spiritDef; }
    
    public abstract void takeAction(java.util.List<Player> players, java.util.List<Enemy> enemies);
}