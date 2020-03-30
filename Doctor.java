package ir.ac.kntu;


import java.util.*;

public class Doctor {
    Scanner scanner = new Scanner(System.in);
    private String name;
    private int PatientNumber;
    private int id;
    private Nurse nurseOne;
    private Nurse nurseTwo;
    //    private ArrayList<Shifts> shifts;
    private Map<String, Set> shifts = new HashMap<>();

    public void addDoctor(Hospital hospital) {
        Doctor tempDoctor = new Doctor();
        System.out.println("Enter the name and id");
        tempDoctor.name = scanner.next();
        tempDoctor.id = scanner.nextInt();
        hospital.setDoctors(tempDoctor);
    }

    public void showDoctor(Scanner scanner, Hospital hospital, WeaklyShifts weaklyShifts) {
        System.out.println("Enter the id");
            System.out.println("For Quite press 0 ");
            int inputId = scanner.nextInt();
            if (inputId == 0) {
                return;
            }
            if (findDoctor(hospital, inputId) != null) {
                doctorInfo(findDoctor(hospital, inputId), weaklyShifts);
            } else {
                System.out.println("wrong Id");
            }
    }

    public void doctorInfo(Doctor doctor, WeaklyShifts weaklyShifts) {
        System.out.println("Name : " + doctor.getName() + "   Id : " + doctor.getId());
        if(doctor.shifts.size()!=0) {
            System.out.println("shifts --->" + doctor.shifts.keySet() + " of " + doctor.shifts.entrySet());
        }else {
            System.out.println("No shift is added .");
        }
    }

    public void addShift(Scanner scanner, Hospital hospital, WeaklyShifts weaklyShifts) {
        int choose = 0, inputId;
        ArrayList<Map> chosenDay;
//        Map<String, Integer> temp = new HashMap<String, Integer>();
        System.out.println("Enter the doctor ID");
        inputId = scanner.nextInt();
        if (findDoctor(hospital, inputId) != null) {
            System.out.println("Pick a day ");
            while (true) {
                chosenDay = whichDay(choose, weaklyShifts);
                if (chosenDay.size() != 3) {
                    break;
                }
                System.out.println("This day shifts are all taken");
                System.out.println("Pick another day");
            }
            handleSHift(choose,inputId,chosenDay,hospital);
//            System.out.println("--------- pick a shift ---------");
//            System.out.println("1 -> Morning");
//            System.out.println("2 -> AfterNoon");
//            System.out.println("3 -> Night");
//            choose = scanner.nextInt();
//            switch (choose) {
//                case 1:
//                    temp.put("Morning", inputId);
//                    chosenDay.add(temp);
//                    findDoctor(hospital, inputId).shifts.put("Morning", chosenDay.get(0).keySet());
//                    break;
//                case 2:
//                    temp.put("AfterNoon", inputId);
//                    chosenDay.add(temp);
//                    break;
//                case 3:
//                    temp.put("Night", inputId);
//                    chosenDay.add(temp);
//                    break;
//                default:
//                    break;
//            }
        } else {
            System.out.println("can't find this ID");
        }
        System.out.println("--------------------------------------");
    }
    private void handleSHift(int choose,int inputId,ArrayList<Map> chosenDay,Hospital hospital){
        Map<String, Integer> temp = new HashMap<String, Integer>();
        System.out.println("--------- pick a shift ---------");
            System.out.println("1 -> Morning");
            System.out.println("2 -> AfterNoon");
            System.out.println("3 -> Night");
            choose = scanner.nextInt();
            switch (choose) {
                case 1:
                    temp.put("Morning", inputId);
                    chosenDay.add(temp);
                    findDoctor(hospital, inputId).shifts.put("Morning", chosenDay.get(0).keySet());
                    break;
                case 2:
                    temp.put("AfterNoon", inputId);
                    chosenDay.add(temp);
                    break;
                case 3:
                    temp.put("Night", inputId);
                    chosenDay.add(temp);
                    break;
                default:
                    break;
            }
    }
    private Doctor findDoctor(Hospital hospital, int inputId) {
        for (int i = 0; i < hospital.getDoctors().size(); i++) {
            System.out.println(hospital.getDoctors().size());
            System.out.println(hospital.getDoctors().get(i).getId());
            if (inputId == hospital.getDoctors().get(i).id) {
                return hospital.getDoctors().get(i);
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    private ArrayList<Map> whichDay(int choose, WeaklyShifts weaklyShifts) {
        System.out.println("1 --> Saturday");
        System.out.println("2 --> Sunday");
        System.out.println("3 --> Monday");
        System.out.println("4 --> Tuesday");
        System.out.println("5 --> Wednesday");
        System.out.println("6 --> Thursday");
        System.out.println("7 --> Friday");
        choose = scanner.nextInt();
        while (true) {
            switch (choose) {
                case 1:
                    return weaklyShifts.getSaturdayShift();
                case 2:
                    return weaklyShifts.getSundayShift();
                case 3:
                    return weaklyShifts.getMondayShift();
                case 4:
                    return weaklyShifts.getTuesdayShift();
                case 5:
                    return weaklyShifts.getWednesdayShift();
                case 6:
                    return weaklyShifts.getThursdayShift();
                case 7:
                    return weaklyShifts.getFridayShift();
                default:
                    System.out.println("Wrong input");
            }
        }
    }
}