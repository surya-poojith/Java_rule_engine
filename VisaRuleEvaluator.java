import java.util.ArrayList;
import java.util.List;

public class VisaRuleEvaluator {

    private final RuleRepository ruleRepository;

    public VisaRuleEvaluator(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    public VisaDecision evaluate(
            Country destination,
            Country passport,
            TravelPurpose purpose,
            int stayDuration
    ) {

        List<VisaRule> matchedRules = new ArrayList<>();

        for (VisaRule rule : ruleRepository.getAllRules()) {
            if (matches(rule, destination, passport, purpose, stayDuration)) {
                matchedRules.add(rule);
            }
        }

        if (matchedRules.isEmpty()) {
            return defaultDecision("No matching rule found");
        }

        if (matchedRules.size() > 1) {
            return conflictDecision("Multiple matching rules found");
        }

        return matchedRules.get(0).getDecision();
    }

    private boolean matches(
            VisaRule rule,
            Country destination,
            Country passport,
            TravelPurpose purpose,
            int stayDuration
    ) {

        return rule.getDestinationCountry() == destination
                && rule.getPassportCountry() == passport
                && rule.getTravelPurpose() == purpose
                && stayDuration <= rule.getStayDuration();
    }

    private VisaDecision defaultDecision(String warning) {
        List<String> warnings = new ArrayList<>();
        warnings.add(warning);

        return new VisaDecision(
                true,
                VisaType.TOURIST,
                new ArrayList<>(),
                0,
                warnings
        );
    }

    private VisaDecision conflictDecision(String warning) {
        List<String> warnings = new ArrayList<>();
        warnings.add(warning);

        return new VisaDecision(
                true,
                VisaType.TOURIST,
                new ArrayList<>(),
                0,
                warnings
        );
    }
}
