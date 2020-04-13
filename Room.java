package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Scanner;

public class Room {
    public static Scanner scanner = new Scanner(System.in);
    private final int MAX_BED_NUMBER = 6;
    private final ArrayList<Patient> patients = new ArrayList<>();
    private int roomNumber;
    private int bedsNumber;
    private MyDate unAvailableStart = new MyDate();
    private MyDate unAvailableFinish = new MyDate();
    private int price;

    public Room(int roomNumber, int bedsNumber, int price) {
        this.roomNumber = roomNumber;
        this.bedsNumber = bedsNumber;
        this.price = price;
    }

    public Room() {
    }

    public static boolean check(int defaultBedNumber, int min, int max) {
        return defaultBedNumber <= min || defaultBedNumber > max;
    }

    private int defaultBedNumber(PartKind partKind) {
        System.out.print("Default Bed number for " + partKind + " Part : ");
        int defaultBedNumberRoom = scanner.nextInt();
        while (check(defaultBedNumberRoom, 0, MAX_BED_NUMBER)) {
            System.out.println("The Max bed each room can have is " + MAX_BED_NUMBER);
            defaultBedNumberRoom = scanner.nextInt();
        }
        return defaultBedNumberRoom;
    }

    private int checkRoomNumberValue(PartKind partKind) {
        System.out.print("Room Number for " + partKind + " Part : ");
        int RoomsNumbers = scanner.nextInt();
        while (RoomsNumbers <= 0) {
            System.out.println("Enter a correct value");
            RoomsNumbers = scanner.nextInt();
        }
        return RoomsNumbers;
    }

    public void makeRooms(Hospital hospital) {
        int normalRoomsNumber = checkRoomNumberValue(PartKind.NORMAL);
        int emergencyRoomsNumber = checkRoomNumberValue(PartKind.EMERGENCY);
        int defaultNormalBedNumber = defaultBedNumber(PartKind.NORMAL);
        int defaultEmergencyBedNumber = defaultBedNumber(PartKind.EMERGENCY);
        System.out.print("Price for " + PartKind.NORMAL + " : ");
        int normalRoomPrice = scanner.nextInt();
        System.out.print("Price for " + PartKind.EMERGENCY + " : ");
        int emergencyRoomPrice = scanner.nextInt();
        for (int i = 1; i <= normalRoomsNumber; i++) {
            Room myRoom = new Room(i, defaultNormalBedNumber, normalRoomPrice);
            hospital.getNormalRooms().add(myRoom);
        }
        for (int i = 1; i <= emergencyRoomsNumber; i++) {
            Room myRoom = new Room(i, defaultEmergencyBedNumber, emergencyRoomPrice);
            hospital.getEmergencyRooms().add(myRoom);
        }
    }

    private void printMenu() {
        System.out.println("-------- ROOM ---------");
        System.out.println("1 --> Show ");
        System.out.println("2 --> Change");
        System.out.println("3 --> Add ");
        System.out.println("4 --> Back ");
        System.out.println(" ---------------");
    }

    public void menu(Hospital hospital) {
        int option;
        while (true) {
            printMenu();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    show(hospital);
                    break;
                case 2:
                    change(hospital);
                    break;
                case 3:
                    addRoom(null, hospital);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    private void printShow() {
        System.out.println("------- Show -------");
        System.out.println("1 --> All Room");
        System.out.println("2 --> Patient");
        System.out.println("3 --> Back ");
        System.out.println(" --------------");
    }

    private void show(Hospital hospital) {
        int option;
        while (true) {
            printShow();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    showRoom(whichPart(), hospital);
                    break;
                case 2:
                    patientsInRoom(hospital);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Wrong input ");
            }
        }
    }

    private void showChange() {
        System.out.println("------- CHANGE ------");
        System.out.println("1 --> Change Beds Number");
        System.out.println("2 --> Make unavailable");
        System.out.println("3 --> Back ");
        System.out.println(" ---------------------");
    }

    public void change(Hospital hospital) {
        Room room = findRoom(null, hospital);
        int option;
        while (true) {
            showChange();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    changeBeds(room);
                    break;
                case 2:
                    roomUnavailable(room);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    public void roomUnavailable(Room room) {
        if (room.patients.size() > 0) {
            System.out.println("Can't make unavailable");
            return;
        }
        System.out.println("first Date");
        room.unAvailableStart = MyDate.dateSet(false);
        System.out.println("second Date : ");
        room.unAvailableFinish = MyDate.dateSet(false);
    }

    public void discountForRoom(Room room) {
        int extraBeds = room.bedsNumber - 1;
        double discount = ((double) (extraBeds * 10)) / 100;
        if (discount != 0) {
            discount = 1 - discount;
            room.price *= discount;
        }
    }

    public void addRoom(PartKind chosenPartKind, Hospital hospital) {
        PartKind partKind = chosenPartKind;
        if (partKind == null) {
            partKind = whichPart();
        }
        int roomNumber;
        if (partKind == PartKind.NORMAL) {
            roomNumber = hospital.getNormalRooms().get(hospital.getNormalRooms().size() - 1).roomNumber + 1;
        } else {
            roomNumber = hospital.getEmergencyRooms().get(hospital.getEmergencyRooms().size() - 1).roomNumber + 1;
        }
        System.out.print("Beds Number : ");
        int bedsNumber = scanner.nextInt();
        while (check(bedsNumber, 0, MAX_BED_NUMBER)) {
            System.out.println("Max number for bed is " + MAX_BED_NUMBER);
            bedsNumber = scanner.nextInt();
        }
        Room room = new Room(roomNumber, bedsNumber, price);
        if (partKind == PartKind.NORMAL) {
            hospital.getNormalRooms().add(room);
        } else {
            hospital.getEmergencyRooms().add(room);
        }
    }

    private Room pickAuto(PartKind partKind, Hospital hospital) {
        System.out.println("1 --> Empty room");
        System.out.println("2 --> Nonempty ROom");
        int option = scanner.nextInt();
        if (option == 1) {
            if (partKind == PartKind.NORMAL) {
                return findMinPatient(hospital.getNormalRooms());
            } else {
                return findMinPatient(hospital.getEmergencyRooms());
            }
        } else {
            if (partKind == PartKind.NORMAL) {
                return findMaxPatient(hospital.getNormalRooms());
            } else {
                return findMaxPatient(hospital.getEmergencyRooms());
            }
        }
    }

    private Room findMaxPatient(ArrayList<Room> rooms) {
        int max = Integer.MIN_VALUE;
        Room foundRoom = null;
        for (Room room : rooms) {
            if (room.unAvailableStart.getYear() == 0) {
                if (room.getPatients().size() > max && room.getPatients().size() < room.bedsNumber) {
                    max = room.getPatients().size();
                    foundRoom = room;
                }
            }
        }
        return foundRoom;
    }

    private Room findMinPatient(ArrayList<Room> rooms) {
        int min = Integer.MAX_VALUE;
        Room foundRoom = null;
        for (Room room : rooms) {
            if (room.unAvailableStart.getYear() == 0) {
                if (room.getPatients().size() < min && room.getPatients().size() < room.bedsNumber) {
                    min = room.getPatients().size();
                    foundRoom = room;
                }
            }
        }
        return foundRoom;
    }

    public void pickRoom(Patient patient, Hospital hospital) {
        PartKind partKind = patient.getPartKind();
        Room room;
        while (true) {
            System.out.println("1 --> Pick by your self ");
            System.out.println("2 --> Pick automatic");
            int option = scanner.nextInt();
            if (option == 1) {
                room = findRoom(partKind, hospital);
                if (room.unAvailableStart.getYear() == 0 || MyDate.howLong(room.unAvailableFinish, patient.getEntry()) > 0) {
                    if (room.bedsNumber > room.patients.size()) {
                        patient.setRoom(room);
                        room.patients.add(patient);
                        return;
                    } else {
                        System.out.println("Room is Full");
                    }
                } else {
                    System.out.println("Unavailable ! ");
                }
            } else {
                room = pickAuto(partKind, hospital);
                if (room != null) {
                    patient.setRoom(room);
                    room.patients.add(patient);
                    return;
                }
                System.out.println("Can't find room");
            }
        }
    }

    public void showRoom(PartKind partKind, Hospital hospital) {
        if (partKind == PartKind.NORMAL) {
            showRoomHandle(hospital.getNormalRooms());
        } else {
            showRoomHandle(hospital.getEmergencyRooms());
        }
    }

    public void showRoomHandle(ArrayList<Room> roomArrayList) {
        System.out.println("-------- ROOMS --------");
        for (Room room : roomArrayList) {
            System.out.print("Room number : " + room.roomNumber);
            System.out.println("\tbeds number : " + room.bedsNumber);
        }
        System.out.println(" ----------------- ");
    }

    private void changeBeds(Room room) {
        System.out.print("Beds Number : ");
        int bedsNumber = scanner.nextInt();
        while (check(bedsNumber, room.patients.size(), MAX_BED_NUMBER)) {
            System.out.println("Max Bed Number this room is" + MAX_BED_NUMBER + "and Min is " + room.patients.size());
            bedsNumber = scanner.nextInt();
        }
        room.setBedsNumber(bedsNumber);
    }

    public void patientsInRoom(Hospital hospital) {
        Room room;
        room = findRoom(null, hospital);
        if (room.patients.size() == 0) {
            System.out.println("No Patient");
            return;
        }
        for (Patient patient : room.patients) {
            System.out.println("Name : " + patient.getName() + "\tId : " + patient.getId());
        }
        System.out.println(" ----------------- ");
    }

    public void unAvailableRoomsHandle(ArrayList<Room> rooms) {
        MyDate.inputPeriod();
        for (Room room : rooms) {
            if (room.unAvailableStart.getYear() != 0) {
                if (checkBetweenDate(room.unAvailableStart, room.unAvailableFinish)) {
                    System.out.println("Number : " + room.getRoomNumber() + "\tPart : " + PartKind.NORMAL);
                }
            }
        }
    }

    public boolean checkBetweenDate(MyDate start, MyDate end) {
        if ((MyDate.howLong(MyDate.getPeriod().get(0), start) >= 0 && MyDate.howLong(start, MyDate.getPeriod().get(1)) >= 0)) {
            return true;
        }
        if ((MyDate.howLong(start, MyDate.getPeriod().get(0)) >= 0 && MyDate.howLong(MyDate.getPeriod().get(1), end) >= 0)) {
            return true;
        }
        if ((MyDate.howLong(MyDate.getPeriod().get(0), end) >= 0 && MyDate.howLong(end, MyDate.getPeriod().get(1)) >= 0)) {
            return true;
        }
        return false;
    }

    public void emptyRoomHandle(ArrayList<Room> rooms, int number) {
        for (Room room : rooms) {
            int patientInRoom = 0;
            for (Patient patient : room.getPatients()) {
                if (!patient.isDischarge()) {
                    patientInRoom++;
                    continue;
                }
                if (checkBetweenDate(patient.getEntry(), patient.getDeparture())) {
                    patientInRoom++;
                }
            }
            if (number != 0) {
                if (room.getBedsNumber() - patientInRoom == number) {
                    System.out.println("Number : " + room.getRoomNumber() + "\t Empty beds : " + number);
                }
            } else {
                if (room.getBedsNumber() - patientInRoom > number) {
                    System.out.println("Number : " + room.getRoomNumber() + "\t Empty beds : " + (room.getBedsNumber() - patientInRoom));
                }
            }
        }
        MyDate.getPeriod().clear();
    }

    public Room findRoom(PartKind partKind, Hospital hospital) {
        if (partKind == null) {
            partKind = whichPart();
        }
        Room foundRoom;
        if (partKind == PartKind.NORMAL) {
            foundRoom = findRoomHandle(partKind, hospital.getNormalRooms(), hospital);
        } else {
            foundRoom = findRoomHandle(partKind, hospital.getEmergencyRooms(), hospital);
        }
        return foundRoom;
    }

    private Room findRoomHandle(PartKind partKind, ArrayList<Room> partRooms, Hospital hospital) {
        int option;
        while (true) {
            showRoom(partKind, hospital);
            System.out.print("Enter Room number : ");
            option = scanner.nextInt();
            for (Room room : partRooms) {
                if (room.roomNumber == option) {
                    return room;
                }
            }
            System.out.println("Cant find room\nAdd Room by 0");
            if (option == 0) {
                addRoom(partKind, hospital);
            }
        }
    }

    public PartKind whichPart() {
        Scanner scanner = new Scanner(System.in);
        PartKind[] partKinds = PartKind.values();
        int option = Integer.MAX_VALUE;
        while (option > partKinds.length) {
            System.out.println("1 --> " + PartKind.NORMAL);
            System.out.println("2 --> " + PartKind.EMERGENCY);
            option = scanner.nextInt();
        }
        PartKind partKind = partKinds[option - 1];
        switch (partKind) {
            case NORMAL:
                return PartKind.NORMAL;
            case EMERGENCY:
                return PartKind.EMERGENCY;
            default:
                return null;
        }
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public int getBedsNumber() {
        return bedsNumber;
    }

    public void setBedsNumber(int bedsNumber) {
        this.bedsNumber = bedsNumber;
    }

    public int getPrice() {
        return price;
    }
}