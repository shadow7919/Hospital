package ir.ac.kntu;

import java.util.ArrayList;

public class Hospital {
    private String name;
    private Address address;
    private int beds = 12;
    private Part part = new Part();
    private ArrayList<Doctor> doctors = new ArrayList<>();
    private ArrayList<Patient> patients = new ArrayList<>();


    public boolean sameId(Doctor doctor) {
        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).getId() == doctor.getId()) {
                return true;
            }
        }
        return false;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public void setDoctors(Doctor doctor) {
        this.doctors.add(doctor);
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String toString() {
        return name + " " + address + " " + beds + " " + part.getPartKind();
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }
}

class Address {
    private String city;
    private String street;
    private String other;

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }


    public void setOther(String other) {
        this.other = other;
    }


    public String toString() {
        return city + " " + street + " " + other;
    }
}