package ir.ac.kntu;

import java.util.Date;

public class Patient {
    private String name;
    private String lastName;
    //    public Part part;
    private Room room;
    private Doctor doctor;
    private char gender;
    private int age;
    private int caseId;
    private Date entry;

    public Date getEntry() {
        return entry;
    }
}