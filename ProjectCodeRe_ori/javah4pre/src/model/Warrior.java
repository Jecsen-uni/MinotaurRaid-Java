package model;
import java.util.List;

public class Warrior extends Player {
    private int immuneCooldown = 0;

    public Warrior(String name) {
        super(name);
        this.hp = 200;
        this.maxHp = 200;
        this.def = 30;
        this.spd = 7;
        this.atk = 20;
    }

    @Override
    public void printActions() {
        System.out.println("1. Basic Attack\t\t2. ImmuneAura\t3. AxeSwing");
    }

    public void reduceCooldown() {
        if (immuneCooldown > 0) immuneCooldown--;
    }

    public double basicAttack(Enemy target) {
        double damage = this.atk - (target.getPhysicalDef() * 0.2);
        damage = Math.max(1, damage); // Minimal 1 damage
        target.takeDamage(damage);
        return damage;
    }

    public void axeSwing(List<Enemy> targets) {
        double damage = (this.atk + 10);
        for (Enemy target : targets) {
            double finalDamage = damage - (target.getPhysicalDef() * 0.2);
            finalDamage = Math.max(1, finalDamage);
            target.takeDamage(finalDamage);
            System.out.printf("%s Axe Swing To %s (-%.1f) (%.1f)\n", this.name, target.getName(), finalDamage, target.getHp());
        }
    }

    public boolean immuneAura(List<Player> party) {
        if (immuneCooldown > 0) {
            System.out.println("Skill still in cooldown");
            System.out.println("Skill failed! Choose another skill.");
            return false;
        }
        for (Player p : party) {
            if (!p.isDead()) {
                p.setImmune(true);
                System.out.printf("%s Immune Aura to %s (Immune)\n", this.name, p.getName());
            }
        }
        immuneCooldown = 3; // 2 turns cooldown (3 karena turn ini juga dihitung)
        return true;
    }
}