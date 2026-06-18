package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Archer;
import model.Enemy;
import model.Entity;
import model.Mage;
import model.Minion;
import model.Minotaur;
import model.Warrior;
import util.InputHelper;

public class TurnProcessor {
    private static final double MINOTAUR_SUMMON_HP_THRESHOLD = 1000;

    private final BattleManager battleManager;
    private final BattleDisplay display;
    private final InputHelper input;
    private final Random random;

    public TurnProcessor(BattleManager battleManager, BattleDisplay display, InputHelper input, Random random) {
        this.battleManager = battleManager;
        this.display = display;
        this.input = input;
        this.random = random;
    }

    public void processTurn(Entity entity) {
        if (battleManager.getPlayerTeam().contains(entity)) {
            processPlayerTurn(entity);
        } else if (entity instanceof Enemy enemy) {
            processEnemyTurn(enemy);
        }
    }

    private void processPlayerTurn(Entity entity) {
        if (entity instanceof Warrior warrior) {
            processWarriorTurn(warrior);
        } else if (entity instanceof Archer archer) {
            processArcherTurn(archer);
        } else if (entity instanceof Mage mage) {
            processMageTurn(mage);
        }
    }

    private void processWarriorTurn(Warrior warrior) {
        while (true) {
            display.printMessage("1. Basic Attack  2. Immune Aura  3. Axe Swing");
            int action = input.readIntInRange("Choose: ", 1, 3);

            if (action == 2 && warrior.isImmuneAuraOnCooldown()) {
                display.printMessage("Immune Aura is on cooldown: " + warrior.getImmuneAuraCooldown() + " turn(s) remaining.");
                continue;
            }

            if (action == 1) {
                Enemy enemy = chooseEnemy();
                double damage = warrior.basicAttack(enemy);
                display.printDamage(warrior, "basic attack", enemy, damage);
            } else if (action == 2) {
                warrior.activateImmuneAura();
                for (Entity teammate : battleManager.getPlayerTeam()) {
                    teammate.setImmune(true);
                }
                display.printMessage(warrior.getName() + " used Immune Aura on the party!");
            } else {
                attackAllEnemies(warrior, "Axe Swing");
            }

            warrior.reduceCooldowns();
            return;
        }
    }

    private void processArcherTurn(Archer archer) {
        display.printMessage("1. Basic Attack  2. Wind Arrow  3. Wind Booster");
        int action = input.readIntInRange("Choose: ", 1, 3);

        if (action == 1) {
            Enemy enemy = chooseEnemy();
            double damage = archer.basicAttack(enemy);
            display.printDamage(archer, "basic attack", enemy, damage);
        } else if (action == 2) {
            Enemy enemy = chooseEnemy();
            double damage = archer.windArrow(enemy);
            display.printDamage(archer, "Wind Arrow", enemy, damage);
        } else {
            Entity teammate = chooseTeammateExcept(archer);
            archer.windBooster(teammate);
            display.printMessage(archer.getName() + " used Wind Booster on " + teammate.getName() + "!");
        }
    }

    private void processMageTurn(Mage mage) {
        display.printMessage("1. Basic Attack  2. Fireball  3. Healing Area");
        int action = input.readIntInRange("Choose: ", 1, 3);

        if (action == 1) {
            Enemy enemy = chooseEnemy();
            double damage = mage.basicAttack(enemy);
            display.printDamage(mage, "basic attack", enemy, damage);
        } else if (action == 2) {
            attackAllEnemies(mage, "Fireball");
        } else {
            for (Entity teammate : battleManager.getPlayerTeam()) {
                double heal = mage.healArea(teammate);
                display.printHeal(mage, teammate, heal);
            }
        }
    }

    private void processEnemyTurn(Enemy enemy) {
        if (enemy instanceof Minotaur minotaur) {
            processMinotaurTurn(minotaur);
        } else if (enemy instanceof Minion) {
            attackRandomPlayer(enemy);
        }
    }

    private void processMinotaurTurn(Minotaur minotaur) {
        if (minotaur.getHp() < MINOTAUR_SUMMON_HP_THRESHOLD && !minotaur.hasSummonedMinions()) {
            battleManager.addEnemy(new Minion("Kenneth Johan"));
            battleManager.addEnemy(new Minion("Memet Darwin"));
            minotaur.setHasSummonedMinions(true);
            display.printMessage(minotaur.getName() + " summoned 2 minions!");
            return;
        }

        if (random.nextInt(2) == 0) {
            display.printMessage(minotaur.getName() + " used Axe Swing on all players!");
            for (Entity player : new ArrayList<>(battleManager.getPlayerTeam())) {
                double damage = minotaur.axeSwing(player);
                display.printDamage(minotaur, "Axe Swing", player, damage);
            }
        } else {
            attackRandomPlayer(minotaur);
        }
    }

    private void attackAllEnemies(Entity attacker, String skillName) {
        display.printMessage(attacker.getName() + " used " + skillName + " on all enemies!");
        for (Enemy enemy : new ArrayList<>(battleManager.getEnemies())) {
            if (!enemy.isAlive()) {
                continue;
            }

            double damage;
            if (attacker instanceof Warrior warrior) {
                damage = warrior.axeSwing(enemy);
            } else if (attacker instanceof Mage mage) {
                damage = mage.fireball(enemy);
            } else {
                damage = attacker.basicAttack(enemy);
            }
            display.printDamage(attacker, skillName, enemy, damage);
        }
    }

    private void attackRandomPlayer(Enemy enemy) {
        List<Entity> alivePlayers = battleManager.getPlayerTeam().stream().filter(Entity::isAlive).toList();
        if (alivePlayers.isEmpty()) {
            return;
        }
        Entity target = alivePlayers.get(random.nextInt(alivePlayers.size()));
        double damage = enemy.basicAttack(target);
        display.printDamage(enemy, "basic attack", target, damage);
    }

    private Enemy chooseEnemy() {
        display.printEnemyTargets(battleManager.getEnemies());
        int targetIndex = input.readIntInRange("Choose target: ", 1, battleManager.getEnemies().size());
        return battleManager.getEnemies().get(targetIndex - 1);
    }

    private Entity chooseTeammateExcept(Entity actor) {
        while (true) {
            display.printPlayerTargets(battleManager.getPlayerTeam());
            int targetIndex = input.readIntInRange("Choose target: ", 1, battleManager.getPlayerTeam().size());
            Entity target = battleManager.getPlayerTeam().get(targetIndex - 1);
            if (target != actor) {
                return target;
            }
            display.printMessage("Cannot use this skill on yourself.");
        }
    }
}
