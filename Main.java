import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        RuleLoader loader = new RuleLoader();
        List<VisaRule> rules = loader.loadRules("visarules.json");

        System.out.println("Rules are loaded");
        System.out.println("Total rules: " + rules.size());

        RuleRepository repository = new RuleRepository(rules);

        VisaRuleEvaluator evaluator = new VisaRuleEvaluator(repository);

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("destination country: ");
            Country destination = Country.valueOf(scanner.nextLine().toUpperCase());

            System.out.print("passport country: ");
            Country passport = Country.valueOf(scanner.nextLine().toUpperCase());

            System.out.print("travel purpose: ");
            TravelPurpose purpose = TravelPurpose.valueOf(scanner.nextLine().toUpperCase());

            System.out.print("stay duration in days: ");
            int stayDuration = Integer.parseInt(scanner.nextLine());

            VisaDecision decision = evaluator.evaluate(
                    destination,
                    passport,
                    purpose,
                    stayDuration
            );

            System.out.println("\nCompleted Visa evaluation.");
            System.out.println(decision);

        } catch (IllegalArgumentException e) {
            System.out.println("\nInvalid input provided.");
            System.out.println("Please check country names, travel purpose, or stay duration.");
        } finally {
            scanner.close();
        }
    }
}
