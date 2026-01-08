import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RuleLoader {

    private static final Set<String> ALLOWED_RULE_FIELDS = Set.of(
            "id",
            "destinationCountry",
            "passportCountry",
            "travelPurpose",
            "stayDuration",
            "decision"
    );

    private static final Set<String> ALLOWED_DECISION_FIELDS = Set.of(
            "visaRequired",
            "visaType",
            "documents",
            "estimatedProcessingDays",
            "warnings"
    );

    public List<VisaRule> loadRules(String filePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(new File(filePath));

            JsonNode rulesNode = rootNode.get("rules");
            if (rulesNode == null || !rulesNode.isArray()) {
                throw new InvalidRuleConfigException("Missing or invalid 'rules' array");
            }

            List<VisaRule> rules = new ArrayList<>();

            for (JsonNode ruleNode : rulesNode) {
                validateRuleFields(ruleNode);
                VisaRule rule = mapToVisaRule(ruleNode);
                rules.add(rule);
            }

            return rules;

        } catch (Exception e) {
            throw new InvalidRuleConfigException("Failed to load rules: " + e.getMessage());
        }
    }

    private void validateRuleFields(JsonNode ruleNode) {
        Iterator<String> fieldNames = ruleNode.fieldNames();
        while (fieldNames.hasNext()) {
            String field = fieldNames.next();
            if (!ALLOWED_RULE_FIELDS.contains(field)) {
                throw new InvalidRuleConfigException("Unknown field in rule: " + field);
            }
        }

        checkRequired(ruleNode, "destinationCountry");
        checkRequired(ruleNode, "passportCountry");
        checkRequired(ruleNode, "travelPurpose");
        checkRequired(ruleNode, "stayDuration");
        checkRequired(ruleNode, "decision");

        JsonNode decisionNode = ruleNode.get("decision");
        validateDecisionFields(decisionNode);
    }

    private void validateDecisionFields(JsonNode decisionNode) {
        Iterator<String> fieldNames = decisionNode.fieldNames();
        while (fieldNames.hasNext()) {
            String field = fieldNames.next();
            if (!ALLOWED_DECISION_FIELDS.contains(field)) {
                throw new InvalidRuleConfigException("Unknown field in decision: " + field);
            }
        }

        checkRequired(decisionNode, "visaRequired");

        boolean visaRequired = decisionNode.get("visaRequired").asBoolean();

        if (visaRequired) {
            checkRequired(decisionNode, "visaType");
            checkRequired(decisionNode, "documents");
            checkRequired(decisionNode, "estimatedProcessingDays");
        }
    }

    private void checkRequired(JsonNode node, String fieldName) {
        if (!node.has(fieldName) || node.get(fieldName).isNull()) {
            throw new InvalidRuleConfigException("Missing required field: " + fieldName);
        }
    }

    private VisaRule mapToVisaRule(JsonNode ruleNode) {
        Country destination = Country.valueOf(ruleNode.get("destinationCountry").asText());
        Country passport = Country.valueOf(ruleNode.get("passportCountry").asText());
        TravelPurpose purpose = TravelPurpose.valueOf(ruleNode.get("travelPurpose").asText());
        int stayDuration = ruleNode.get("stayDuration").asInt();

        JsonNode decisionNode = ruleNode.get("decision");

        boolean visaRequired = decisionNode.get("visaRequired").asBoolean();
        VisaType visaType = visaRequired
                ? VisaType.valueOf(decisionNode.get("visaType").asText())
                : VisaType.NONE;

        List<DocumentType> documents = new ArrayList<>();
        if (visaRequired) {
            for (JsonNode doc : decisionNode.get("documents")) {
                documents.add(DocumentType.valueOf(doc.asText()));
            }
        }

        int processingDays = visaRequired
                ? decisionNode.get("estimatedProcessingDays").asInt()
                : 0;

        List<String> warnings = new ArrayList<>();
        if (decisionNode.has("warnings")) {
            for (JsonNode w : decisionNode.get("warnings")) {
                warnings.add(w.asText());
            }
        }

        VisaDecision decision = new VisaDecision(
                visaRequired,
                visaType,
                documents,
                processingDays,
                warnings
        );

        return new VisaRule(
                destination,
                passport,
                purpose,
                stayDuration,
                decision
        );
    }
}
