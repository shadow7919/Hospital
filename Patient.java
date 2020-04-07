package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Patient {
    Scanner scanner = new Scanner(System.in);
    int caseId;
    private String name;
    private int id;
    private PartKind partKind;
    private Room room = new Room();
    private Doctor doctor;
    private Disease disease;
    private Insurance insurance;
    private MyDate entry;
    private MyDate departure;
    private int howManyDays;
    private double totalPrice;
    private int age;
    private Gender gender;
    private boolean isDischarge = false;
    private ArrayList<Nurse> nurses = new ArrayList<>();

    private void printMenu() {
        System.out.println("----------- Patient Menu -----------");
        System.out.println("1--> Add");
        System.out.println("2--> Information");
        System.out.println("3--> Change");
        System.out.println("4--> discharge");
        System.out.println("5--> Back");
    }

    public void menu() {
        int option;
        while (true) {
            printMenu();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    patientShow(null);
                    break;
                case 3:
                    change();
                    break;
                case 4:
                    dischargePatient();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Wrong input");
            }
            System.out.println("--------------------------");
        }
    }

    public void change() {
        System.out.println("-------- CHANGE --------");
        Patient patient = checkId();
        if (patient == null) {
            System.out.println("No patient Registered with this id");
            return;
        }
        int option;
        while (true) {
            patientShow(patient);
            changePrint();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    patient.name = scanner.nextLine();
                    break;
                case 2:
                    setAge(patient);
                    break;
                case 3:
                    chooseGender(patient);
                    break;
                case 4:
                    patient.entry = MyDate.dateSet();
                    break;
                case 5:
                    whichDisease(patient);
                    break;
                case 6:
                    room.pickRoom(patient);
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Wrong input ");
                    break;
            }
            System.out.println("------------------------");
        }
    }

    private void changePrint() {
        System.out.println("Which you want to change");
        System.out.println("1 --> Name ");
        System.out.println("2 --> Age ");
        System.out.println("3 --> Gender");
        System.out.println("4 --> entryDate");
        System.out.println("5 --> Disease");
        System.out.println("6 --> Room ");
        System.out.println("7 --> Back");
    }

    private boolean noSameId(int inputId) {
        for (Patient patient1 : Hospital.getPatients()) {
            if (patient1.id == inputId) {
                System.out.println("patient Registered with this id");
                return false;
            }
        }
        return true;
    }

    public void addPatient() {
        System.out.println("----------- Add Patient -----------");
        Random random = new Random();
        Patient patient = new Patient();
        System.out.print("Enter id : ");
        int inputId = scanner.nextInt();
        while (!noSameId(inputId)) {
            System.out.println("Same id registered");
            inputId = scanner.nextInt();
        }
        patient.id = inputId;
        scanner.nextLine();
        chooseGender(patient);
        System.out.print("enter " + patient.gender.getGender() + " name : ");
        patient.name = scanner.nextLine();
        setAge(patient);
        patient.entry = MyDate.dateSet();
        whichDisease(patient);
        patient.partKind = Room.whichPart();
        room.pickRoom(patient);
        patient.caseId = random.nextInt(100000) + patient.age + patient.id % 100000;
        addDoctorNurse(patient);
        Hospital.getPatients().add(patient);
    }

    public void addDoctorNurse(Patient patient) {
        Doctor doctor = whichDoctorHavePatient();
        if (doctor == null) {
            return;
        }
        patient.doctor = doctor;
        doctor.getPatients().add(patient);
        for (Nurse nurse : doctor.getNurses()) {
            nurse.getPatients().add(patient);
        }
    }

    public void setAge(Patient patient) {
        while (true) {
            System.out.print("Enter " + patient.gender.getGender() + " age : ");
            int age = scanner.nextInt();
            if (age > 0 && age < 200) {
                patient.age = age;
                return;
            } else {
                System.out.println("Age should be under 200");
            }
        }
    }

    private void chooseGender(Patient patient) {
        System.out.print("Gender\tM or F : ");
        while (true) {
            String chooseGender = scanner.nextLine();
            try {
                patient.gender = Gender.valueOf(chooseGender);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong gender");
            }
        }
    }

    private void whichDisease(Patient patient) {
        Disease[] diseases = Disease.values();
        while (true) {
            System.out.println("1--> " + Disease.ACCIDENT);
            System.out.println("2--> " + Disease.BURN);
            System.out.println("3--> " + Disease.STRIKE);
            System.out.println("4--> " + Disease.SOMETHING_ELSE);
            int choose = scanner.nextInt();
            if (choose <= diseases.length) {
                patient.disease = diseases[choose - 1];
                return;
            }
            System.out.println("Wrong input");
        }
    }

    private void patientShow(Patient myPatient) {
        Patient patient = myPatient;
        if (myPatient == null) {
            patient = checkId();
            if (patient == null) {
                System.out.println("No patient Registered with this id");
                return;
            }
        }
        System.out.println("Name : " + patient.name + "\t age : " + patient.age);
        System.out.println(patient.gender.getGender() + " in " + patient.partKind + " PART");
        System.out.println("entry date : " + patient.entry.getDay() + " / " + patient.entry.getMonth() + " / " + patient.entry.getYear());
        if (patient.isDischarge) {
            System.out.println("departure date : " + patient.departure.getDay() + " / " + patient.departure.getMonth() + " / " + patient.departure.getYear());
        }
        System.out.println("Room Number : " + patient.room.getRoomNumber());
        System.out.println("Disease : " + patient.disease);
        System.out.println("CaseId : " + patient.caseId);
        if (patient.doctor != null) {
            System.out.println("Doctor,\tName : " + patient.doctor.getName() + ",\tId : " + patient.doctor.getId());
            for (Nurse nurse : patient.nurses) {
                System.out.println("Nurse,\tName : " + nurse.getName() + ",\tId : " + nurse.getId());
            }
        }
    }

    public Patient checkId() {
        System.out.print("Enter id : ");
        int inputId = scanner.nextInt();
        for (Patient patient : Hospital.getPatients()) {
            if (inputId == patient.id) {
                return patient;
            }
        }
        return null;
    }

    public Doctor whichDoctorHavePatient() {
        int minPatientNumber = Integer.MAX_VALUE;
        Doctor chosenDoctor = null;
        for (Doctor doctor : Hospital.getDoctors()) {
            if (doctor.getPatients().size() < minPatientNumber) {
                minPatientNumber = doctor.getPatients().size();
                chosenDoctor = doctor;
            }
        }

        if (chosenDoctor == null || chosenDoctor.getPatients().size() == 5) {
            return null;
        }
        return chosenDoctor;
    }

    public void dischargePatient() {
        System.out.println("---------- DISCHARGE ----------");
        Patient patient = checkId();
        if (patient == null) {
            System.out.println("No patient Registered with this id ");
            return;
        }
        if (patient.isDischarge) {
            System.out.println("Already discharged ");
            return;
        }
        departureDateSet(patient);
        patient.howManyDays = MyDate.howLong(patient.entry, patient.departure);
        patient.room.discountForRoom(patient.room);
        patient.totalPrice = patient.room.getPrice() * patient.howManyDays;
        haveInsurance(patient);
        patient.isDischarge = true;
        System.out.println(patient.totalPrice);
        patient.doctor.getPatients().remove(patient);
        for (Nurse nurse : patient.nurses) {
            nurse.getPatients().remove(patient);
        }
        patient.room.getPatients().remove(patient);
        System.out.println("-----------------------------------");
    }

    private void departureDateSet(Patient patient) {
        while (true) {
            patient.departure = MyDate.dateSet();
            if (0 < MyDate.howLong(patient.entry, patient.departure)) {
                return;
            }
            System.out.println("departure should be bigger than entry");
        }
    }

    private Insurance haveInsuranceMenu() {
        int option;
        Insurance[] insurance = Insurance.values();
        System.out.println("-------- Insurance -------");
        while (true) {
            System.out.println("1 --> " + Insurance.ARMED_FORCES);
            System.out.println("2 --> " + Insurance.SOCIAL_INSURANCE);
            System.out.println("3 --> " + Insurance.HEALTH_SERVICES);
            System.out.println("4 --> " + Insurance.NON);
            option = scanner.nextInt();
            if (option <= insurance.length) {
                return insurance[option];
            }
            System.out.println("Wrong input");
        }
    }

    private void haveInsurance(Patient patient) {
        while (true) {
            switch (haveInsuranceMenu()) {
                case ARMED_FORCES:
                    patient.totalPrice *= Insurance.ARMED_FORCES.getDiscount();
                    return;
                case SOCIAL_INSURANCE:
                    patient.totalPrice *= Insurance.SOCIAL_INSURANCE.getDiscount();
                    return;
                case HEALTH_SERVICES:
                    patient.totalPrice *= Insurance.HEALTH_SERVICES.getDiscount();
                    return;
                case NON:
                    return;
                default:
                    System.out.println("Wrong input ");
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public ArrayList<Nurse> getNurses() {
        return nurses;
    }

    public PartKind getPartKind() {
        return partKind;
    }

    public void setPartKind(PartKind partKind) {
        this.partKind = partKind;
    }

    private enum Gender {
        M("his"), F("her");
        private String gender;

        Gender(String gender) {
            this.gender = gender;
        }

        public String getGender() {
            return gender;
        }
    }

}