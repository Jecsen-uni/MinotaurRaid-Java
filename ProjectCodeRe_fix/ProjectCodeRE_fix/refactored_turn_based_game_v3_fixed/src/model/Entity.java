package model;

public abstract class Entity {
    private final String name;
    private final double maxHp;
    private double hp;
    private final double defense;
    private double speed;
    private final double attack;
    private boolean immune;

    protected Entity(String name, double maxHp, double defense, double speed, double attack) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.defense = defense;
        this.speed = speed;
        this.attack = attack;
    }

    public abstract double basicAttack(Entity target);

    protected double calculateDamage(double baseDamage, double targetDefense, double minimumDamage) {
        return Math.max(minimumDamage, baseDamage - (targetDefense * 0.2));
    }

    public void takeDamage(double damage) {
        if (damage < 0) {
            return;
        }

        if (immune) {
            System.out.println(name + " is immune!");
            immune = false;
            return;
        }

        hp = Math.max(0, hp - damage);
    }

    public double heal(double amount) {
        if (amount <= 0 || !isAlive()) {
            return 0;
        }
        double before = hp;
        hp = Math.min(maxHp, hp + amount);
        return hp - before;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public String getName() { return name; }
    public double getMaxHp() { return maxHp; }
    public double getHp() { return hp; }
    public double getDefense() { return defense; }
    public double getSpeed() { return speed; }
    public double getAttack() { return attack; }
    public boolean isImmune() { return immune; }

    public void setImmune(boolean immune) { this.immune = immune; }
    public void addSpeed(double amount) { this.speed += amount; }
}
