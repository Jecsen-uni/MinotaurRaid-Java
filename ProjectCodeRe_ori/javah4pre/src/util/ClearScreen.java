package util;

public class ClearScreen {
	public static void clearConsoleTurn(){
        for(int i = 0; i < 5; i++){
            System.out.println();
        }
    }
	
	public static void clearConsoleRound(){
        for(int i = 0; i < 50; i++){
            System.out.println();
        }
    }
}
