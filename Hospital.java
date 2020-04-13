package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Scanner;

import static ir.ac.kntu.MyDate.howLong;

public class Hospital {
    private final ArrayList<Doctor> doctors = new ArrayList<>();
    private final ArrayList<Patient> patients = new ArrayList<>();
    private final ArrayList<Room> normalRooms = new ArrayList<>();
    private final ArrayList<Room> emergencyRooms = new ArrayList<>();
    private final ArrayList<Nurse> nurses = new ArrayList<>();
    private final ArrayList<ShiftTimeClass> shiftsTimes = new ArrayList<>();
    private final Room room = new Room();
    private final Doctor tempDoctor = new Doctor();
    Scanner scanner = new Scanner(System.in);
    private String name;

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public ArrayList<Room> getNormalRooms() {
        return normalRooms;
    }

    public ArrayList<Room> getEmergencyRooms() {
        return emergencyRooms;
    }

    public ArrayList<Nurse> getNurses() {
        return nurses;
    }

    public ArrayList<ShiftTimeClass> getShiftsTimes() {
        return shiftsTimes;
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
                    tempDoctor.doctorPatients(this);
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
        System.out.println("1 --> Room have empty beds ");
        System.out.println("2 --> Room have empty beds number");
        System.out.println("3 --> Room were unavailable ");
        System.out.println("4 --> Doctor or nurse in shift ");
        System.out.println("5 --> Doctor or nurse between times");
        System.out.println("6 --> Nurses work for doctor");
        System.out.println("7 --> Nurses care patient ");
        System.out.println("8 --> Back");
        System.out.println("--------------------------");
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
                    tempDoctor.nurseOfDoctor(this);
                    break;
                case 7:
                    nurseOfPatient();
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Wrong input ");
            }
        }
    }

    private void nurseOfPatient() {
        Patient patient = new Patient();
        patient = patient.checkId(this);
        if (patient == null) {
            System.out.println("No patient Registered with this id");
            return;
        }
        for (Nurse nurse : patient.getNurses()) {
            System.out.println("Nurse : " + nurse.getName() + " --> " + nurse.getId());
        }
    }

    private void shiftSearchBetweenTimes() {
        ShiftTimeClass tempShiftTimeClass = new ShiftTimeClass();
        PartKind partKind = room.whichPart();
        System.out.println("First time ");
        Week week1 = tempShiftTimeClass.whichDay();
        ShiftsTime shiftsTime1 = tempShiftTimeClass.chooseShift();
        System.out.println("Second time ");
        Week week2 = tempShiftTimeClass.whichDay();
        ShiftsTime shiftsTime2 = tempShiftTimeClass.chooseShift();
        int first = week1.getNumber() * 10 + shiftsTime1.getNumber();
        int second = week2.getNumber() * 10 + shiftsTime2.getNumber();
        int middle;
        for (ShiftTimeClass shiftTimeClass : shiftsTimes) {
            middle = shiftTimeClass.week.getNumber() * 10 + shiftTimeClass.shiftsTime.getNumber();
            if (shiftTimeClass.partKind == partKind) {
                if (first <= middle && middle <= second) {
                    showShift(shiftTimeClass);
                }
            }
        }
    }

    private void unAvailableRooms() {
        if (room.whichPart() == PartKind.NORMAL) {
            room.unAvailableRoomsHandle(normalRooms);
        } else {
            room.unAvailableRoomsHandle(emergencyRooms);
        }
        MyDate.getPeriod().clear();
    }

    private void shiftSearch() {
        ShiftTimeClass shiftTimeClass = new ShiftTimeClass();
        Week week = shiftTimeClass.whichDay();
        ShiftsTime shiftsTime = shiftTimeClass.chooseShift();
        PartKind partKind = room.whichPart();
        shiftTimeClass = new ShiftTimeClass(week, shiftsTime, partKind, null);
        for (ShiftTimeClass registeredShiftTimeClass : shiftsTimes) {
            if (shiftTimeClass.equals(registeredShiftTimeClass)) {
                showShift(registeredShiftTimeClass);
            }
        }
    }

    private void showShift(ShiftTimeClass shiftTimeClass) {
        System.out.println("--------------------------");
        System.out.print(shiftTimeClass.shiftsTime + " --> " + shiftTimeClass.week + " --> " + shiftTimeClass.partKind + ", DOCTOR : ");
        System.out.println(shiftTimeClass.doctor.getName() + " --> " + shiftTimeClass.doctor.getId());
        for (Nurse nurse : shiftTimeClass.doctor.getNurses()) {
            System.out.print(shiftTimeClass.shiftsTime + " --> " + shiftTimeClass.week + " --> " + shiftTimeClass.partKind);
            System.out.println(", Nurse : " + nurse.getName() + " --> " + nurse.getId());
        }
    }

    private void emptyRoom(boolean valuableNumber) {
        int number = 0;
        MyDate.inputPeriod();
        if (valuableNumber) {
            System.out.print("Enter Empty beds number : ");
            number = scanner.nextInt();
        }
        if (room.whichPart() == PartKind.NORMAL) {
            room.emptyRoomHandle(normalRooms, number);
            return;
        }
        room.emptyRoomHandle(emergencyRooms, number);
    }

    public void printShowMenu() {
        System.out.println("---------- SHOW ----------");
        System.out.println("1 --> Patient");
        System.out.println("2 --> Doctor Patients");
        System.out.println("3 --> Hospital income ");
        System.out.println("4 --> Shifts Workers");
        System.out.println("5 --> Shifts Part");
        System.out.println("6 --> Back");
        System.out.println("-------------------------");
    }

    public void ShiftsOfPart() {
        boolean entered = false;
        PartKind partKind = room.whichPart();
        for (ShiftTimeClass shiftTimeClass : shiftsTimes) {
            if (shiftTimeClass.partKind == partKind) {
                entered = true;
                System.out.println(shiftTimeClass.shiftsTime + " --> " + shiftTimeClass.week);
            }
        }
        if (!entered) {
            System.out.println("No shift registered in part");
        }
    }

    public void shiftOfWorker() {
        int option;
        System.out.println("1 --> Doctor");
        System.out.println("2 --> Nurse");
        option = scanner.nextInt();
        switch (option) {
            case 1:
                Doctor doctor = tempDoctor.findDoctor(this);
                if (doctor != null) {
                    doctor.showDoctorShifts(doctor);
                }
                break;
            case 2:
                Nurse nurse = new Nurse();
                nurse = nurse.findNurse(this);
                if (nurse != null) {
                    nurse.showShifts(nurse);
                }
                break;
            default:
                System.out.println("Wrong input");
        }
    }

    public void income() {
        int totalIncome = 0;
        MyDate.inputPeriod();
        for (Patient patient : patients) {
            if (patient.isDischarge()) {
                if (howLong(MyDate.getPeriod().get(0), patient.getDeparture()) >= 0 && howLong(patient.getDeparture(), MyDate.getPeriod().get(1)) >= 0) {
                    System.out.println(patient.getTotalPrice() + " from " + patient.getId());
                    totalIncome += patient.getTotalPrice();
                }
            }
        }
        MyDate.getPeriod().clear();
        System.out.println("Income of hospital is " + totalIncome);
    }

    public void printPatientMenu() {
        System.out.println("1 --> Patient in Part ");
        System.out.println("2 --> Patient Doctor");
        System.out.println("3 --> Patient Nurse ");
        System.out.println("4 --> Patients Hospitalized Period");
        System.out.println("5 --> Patients In Room");
        System.out.println("6 --> Discharged Patients");
        System.out.println("7 --> Back");
        System.out.println("-----------------------");
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
        for (Patient patient : patients) {
            if (patient.isDischarge()) {
                entered = true;
                showPatientMainProperty(patient);
            }
        }
        if (!entered) {
            System.out.println("No patient is discharged");
        }
        System.out.println(" -------------- ");
    }

    public void hospitalizedPatient() {
        boolean entered = false;
        MyDate.inputPeriod();
        for (Patient patient : patients) {
            if (!patient.isDischarge()) {
                if ((howLong(MyDate.getPeriod().get(0), patient.getEntry()) >= 0 && howLong(patient.getEntry(), MyDate.getPeriod().get(1)) >= 0)) {
                    showPatientMainProperty(patient);
                    entered = true;
                }
            } else {
                if (room.checkBetweenDate(patient.getEntry(), patient.getDeparture())) {
                    entered = true;
                    showPatientMainProperty(patient);
                    MyDate.showDate(patient.getEntry());
                    if (patient.isDischarge()) {
                        System.out.print("\t ---> ");
                        MyDate.showDate(patient.getDeparture());
                        System.out.println();
                    }
                }
            }
        }
        System.out.println(" ------------ ");
        MyDate.getPeriod().clear();
        if (!entered) {
            System.out.println("No Patient in date period");
        }
    }

    public void showPatientMainProperty(Patient patient) {
        System.out.println("Name : " + patient.getName() + "\tId : " + patient.getId());
        System.out.println("Part : " + patient.getPartKind() + "\tRoom : " + patient.getRoom().getRoomNumber());
    }

    public void showPatientRoom(Room room) {
        Room myRoom = room.findRoom(room.whichPart(), this);
        for (Patient patient : myRoom.getPatients()) {
            showPatientMainProperty(patient);
        }
        if (myRoom.getPatients().size() == 0) {
            System.out.println("No patient in room");
        }
        System.out.println("-----------------------");
    }

    public void showPatientDoctor() {
        Patient patient = new Patient();
        patient = patient.checkId(this);
        if (patient == null) {
            System.out.println("No patient Registered with this id");
            return;
        }
        if (patient.getDoctor() != null) {
            patient.getDoctor().showDoctor(patient.getDoctor(), this);
        } else {
            System.out.println("No doctor registered for this patient ");
        }
        System.out.println("-----------------------");
    }

    public void showPatientNurse() {
        Patient patient = new Patient();
        patient = patient.checkId(this);
        if (patient == null) {
            System.out.println("No patient Registered with this id");
            return;
        }
        if (patient.getNurses().size() == 0) {
            System.out.println("No Nurse registered for this patient ");
            return;
        }
        for (Nurse nurse : patient.getNurses()) {
            System.out.println("Nurse : " + nurse.getName() + " --> " + nurse.getId());
        }
        System.out.println(" ----------------------");
    }

    public void showPatientsPart() {
        boolean entered = false;
        PartKind partKind = room.whichPart();
        for (Patient patient : patients) {
            if (patient.getPartKind() == partKind) {
                entered = true;
                showPatientMainProperty(patient);
            }
        }
        if (!entered) {
            System.out.println("No patient in this part ");
        }
        System.out.println(" -------------- ");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}