package model;

public class Archer extends Player {
    public Archer(String name) {
        super(name);
        this.hp = 125;
        this.maxHp = 125;
        this.def = 25;
        this.spd = 10;
        this.atk = 30;
    }

    @Override
    public void printActions() {
        System.out.println("1. Basic Attack\t\t2. WindArrow\t3. WindBooster");
    }

    public double basicAttack(Enemy target) {
        double damage = this.atk - (target.getSpiritDef() * 0.2);
        damage = Math.max(1, damage);
        target.takeDamage(damage);
        return damage;
    }

    public double windArrow(Enemy target) {
        double damage = (this.atk + 30) - (target.getSpiritDef() * 0.2);
        damage = Math.max(1, damage);
        target.takeDamage(damage);
        return damage;
    }

    public void windBooster(Player target) {
        System.out.println(target.getName() + " receive Wind Booster.");
    }
}