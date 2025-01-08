package user.entity;

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
