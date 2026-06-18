package model;

public class Minotaur extends Enemy {
    private boolean hasSummonedMinions;

    public Minotaur(String name) {
        super(name, 2000, 35, 30, 32, 8, 25);
    }

    public double axeSwing(Entity target) {
        double damage = calculateDamage(getAttack(), target.getDefense(), 1);
        target.takeDamage(damage);
        return damage;
    }

    public boolean hasSummonedMinions() {
        return hasSummonedMinions;
    }

    public void setHasSummonedMinions(boolean hasSummonedMinions) {
        this.hasSummonedMinions = hasSummonedMinions;
    }
}
