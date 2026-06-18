package model;

public abstract class Entity {
    protected String name;
    protected double hp;
    protected double maxHp;
    protected double spd;
    protected boolean immune;

    public Entity(String name) {
        this.name = name;
        this.immune = false;
    }

    public String getName() { return name; }
    public double getHp() { return hp; }
    public double getSpd() { return spd; }
    public boolean isDead() { return hp <= 0; }

    public void setImmune(boolean immune) { this.immune = immune; }
    public boolean isImmune() { return immune; }

    public void takeDamage(double damage) {
        if (immune) {
            System.out.println("( Immune )");
            immune = false; // Immune hilang setelah menahan 1 hit
            return;
        }
        this.hp -= damage;
        if (this.hp < 0) this.hp = 0;
    }

    public void heal(double amount) {
        if (!isDead()) {
            this.hp += amount;
            if (this.hp > maxHp) this.hp = maxHp;
        }
    }
}