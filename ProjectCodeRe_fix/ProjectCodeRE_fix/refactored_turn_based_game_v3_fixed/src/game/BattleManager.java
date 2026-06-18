package game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import model.Enemy;
import model.Entity;
import model.Minotaur;
import util.ConsoleUtil;
import util.InputHelper;

public class BattleManager {
    private static final long TURN_DELAY_MS = 700;

    private final List<Entity> playerTeam;
    private final List<Enemy> enemies;
    private final List<Entity> allEntities;
    private final BattleDisplay display;
    private final TurnProcessor turnProcessor;
    private boolean battleEnded;
    private int round;

    public BattleManager(List<Entity> playerTeam, Scanner scanner) {
        this.playerTeam = new ArrayList<>(playerTeam);
        this.enemies = new ArrayList<>();
        this.allEntities = new ArrayList<>(playerTeam);
        this.display = new BattleDisplay();
        this.turnProcessor = new TurnProcessor(this, display, new InputHelper(scanner), new Random());
        this.round = 1;
        addEnemy(new Minotaur("Kelly Johan"));
    }

    public void startBattle() {
        while (!battleEnded) {
            List<Entity> turnOrder = getTurnOrder();
            display.printBattleStatus(playerTeam, enemies, turnOrder, round);

            for (Entity entity : turnOrder) {
                if (battleEnded) {
                    break;
                }
                if (!entity.isAlive()) {
                    continue;
                }

                display.printBattleStatus(playerTeam, enemies, getTurnOrder(), round);
                display.printMessage(entity.getName() + "'s turn");
                turnProcessor.processTurn(entity);
                updateGameStatus();
                ConsoleUtil.sleep(TURN_DELAY_MS);
            }
            round++;
        }
    }

    public List<Entity> getTurnOrder() {
        return allEntities.stream()
                .filter(Entity::isAlive)
                .sorted(Comparator.comparingDouble(Entity::getSpeed).reversed())
                .toList();
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
        allEntities.add(enemy);
    }

    private void updateGameStatus() {
        removeDeadEntities();

        if (playerTeam.stream().noneMatch(Entity::isAlive)) {
            display.printBattleStatus(playerTeam, enemies, getTurnOrder(), round);
            display.printMessage("You lose. Everyone died.");
            battleEnded = true;
            return;
        }

        if (enemies.stream().noneMatch(Entity::isAlive)) {
            display.printBattleStatus(playerTeam, enemies, getTurnOrder(), round);
            display.printMessage("You win!");
            battleEnded = true;
        }
    }

    private void removeDeadEntities() {
        playerTeam.removeIf(entity -> !entity.isAlive());
        enemies.removeIf(entity -> !entity.isAlive());
        allEntities.removeIf(entity -> !entity.isAlive());
    }

    public List<Entity> getPlayerTeam() {
        return playerTeam;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
