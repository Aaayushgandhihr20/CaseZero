package casezero;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// Creates a summary report of the investigation
class ReportService {

    public void generateReport(
            Player player,
            CaseFile caseFile,
            Journal journal,
            GameResult result) {

        System.out.println("\n========== CASEZERO FINAL REPORT ==========");

        System.out.println("Case: " + caseFile.getCaseName());

        System.out.println("Detective: " + player.getName());

        System.out.println("Collected Evidence: "
                + player.getInventory().size());

        System.out.println("Final Score: " + player.getScore());

        System.out.println("Accused Person: "
                + result.getAccusedPerson());

        System.out.println("Case Solved: "
                + (result.isSolved() ? "Yes" : "No"));

        System.out.println("\nEvidence Collected:");

        // Stream API: converts clue objects into a list of clue names
        List<String> clueNames = player.getInventory()
                .stream()
                .map(Clue::getName)
                .sorted()
                .collect(Collectors.toList());

        clueNames.forEach(name ->
                System.out.println("- " + name));

        System.out.println("\nInvestigation Journal:");
        journal.displayJournal();

        System.out.println("============================================");
    }

    public void showCluesByName(Player player) {

        System.out.println("\nClues in alphabetical order:");

        player.getInventory()
                .stream()
                .sorted(Comparator.comparing(Clue::getName))
                .forEach(clue ->
                        System.out.println(clue.getName()));
    }
}
