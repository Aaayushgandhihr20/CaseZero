
package casezero;

import java.util.Scanner;

class Input {

    private static final Scanner scanner = new Scanner(System.in);

    // Reads an integer within the given range
    public static int getChoice(String message, int min, int max) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            try {

                int choice = Integer.parseInt(input);

                if (choice >= min && choice <= max) {
                    return choice;
                }

                System.out.println(
                    "Please enter a number between " + min + " and " + max + "."
                );

            } catch (NumberFormatException e) {

                System.out.println("Invalid input! Please enter a number.");

            }
        }
    }

    // Reads a non-empty text value
    public static String getText(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Input cannot be empty. Try again.");
        }
    }

    // Reads a yes/no answer
    public static boolean getYesNo(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("yes") || input.equals("y")) {
                return true;
            }

            if (input.equals("no") || input.equals("n")) {
                return false;
            }

            System.out.println("Please enter yes or no.");
        }
    }

    // Waits until the player presses Enter
    public static void pause() {

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
}

