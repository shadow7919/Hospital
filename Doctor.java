package ir.ac.kntu;

import javax.print.Doc;
import java.util.*;

public class Doctor {
    Scanner scanner = new Scanner(System.in);
    private String name;
    private int PatientNumber;
    private int id;
    private Nurse nurseOne;
    private Nurse nurseTwo;
    private Map<String, String> shifts = new HashMap<>();
    private Week daysShift;

    public void addDoctor(Hospital hospital) {
        Doctor tempDoctor = new Doctor();
        System.out.println("Enter the name and id");
        tempDoctor.name = scanner.next();
        tempDoctor.id = scanner.nextInt();
        if(hospital.sameId(tempDoctor)){
            System.out.println("We have the same Id added");
            return;
        }
        hospital.setDoctors(tempDoctor);
    }
    public void remove(Hospital hospital){
        System.out.println("Enter the id");
        int inputId = scanner.nextInt();
        for (int i = 0; i < hospital.getDoctors().size(); i++) {
            if(inputId == hospital.getDoctors().get(i).getId()) {
                
                hospital.getDoctors().remove(i);
                return;
            }
        }
        System.out.println("Can't find doctor with this Id");
    }
    public void showDoctor(Hospital hospital, WeaklyShifts weaklyShifts) {
        System.out.println("Enter the id");
        int inputId = scanner.nextInt();
        if (findDoctor(hospital, inputId) != null) {
            doctorInfo(findDoctor(hospital, inputId));
        } else {
            System.out.println("Can't find doctor with this Id");
        }
    }

    private void doctorInfo(Doctor doctor) {
        System.out.println("Name : " + doctor.getName() + "   Id : " + doctor.getId());
        if (doctor.shifts.size() != 0) {
            System.out.println("shifts --->" + doctor.shifts.keySet() + " of " + doctor.shifts.values());
        } else {
            System.out.println("No shift is added ");
        }
    }

    public void addShift(Hospital hospital, WeaklyShifts weaklyShifts) {
        int choose = 0, inputId;
        ArrayList<Map> chosenDay;
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
            handleSHift(choose, inputId, chosenDay, hospital, weaklyShifts);
        } else {
            System.out.println("can't find this ID");
        }
        System.out.println("--------------------------------------");
    }

    private void handleSHift(int choose, int inputId, ArrayList<Map> chosenDay, Hospital hospital, WeaklyShifts weaklyShifts) {
        Map<String, Doctor> temp = new HashMap<String, Doctor>();
        System.out.println("--------- pick a shift ---------");
        System.out.println("1 -> "+ ShiftsTime.MORNING);
        System.out.println("2 -> "+ ShiftsTime.AFTER_NOON);
        System.out.println("3 -> "+ ShiftsTime.NIGHT);
        choose = scanner.nextInt();
        switch (choose) {
            case 1:
                if(isTaken(ShiftsTime.MORNING,findDoctor(hospital,inputId),weaklyShifts) == false) {
                    temp.put(ShiftsTime.MORNING.name(), findDoctor(hospital,inputId));
                    chosenDay.add(temp);
                    findDoctor(hospital, inputId).shifts.put(ShiftsTime.MORNING.name(), daysShift.name());
                }else {
                    System.out.println("This shift is already taken ");
                }
                break;
            case 2:
                if(isTaken(ShiftsTime.AFTER_NOON,findDoctor(hospital,inputId),weaklyShifts) == false) {
                    temp.put(ShiftsTime.AFTER_NOON.name(),findDoctor(hospital,inputId));
                    chosenDay.add(temp);
                    findDoctor(hospital, inputId).shifts.put(ShiftsTime.AFTER_NOON.name(), daysShift.name());
                }else{
                    System.out.println("This shift is already taken");
                }
                break;
            case 3:
                if(isTaken(ShiftsTime.NIGHT,findDoctor(hospital,inputId),weaklyShifts) == false) {
                    temp.put(ShiftsTime.NIGHT.name(), findDoctor(hospital,inputId));
                    chosenDay.add(temp);
                    findDoctor(hospital, inputId).shifts.put(ShiftsTime.NIGHT.name(), daysShift.name());
                }else {
                    System.out.println("This shift is already taken ");
                }
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

    private ArrayList<Map> whichDay(int choose, WeaklyShifts weaklyShifts) {
        showDay();
        choose = scanner.nextInt();
        while (true) {
            switch (choose) {
                case 1:
                    daysShift = Week.SATURDAY;
                    return weaklyShifts.getSaturdayShift();
                case 2:
                    daysShift = Week.SUNDAY;
                    return weaklyShifts.getSundayShift();
                case 3:
                    daysShift = Week.MONDAY;
                    return weaklyShifts.getMondayShift();
                case 4:
                    daysShift = Week.TUESDAY;
                    return weaklyShifts.getTuesdayShift();
                case 5:
                    daysShift = Week.WEDNESDAY;
                    return weaklyShifts.getWednesdayShift();
                case 6:
                    daysShift = Week.THURSDAY;
                    return weaklyShifts.getThursdayShift();
                case 7:
                    daysShift = Week.FRIDAY;
                    return weaklyShifts.getFridayShift();
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    public static void showDay() {
        System.out.println("1 --> "+ Week.SATURDAY);
        System.out.println("2 --> "+ Week.SUNDAY);
        System.out.println("3 --> "+ Week.MONDAY);
        System.out.println("4 --> "+ Week.TUESDAY);
        System.out.println("5 --> "+ Week.WEDNESDAY);
        System.out.println("6 --> "+ Week.THURSDAY);
        System.out.println("7 --> "+ Week.FRIDAY);
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    private boolean isTaken(ShiftsTime shiftsTime, Doctor doctor, WeaklyShifts weaklyShifts) {
        switch (daysShift) {
            case SATURDAY:
                if(weaklyShifts.doctorHaveShiftThisDay(doctor,weaklyShifts.getSaturdayShift(),shiftsTime))
                    return true;
            case SUNDAY:
                if(weaklyShifts.doctorHaveShiftThisDay(doctor,weaklyShifts.getSundayShift(),shiftsTime))
                    return true;
            case MONDAY:
                if(weaklyShifts.doctorHaveShiftThisDay(doctor,weaklyShifts.getMondayShift(),shiftsTime))
                    return true;
            case TUESDAY:
                if(weaklyShifts.doctorHaveShiftThisDay(doctor,weaklyShifts.getTuesdayShift(),shiftsTime))
                    return true;
            case WEDNESDAY:
                if(weaklyShifts.doctorHaveShiftThisDay(doctor,weaklyShifts.getWednesdayShift(),shiftsTime))
                    return true;
            case THURSDAY:
                if(weaklyShifts.doctorHaveShiftThisDay(doctor,weaklyShifts.getThursdayShift(),shiftsTime))
                    return true;
            case FRIDAY:
                if(weaklyShifts.doctorHaveShiftThisDay(doctor,weaklyShifts.getFridayShift(),shiftsTime))
                    return true;
            default:
        }
        return false;
    }
}
enum ShiftsTime {
    MORNING, AFTER_NOON, NIGHT;
}