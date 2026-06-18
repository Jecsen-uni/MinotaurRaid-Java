package game;

import java.util.List;

import model.Enemy;
import model.Entity;
import util.ConsoleUtil;

public class BattleDisplay {
    public void printBattleStatus(List<Entity> players, List<Enemy> enemies, List<Entity> turnOrder, int round) {
        ConsoleUtil.clearConsole();
        System.out.println("========================================================================");
        System.out.printf("%-35s|| %-35s%n", "[ PARTY ]", "[ ENEMY ]");
        System.out.println("------------------------------------------------------------------------");
        System.out.printf("%-15s %-10s %-10s || %-15s %-10s %-10s%n", "Name", "HP", "SPD", "Name", "HP", "SPD");
        System.out.println("------------------------------------------------------------------------");

        int maxRows = Math.max(players.size(), enemies.size());
        for (int i = 0; i < maxRows; i++) {
            String playerText = i < players.size() ? formatEntity(players.get(i)) : "";
            String enemyText = i < enemies.size() ? formatEntity(enemies.get(i)) : "";
            System.out.printf("%-35s || %-35s%n", playerText, enemyText);
        }

        System.out.println("========================================================================");
        System.out.println("Round " + round);
        System.out.println("Turn Order:");
        for (int i = 0; i < turnOrder.size(); i++) {
            Entity entity = turnOrder.get(i);
            System.out.printf("%d. %s (%s, SPD=%.1f)%n", i + 1, entity.getName(), entity.getClass().getSimpleName(), entity.getSpeed());
        }
        System.out.println();
    }

    public void printEnemyTargets(List<Enemy> enemies) {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            System.out.printf("%d. %s (HP %.1f/%.1f)%n", i + 1, enemy.getName(), enemy.getHp(), enemy.getMaxHp());
        }
    }

    public void printPlayerTargets(List<Entity> players) {
        for (int i = 0; i < players.size(); i++) {
            Entity player = players.get(i);
            System.out.printf("%d. %s (HP %.1f/%.1f)%n", i + 1, player.getName(), player.getHp(), player.getMaxHp());
        }
    }

    public void printDamage(Entity attacker, String skillName, Entity target, double damage) {
        System.out.printf("%s used %s on %s: %.1f damage. %s HP: %.1f/%.1f%n",
                attacker.getName(), skillName, target.getName(), damage, target.getName(), target.getHp(), target.getMaxHp());
    }

    public void printHeal(Entity healer, Entity target, double heal) {
        System.out.printf("%s healed %s for %.1f. HP: %.1f/%.1f%n",
                healer.getName(), target.getName(), heal, target.getHp(), target.getMaxHp());
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    private String formatEntity(Entity entity) {
        return String.format("%-15s %-10.1f %-10.1f", entity.getName(), entity.getHp(), entity.getSpeed());
    }
}
