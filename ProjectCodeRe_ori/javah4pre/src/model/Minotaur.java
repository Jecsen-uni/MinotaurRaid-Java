package model;
import java.util.List;
import java.util.Random;

public class Minotaur extends Enemy {
    private boolean hasSummoned = false;

    public Minotaur() {
        super("Kelly Johan");
        this.hp = 800;
        this.maxHp = 800;
        this.physicalDef = 35;
        this.magicDef = 30;
        this.spiritDef = 32;
        this.spd = 8;
        this.atk = 25;
    }

    @Override
    public void takeAction(List<Player> players, List<Enemy> enemies) {
        System.out.println(this.name + "'s Action:");
        
        // Summon Minion
        if (this.hp < 1000 && !hasSummoned) {
            System.out.println("Minotaur summon 2 minion");
            enemies.add(new Minion("Kenneth Johan"));
            enemies.add(new Minion("Memet Darwin"));
            hasSummoned = true;
            return;
        }

        Random rand = new Random();
        int action = rand.nextInt(2); // 0 untuk basic, 1 untuk axe swing

        // Cari player yang masih hidup
        List<Player> alivePlayers = new java.util.ArrayList<>();
        for (Player p : players) if (!p.isDead()) alivePlayers.add(p);
        
        if (alivePlayers.isEmpty()) return;

        if (action == 0) {
            Player target = alivePlayers.get(rand.nextInt(alivePlayers.size()));
            double damage = this.atk - (target.getDef() * 0.2);
            damage = Math.max(1, damage);
            target.takeDamage(damage);
            System.out.printf("%s basic attack to %s (-%.1f) (%.1f)\n", this.name, target.getName(), damage, target.getHp());
        } else {
            for (Player p : alivePlayers) {
                double damage = (this.atk + 15) - (p.getDef() * 0.2);
                damage = Math.max(1, damage);
                p.takeDamage(damage);
                if (!p.isImmune() && !p.isDead()) {
                     System.out.printf("%s Axe Swing To %s (-%.1f) (%.1f)\n", this.name, p.getName(), damage, p.getHp());
                } else if (p.isImmune()) {
                     System.out.printf("%s Axe Swing to %s ( Immune )\n", this.name, p.getName());
                }
            }
        }
    }
}