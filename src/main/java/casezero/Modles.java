
package casezero;

import java.util.ArrayList;
import java.util.List;

// Stores the main game data
class GameData {

    private Player player;
    private List<Suspect> suspects;
    private List<Clue> clues;
    private List<Location> locations;

    public GameData() {
        suspects = new ArrayList<>();
        clues = new ArrayList<>();
        locations = new ArrayList<>();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Suspect> getSuspects() {
        return suspects;
    }

    public List<Clue> getClues() {
        return clues;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void addSuspect(Suspect suspect) {
        suspects.add(suspect);
    }

    public void addClue(Clue clue) {
        clues.add(clue);
    }

    public void addLocation(Location location) {
        locations.add(location);
    }
}

// Represents a place in the university
class Location {

    private int id;
    private String name;
    private String description;

    public Location(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return id + ". " + name + " - " + description;
    }
}

// Represents an object that can be found
class GameObject {

    private String name;
    private String description;
    private boolean discovered;

    public GameObject(String name, String description) {
        this.name = name;
        this.description = description;
        this.discovered = false;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void discover() {
        discovered = true;
    }
}

// Base class for people in the mystery
abstract class Character {

    protected String name;
    protected String description;

    public Character(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public abstract void speak();
}

// Represents a person suspected of the incident
class Suspect extends Character {

    private String alibi;
    private boolean questioned;

    public Suspect(String name, String description, String alibi) {
        super(name, description);
        this.alibi = alibi;
        this.questioned = false;
    }

    public String getAlibi() {
        return alibi;
    }

    public boolean isQuestioned() {
        return questioned;
    }

    public void question() {
        questioned = true;
    }

    @Override
    public void speak() {
        System.out.println(name + ": " + alibi);
    }
}

// Represents someone who provides information
class Witness extends Character {

    private String statement;

    public Witness(String name, String description, String statement) {
        super(name, description);
        this.statement = statement;
    }

    public String getStatement() {
        return statement;
    }

    @Override
    public void speak() {
        System.out.println(name + ": " + statement);
    }
}

// Represents evidence found by the player
class Clue {

    private int id;
    private String name;
    private String description;
    private boolean collected;

    public Clue(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.collected = false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        collected = true;
    }

    @Override
    public String toString() {
        return name + ": " + description;
    }
}

// Stores player information
class Player {

    private String name;
    private int score;
    private List<Clue> inventory;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.inventory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public List<Clue> getInventory() {
        return inventory;
    }

    public void addScore(int points) {
        score += points;
    }

    public void collectClue(Clue clue) {
        if (!clue.isCollected()) {
            clue.collect();
            inventory.add(clue);
            addScore(10);
        }
    }
}

// Stores information about the current case
class CaseFile {

    private String caseName;
    private String caseDescription;
    private List<String> collectedEvidence;

    public CaseFile(String caseName, String caseDescription) {
        this.caseName = caseName;
        this.caseDescription = caseDescription;
        this.collectedEvidence = new ArrayList<>();
    }

    public String getCaseName() {
        return caseName;
    }

    public String getCaseDescription() {
        return caseDescription;
    }

    public List<String> getCollectedEvidence() {
        return collectedEvidence;
    }

    public void addEvidence(String evidence) {
        collectedEvidence.add(evidence);
    }

    public void displayCase() {
        System.out.println("\nCase: " + caseName);
        System.out.println(caseDescription);
        System.out.println("Evidence collected: " + collectedEvidence.size());
    }
}

// Stores the player's investigation notes
class Journal {

    private List<JournalEntry> entries;

    public Journal() {
        entries = new ArrayList<>();
    }

    public void addEntry(String text) {
        entries.add(new JournalEntry(text));
    }

    public List<JournalEntry> getEntries() {
        return entries;
    }

    public void displayJournal() {
        System.out.println("\n========== INVESTIGATION JOURNAL ==========");

        if (entries.isEmpty()) {
            System.out.println("No notes have been added yet.");
            return;
        }

        for (int i = 0; i < entries.size(); i++) {
            System.out.println((i + 1) + ". " + entries.get(i).getText());
        }
    }
}

// Represents one journal note
class JournalEntry {

    private String text;

    public JournalEntry(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

// Stores the result of an investigation
class GameResult {

    private boolean solved;
    private String accusedPerson;
    private int finalScore;

    public GameResult(boolean solved, String accusedPerson, int finalScore) {
        this.solved = solved;
        this.accusedPerson = accusedPerson;
        this.finalScore = finalScore;
    }

    public boolean isSolved() {
        return solved;
    }

    public String getAccusedPerson() {
        return accusedPerson;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public void displayResult() {
        System.out.println("\n========== CASE RESULT ==========");

        if (solved) {
            System.out.println("Case successfully solved!");
        } else {
            System.out.println("The mystery remains unsolved.");
        }

        System.out.println("Accused person: " + accusedPerson);
        System.out.println("Final score: " + finalScore);
    }
}

