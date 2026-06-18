package model;

public class Archer extends Entity {
    private static final double WIND_ARROW_BONUS_DAMAGE = 30;
    private static final double WIND_BOOSTER_SPEED_BONUS = 3;

    public Archer(String name) {
        super(name, 125, 25, 10, 30);
    }

    @Override
    public double basicAttack(Entity target) {
        Enemy enemy = (Enemy) target;
        double damage = calculateDamage(getAttack(), enemy.getSpiritDefense(), 1);
        enemy.takeDamage(damage);
        return damage;
    }

    public double windArrow(Entity target) {
        Enemy enemy = (Enemy) target;
        double damage = calculateDamage(getAttack() + WIND_ARROW_BONUS_DAMAGE, enemy.getSpiritDefense(), 1);
        enemy.takeDamage(damage);
        return damage;
    }

    public void windBooster(Entity teammate) {
        teammate.addSpeed(WIND_BOOSTER_SPEED_BONUS);
    }
}
