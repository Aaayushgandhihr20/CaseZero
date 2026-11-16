
package casezero;

// Contains the story introduction
class Story {

    public static void showIntroduction() {

        System.out.println("\n========================================");
        System.out.println("       THE MIDNIGHT MANUSCRIPT");
        System.out.println("========================================");

        System.out.println("\nIt is 8:15 PM at the university.");

        System.out.println("A rare historical manuscript has");
        System.out.println("mysteriously disappeared from the archive.");

        System.out.println("\nThe archive was locked.");
        System.out.println("Only a few people had access to it.");

        System.out.println("\nYou have been assigned to investigate");
        System.out.println("the incident and identify the culprit.");

        System.out.println("\nThere are three suspects:");
        System.out.println("1. Asha - Library Assistant");
        System.out.println("2. Kabir - Research Scholar");
        System.out.println("3. Dev - Archive Technician");

        System.out.println("\nRitika, a university student, may also");
        System.out.println("have information about the incident.");

        System.out.println("\nExplore the university, collect clues,");
        System.out.println("question suspects and solve the case.");

        System.out.println("\nYour investigation starts now!");
        System.out.println("========================================");
    }
}

// Creates the initial game information
class StorySetup {

    public static GameData createGameData() {

        GameData data = new GameData();

        // Create university locations
        data.addLocation(new Location(
            1,
            "Archive Study",
            "The room where the manuscript was kept."
        ));

        data.addLocation(new Location(
            2,
            "Main Hallway",
            "The hallway connecting university rooms."
        ));

        data.addLocation(new Location(
            3,
            "Restoration Lab",
            "A laboratory used to restore old documents."
        ));

        data.addLocation(new Location(
            4,
            "North Courtyard",
            "An outdoor area behind the university building."
        ));

        // Create suspects
        data.addSuspect(new Suspect(
            "Asha",
            "Library Assistant",
            "I was arranging books in the library."
        ));

        data.addSuspect(new Suspect(
            "Kabir",
            "Research Scholar",
            "I was working on my research project."
        ));

        data.addSuspect(new Suspect(
            "Dev",
            "Archive Technician",
            "I was checking the archive equipment."
        ));

        // Create clues
        data.addClue(new Clue(
            1,
            "Broken Lock",
            "The archive lock appears to have been damaged."
        ));

        data.addClue(new Clue(
            2,
            "Blue Thread",
            "A small piece of blue thread is near the archive."
        ));

        data.addClue(new Clue(
            3,
            "Footprint",
            "A muddy footprint is found near the hallway."
        ));

        data.addClue(new Clue(
            4,
            "Access Record",
            "The archive access record shows an unusual entry."
        ));

        data.addClue(new Clue(
            5,
            "Repair Note",
            "A note mentions maintenance of the archive lock."
        ));

        data.addClue(new Clue(
            6,
            "Witness Statement",
            "Someone was seen near the archive that evening."
        ));

        return data;
    }

    // Creates the witness
    public static Witness createWitness() {

        return new Witness(
            "Ritika",
            "University Student",
            "I noticed someone leaving the archive around 8 PM."
        );
    }
}

