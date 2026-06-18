package game;

import model.*;
import util.ClearScreen;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class BattleManager {
    private List<Player> players;
    private List<Enemy> enemies;
    private Scanner scanner;
    private int round = 1;

    public BattleManager(List<Player> players) {
        this.players = players;
        this.enemies = new ArrayList<>();
        this.enemies.add(new Minotaur());
        this.scanner = new Scanner(System.in);
    }

    // Method baru untuk menampilkan status (HP & SPD)
    private void printBattleStatus() {
    	ClearScreen.clearConsoleTurn();
        System.out.println("========================================================================");
        System.out.printf("%-35s|| %-35s%n", "[ PARTY ]", "[ ENEMY ]");
        System.out.println("------------------------------------------------------------------------");
        System.out.printf("%-15s %-10s %-10s || %-15s %-10s %-10s%n",
                "Name", "HP", "SPD", "Name", "HP", "SPD");
        System.out.println("------------------------------------------------------------------------");

        int maxRow = Math.max(players.size(), enemies.size());
        for (int i = 0; i < maxRow; i++) {
            String pName = "", eName = "";
            double pHP = 0, eHP = 0;
            double pSPD = 0, eSPD = 0;

            if (i < players.size()) {
                Player p = players.get(i);
                pName = p.getName() + " (" + p.getClass().getSimpleName() + ")";
                pHP = p.getHp();
                pSPD = p.getSpd();
            }

            if (i < enemies.size()) {
                Enemy e = enemies.get(i);
                eName = e.getName() + " (" + e.getClass().getSimpleName() + ")";
                eHP = e.getHp();
                eSPD = e.getSpd();
            }
            
            // Hanya tampilkan jika entity masih hidup atau minimal nama stringnya tidak kosong
            if (!pName.isEmpty() || !eName.isEmpty()) {
                System.out.printf("%-15s %-10.1f %-10.1f || %-15s %-10.1f %-10.1f%n", pName, pHP, pSPD, eName, eHP, eSPD);
            }
        }
        System.out.println("========================================================================");
    }

    public void startFight() {
        while (true) {
            // Panggil tampilan status SEBELUM turn order
        	ClearScreen.clearConsoleRound();
            printBattleStatus();
            System.out.println("\n===== Round " + round + " =====");

            // Gabung semua entitas untuk urutan Turn berdasarkan Spd
            List<Entity> turnOrder = new ArrayList<>();
            for (Player p : players) if (!p.isDead()) turnOrder.add(p);
            for (Enemy e : enemies) if (!e.isDead()) turnOrder.add(e);

            // Sort berdasarkan Speed terbesar
            turnOrder.sort((e1, e2) -> Double.compare(e2.getSpd(), e1.getSpd()));

            for (int i = 0; i < turnOrder.size(); i++) {
                Entity current = turnOrder.get(i);
                if (current.isDead()) continue;

                // Cek menang/kalah di tiap turn
                if (checkWin()) {
                    System.out.println("You win!");
                    return;
                }
                if (checkLose()) {
                    System.out.println("Your party has been defeated...");
                    return;
                }

                printTurnOrder(turnOrder);

                if (current instanceof Player) {
                    Player p = (Player) current;

                    if (p instanceof Warrior) ((Warrior) p).reduceCooldown();

                    boolean turnValid = false;
                    while (!turnValid) {
                        System.out.println("\n" + p.getName() + "'s Action:");
                        p.printActions();
                        System.out.print("Choose: ");
                        int choice = scanner.nextInt();

                        turnValid = processPlayerAction(p, choice, i, turnOrder);
                    }
                } else if (current instanceof Enemy) {
                    ((Enemy) current).takeAction(players, enemies);
                }
                System.out.println();
            }
            round++;
        }
    }

    private boolean processPlayerAction(Player p, int choice, int currentIdx, List<Entity> turnOrder) {
        if (choice == 1) { // Basic Attack
            Enemy target = selectEnemyTarget();
            if (target != null) {
                double dmg = 0;
                if (p instanceof Warrior) dmg = ((Warrior) p).basicAttack(target);
                else if (p instanceof Archer) dmg = ((Archer) p).basicAttack(target);
                else if (p instanceof Mage) dmg = ((Mage) p).basicAttack(target);
                System.out.printf("%s basic Attack %s : (-%.1f) (%.1f)\n", p.getName(), target.getName(), dmg, target.getHp());
                return true;
            }
        }
        // Skill Khusus
        else if (choice == 2) {
            if (p instanceof Warrior) {
                return ((Warrior) p).immuneAura(players);
            } else if (p instanceof Archer) {
                Enemy target = selectEnemyTarget();
                if (target != null) {
                    double dmg = ((Archer) p).windArrow(target);
                    System.out.printf("%s Wind Arrow to %s (-%.1f) (%.1f)\n", p.getName(), target.getName(), dmg, target.getHp());
                    return true;
                }
            } else if (p instanceof Mage) {
                ((Mage) p).fireball(enemies);
                return true;
            }
        } else if (choice == 3) {
            if (p instanceof Warrior) {
                ((Warrior) p).axeSwing(enemies);
                return true;
            } else if (p instanceof Archer) {
                Player target = selectPartyTarget((Archer) p);
                if (target != null) {
                    ((Archer) p).windBooster(target);
                    // Manipulasi turn: Target maju langsung setelah Archer
                    turnOrder.remove(target);
                    turnOrder.add(currentIdx + 1, target);
                    return true;
                }
            } else if (p instanceof Mage) {
                ((Mage) p).healingArea(players);
                return true;
            }
        }
        return false;
    }

    private void printTurnOrder(List<Entity> turnOrder) {
        System.out.println("Turn Order:");
        int idx = 1;
        for (Entity e : turnOrder) {
            if (!e.isDead()) {
                String role = e.getClass().getSimpleName();
                System.out.printf("%d. %s (%s, SPD=%.1f)\n", idx++, e.getName(), role, e.getSpd());
            }
        }
    }

    private Enemy selectEnemyTarget() {
        System.out.println("Target List");
        List<Enemy> aliveEnemies = new ArrayList<>();
        int idx = 1;
        for (Enemy e : enemies) {
            if (!e.isDead()) {
                System.out.println(idx + ". " + e.getName());
                aliveEnemies.add(e);
                idx++;
            }
        }
        System.out.print("Choose Target: ");
        int targetIdx = scanner.nextInt() - 1;
        if (targetIdx >= 0 && targetIdx < aliveEnemies.size()) {
            return aliveEnemies.get(targetIdx);
        }
        return null;
    }

    private Player selectPartyTarget(Archer caster) {
        System.out.println("Party List");
        int idx = 1;
        for (Player p : players) {
            if (!p.isDead()) {
                System.out.print(idx + ". " + p.getName() + "   ");
                idx++;
            }
        }
        System.out.print("\nChoose Target: ");
        int targetIdx = scanner.nextInt() - 1;
        Player target = players.get(targetIdx);
        if (target == caster) {
            System.out.println("Cannot use Wind Booster on yourself!");
            return null;
        }
        return target;
    }

    private boolean checkWin() {
        for (Enemy e : enemies) if (!e.isDead()) return false;
        return true;
    }

    private boolean checkLose() {
        for (Player p : players) if (!p.isDead()) return false;
        return true;
    }
}