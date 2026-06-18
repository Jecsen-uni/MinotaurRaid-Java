package model;

public class Enemy extends Entity {
    private final double physicalDefense;
    private final double magicDefense;
    private final double spiritDefense;

    protected Enemy(String name, double hp, double physicalDefense, double magicDefense,
                    double spiritDefense, double speed, double attack) {
        super(name, hp, physicalDefense, speed, attack);
        this.physicalDefense = physicalDefense;
        this.magicDefense = magicDefense;
        this.spiritDefense = spiritDefense;
    }

    @Override
    public double basicAttack(Entity target) {
        double damage = calculateDamage(getAttack(), target.getDefense(), 1);
        target.takeDamage(damage);
        return damage;
    }

    public double getPhysicalDefense() { return physicalDefense; }
    public double getMagicDefense() { return magicDefense; }
    public double getSpiritDefense() { return spiritDefense; }
}
