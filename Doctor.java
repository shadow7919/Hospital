package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Scanner;

public class Doctor extends Person {
    private final static int MAX_NURSES = 2;
    private final static int MAX_SHIFTS = 3;
    private final ArrayList<Nurse> nurses = new ArrayList<>();
    private final ArrayList<ShiftTimeClass> doctorShift = new ArrayList<>();
    private final ArrayList<Patient> patients = new ArrayList<>();
    private final ArrayList<Patient> dischargedPatients = new ArrayList<>();
    private final Room room = new Room();
    Scanner scanner = new Scanner(System.in);
    private Week daysShift;


    public Doctor findDoctor(Hospital hospital) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter id : ");
        int inputId = scanner.nextInt();
        for (Doctor doctor : hospital.getDoctors()) {
            if (inputId == doctor.getId()) {
                return doctor;
            }
        }
        System.out.println("Can't find ");
        return null;
    }

    private void showMenu() {
        System.out.println("----------- Doctor Menu -----------");
        System.out.println("1 --> Add doctor");
        System.out.println("2 --> Choose doctor's shift");
        System.out.println("3 --> Auto shift");
        System.out.println("4 --> Doctor information");
        System.out.println("5 --> Change information");
        System.out.println("6 --> Back ");
        System.out.println("-----------------------");
    }

    public void doctorMenu(Hospital hospital) {
        int option;
        while (true) {
            showMenu();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    addDoctor(hospital);
                    break;
                case 2:
                    addShift(hospital);
                    break;
                case 3:
                    autoShift(hospital);
                    break;
                case 4:
                    showDoctor(null, hospital);
                    break;
                case 5:
                    change(hospital);
                    break;
                case 6:
                    scanner.nextLine();
                    return;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    public void change(Hospital hospital) {
        Doctor doctor;
        doctor = findDoctor(hospital);
        if (doctor != null) {
            System.out.println("------- CHANGE --------");
            System.out.println("1 --> Change Name");
            System.out.println("2 --> remove Shift ");
            System.out.println("3 --> remove Doctor");
            System.out.println(" ---------------------");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    scanner.nextLine();
                    System.out.print("Enter name : ");
                    doctor.setName(scanner.nextLine());
                    break;
                case 2:
                    removeDoctorShift(doctor, hospital);
                    break;
                case 3:
                    remove(doctor, hospital);
                    break;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    public void autoShift(Hospital hospital) {
        PartKind partKind = room.whichPart();
        ShiftsTime[] shiftsTimes = ShiftsTime.values();
        Week[] weeks = Week.values();
        int first = 0, second = 0;
        for (Doctor doctor : hospital.getDoctors()) {
            for (int i = doctor.doctorShift.size(); i < MAX_SHIFTS; i++) {
                ShiftTimeClass shiftTimeClass = new ShiftTimeClass(weeks[first], shiftsTimes[second], partKind, doctor);
                addShiftsToEveryWhere(doctor, shiftTimeClass, hospital);
                first++;
                if (first == 7) {
                    second++;
                    first = 1;
                }
                if (second == 3) {
                    System.out.println("No more shift can add ");
                    return;
                }
            }
        }
    }

    public void removeDoctorShift(Doctor doctor, Hospital hospital) {
        showDoctorShifts(doctor);
        ShiftTimeClass chosenShiftTimeClass = new ShiftTimeClass();
        PartKind partKind = room.whichPart();
        Week week = chosenShiftTimeClass.whichDay();
        ShiftsTime shiftsTime = chosenShiftTimeClass.chooseShift();
        chosenShiftTimeClass = new ShiftTimeClass(week, shiftsTime, partKind, doctor);
        for (ShiftTimeClass shiftTimeClass : doctor.doctorShift) {
            if (chosenShiftTimeClass.equals(shiftTimeClass) && chosenShiftTimeClass.doctor.getId() == shiftTimeClass.doctor.getId()) {
                doctor.doctorShift.remove(shiftTimeClass);
                for (Nurse nurse : doctor.getNurses()) {
                    nurse.getNurseShift().remove(shiftTimeClass);
                }
                hospital.getShiftsTimes().remove(shiftTimeClass);
                return;
            }
        }
    }

    public void doctorPatients(Hospital hospital) {
        boolean entered = false;
        Doctor doctor = findDoctor(hospital);
        if (doctor != null) {
            MyDate.inputPeriod();
            for (Patient patient : doctor.patients) {
                if (MyDate.howLong(MyDate.getPeriod().get(0), patient.getEntry()) >= 0 && MyDate.howLong(patient.getEntry(), MyDate.getPeriod().get(1)) >= 0) {
                    hospital.showPatientMainProperty(patient);
                    entered = true;
                }
            }
            for (Patient patient : doctor.dischargedPatients) {
                if (MyDate.howLong(MyDate.getPeriod().get(0), patient.getEntry()) >= 0 && MyDate.howLong(patient.getEntry(), MyDate.getPeriod().get(1)) >= 0) {
                    hospital.showPatientMainProperty(patient);
                    entered = true;
                }
            }
            MyDate.getPeriod().clear();
            if (!entered) {
                System.out.println("No patient in that period ");
            }
        }
    }

    public void nurseOfDoctor(Hospital hospital) {
        Doctor doctor = findDoctor(hospital);
        if (doctor == null) {
            return;
        }
        for (Nurse nurse : doctor.getNurses()) {
            System.out.println("Nurse : " + nurse.getName() + " --> " + nurse.getId());
        }
        System.out.println(" ---------------------");
    }

    public boolean sameId(int inputId, Hospital hospital) {
        for (Doctor doctor : hospital.getDoctors()) {
            if (doctor.getId() == inputId) {
                return true;
            }
        }
        return false;
    }

    public void addDoctor(Hospital hospital) {
        System.out.print("Enter id : ");
        int inputId = scanner.nextInt();
        while (sameId(inputId, hospital)) {
            System.out.println("Same id registered");
            inputId = scanner.nextInt();
        }
        scanner.nextLine();
        Doctor doctor = new Doctor();
        doctor.setId(inputId);
        System.out.print("Enter name : ");
        doctor.setName(scanner.nextLine());
        doctor.addNurse(doctor, hospital);
        doctor.pickPatient(doctor, hospital);
        hospital.getDoctors().add(doctor);
    }

    private void pickPatient(Doctor doctor, Hospital hospital) {
        for (Patient myPatient : hospital.getPatients()) {
            if (myPatient.getDoctor() == null) {
                myPatient.setDoctor(doctor);
                doctor.patients.add(myPatient);
                return;
            }
        }
    }

    private void addNurse(Doctor doctor, Hospital hospital) {
        for (Nurse myNurse : hospital.getNurses()) {
            if (doctor.nurses.size() == MAX_NURSES) {
                break;
            }
            if (!myNurse.isPartSource() && myNurse.getDoctors().size() < MAX_NURSES) {
                doctor.nurses.add(myNurse);
                myNurse.getDoctors().add(doctor);
            }
        }
    }

    public void remove(Doctor doctor, Hospital hospital) {
        hospital.getDoctors().remove(doctor);
        for (Nurse nurse : doctor.nurses) {
            nurse.getDoctors().remove(doctor);
        }
        for (ShiftTimeClass shiftTimeClass : doctor.doctorShift) {
            for (Nurse nurse : doctor.getNurses()) {
                nurse.getNurseShift().remove(shiftTimeClass);
            }
        }
        for (Patient patient : doctor.patients) {
            patient.setDoctor(null);
        }
    }

    public void showDoctor(Doctor doctor, Hospital hospital) {
        if (doctor == null) {
            doctor = findDoctor(hospital);
        }
        if (doctor != null) {
            System.out.println("Name : " + doctor.getName() + "   Id : " + doctor.getId());
            doctorInfo(doctor);
        }
    }

    private void doctorInfo(Doctor doctor) {
        while (true) {
            System.out.println("1 --> Nurses");
            System.out.println("2 --> Patients");
            System.out.println("3 --> Shifts");
            System.out.println("4 --> Back");
            System.out.println("----------------");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    showDoctorNurses(doctor);
                    break;
                case 2:
                    showDoctorPatients(doctor);
                    break;
                case 3:
                    showDoctorShifts(doctor);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    public void showDoctorPatients(Doctor doctor) {
        if (doctor.patients.size() == 0) {
            System.out.println("No patient is registered");
            return;
        }
        System.out.println("------- Patient -------");
        for (Patient patient : doctor.patients) {
            System.out.println("ID : " + patient.getId() + "\tName : " + patient.getName());
        }
        System.out.println(" ---------------------");
    }

    private void showDoctorNurses(Doctor doctor) {
        if (doctor.nurses.size() == 0) {
            System.out.println("No nurse is registered");
            return;
        }
        System.out.println("------- Nurse -------");
        for (Nurse nurse : doctor.nurses) {
            System.out.println("ID : " + nurse.getId() + "\tName : " + nurse.getName());
        }
        System.out.println(" ---------------------");
    }

    public void showDoctorShifts(Doctor doctor) {
        if (doctor.doctorShift.size() == 0) {
            System.out.println("No shift is added ");
            return;
        }
        PartKind partKind = room.whichPart();
        for (ShiftTimeClass shift : doctor.doctorShift) {
            if (shift.partKind.equals(partKind) && shift.doctor.getId() == doctor.getId()) {
                System.out.println(shift.shiftsTime + " --> " + shift.week);
            }
        }
        System.out.println(" ----------------- ");
    }

    public void addShift(Hospital hospital) {
        ShiftTimeClass shiftTimeClass = new ShiftTimeClass();
        Doctor doctor = findDoctor(hospital);
        PartKind partKind;
        if (doctor != null) {
            partKind = room.whichPart();
            if (checkDoctorShiftsNumber(doctor)) {
                System.out.println("Pick a day ");
                doctor.daysShift = shiftTimeClass.whichDay();
                handleShift(doctor, partKind, hospital);
            }
        }
    }

    private boolean checkDoctorShiftsNumber(Doctor doctor) {
        if (doctor.doctorShift.size() == MAX_SHIFTS) {
            System.out.println(doctor.getName() + " already have " + MAX_SHIFTS + " shifts");
            return false;
        } else {
            return true;
        }
    }

    private void addShiftsToEveryWhere(Doctor doctor, ShiftTimeClass shiftTimeClass, Hospital hospital) {
        hospital.getShiftsTimes().add(shiftTimeClass);
        doctor.doctorShift.add(shiftTimeClass);
        for (Nurse nurse : doctor.getNurses()) {
            nurse.getNurseShift().add(shiftTimeClass);
        }
    }

    private void handleShift(Doctor doctor, PartKind partKind, Hospital hospital) {
        ShiftTimeClass shiftTimeClass = new ShiftTimeClass();
        shiftTimeClass.showShift();
        int choose = scanner.nextInt();
        switch (choose) {
            case 1:
                shiftTimeClass = new ShiftTimeClass(doctor.daysShift, ShiftsTime.MORNING, partKind, doctor);
                if (isNotTaken(shiftTimeClass, hospital)) {
                    addShiftsToEveryWhere(doctor, shiftTimeClass, hospital);
                } else {
                    System.out.println("This shift is already taken ");
                }
                break;
            case 2:
                shiftTimeClass = new ShiftTimeClass(doctor.daysShift, ShiftsTime.AFTER_NOON, partKind, doctor);
                if (isNotTaken(shiftTimeClass, hospital)) {
                    addShiftsToEveryWhere(doctor, shiftTimeClass, hospital);
                } else {
                    System.out.println("This shift is already taken");
                }
                break;
            case 3:
                shiftTimeClass = new ShiftTimeClass(doctor.daysShift, ShiftsTime.NIGHT, partKind, doctor);
                if (isNotTaken(shiftTimeClass, hospital)) {
                    addShiftsToEveryWhere(doctor, shiftTimeClass, hospital);
                } else {
                    System.out.println("This shift is already taken ");
                }
                break;
            default:
                System.out.println("Wrong input ");
        }
    }

    private boolean isNotTaken(ShiftTimeClass shiftTimeClass, Hospital hospital) {
        for (ShiftTimeClass registeredTimeClass : hospital.getShiftsTimes()) {
            if (shiftTimeClass.equals(registeredTimeClass) && registeredTimeClass.doctor.getId() == shiftTimeClass.doctor.getId()) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Patient> getDischargedPatients() {
        return dischargedPatients;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public ArrayList<ShiftTimeClass> getDoctorShift() {
        return doctorShift;
    }

    public ArrayList<Nurse> getNurses() {
        return nurses;
    }
}