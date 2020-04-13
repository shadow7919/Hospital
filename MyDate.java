package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Scanner;

public class MyDate {
    private static final ArrayList<MyDate> period = new ArrayList<>();
    private int year = 0;
    private int month = 1;
    private int day = 1;
    private int hour = 0;
    private Separator separator = Separator.DASH;

    public MyDate(int year, int month, int day, int hour) {
        checkAndSetDate(year, month, day, hour);
    }

    public MyDate() {
    }

    public static MyDate dateSet(boolean isHours) {
        Scanner scanner = new Scanner(System.in);
        int entryHour = 24;
        while (true) {
            System.out.println("Enter date ");
            if (isHours) {
                System.out.print("hour / ");
            }
            System.out.print("day / month / year : ");
            if (isHours) {
                entryHour = scanner.nextInt();
            }
            int entryDay = scanner.nextInt();
            int entryMonth = scanner.nextInt();
            int entryYear = scanner.nextInt();
            MyDate date = new MyDate(entryYear, entryMonth, entryDay, entryHour);
            if (date.year != 0) {
                return date;
            }
            System.out.println("Wrong Date ");
        }
    }

    public static void showDate(MyDate date) {
        System.out.print(date.hour + " / " + date.day + " / " + date.month + " / " + date.year);
    }

    public static int howLong(MyDate before, MyDate after) {
        int differenceYear = after.year - before.year;
        int differenceMonthToDay = monthToDay(after.month) - monthToDay(before.month);
        int differenceDay = after.day - before.day;
        return differenceYear * 365 + differenceMonthToDay + differenceDay;
    }

    private static int monthToDay(int month) {
        if (month < 7) {
            return month * 31;
        } else {
            return 6 * 31 + (month - 6) * 30;
        }
    }

    public static void inputPeriod() {
        while (true) {
            System.out.println("Enter first date");
            MyDate.getPeriod().add(MyDate.dateSet(false));
            System.out.println("Enter second date");
            MyDate.getPeriod().add(MyDate.dateSet(false));
            if (MyDate.howLong(MyDate.getPeriod().get(0), MyDate.getPeriod().get(1)) > 0) {
                break;
            }
            System.out.println("wrong Dates");
        }
    }

    public static ArrayList<MyDate> getPeriod() {
        return period;
    }

    private void checkAndSetDate(int year, int month, int day, int hour) {
        if (checkInputs(year, month, day, hour)) {
            this.year = year;
            this.month = month;
            this.day = day;
            this.hour = hour;
        } else {
            this.hour = 0;
            this.year = 0;
            this.month = 1;
            this.day = 1;
        }
    }

    private boolean checkInputs(int year, int month, int day, int hour) {
        if (month < 1 || month > 12) {
            return false;
        }
        if (day < 1 || day > 31) {
            return false;
        }
        if (month > 6 && day == 31) {
            return false;
        }
        if (month == 12 && day == 30 && !isLeapYear(year)) {
            return false;
        }
        if (hour <= 0 || hour > 24) {
            return false;
        }
        return true;
    }

    public int getYear() {
        return year;
    }

    public String toString() {
        switch (separator) {
            case DASH:
                return year + "-" + month + "-" + day;
            case COLON:
                return year + ":" + month + ":" + day;
        }
        return year + "-" + month + "-" + day;
    }

    private void handleEsfand(MyDate curDate, MyDate nextDate) {
        int boundaryDay = 29;
        if (isLeapYear(curDate.year)) {
            boundaryDay = 30;
        }
        if (curDate.day == boundaryDay) {
            nextDate.year++;
            nextDate.month = 1;
            nextDate.day = 1;
        } else {
            nextDate.day++;
        }
    }

    private boolean isLeapYear(int year) {
        double a = 0.025;
        double b = 266;
        double leapDays0 = 0, leapDays1 = 0;
        int frac0 = 0, frac1 = 0;
        if (year > 0) {
            leapDays0 = ((year + 38) % 2820) * 0.24219 + a;  //0.24219 ~ extra days of one year
            leapDays1 = ((year + 39) % 2820) * 0.24219 + a;  //38 days is the difference of epoch to
            //2820-year cycle
        } else if (year < 0) {
            leapDays0 = ((year + 39) % 2820) * 0.24219 + a;
            leapDays1 = ((year + 40) % 2820) * 0.24219 + a;
        } else {
            return false;
        }

        frac0 = (int) ((leapDays0 - (int) (leapDays0)) * 1000);
        frac1 = (int) ((leapDays1 - (int) (leapDays1)) * 1000);

        if (frac0 <= b && frac1 > b) {
            return true;
        } else {
            return false;
        }
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + hour;
        result = prime * result + day;
        result = prime * result + month;
        result = prime * result + year;
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        MyDate other = (MyDate) obj;
        if (day != other.day) {
            return false;
        }
        if (month != other.month) {
            return false;
        }
        if (year != other.year) {
            return false;
        }
        if (hour != other.hour) {
            return false;
        }
        return true;
    }

    public enum Separator {DASH, COLON}
}