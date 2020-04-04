package ir.ac.kntu;

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
    private int totalPrice;
    private int age;
    private Gender gender;

    public void printMenu(Hospital hospital) {
        int option;
        while (true) {
            System.out.println("----------- Patient Menu -----------");
            System.out.println("1--> Add Patient");
            System.out.println("2--> Patient information");
            System.out.println("3--> discharge Patient");
            System.out.println("4--> Back to previous menu");
            System.out.println("---------------------------------------");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    addPatient(hospital);
                    break;
                case 2:
                    patientShow(hospital);
                    break;
                case 3:
                    dischargePatient(hospital);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    public void addPatient(Hospital hospital) {
        Random random = new Random();
        System.out.println("----------- Add Patient -----------");
        System.out.print("enter the id : ");
        int inputId = scanner.nextInt();
        scanner.nextLine();
        if (checkId(hospital, inputId) == null) {
            id = inputId;
            System.out.println("What is the gender of patient M or F");
            chooseGender();
            System.out.print("enter " + gender.getGender() + " name : ");
            name = scanner.nextLine();
            System.out.print("Enter " + gender.getGender() + " age : ");
            age = scanner.nextInt();
            entryDateSet();
            whichDisease();
            room.pickRoom(hospital, this);
            caseId = random.nextInt(100000) + age + id % 100000;
            doctor = whichDoctorHavePatient(hospital);
            if (doctor != null) {
                doctor.getPatients().add(this);
            } else {
                System.out.println("There is no doctor to take care of this patient ");
            }
            hospital.getPatients().add(this);
        } else {
            System.out.println("id is already Registered");
        }
    }

    private void entryDateSet() {
        while (true) {
            System.out.print("Enter entry date ( day / month / year ) :");
            int entryDay = scanner.nextInt();
            int entryMonth = scanner.nextInt();
            int entryYear = scanner.nextInt();
            entry = new MyDate(entryYear, entryMonth, entryDay);
            if (entry.getYear() != 0) {
                break;
            }
            System.out.println("Wrong Date ");
        }
    }

    private void chooseGender() {
        while (true) {
            String chooseGender = scanner.nextLine();
            try {
                gender = Gender.valueOf(chooseGender);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong gender");
            }
        }
    }

    private void whichDisease() {
        System.out.println("Which disease does" + name + "have ?");
        while (true) {
            System.out.println("---> " + Disease.ACCIDENT);
            System.out.println("---> " + Disease.BURN);
            System.out.println("---> " + Disease.STRIKE);
            System.out.println("---> " + Disease.SOMETHING_ELSE);
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
        Patient patient = null;
        while (patient == null) {
            System.out.println("Enter the id");
            int inputId = scanner.nextInt();
            for (int i = 0; i < hospital.getPatients().size(); i++) {
                if (inputId == hospital.getPatients().get(i).id) {
                    patient = hospital.getPatients().get(i);
                }
            }
            System.out.println("no one with this is Registered");
        }
        System.out.println("Name : " + patient.name + "\t age : " + patient.age);
        System.out.println(patient.gender.getGender() + " in " + patient.partKind + " PART");
        System.out.println("entry date : " + entry.getDay() + " / " + entry.getMonth() + " / " + entry.getYear());
        System.out.println("Room Number : " + room.getRoomNumber());
        System.out.println("Disease : " + patient.disease);
        System.out.println("CaseId : " + patient.caseId);
        if (patient.doctor != null) {
            System.out.println("Doctor of " + patient.name + " is " + patient.doctor.getName() + " ( doctors id : " + patient.doctor.getId() + " )");
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

    public Doctor whichDoctorHavePatient(Hospital hospital) {
        int minPatientNumber = Integer.MAX_VALUE;
        Doctor chosenDoctor = new Doctor();
        for (Doctor doctor : hospital.getDoctors()) {
            if (doctor.getPatients().size() < minPatientNumber) {
                minPatientNumber = doctor.getPatients().size();
                chosenDoctor = doctor;
            }
        }
        if (chosenDoctor.getPatients().size() < 5) {
            return doctor;
        } else {
            return null;
        }
    }

    public void dischargePatient(Hospital hospital) {
        Patient patient;
        System.out.println("---------- DISCHARGE ----------");
        System.out.println("enter the id");
        int inputId = scanner.nextInt();
        if (checkId(hospital, inputId) != null) {
            patient = checkId(hospital, inputId);
            patient.howManyDays = howLong(entry, departure);
            patient.room.discountForRoom(patient.room);
            patient.totalPrice = patient.room.getPrice() * patient.howManyDays;
            haveInsurance(patient);
            System.out.println(patient.totalPrice);
        } else {
            System.out.println("No patient Registered with this id ");
        }
        System.out.println("-----------------------------------");
    }

    private void departureDateSet(Patient patient) {
        while (true) {
            System.out.print("Enter departure date ( day / month / year ) :");
            int day = scanner.nextInt();
            int month = scanner.nextInt();
            int year = scanner.nextInt();
            patient.departure = new MyDate(year, month, day);
            if (patient.departure.getYear() != 0) {
                break;
            }
            System.out.println("Wrong Date ");
        }
    }

    private void haveInsurance(Patient patient) {
        System.out.println("Does " + patient.name + " have insurance ");
        System.out.println("1 --> " + Insurance.armedForces);
        System.out.println("2 --> " + Insurance.socialInsurance);
        System.out.println("3 --> " + Insurance.healthService);
        System.out.println("if you don't have Insurance enter another Integer ");
        int option = scanner.nextInt();
        switch (option) {
            case 1:
                patient.totalPrice *= Insurance.armedForces.getDiscount();
                break;
            case 2:
                patient.totalPrice *= Insurance.socialInsurance.getDiscount();
                break;
            case 3:
                patient.totalPrice *= Insurance.healthService.getDiscount();
                break;
            default:
                break;
        }
    }

    public int howLong(MyDate entry, MyDate departure) {
        int differenceYear = departure.getYear() - entry.getYear();
        int differenceMonthToDay = monthToDay(departure.getMonth()) - monthToDay(entry.getMonth());
        int differenceDay = departure.getDay() - entry.getDay();
        return differenceYear * 365 + differenceMonthToDay + differenceDay;
    }

    private int monthToDay(int month) {
        if (month < 7) {
            return month * 31;
        } else {
            return 6 * 31 + (month - 6) * 30;
        }
    }

    public int getId() {
        return id;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setPartKind(PartKind partKind) {
        this.partKind = partKind;
    }

    public String getName() {
        return name;
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
/*
enter the age for patient
 */