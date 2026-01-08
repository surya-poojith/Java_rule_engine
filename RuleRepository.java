import java.util.Collections;
import java.util.List;

public class RuleRepository {

    private final List<VisaRule> rules;

    public RuleRepository(List<VisaRule> rules) {
        if (rules == null || rules.isEmpty()) { //here we used defensive coding
            throw new IllegalArgumentException("Rules list cannot be null or empty");
        }
        this.rules = rules;
    }

    public List<VisaRule> getAllRules() {
        return Collections.unmodifiableList(rules);
    }
}
