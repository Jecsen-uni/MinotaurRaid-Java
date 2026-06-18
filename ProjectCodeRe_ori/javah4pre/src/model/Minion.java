package model;
import java.util.List;
import java.util.Random;

public class Minion extends Enemy {
    public Minion(String name) {
        super(name);
        this.hp = 400;
        this.maxHp = 400;
        this.physicalDef = 20;
        this.magicDef = 15;
        this.spiritDef = 18;
        this.spd = 1;
        this.atk = 20;
    }

    @Override
    public void takeAction(List<Player> players, List<Enemy> enemies) {
        System.out.println(this.name + "'s Action:");
        List<Player> alivePlayers = new java.util.ArrayList<>();
        for (Player p : players) if (!p.isDead()) alivePlayers.add(p);
        
        if (alivePlayers.isEmpty()) return;

        Random rand = new Random();
        Player target = alivePlayers.get(rand.nextInt(alivePlayers.size()));
        
        double damage = this.atk - (target.getDef() * 0.2);
        damage = Math.max(1, damage);
        target.takeDamage(damage);
        System.out.printf("%s basic attack to %s (-%.1f) (%.1f)\n", this.name, target.getName(), damage, target.getHp());
    }
}