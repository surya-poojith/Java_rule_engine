public class VisaRule {

    private final Country destinationCountry;
    private final Country passportCountry;
    private final TravelPurpose travelPurpose;
    private final int stayDuration;
    private final VisaDecision decision;

    public VisaRule(
            Country destinationCountry,
            Country passportCountry,
            TravelPurpose travelPurpose,
            int stayDuration,
            VisaDecision decision
    ) {
        this.destinationCountry = destinationCountry;
        this.passportCountry = passportCountry;
        this.travelPurpose = travelPurpose;
        this.stayDuration = stayDuration;
        this.decision = decision;
    }

    public Country getDestinationCountry() {
        return destinationCountry;
    }

    public Country getPassportCountry() {
        return passportCountry;
    }

    public TravelPurpose getTravelPurpose() {
        return travelPurpose;
    }

    public int getStayDuration() {
        return stayDuration;
    }

    public VisaDecision getDecision() {
        return decision;
    }
}
