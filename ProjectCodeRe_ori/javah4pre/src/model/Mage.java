package model;
import java.util.List;

public class Mage extends Player {
    public Mage(String name) {
        super(name);
        this.hp = 100;
        this.maxHp = 100;
        this.def = 20;
        this.spd = 5;
        this.atk = 40;
    }

    @Override
    public void printActions() {
        System.out.println("1. Basic Attack\t\t2. Fireball\t\t3. HealingArea");
    }

    public double basicAttack(Enemy target) {
        double damage = this.atk - (target.getMagicDef() * 0.2);
        damage = Math.max(1, damage);
        target.takeDamage(damage);
        return damage;
    }

    public void fireball(List<Enemy> targets) {
        double baseDamage = this.atk + 15;
        for (Enemy target : targets) {
            double finalDamage = baseDamage - (target.getMagicDef() * 0.2);
            finalDamage = Math.max(1, finalDamage);
            target.takeDamage(finalDamage);
            System.out.printf("%s FireBall To %s (-%.1f) (%.1f)\n", this.name, target.getName(), finalDamage, target.getHp());
        }
    }

    public void healingArea(List<Player> party) {
        double healAmount = this.atk * 0.5;
        for (Player p : party) {
            if (!p.isDead()) {
                p.heal(healAmount);
                System.out.printf("%s Heal %s (+%.1f) (%.1f)\n", this.name, p.getName(), healAmount, p.getHp());
            }
        }
    }
}