import java.util.Collections;
import java.util.List;

public class VisaDecision {

    private final boolean visaRequired;
    private final VisaType visaType;
    private final List<DocumentType> documents;
    private final int estimatedProcessingDays;
    private final List<String> warnings;

    public VisaDecision(
            boolean visaRequired,
            VisaType visaType,
            List<DocumentType> documents,
            int estimatedProcessingDays,
            List<String> warnings
    ) {
        this.visaRequired = visaRequired;
        this.visaType = visaType;
        this.documents = documents == null
                ? List.of()
                : Collections.unmodifiableList(documents);
        this.estimatedProcessingDays = estimatedProcessingDays;
        this.warnings = warnings == null
                ? List.of()
                : Collections.unmodifiableList(warnings);
    }

    public boolean isVisaRequired() {
        return visaRequired;
    }

    public VisaType getVisaType() {
        return visaType;
    }

    public List<DocumentType> getDocuments() {
        return documents;
    }

    public int getEstimatedProcessingDays() {
        return estimatedProcessingDays;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    @Override
    public String toString() {
        return "VisaDecision{" +
                "visaRequired=" + visaRequired +
                ", visaType=" + visaType +
                ", documents=" + documents +
                ", estimatedProcessingDays=" + estimatedProcessingDays +
                ", warnings=" + warnings +
                '}';
    }
}
