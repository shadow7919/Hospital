package ir.ac.kntu;

public enum Week {
    SATURDAY(1), SUNDAY(2), MONDAY(3), TUESDAY(4), WEDNESDAY(5), THURSDAY(6), FRIDAY(7);
    private final int number;

    Week(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}