package casezero;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

// Handles file operations for CASEZERO
class FileService {

    private final String folder = "exports";

    public FileService() {
        File directory = new File(folder);

        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    // Exports collected clues into a CSV file
    public void exportClues(Player player) {

        Path file = Path.of(folder, "collected_clues.csv");

        try (BufferedWriter writer =
                 new BufferedWriter(new FileWriter(file.toFile()))) {

            writer.write("Clue ID,Clue Name,Description");
            writer.newLine();

            for (Clue clue : player.getInventory()) {

                writer.write(
                    CsvHelper.format(String.valueOf(clue.getId())) + "," +
                    CsvHelper.format(clue.getName()) + "," +
                    CsvHelper.format(clue.getDescription())
                );

                writer.newLine();
            }

            System.out.println("Clues exported successfully!");

        } catch (IOException e) {

            System.out.println("Error exporting clues: " + e.getMessage());
        }
    }

    // Exports journal notes into a text file
    public void exportJournal(Journal journal) {

        Path file = Path.of(folder, "investigation_notes.txt");

        try (BufferedWriter writer =
                 new BufferedWriter(new FileWriter(file.toFile()))) {

            for (JournalEntry entry : journal.getEntries()) {

                writer.write(entry.getText());
                writer.newLine();
            }

            System.out.println("Journal exported successfully!");

        } catch (IOException e) {

            System.out.println("Error exporting journal: " + e.getMessage());
        }
    }

    // Imports journal notes from a text file
    public void importJournal(Journal journal) {

        Path file = Path.of(folder, "import_notes.txt");

        if (!Files.exists(file)) {
            System.out.println("Import file not found.");
            System.out.println("Create exports/import_notes.txt first.");
            return;
        }

        try (BufferedReader reader =
                 new BufferedReader(new FileReader(file.toFile()))) {

            String line;
            int count = 0;

            while ((line = reader.readLine()) != null) {

                if (!line.isBlank()) {
                    journal.addEntry(line);
                    count++;
                }
            }

            System.out.println(count + " notes imported successfully!");

        } catch (IOException e) {

            System.out.println("Error importing notes: " + e.getMessage());
        }
    }

    // Displays the contents of an exported clue file
    public void displayExportedClues() {

        Path file = Path.of(folder, "collected_clues.csv");

        if (!Files.exists(file)) {
            System.out.println("No exported clues found.");
            return;
        }

        try (BufferedReader reader =
                 new BufferedReader(new FileReader(file.toFile()))) {

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {

            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}

// Helps format CSV values safely
class CsvHelper {

    public static String format(String value) {

        if (value == null) {
            return "\"\"";
        }

        String formatted = value.replace("\"", "\"\"");

        return "\"" + formatted + "\"";
    }

    // Parses a simple CSV row
    public static List<String> parse(String line) {

        List<String> values = new ArrayList<>();

        StringBuilder current = new StringBuilder();

        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {

            char character = line.charAt(i);

            if (character == '"') {

                if (insideQuotes && i + 1 < line.length()
                    && line.charAt(i + 1) == '"') {

                    current.append('"');
                    i++;

                } else {

                    insideQuotes = !insideQuotes;
                }

            } else if (character == ',' && !insideQuotes) {

                values.add(current.toString());
                current.setLength(0);

            } else {

                current.append(character);
            }
        }

        values.add(current.toString());

        return values;
    }
}
