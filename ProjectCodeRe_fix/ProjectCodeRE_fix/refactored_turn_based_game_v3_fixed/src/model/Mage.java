package model;

public class Mage extends Entity {
    private static final double FIREBALL_BONUS_DAMAGE = 15;

    public Mage(String name) {
        super(name, 100, 20, 5, 40);
    }

    @Override
    public double basicAttack(Entity target) {
        Enemy enemy = (Enemy) target;
        double damage = calculateDamage(getAttack(), enemy.getMagicDefense(), 1);
        enemy.takeDamage(damage);
        return damage;
    }

    public double fireball(Entity target) {
        Enemy enemy = (Enemy) target;
        double damage = calculateDamage(getAttack() + FIREBALL_BONUS_DAMAGE, enemy.getMagicDefense(), 1);
        enemy.takeDamage(damage);
        return damage;
    }

    public double healArea(Entity target) {
        return target.heal(target.getAttack() * 0.5);
    }
}
