package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Scanner;

public class Nurse {
    Scanner scanner = new Scanner(System.in);
    private String name;
    private int id;
    private ArrayList<Doctor> doctors = new ArrayList<>();
    private boolean isPartSource;
    private ArrayList<ShiftTimeClass> nurseShift = new ArrayList<>();

    public void NurseMenu(Hospital hospital) {
        int option;
        while (true) {
            printMenu();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    addNurses(hospital);
                    break;
                case 2:
                    showNurses(hospital);
                    break;
                case 3:
                    showNurseShifts(hospital);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Wrong input ");
                    break;
            }
        }
    }

    private void printMenu() {
        System.out.println("---------- NURSE ---------");
        System.out.println("1 --> add nurse");
        System.out.println("2 --> show nurse");
        System.out.println("3 --> show nurse shifts");
        System.out.println("4 -- > Back to previous menu");
        System.out.println("---------------------------");
    }

    public void addNurses(Hospital hospital) {
        System.out.println("-------- Add Nurse --------");
        System.out.println("Enter id for new Nurse");
        int inputId = scanner.nextInt();
        while (sameId(hospital, inputId) != null) {
            System.out.println("we have same id saved");
            inputId = scanner.nextInt();
        }
        id = inputId;
        System.out.print("Enter Name : ");
        scanner.nextLine();
        name = scanner.nextLine();
        System.out.println(name + " serve in part source ?");
        System.out.print(YesOrNo.YES + " OR " + YesOrNo.NO + "  ");
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
        isPartSource = yesOrNo.isInside();
        if (!isPartSource) {
            chooseNurseDoctor(hospital);
        }
        hospital.getAllNurses().add(this);
    }

    public void showNurses(Hospital hospital) {
        Nurse nurse;
        System.out.println("-------- SHOW NURSE --------");
        System.out.print("Enter the id : ");
        int inputId = scanner.nextInt();
        while (sameId(hospital, inputId) == null && inputId != 0) {
            System.out.println("Either enter the write id or 0");
            inputId = scanner.nextInt();
        }
        nurse = sameId(hospital, inputId);
        System.out.println("ID : " + nurse.id + "       Name : " + nurse.name);
        if (nurse.isPartSource) {
            System.out.println(name + " work in part source ");
        } else {
            for (Doctor doctor : nurse.doctors) {
                System.out.println("The doctor of " + nurse.name + " is");
                System.out.println(doctor.getName() + "   " + doctor.getId());
            }
        }
    }

    public void showNurseShifts(Hospital hospital) {
        System.out.println("Enter id for Nurse");
        int inputId = scanner.nextInt();
        while (sameId(hospital, inputId) == null) {
            System.out.println("we cant find someone with this id");
            inputId = scanner.nextInt();
        }
        Nurse nurse = sameId(hospital, inputId);
        if (nurse.nurseShift.size() != 0) {
            for (ShiftTimeClass shiftTimeClass : nurse.nurseShift) {
                System.out.println(shiftTimeClass.shiftsTime + " OF " + shiftTimeClass.week);
            }
        } else {
            System.out.println("no shift is added for this nurse");
        }
    }

    private void chooseNurseDoctor(Hospital hospital) {
        if (hospital.getDoctors().size() > 0) {
            for (Doctor doctor : hospital.getDoctors()) {
                if (doctor.getNurses().size() < 2) {
                    doctors.add(doctor);
                    this.nurseShift.addAll(doctor.getDoctorShift());
                }
                if (doctors.size() == 2) {
                    break;
                }
            }

        } else {
            System.out.println("There is no doctor to add");
        }
    }

    public Nurse sameId(Hospital hospital, int inputId) {
        for (Nurse nurse : hospital.getAllNurses()) {
            if (inputId == nurse.id) {
                return nurse;
            }
        }
        return null;
    }

    private enum YesOrNo {
        YES(true), NO(false);
        public boolean inside;

        YesOrNo(boolean inside) {
            this.inside = inside;
        }

        public boolean isInside() {
            return inside;
        }
    }
}