package ir.ac.kntu;

import java.util.Date;

enum Disease {
    BURN, STRIKE, ACCIDENT, SOMETHING_ELSE
}

enum Insurance {
    socialInsurance(.9),
    armedForces(.5),
    healthService(.75);
    private double discount;

    Insurance(double num) {
        discount = num;
    }

    public double getDiscount() {
        return discount;
    }
}

public class Record {
    private String firstName;
    private String lastName;
    private int id;
    //    private Part part;
    private Disease disease;
    private Date date;
    //    private hour;
    private Insurance insurance;
    private Doctor doctor;
    private int caseId;
    private char gender;
    private int age;
}
