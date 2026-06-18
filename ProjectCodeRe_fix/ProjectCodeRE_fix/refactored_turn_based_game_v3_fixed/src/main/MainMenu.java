package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import game.BattleManager;
import model.Archer;
import model.Entity;
import model.Mage;
import model.Warrior;
import util.InputHelper;

public class MainMenu {
    private static final int PARTY_SIZE = 4;

    private final Scanner scanner;
    private final InputHelper input;

    public MainMenu() {
        this.scanner = new Scanner(System.in);
        this.input = new InputHelper(scanner);
    }

    public void show() {
        while (true) {
            System.out.println("Main Menu");
            System.out.println("=================================");
            System.out.println("1. Play");
            System.out.println("2. Exit");

            int choice = input.readIntInRange("Choose menu: ", 1, 2);
            if (choice == 1) {
                setupGame();
            } else {
                System.out.println("Goodbye!");
                return;
            }
        }
    }

    private void setupGame() {
        List<Entity> playerTeam = createParty();
        showParty(playerTeam);

        System.out.println("1. Fight  2. Back");
        int choice = input.readIntInRange("Choose menu: ", 1, 2);
        if (choice == 1) {
            new BattleManager(playerTeam, scanner).startBattle();
        }
    }

    private List<Entity> createParty() {
        List<Entity> playerTeam = new ArrayList<>();
        while (playerTeam.size() < PARTY_SIZE) {
            System.out.println("=== Choose " + PARTY_SIZE + " raid party members ===");
            System.out.println("1. Warrior");
            System.out.println("2. Mage");
            System.out.println("3. Archer");

            int type = input.readIntInRange("Choose member " + (playerTeam.size() + 1) + " (1-3): ", 1, 3);
            String name = input.readNonEmptyString("Enter name: ");
            playerTeam.add(createPlayer(type, name));
        }
        return playerTeam;
    }

    private Entity createPlayer(int type, String name) {
        if (type == 1) {
            return new Warrior(name);
        } else if (type == 2) {
            return new Mage(name);
        } else if (type == 3) {
            return new Archer(name);
        }
        throw new IllegalArgumentException("Invalid player type: " + type);
    }

    private void showParty(List<Entity> playerTeam) {
        System.out.println("=== Your Party ===");
        for (int i = 0; i < playerTeam.size(); i++) {
            Entity member = playerTeam.get(i);
            System.out.printf("Slot %d: %s %s%n", i + 1, member.getName(), member.getClass().getSimpleName());
        }
    }
}
