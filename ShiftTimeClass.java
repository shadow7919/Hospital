package ir.ac.kntu;

import java.util.Objects;

public class ShiftTimeClass {
    public Week week;
    public ShiftsTime shiftsTime;
    public PartKind partKind;
    public Doctor doctor;

    public ShiftTimeClass(Week week, ShiftsTime shiftsTime, PartKind partKind, Doctor doctor) {
        this.week = week;
        this.shiftsTime = shiftsTime;
        this.partKind = partKind;
        this.doctor = doctor;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShiftTimeClass that = (ShiftTimeClass) o;
        return week == that.week &&
                shiftsTime == that.shiftsTime &&
                partKind == that.partKind;
    }

    public int hashCode() {
        return Objects.hash(week, shiftsTime, partKind);
    }
}
