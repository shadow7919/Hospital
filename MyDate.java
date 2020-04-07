package ir.ac.kntu;

import java.util.Scanner;

public class MyDate {
    private int year;
    private int month;
    private int day;
    private Separator separator = Separator.DASH;

    public MyDate(int year, int month, int day) {
        checkAndSetDate(year, month, day);
    }

    public MyDate(MyDate date) {
        this.year = date.year;
        this.month = date.month;
        this.day = date.day;
    }

    public MyDate() {
    }

    public static MyDate dateSet() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter entry date ( day / month / year ) :");
            int entryDay = scanner.nextInt();
            int entryMonth = scanner.nextInt();
            int entryYear = scanner.nextInt();
            MyDate date = new MyDate(entryYear, entryMonth, entryDay);
            if (date.year != 0) {
                return date;
            }
            System.out.println("Wrong Date ");
        }
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

    private void checkAndSetDate(int year, int month, int day) {
        if (checkInputs(year, month, day)) {
            this.year = year;
            this.month = month;
            this.day = day;
        } else {
            this.year = 0;
            this.month = 1;
            this.day = 1;
        }
    }

    private boolean checkInputs(int year, int month, int day) {
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
        return true;
    }

    public int getYear() {
        return year;
    }


    public int getMonth() {
        return month;
    }


    public int getDay() {
        return day;
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
        return true;
    }

    public enum Separator {DASH, COLON}
}
