package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Scanner;

public class Room {
    private static final int MAX_BED_NUMBER = 6;
    Scanner scanner = new Scanner(System.in);
    private int roomNumber;
    private int bedsNumber;
    private boolean isAvailable = true;
    private int price;
    private ArrayList<Patient> patients = new ArrayList<>();

    public Room(int roomNumber, int bedsNumber, int price) {
        this.roomNumber = roomNumber;
        this.bedsNumber = bedsNumber;
        this.price = price;
    }

    public Room() {
    }

    public static PartKind whichPart() {
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

    private void printMenu() {
        System.out.println("-------- ROOM ---------");
        System.out.println("1 --> Show ");
        System.out.println("2 --> Change");
        System.out.println("3 --> Add ");
        System.out.println("4 --> Back ");
        System.out.println("-------------------------");
    }

    public void menu() {
        int option;
        while (true) {
            printMenu();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    show();
                    break;
                case 2:
                    change();
                    break;
                case 3:
                    addRoom(null);
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
    }

    private void show() {
        int option;
        while (true) {
            printShow();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    showRoom(whichPart());
                    break;
                case 2:
                    patientsInRoom();
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
    }

    public void change() {
        Room room = findRoom(null);
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
        room.isAvailable = false;
        System.out.println(room.roomNumber + " is unavailable now");
    }

    public void discountForRoom(Room room) {
        int extraBeds = room.bedsNumber - 1;
        double discount = ((double) (extraBeds * 10)) / 100;
        if (discount != 0) {
            discount = 1 - discount;
            room.price *= discount;
        }
    }

    public void addRoom(PartKind chosenPartKind) {
        PartKind partKind = chosenPartKind;
        if (partKind == null) {
            partKind = whichPart();
        }
        int roomNumber;
        if (partKind == PartKind.NORMAL) {
            roomNumber = Hospital.getNormalRooms().get(Hospital.getNormalRooms().size() - 1).roomNumber + 1;
        } else {
            roomNumber = Hospital.getEmergencyRooms().get(Hospital.getEmergencyRooms().size() - 1).roomNumber + 1;
        }
        System.out.print("Beds Number : ");
        int bedsNumber = scanner.nextInt();
        while (check(bedsNumber, 0, MAX_BED_NUMBER)) {
            System.out.println("Max number for bed is " + MAX_BED_NUMBER);
            bedsNumber = scanner.nextInt();
        }
        Room room = new Room(roomNumber, bedsNumber, price);
        availableHandle(room);
        if (partKind == PartKind.NORMAL) {
            Hospital.getNormalRooms().add(room);
        } else {
            Hospital.getEmergencyRooms().add(room);
        }
    }

    private void availableHandle(Room room) {
        YesOrNo yesOrNo;
        System.out.print("Available ?\t" + YesOrNo.YES + " OR " + YesOrNo.NO + " : ");
        String choose = scanner.next();
        while (true) {
            try {
                yesOrNo = YesOrNo.valueOf(choose);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong input ");
            }
        }
        room.isAvailable = yesOrNo.inside;
    }

    private Room pickAuto(PartKind partKind) {
        System.out.println("1 --> Empty room");
        System.out.println("2 --> Nonempty ROom");
        int option = scanner.nextInt();
        if (option == 1) {
            if (partKind == PartKind.NORMAL) {
                return findMinPatient(Hospital.getNormalRooms());
            } else {
                return findMinPatient(Hospital.getEmergencyRooms());
            }
        } else {
            if (partKind == PartKind.NORMAL) {
                return findMaxPatient(Hospital.getNormalRooms());
            } else {
                return findMaxPatient(Hospital.getEmergencyRooms());
            }
        }
    }

    private Room findMaxPatient(ArrayList<Room> rooms) {
        int max = Integer.MIN_VALUE;
        Room foundRoom = null;
        for (Room room : rooms) {
            if (room.isAvailable) {
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
            if (room.isAvailable) {
                if (room.getPatients().size() < min && room.getPatients().size() < room.bedsNumber) {
                    min = room.getPatients().size();
                    foundRoom = room;
                }
            }
        }
        return foundRoom;
    }

    public void pickRoom(Patient patient) {
        PartKind partKind = patient.getPartKind();
        Room room;
        while (true) {
            System.out.println("1 --> Pick by your self ");
            System.out.println("2 --> Pick automatic");
            int option = scanner.nextInt();
            if (option == 1) {
                room = findRoom(partKind);
                if (room.isAvailable) {
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
                room = pickAuto(partKind);
                if (room != null) {
                    patient.setRoom(room);
                    room.patients.add(patient);
                    return;
                }
                System.out.println("Can't find room");
            }
        }
    }

    public void showRoom(PartKind partKind) {
        if (partKind == PartKind.NORMAL) {
            showRoomHandle(Hospital.getNormalRooms());
        } else {
            showRoomHandle(Hospital.getEmergencyRooms());
        }
    }

    public void showRoomHandle(ArrayList<Room> roomArrayList) {
        System.out.println("-------- ROOMS --------");
        for (Room room : roomArrayList) {
            System.out.print("Room number : " + room.roomNumber);
            System.out.println("\tbeds number : " + room.bedsNumber);
        }
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

    public void patientsInRoom() {
        Room room;
        room = findRoom(null);
        if (room.patients.size() == 0) {
            System.out.println("No Patient");
            return;
        }
        for (Patient patient : room.patients) {
            System.out.println("Name : " + patient.getName() + "\tId : " + patient.getId());
        }
    }

    public Room findRoom(PartKind partKind) {
        if (partKind == null) {
            partKind = whichPart();
        }
        Room foundRoom;
        if (partKind == PartKind.NORMAL) {
            foundRoom = findRoomHandle(partKind, Hospital.getNormalRooms());
        } else {
            foundRoom = findRoomHandle(partKind, Hospital.getEmergencyRooms());
        }
        return foundRoom;
    }

    private Room findRoomHandle(PartKind partKind, ArrayList<Room> partRooms) {
        int option;
        while (true) {
            showRoom(partKind);
            System.out.print("Enter Room number : ");
            option = scanner.nextInt();
            for (Room room : partRooms) {
                if (room.roomNumber == option) {
                    return room;
                }
            }
            System.out.println("Cant find room ---> Add Room by 0");
            if (option == 0) {
                addRoom(partKind);
            }
        }
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

    public void makeRooms() {
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
            Hospital.getNormalRooms().add(myRoom);
        }
        for (int i = 1; i <= emergencyRoomsNumber; i++) {
            Room myRoom = new Room(i, defaultEmergencyBedNumber, emergencyRoomPrice);
            Hospital.getEmergencyRooms().add(myRoom);
        }
    }

    public boolean check(int defaultBedNumber, int min, int max) {
        return defaultBedNumber <= min || defaultBedNumber > max;
    }

    public void setBedsNumber(int bedsNumber) {
        this.bedsNumber = bedsNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public int getPrice() {
        return price;
    }
}