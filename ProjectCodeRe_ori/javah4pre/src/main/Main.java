package main;

import model.*;
import game.BattleManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        while (true) {
            System.out.println("\n===== Main Menu =====");
            System.out.println("1. Play");
            System.out.println("2. Exit");
            System.out.print("Choose menu: ");
            
            int menu = -1;
            try {
                menu = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid Input.");
            }
            scanner.nextLine(); // clear buffer

            if (menu == 1) {
                playGame();
            } else if (menu == 2) {
                System.out.println("Thanks for playing, game over.");
                break;
            }
        }
    }

    private void playGame() {
        List<Player> party = new ArrayList<>();
        int warriorCount = 0, mageCount = 0, archerCount = 0;

        for (int i = 1; i <= 4; i++) {
            boolean valid = false;
            while (!valid) {
                System.out.println("\nSelect Role for Slot " + i + ":");
                System.out.println("1. Warrior");
                System.out.println("2. Archer");
                System.out.println("3. Mage");
                System.out.print("Choose role: ");
                int role = scanner.nextInt();
                scanner.nextLine();

                if (role == 1 && warriorCount >= 2) { System.out.println("Max 2 Warriors!"); continue; }
                if (role == 2 && archerCount >= 2) { System.out.println("Max 2 Archers!"); continue; }
                if (role == 3 && mageCount >= 2) { System.out.println("Max 2 Mages!"); continue; }

                System.out.print("Input Name: ");
                String name = scanner.nextLine();

                if (role == 1) { party.add(new Warrior(name)); warriorCount++; valid = true; }
                else if (role == 2) { party.add(new Archer(name)); archerCount++; valid = true; }
                else if (role == 3) { party.add(new Mage(name)); mageCount++; valid = true; }
                else { System.out.println("Invalid Role."); }
            }
        }

        // Tampilkan Party
        System.out.println("\n=== Your Party ===");
        for (int i = 0; i < party.size(); i++) {
            System.out.println("Slot " + (i+1) + ": " + party.get(i).getName() + " (" + party.get(i).getClass().getSimpleName() + ")");
        }

        System.out.println("=====");
        System.out.println("1. Fight");
        System.out.println("2. Back");
        System.out.print("Choose menu: ");
        int action = scanner.nextInt();
        
        if (action == 1) {
            BattleManager bm = new BattleManager(party);
            bm.startFight();
        }
    }
}