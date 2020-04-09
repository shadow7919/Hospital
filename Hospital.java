package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Scanner;

public class Hospital {
    private static final ArrayList<Doctor> doctors = new ArrayList<>();
    private static final ArrayList<Patient> patients = new ArrayList<>();
    private static final ArrayList<Room> normalRooms = new ArrayList<>();
    private static final ArrayList<Room> emergencyRooms = new ArrayList<>();
    private static final ArrayList<Nurse> nurses = new ArrayList<>();
    private static final ArrayList<ShiftTimeClass> shiftsTimes = new ArrayList<>();
    private final ArrayList<MyDate> period = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    private String name;

    public static ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public static ArrayList<ShiftTimeClass> getShiftsTimes() {
        return shiftsTimes;
    }

    public static ArrayList<Patient> getPatients() {
        return patients;
    }

    public static ArrayList<Room> getNormalRooms() {
        return normalRooms;
    }

    public static ArrayList<Room> getEmergencyRooms() {
        return emergencyRooms;
    }

    public static ArrayList<Nurse> getNurses() {
        return nurses;
    }

    public void showMenu() {
        int option;
        while (true) {
            printShowMenu();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    patientShow();
                    break;
                case 2:
                    doctorPatients();
                    break;
                case 3:
                    income();
                    break;
                case 4:
                    shiftOfWorker();
                    break;
                case 5:
                    ShiftsOfPart();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Wrong input ");
            }
        }
    }

    private void printSearchMenu() {
        System.out.println("---------- SEARCH ----------");
        System.out.println("1 --> Room can get patient ");
        System.out.println("2 --> Room with empty beds number");
        System.out.println("3 --> Room were unavailable ");
        System.out.println("4 --> Doctor or nurse in shift ");
        System.out.println("5 --> Doctor or nurse between times");
        System.out.println("6 --> Nurses work for doctor");
        System.out.println("7 --> Nurses care patient ");
        System.out.println("8 --> Back");
    }

    public void searchMenu() {
        int option;
        while (true) {
            printSearchMenu();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    emptyRoom(false);
                    break;
                case 2:
                    emptyRoom(true);
                    break;
                case 3:
                    unAvailableRooms();
                    break;
                case 4:
                    shiftSearch();
                    break;
                case 5:
                    shiftSearchBetweenTimes();
                    break;
                case 6:
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Wrong input ");
            }
        }
    }

    private void shiftSearchBetweenTimes() {
        Doctor doctor = new Doctor();
        PartKind partKind = Room.whichPart();
        System.out.println("First time ");
        Week week1 = doctor.whichDay();
        ShiftsTime shiftsTime1 = doctor.chooseShift();
        System.out.println("Second time ");
        Week week2 = doctor.whichDay();
        ShiftsTime shiftsTime2 = doctor.chooseShift();
        ShiftTimeClass shiftTimeClass1 = new ShiftTimeClass(week1, shiftsTime1, partKind, doctor);
        ShiftTimeClass shiftTimeClass2 = new ShiftTimeClass(week2, shiftsTime2, partKind, doctor);
    }

    private int makeShiftToNumber(ShiftTimeClass shiftTimeClass) {
        int number;
        number = shiftTimeClass.week.getNumber();
        number = number * 10 + shiftTimeClass.shiftsTime.getNumber();
        return number;
    }

    private void unAvailableRooms() {
        PartKind partKind = Room.whichPart();
        inputPeriod();
        if (partKind == PartKind.NORMAL) {
            unAvailableRoomsHandle(Hospital.getNormalRooms());
        } else {
            unAvailableRoomsHandle(Hospital.getEmergencyRooms());
        }
        period.clear();
    }

    private void unAvailableRoomsHandle(ArrayList<Room> rooms) {
        for (Room room : rooms) {
            if (room.getUnAvailableStart().getYear() != 0) {
                if (MyDate.howLong(room.getUnAvailableFinsh(), period.get(1)) >= 0 && MyDate.howLong(period.get(0), room.getUnAvailableStart()) >= 0) {
                    System.out.println("Number : " + room.getRoomNumber() + "\tPart : " + PartKind.NORMAL);

                }
            }
        }
    }

    private void shiftSearch() {
        Doctor doctor = new Doctor();
        Week week = doctor.whichDay();
        ShiftsTime shiftsTime = doctor.chooseShift();
        PartKind partKind = Room.whichPart();
        ShiftTimeClass shiftTimeClass = new ShiftTimeClass(week, shiftsTime, partKind, doctor);
        for (ShiftTimeClass registeredShiftTimeClass : Hospital.getShiftsTimes()) {
            if (shiftTimeClass.equals(registeredShiftTimeClass)) {
                System.out.print(shiftTimeClass.shiftsTime + " --> " + shiftTimeClass.week + " --> " + shiftTimeClass.partKind + " DOCTOR : ");
                System.out.println(registeredShiftTimeClass.doctor.getName() + " --> " + registeredShiftTimeClass.doctor.getId());
                for (Nurse nurse : registeredShiftTimeClass.doctor.getNurses()) {
                    System.out.println("----- Nurses ----- ");
                    System.out.println(nurse.getName() + " --> " + nurse.getId());
                    System.out.println("--------------------");
                }
            }
        }
    }

    private void emptyRoom(boolean valuableNumber) {
        int number = 0;
        PartKind partKind = Room.whichPart();
        inputPeriod();
        if (valuableNumber) {
            System.out.print("Enter Empty beds number : ");
            number = scanner.nextInt();
        }
        if (partKind == PartKind.NORMAL) {
            emptyRoomHandle(valuableNumber, Hospital.getNormalRooms(), number);
        } else {
            emptyRoomHandle(valuableNumber, Hospital.getEmergencyRooms(), number);
        }
        period.clear();
    }

    private void emptyRoomHandle(boolean valuableNumber, ArrayList<Room> rooms, int number) {
        int patientInRoom;
        for (Room room : rooms) {
            patientInRoom = 0;
            for (Patient patient : room.getPatients()) {
                if (MyDate.howLong(period.get(0), patient.getEntry()) >= 0 && MyDate.howLong(patient.getEntry(), period.get(1)) >= 0) {
                    patientInRoom++;
                }
            }
            if (valuableNumber) {
                if (room.getBedsNumber() - patientInRoom == number) {
                    System.out.println("Number : " + room.getRoomNumber() + "\t Empty beds : " + number);
                }
            } else {
                if (room.getBedsNumber() - patientInRoom > number) {
                    System.out.println("Number : " + room.getRoomNumber() + "\t Empty beds : " + (room.getBedsNumber() - patientInRoom));
                }
            }
        }
    }

    public void printShowMenu() {
        System.out.println("---------- SHOW ----------");
        System.out.println("1 --> Patient");
        System.out.println("2 --> Doctor Patients");
        System.out.println("3 --> Hospital income ");
        System.out.println("4 --> Shifts Workers");
        System.out.println("5 --> Shifts Part");
        System.out.println("6 --> Back");
    }

    public void ShiftsOfPart() {
        boolean entered = false;
        PartKind partKind = Room.whichPart();
        for (ShiftTimeClass shiftTimeClass : Hospital.getShiftsTimes()) {
            if (shiftTimeClass.partKind == partKind) {
                entered = true;
                System.out.println(shiftTimeClass.shiftsTime + " --> " + shiftTimeClass.week);
            }
        }
        if (Hospital.getShiftsTimes().size() == 0) {
            System.out.println("No shift registered");
            return;
        }
        if (!entered) {
            System.out.println("No shift registered in part");
        }
    }

    public void shiftOfWorker() {
        int option;
        while (true) {
            System.out.println("1 --> Doctor");
            System.out.println("2 --> Nurse");
            System.out.println("3 --> Back");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    Doctor doctor = Doctor.findDoctor();
                    if (doctor != null) {
                        doctor.showDoctorShifts(doctor);
                    }
                    break;
                case 2:
                    Nurse nurse = new Nurse();
                    nurse = nurse.findNurse();
                    if (nurse != null) {
                        nurse.showShifts(nurse);
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    public void income() {
        int totalIncome = 0;
        inputPeriod();
        for (Patient patient : Hospital.getPatients()) {
            if (MyDate.howLong(period.get(0), patient.getEntry()) >= 0 && MyDate.howLong(patient.getEntry(), period.get(1)) >= 0) {
                System.out.println(patient.getTotalPrice() + " from " + patient.getId());
                totalIncome += patient.getTotalPrice();
            }
        }
        period.clear();
        if (totalIncome == 0) {
            System.out.println("No income yet");
        } else {
            System.out.println(totalIncome);
        }
    }

    public void doctorPatients() {
        boolean entered = false;
        System.out.println("Enter id ");
        Doctor doctor = Doctor.findDoctor();
        if (doctor != null) {
            inputPeriod();
            for (Patient patient : doctor.getPatients()) {
                if (MyDate.howLong(period.get(0), patient.getEntry()) >= 0 && MyDate.howLong(patient.getEntry(), period.get(1)) >= 0) {
                    showPatientMainProperty(patient);
                    entered = true;
                }
            }
            period.clear();
            if (!entered) {
                System.out.println("No patient in period");
            }
        }
    }

    public void printPatientMenu() {
        System.out.println("-------  Patients -------");
        System.out.println("1 --> Patient in Part ");
        System.out.println("2 --> Patient Doctor");
        System.out.println("3 --> Patient Nurse ");
        System.out.println("4 --> Patients Hospitalized Period");
        System.out.println("5 --> Patients In Room");
        System.out.println("6 --> Discharged Patients");
        System.out.println("7 --> Back");
    }

    public void patientShow() {
        Room room = new Room();
        int option;
        while (true) {
            printPatientMenu();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    showPatientsPart();
                    break;
                case 2:
                    showPatientDoctor();
                    break;
                case 3:
                    showPatientNurse();
                    break;
                case 4:
                    hospitalizedPatient();
                    break;
                case 5:
                    showPatientRoom(room);
                    break;
                case 6:
                    showDischargedPatients();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    public void showDischargedPatients() {
        boolean entered = false;
        for (Patient patient : Hospital.getPatients()) {
            if (patient.isDischarge()) {
                entered = true;
                showPatientMainProperty(patient);
            }
        }
        if (Hospital.getPatients().size() == 0) {
            System.out.println("No Patient is registered");
            return;
        }
        if (!entered) {
            System.out.println("No patient is discharged");
        }
    }

    public void inputPeriod() {
        while (true) {
            System.out.println("Enter first period : ");
            period.add(MyDate.dateSet(false));
            System.out.println("Enter second period : ");
            period.add(MyDate.dateSet(false));
            if (MyDate.howLong(period.get(0), period.get(1)) > 0) {
                break;
            }
            System.out.println("wrong Dates");
        }
    }

    public void hospitalizedPatient() {
        boolean entered = false;
        inputPeriod();
        for (Patient patient : patients) {
            if (MyDate.howLong(period.get(0), patient.getEntry()) >= 0 && MyDate.howLong(patient.getEntry(), period.get(1)) >= 0) {
                entered = true;
                showPatientMainProperty(patient);
                System.out.print("From : ");
                MyDate.showDate(patient.getEntry());
                if (patient.isDischarge()) {
                    System.out.println("To :");
                    MyDate.showDate(patient.getDeparture());
                }
            }
        }
        period.clear();
        if (!entered) {
            System.out.println("No Patient in period");
        }
    }

    public void showPatientMainProperty(Patient patient) {
        System.out.println("Name : " + patient.getName() + "\tId : " + patient.getId());
        System.out.println("Part : " + patient.getPartKind() + "\tRoom : " + patient.getRoom().getRoomNumber());
    }

    public void showPatientRoom(Room room) {
        PartKind partKind = Room.whichPart();
        Room myRoom = room.findRoom(partKind);
        for (Patient patient : myRoom.getPatients()) {
            showPatientMainProperty(patient);
        }
        if (myRoom.getPatients().size() == 0) {
            System.out.println("No patient in room");
        }
    }

    public void showPatientDoctor() {
        Patient patient = new Patient();
        patient = patient.checkId();
        if (patient == null) {
            System.out.println("No patient Registered with this id");
            return;
        }
        if (patient.getDoctor() != null) {
            patient.getDoctor().showDoctor(patient.getDoctor());
        } else {
            System.out.println("No doctor registered for this patient ");
        }
    }

    public void showPatientNurse() {
        Patient patient = new Patient();
        patient = patient.checkId();
        if (patient == null) {
            System.out.println("No patient Registered with this id");
            return;
        }
        if (patient.getNurses().size() == 0) {
            System.out.println("No Nurse registered for this patient ");
            return;
        }
        for (Nurse nurse : patient.getNurses()) {
            System.out.println("ID : " + nurse.getId() + "Name " + nurse.getName());
        }
    }

    public void showPatientsPart() {
        boolean entered = false;
        PartKind partKind = Room.whichPart();
        for (Patient patient : Hospital.getPatients()) {
            if (patient.getPartKind() == partKind) {
                entered = true;
                showPatientMainProperty(patient);
            }
        }
        if (!entered) {
            System.out.println("No patient in this part ");
        }
    }

    public void setName(String name) {
        this.name = name;
    }
}