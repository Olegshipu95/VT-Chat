package itmo.high_perf_sys.chat.model.entity;

public enum MaritalStatus {
    SINGLE("SINGLE"),
    DATING("DATING"),
    MARRIED("MARRIED"),
    DIVORCED("DIVORCED");

    String statusName;

    MaritalStatus(String aStatus) {
        statusName = aStatus;
    }
}
