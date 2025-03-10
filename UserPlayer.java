import java.util.ArrayList;
import java.util.Scanner;
/*
* Names: Gurpreet Singh
* netID: gsingh38,
* G#: 01437947
* Lecture section: 201
* Lab section: 001
*/
public class UserPlayer extends Player {
    private String name;
    private Scanner input;

    public UserPlayer(Scanner input, String name) {
        this.input = input;
        this.name = name;
    }

    @Override
    public MiniCheckers chooseMove(MiniCheckers game) {
        System.out.println("Current Board:\n" + game.toString());
        ArrayList<MiniCheckers> possibleMoves = game.possibleMoves(this);

       
        System.out.println("Possible Moves:");
        for (int i = 0; i < possibleMoves.size(); i++) {
            System.out.println(i + ":");
            System.out.println(possibleMoves.get(i));
        }

       
        int userChoice = getUserInput(0, possibleMoves.size() - 1, "Select a move: ");
        return possibleMoves.get(userChoice);
    }

    private int getUserInput(int min, int max, String message) {
        int userInput = -1;
        boolean isValidInput = false;

        while (!isValidInput) {
            System.out.print(message);

            if (input.hasNextInt()) {
                userInput = input.nextInt();
                if (userInput >= min && userInput <= max) {
                    isValidInput = true;
                } else {
                    System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
                }
            } else {
                input.next();
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        return userInput;
    }

    @Override
    public String toString() {
        return name;
    }
}
