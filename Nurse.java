package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Scanner;

public class Nurse {
    Scanner scanner = new Scanner(System.in);
    private String name;
    private int id;
    private ArrayList<Doctor> doctors;
    private boolean isPartSource;
    private WeaklyShifts weaklyShifts = new WeaklyShifts();

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
        System.out.println("ID : " + nurse.id + " Name : " + nurse.name);
        if (nurse.isPartSource) {
            System.out.println(name + " work in part source ");
        } else {
            if (nurse.doctors.get(0) != null) {
                System.out.println("The doctor of " + nurse.name);
                System.out.println(nurse.doctors.get(0).getId());
            }
            if (nurse.doctors.get(1) != null) {
                System.out.println("The doctor of " + nurse.name);
                System.out.println(nurse.doctors.get(0).getId());
            }
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