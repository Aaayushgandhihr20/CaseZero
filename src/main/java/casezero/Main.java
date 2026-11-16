package casezero;

public class Main {

    public static void main(String[] args) {

        System.out.println("==================================");
        System.out.println("             CASEZERO");
        System.out.println("    THE MIDNIGHT MANUSCRIPT");
        System.out.println("==================================");

        // Check database connection
        Database.testConnection();

        Game game = new Game();
        game.start();
    }
}

class Game {

    private boolean running = true;

    public void start() {

        System.out.println("\nWelcome, Detective!");
        System.out.println("A mysterious incident has occurred");
        System.out.println("at the university archive.");

        while (running) {

            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. Start Investigation");
            System.out.println("2. View Game Information");
            System.out.println("3. Exit");
            System.out.println("================================");

            int choice = Input.getChoice(
                "Enter your choice: ", 1, 3
            );

            switch (choice) {

                case 1:

                    Story.showIntroduction();

                    String playerName = Input.getText(
                        "Enter your detective name: "
                    );

                    GameData data = StorySetup.createGameData();

                    Player player = new Player(playerName);

                    CaseFile caseFile = new CaseFile(
                        "The Midnight Manuscript",
                        "Investigate the disappearance of the university manuscript."
                    );

                    Journal journal = new Journal();

                    Witness witness = StorySetup.createWitness();

                    InvestigationService investigation =
                        new InvestigationService(
                            data,
                            player,
                            caseFile,
                            journal,
                            witness
                        );

                    investigation.startInvestigation();

                    running = false;
                    break;

                case 2:
                    showInformation();
                    break;

                case 3:
                    System.out.println(
                        "\nThank you for playing CASEZERO!"
                    );
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private void showInformation() {

        System.out.println("\n========== GAME INFORMATION ==========");
        System.out.println("Game Name: CASEZERO");
        System.out.println("Genre: Detective Mystery");
        System.out.println("Platform: Command Line Interface");
        System.out.println("Objective: Find the missing manuscript.");
        System.out.println("======================================");

    }
}