package ir.ac.kntu;

public enum ShiftsTime {
    MORNING(1), AFTER_NOON(2), NIGHT(3);
    private final int number;

    ShiftsTime(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
