package datasub.test.domain;

public enum RecurringCycle {

    DAY("day"), WEEK("week"), BI_WEEKLY("bi-weekly"), MONTH("month");

    private String jsonName;

    RecurringCycle(String jsonName) {
        this.jsonName = jsonName;
    }

    public String getJsonName() {
        return jsonName;
    }

    public void setJsonName(String jsonName) {
        this.jsonName = jsonName;
    }

    public static RecurringCycle fromString(String jsonName) {
        for (RecurringCycle r : RecurringCycle.values()) {
            if (r.jsonName.equals(jsonName)) {
                return r;
            }
        }
        return null;
    }
}
