package model;

public class Warrior extends Entity {
    private static final int IMMUNE_AURA_COOLDOWN = 3;
    private int immuneAuraCooldown;

    public Warrior(String name) {
        super(name, 200, 30, 7, 20);
    }

    @Override
    public double basicAttack(Entity target) {
        Enemy enemy = (Enemy) target;
        double damage = calculateDamage(getAttack(), enemy.getPhysicalDefense(), 1);
        enemy.takeDamage(damage);
        return damage;
    }

    public double axeSwing(Entity target) {
        Enemy enemy = (Enemy) target;
        double damage = calculateDamage(getAttack(), enemy.getPhysicalDefense(), 1);
        enemy.takeDamage(damage);
        return damage;
    }

    public boolean isImmuneAuraOnCooldown() {
        return immuneAuraCooldown > 0;
    }

    public void activateImmuneAura() {
        immuneAuraCooldown = IMMUNE_AURA_COOLDOWN;
    }

    public void reduceCooldowns() {
        if (immuneAuraCooldown > 0) {
            immuneAuraCooldown--;
        }
    }

    public int getImmuneAuraCooldown() {
        return immuneAuraCooldown;
    }
}
