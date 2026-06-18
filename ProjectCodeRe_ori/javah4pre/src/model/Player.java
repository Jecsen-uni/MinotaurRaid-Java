package model;

public abstract class Player extends Entity {
    protected double def;
    protected double atk;

    public Player(String name) {
        super(name);
    }

    public double getDef() { return def; }
    
    public abstract void printActions();
}