
package casezero;

import java.util.List;

// Controls the investigation gameplay
class InvestigationService {

    private GameData data;
    private Player player;
    private CaseFile caseFile;
    private Journal journal;
    private Witness witness;

    public InvestigationService(
        GameData data,
        Player player,
        CaseFile caseFile,
        Journal journal,
        Witness witness
    ) {
        this.data = data;
        this.player = player;
        this.caseFile = caseFile;
        this.journal = journal;
        this.witness = witness;
    }

    // Main investigation menu
    public void startInvestigation() {

        boolean investigating = true;

        while (investigating) {

            System.out.println("\n========== INVESTIGATION ==========");
            System.out.println("1. Explore a location");
            System.out.println("2. Question a suspect");
            System.out.println("3. Talk to witness");
            System.out.println("4. View collected clues");
            System.out.println("5. View journal");
            System.out.println("6. Ask for a hint");
            System.out.println("7. Make an accusation");
            System.out.println("8. Return to main menu");
            System.out.println("===================================");

            int choice = Input.getChoice("Choose an option: ", 1, 8);

            switch (choice) {

                case 1:
                    exploreLocation();
                    break;

                case 2:
                    questionSuspect();
                    break;

                case 3:
                    talkToWitness();
                    break;

                case 4:
                    showEvidence();
                    break;

                case 5:
                    journal.displayJournal();
                    break;

                case 6:
                    Hint.showHint(player.getInventory().size());
                    break;

                case 7:
                    Accusation.makeAccusation(player);
                    investigating = false;
                    break;

                case 8:
                    investigating = false;
                    System.out.println("Returning from investigation...");
                    break;
            }
        }
    }

    // Allows the player to explore university locations
    private void exploreLocation() {

        List<Location> locations = data.getLocations();

        System.out.println("\nAvailable Locations:");

        for (Location location : locations) {
            System.out.println(location);
        }

        int choice = Input.getChoice(
            "Select a location: ", 1, locations.size()
        );

        Location selected = locations.get(choice - 1);

        System.out.println("\nYou are exploring: " + selected.getName());
        System.out.println(selected.getDescription());

        journal.addEntry("Explored " + selected.getName());

        collectClues(selected.getId());
    }

    // Finds clues based on the selected location
    private void collectClues(int locationId) {

        System.out.println("\nSearching the area...");

        boolean found = false;

        for (Clue clue : data.getClues()) {

            int clueLocation = getClueLocation(clue.getId());

            if (clueLocation == locationId && !clue.isCollected()) {

                found = true;

                System.out.println("\nClue found: " + clue.getName());
                System.out.println(clue.getDescription());

                String answer = Input.getText(
                    "Collect this clue? (yes/no): "
                );

                if (answer.equalsIgnoreCase("yes")
                    || answer.equalsIgnoreCase("y")) {

                    Evidence evidence = new Evidence(clue);

                    if (evidence.collect(player)) {
                        caseFile.addEvidence(clue.getName());
                        journal.addEntry(
                            "Collected clue: " + clue.getName()
                        );
                    }
                }
            }
        }

        if (!found) {
            System.out.println("No new clues found in this location.");
        }
    }

    // Connects each clue to a university location
    private int getClueLocation(int clueId) {

        switch (clueId) {

            case 1:
            case 4:
                return 1;

            case 2:
            case 3:
                return 2;

            case 5:
                return 3;

            case 6:
                return 4;

            default:
                return -1;
        }
    }

    // Allows the player to question suspects
    private void questionSuspect() {

        List<Suspect> suspects = data.getSuspects();

        System.out.println("\n========== SUSPECTS ==========");

        for (int i = 0; i < suspects.size(); i++) {

            System.out.println(
                (i + 1) + ". " + suspects.get(i).getName()
            );
        }

        int choice = Input.getChoice(
            "Select a suspect: ", 1, suspects.size()
        );

        Suspect suspect = suspects.get(choice - 1);

        System.out.println("\nYou are questioning " + suspect.getName());

        suspect.speak();

        if (!suspect.isQuestioned()) {
            suspect.question();
            journal.addEntry(
                "Questioned suspect: " + suspect.getName()
            );
        }
    }

    // Allows the player to talk to the witness
    private void talkToWitness() {

        System.out.println("\nTalking to " + witness.getName());

        witness.speak();

        journal.addEntry("Talked to witness " + witness.getName());
    }

    // Displays collected evidence
    private void showEvidence() {

        System.out.println("\n========== EVIDENCE BOARD ==========");

        List<Clue> clues = player.getInventory();

        if (clues.isEmpty()) {
            System.out.println("No clues collected yet.");
            return;
        }

        for (Clue clue : clues) {
            System.out.println(
                clue.getId() + ". " + clue.getName()
            );
            System.out.println(clue.getDescription());
        }

        caseFile.displayCase();
        System.out.println("Current score: " + player.getScore());
    }
}

// Represents collected evidence
class Evidence {

    private Clue clue;

    public Evidence(Clue clue) {
        this.clue = clue;
    }

    public boolean collect(Player player) {

        if (clue.isCollected()) {
            System.out.println("This clue is already collected.");
            return false;
        }

        player.collectClue(clue);

        System.out.println("Evidence successfully collected!");
        player.addScore(5);

        return true;
    }

    public Clue getClue() {
        return clue;
    }
}

// Provides investigation hints
class Hint {

    public static void showHint(int clueCount) {

        System.out.println("\n========== DETECTIVE HINT ==========");

        if (clueCount == 0) {

            System.out.println(
                "Start by exploring the Archive Study."
            );

        } else if (clueCount < 3) {

            System.out.println(
                "Check the Main Hallway and look for unusual objects."
            );

        } else if (clueCount < 5) {

            System.out.println(
                "The Restoration Lab may contain important information."
            );

        } else {

            System.out.println(
                "Compare the access record and repair note."
            );

            System.out.println(
                "Pay attention to the Archive Technician's role."
            );
        }
    }
}

// Handles the player's final accusation
class Accusation {

    public static void makeAccusation(Player player) {

        System.out.println("\n========== FINAL ACCUSATION ==========");
        System.out.println("Who do you believe took the manuscript?");
        System.out.println("1. Asha");
        System.out.println("2. Kabir");
        System.out.println("3. Dev");

        int choice = Input.getChoice(
            "Enter your accusation: ", 1, 3
        );

        String accusedPerson;

        switch (choice) {

            case 1:
                accusedPerson = "Asha";
                break;

            case 2:
                accusedPerson = "Kabir";
                break;

            default:
                accusedPerson = "Dev";
                break;
        }

        boolean correct = accusedPerson.equals("Dev");

        GameResult result = new GameResult(
            correct,
            accusedPerson,
            player.getScore()
        );

        result.displayResult();

        if (correct) {
            System.out.println(
                "\nCASE SOLVED! Dev was responsible for hiding the manuscript."
            );
        } else {
            System.out.println(
                "\nIncorrect accusation. The real culprit was Dev."
            );
        }
    }
}

