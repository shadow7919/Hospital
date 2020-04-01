package ir.ac.kntu;

import java.util.Date;

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

enum Disease {
    BURN,STRIKE,ACCIDENT,SOMETHING_ELSE
}

enum Insurance {
    socialInsurance(10),
    armedForces(50),
    healthService(25);
    private int discount;

    Insurance(int num) {
        discount = num;
    }

    public int getDiscount() {
        return discount;
    }
}
