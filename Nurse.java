package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Scanner;

public class Nurse {
    Scanner scanner = new Scanner(System.in);
    private String name;
    private int id;
    private ArrayList<Doctor> doctors = new ArrayList<>();
    private boolean isPartSource;
    private ArrayList<Patient> patients = new ArrayList<>();
    private ArrayList<ShiftTimeClass> nurseShift = new ArrayList<>();

    public void nurseMenu() {
        int option;
        while (true) {
            printMenu();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    addNurses();
                    break;
                case 2:
                    showNurses();
                    break;
                case 3:
                    change();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Wrong input ");
            }
        }
    }

    private Nurse findNurse() {
        System.out.print("Enter id : ");
        int inputId = scanner.nextInt();
        if (sameId(inputId) == null) {
            System.out.println("Wrong input");
        }
        return sameId(inputId);
    }

    private void change() {
        Nurse nurse = findNurse();
        if (nurse == null) {
            return;
        }
        changeMenu();
        int option = scanner.nextInt();
        switch (option) {
            case 1:
                scanner.nextLine();
                System.out.println("Name : ");
                nurse.name = scanner.nextLine();
                break;
            case 2:
                removeNurse(nurse);
                Hospital.getAllNurses().remove(nurse);
                break;
            case 3:
                ChangeToPartSource(nurse);
                break;
            default:
                break;
        }
    }

    private void ChangeToPartSource(Nurse nurse) {
        removeNurse(nurse);
        nurse.isPartSource = true;
    }

    private void changeMenu() {
        System.out.println("------- CHANGE --------");
        System.out.println("1 --> Change Name");
        System.out.println("2 --> Remove Nurse");
        System.out.println("3 --> Make partSource");
    }

    private void removeNurse(Nurse nurse) {
        for (Doctor doctor : nurse.doctors) {
            doctor.getNurses().remove(nurse);
        }
        for (Patient patient : nurse.patients) {
            patient.getNurses().remove(nurse);
        }
    }

    private void printMenu() {
        System.out.println("---------- NURSE ---------");
        System.out.println("1 --> Add ");
        System.out.println("2 --> Show ");
        System.out.println("3 --> Change ");
        System.out.println("4 -- > Back to previous menu");
        System.out.println("---------------------------");
    }

    public void addNurses() {
        Nurse nurse = new Nurse();
        System.out.println("-------- Add Nurse --------");
        System.out.print("Enter id : ");
        int inputId = scanner.nextInt();
        while (sameId(inputId) != null) {
            System.out.println("we have same id Registered");
            inputId = scanner.nextInt();
        }
        scanner.nextLine();
        nurse.id = inputId;
        System.out.print("Enter Name : ");
        nurse.name = scanner.nextLine();
        System.out.println(nurse.name + " serve in part source ?");
        System.out.print(YesOrNo.YES + " OR " + YesOrNo.NO + " : ");
        String input = scanner.next();
        YesOrNo yesOrNo;
        while (true) {
            try {
                yesOrNo = YesOrNo.valueOf(input);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong input ");
            }
            input = scanner.next();
        }
        nurse.isPartSource = yesOrNo.isInside();
        if (!nurse.isPartSource) {
            chooseNurseDoctor(nurse);
        }
        Hospital.getAllNurses().add(nurse);
    }

    private void showNursesMenu() {
        System.out.println("1 --> Doctors");
        System.out.println("2 --> Patients");
        System.out.println("3 --> Shifts");
        System.out.println("4 --> Back");
    }

    public void showNurses() {
        System.out.println("-------- SHOW NURSE --------");
        Nurse nurse = findNurse();
        if (nurse == null) {
            return;
        }
        System.out.println("ID : " + nurse.id + "\tName : " + nurse.name);
        if (nurse.isPartSource) {
            System.out.println(" Part Source Nurse");
            return;
        }
        int option;
        while (true) {
            showNursesMenu();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    showDoctors(nurse);
                    break;
                case 2:
                    showPatient(nurse);
                    break;
                case 3:
                    showShifts(nurse);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Wrong input");
            }
            System.out.println("--------------------------------");
        }
    }

    private void showShifts(Nurse nurse) {
        if (nurse.nurseShift.size() == 0) {
            System.out.println("No shift is registered");
        }
        System.out.println("------- Shifts --------");
        for (ShiftTimeClass shiftTimeClass : nurse.nurseShift) {
            System.out.println(shiftTimeClass.shiftsTime + " ---> " + shiftTimeClass.week);
        }
    }

    private void showPatient(Nurse nurse) {
        if (nurse.patients.size() == 0) {
            System.out.println("No patient is registered");
            return;
        }
        System.out.println("------- Patients -------");
        for (Patient patient : nurse.patients) {
            System.out.println("ID : " + patient.getId() + "\tName : " + patient.getName());
        }
    }

    private void showDoctors(Nurse nurse) {
        if (nurse.doctors.size() == 0) {
            System.out.println("No doctor is registered");
            return;
        }
        System.out.println("------- Doctors --------");
        for (Doctor doctor : nurse.doctors) {
            System.out.println("ID : " + doctor.getId() + "\tName : " + doctor.getName());
        }
    }

    private void chooseNurseDoctor(Nurse nurse) {
        for (Doctor doctor : Hospital.getDoctors()) {
            if (doctors.size() == 2) {
                break;
            }
            if (doctor.getNurses().size() < 2) {
                nurse.doctors.add(doctor);
                nurse.nurseShift.addAll(doctor.getDoctorShift());
                nurse.patients.addAll(doctor.getPatients());
                doctor.getNurses().add(nurse);
                for (Patient patient : doctor.getPatients()) {
                    patient.getNurses().add(nurse);
                }
            }
        }
    }

    public Nurse sameId(int inputId) {
        for (Nurse nurse : Hospital.getAllNurses()) {
            if (inputId == nurse.id) {
                return nurse;
            }
        }
        return null;
    }

    public boolean isPartSource() {
        return isPartSource;
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public ArrayList<ShiftTimeClass> getNurseShift() {
        return nurseShift;
    }

    public void setNurseShift(ArrayList<ShiftTimeClass> nurseShift) {
        this.nurseShift = nurseShift;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }
}