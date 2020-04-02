package ir.ac.kntu;

import java.util.Scanner;

public class Patient {
    Scanner scanner = new Scanner(System.in);
    private String name;
    private int id;
    private Part part = new Part();
    private Room room = new Room();
    private Doctor doctor;
    private Disease disease;
    //    private int age;
//    private int caseId;
    private MyDate entry = new MyDate();

    public void addPatient(Hospital hospital) {
        System.out.println("----------- Add Patient -----------");
        System.out.println("enter the id");
        int inputId = scanner.nextInt();
        scanner.nextLine();
        Gender gender;
        if (checkId(hospital, inputId) == null) {
            id = inputId;
            System.out.println("What is the gender of patient M or F");
            while (true) {
                String chooseGender = scanner.nextLine();
                try {
                    gender = Gender.valueOf(chooseGender);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Wrong gender");
                }
            }
            System.out.print("enter "+gender.getGender() +" name : ");
            name = scanner.nextLine();
            System.out.print("entry date ( day / month / year ) :");
            entry.setDay(scanner.nextInt());
            entry.setMonth(scanner.nextInt());
            entry.setYear(scanner.nextInt());
            choosePart();
            whichDisease();
            room.pickRoom(hospital, part.getPartKind(), this, part);
            // age , insurance,case id,
            hospital.getPatients().add(this);
        } else {
            System.out.println("id is already saved");
        }
    }

    private void choosePart() {
        System.out.println("Choose part --> \n1-->" + PartKind.NORMAL + "\n2-->" + PartKind.EMERGENCY);
        int whichPart;
        do {
            whichPart = scanner.nextInt();
            switch (whichPart) {
                case 1:
                    part.setPartKind(PartKind.NORMAL);
                    break;
                case 2:
                    part.setPartKind(PartKind.EMERGENCY);
                    break;
                default:
                    System.out.println("Wrong input");
                    break;
            }
        } while (whichPart != 1 && whichPart != 2);
    }

    private void whichDisease() {
        System.out.println("Which disease does" + name + "have ?");
        while (true) {
            System.out.println("1--> " + Disease.ACCIDENT);
            System.out.println("2--> " + Disease.BURN);
            System.out.println("3--> " + Disease.STRIKE);
            System.out.println("4--> " + Disease.SOMETHING_ELSE);
            String choose = scanner.next();
            Disease disease;
            try {
                disease = Disease.valueOf(choose);
                this.disease = disease;
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong input");
            }
        }
    }

    private void patientShow(Hospital hospital) {
        System.out.println("Enter the id");
        Patient patient = null;
        while (true) {
            int inputId = scanner.nextInt();
            for (int i = 0; i < hospital.getPatients().size(); i++) {
                if (inputId == hospital.getPatients().get(i).id) {
                    patient = hospital.getPatients().get(i);
                    System.out.println(patient.name + " is  in " + patient.part.getPartKind() + " PART");
                    System.out.println("entry date is : " + entry.getDay() + " / " + entry.getMonth() + " / " + entry.getYear());
                    if(patient.doctor != null) {
                        System.out.println("Doctor of " + patient.name + " is " + patient.doctor.getName() + " ( doctors id : " + patient.doctor.getId() + " )");
                    }
                    System.out.println(patient.name + " is in room " + room.getRoomNumber());
                    System.out.println(patient.name + " is here for " + patient.disease);
                    // something else may be added for show
                }
            }
            if (patient != null) {
                break;
            }
            System.out.println("There is no one with this in this hospital");
        }
    }

    public void printMenu(Hospital hospital) {
        int option = 0;
        while (true) {
            System.out.println("----------- Patient Menu -----------");
            System.out.println("1. Add Patient");
            System.out.println("2. Patient doctor ");
            System.out.println("3. Patient information");
            System.out.println("4. discharge Patient");
            System.out.println("5. Back to previous menu");
            System.out.println("---------------------------------------");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    addPatient(hospital);
                    break;
                case 2:
                    pickDoctorForPatient(hospital);
                    break;
                case 3:
                    patientShow(hospital);
                    break;
                case 4:
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    public Patient checkId(Hospital hospital, int id) {
        for (int i = 0; i < hospital.getPatients().size(); i++) {
            if (id == hospital.getPatients().get(i).getId()) {
                return hospital.getPatients().get(i);
            }
        }
        return null;
    }

    public void pickDoctorForPatient(Hospital hospital) {
        System.out.println("Enter the Patient id");
        int inputId = scanner.nextInt();
        while (checkId(hospital, inputId) == null) {
            System.out.println("cant find patient with this id");
            inputId = scanner.nextInt();
        }
        Patient patient = checkId(hospital, inputId);
        Doctor doctor = whichDoctorHavePatient(hospital);
        patient.doctor = doctor;
        doctor.setPatientId(patient.id);
    }
    public Doctor whichDoctorHavePatient(Hospital hospital){
        int minPatientNumber = Integer.MAX_VALUE;
        Doctor chosenDoctor = new Doctor();
        for (Doctor doctor : hospital.getDoctors()){
            if(doctor.getPatients().size() < minPatientNumber){
                minPatientNumber = doctor.getPatients().size();
                chosenDoctor = doctor ;
            }
        }
        if(chosenDoctor.getPatients().size()<5){
            return doctor;
        }else{
            return null;
        }
    }
    public int getId() {
        return id;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
    public void setDoctor(Doctor doctor){
        this.doctor = doctor;
    }
    public void setId(int id){
        this.id = id ;
    }
}
enum Gender{
    M("his"),F("her");
    private String gender;
    private Gender(String gender){
        this.gender = gender;
    }
    public String getGender(){
        return gender;
    }
}
/*
1--> add patient{
enter the name
enter the id
enter the room
enter gender
enter the age
enter entry date
pick doctor
}
 */