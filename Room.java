package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Scanner;

public class Room {
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
                    RoomMenu();
                    break;
                case 2:
                    change();
                    break;
                case 3:
                    addRoom();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    private void showRoomMenu() {
        System.out.println("------- Show -------");
        System.out.println("1 --> All Room");
        System.out.println("2 --> Patient");
        System.out.println("3 --> Back ");
    }

    private void RoomMenu() {
        showRoomMenu();
        int option;
        while (true) {
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    showRoom(whichPart(null));
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
        if (room == null) {
            return;
        }
        showChange();
        int option;
        while (true) {
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
        room.isAvailable = false;
        System.out.println(room.roomNumber + " is unavailable now");
    }

    private PartKind whichPart(Patient patient) {
        System.out.println("Pick which part ");
        System.out.println(PartKind.NORMAL + " OR " + PartKind.EMERGENCY);
        String choose;
        PartKind partKind;
        while (true) {
            choose = scanner.next();
            try {
                partKind = PartKind.valueOf(choose);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong input ");
            }
        }
        if (patient != null) {
            patient.setPartKind(partKind);
        }
        return partKind;
    }

    public void discountForRoom(Room room) {
        int extraBeds = room.bedsNumber - 1;
        double discount = ((double) (extraBeds * 10)) / 100;
        if (discount != 0) {
            discount = 1 - discount;
            room.price *= discount;
        }
    }

    public void addRoom() {
        PartKind partKind = whichPart(null);
        int roomNumber;
        if (partKind == PartKind.NORMAL) {
            roomNumber = Hospital.getNormalRooms().get(Hospital.getNormalRooms().size() - 1).roomNumber + 1;
        } else {
            roomNumber = Hospital.getEmergencyRooms().get(Hospital.getEmergencyRooms().size() - 1).roomNumber + 1;
        }
        System.out.print("How many beds you want ?");
        int bedsNumber = scanner.nextInt();
        while (check(bedsNumber)) {
            System.out.println("The max number of bed for each room is 6");
            bedsNumber = scanner.nextInt();
        }
        Room room = new Room(roomNumber, bedsNumber, price);
        availableHandle(room);
        Hospital.getNormalRooms().add(room);
    }

    private void availableHandle(Room room) {
        YesOrNo yesOrNo;
        System.out.println("Available ?\t" + YesOrNo.YES + " OR " + YesOrNo.NO);
        String choose = scanner.next();
        while (true) {
            try {
                yesOrNo = YesOrNo.valueOf(choose);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong input ");
            }
        }
        if (!yesOrNo.inside) {
            room.isAvailable = true;
        }
    }

    public void pickRoom(Patient patient) {
        Room room;
        room = findRoom(patient);
        if (room != null) {
            if (room.isAvailable) {
                patient.setRoom(room);
                room.patients.add(patient);
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
            System.out.println("  and it's beds number " + room.bedsNumber);
        }
    }


    private void changeBeds(Room room) {
        System.out.println("how many beds you want ?");
        int bedsNumber = scanner.nextInt();
        while (check(bedsNumber)) {
            System.out.println("The max number of beds in a room is 6");
            bedsNumber = scanner.nextInt();
        }
        room.setBedsNumber(bedsNumber);
    }

    public void patientsInRoom() {
        Room room;
        System.out.println("Patient in Room : ");
        room = findRoom(null);
        for (Patient patient : room.patients) {
            System.out.println("Name : " + patient.getName() + "\tId : " + patient.getId());
        }
    }

    public Room findRoom(Patient patient) {
        PartKind partKind = whichPart(patient);
        Room foundRoom;
        if (partKind == PartKind.NORMAL) {
            foundRoom = findRoomHandle(partKind, Hospital.getNormalRooms());
        } else {
            foundRoom = findRoomHandle(partKind, Hospital.getEmergencyRooms());
        }
        if (foundRoom == null) {
            System.out.println("Cant find that Room");
        }
        return foundRoom;
    }

    private Room findRoomHandle(PartKind partKind, ArrayList<Room> partRooms) {
        showRoom(partKind);
        System.out.print("Enter Room number :");
        int option = scanner.nextInt();
        for (Room room : partRooms) {
            if (room.roomNumber == option) {
                return room;
            }
        }
        return null;
    }

    private int defaultBedNumber(PartKind partKind) {
        System.out.print("Default Bed number for" + partKind + " Part ");
        int defaultBedNumberRoom = scanner.nextInt();
        while (check(defaultBedNumberRoom)) {
            System.out.println("The Max bed each room can have is 6");
            defaultBedNumberRoom = scanner.nextInt();
        }
        return defaultBedNumberRoom;
    }

    public void makeRooms() {
        System.out.print("Room Number for " + PartKind.NORMAL + " Part ");
        int normalRoomsNumber = scanner.nextInt();
        System.out.print("Room Number for " + PartKind.EMERGENCY + " Part ");
        int emergencyRoomsNumber = scanner.nextInt();
        int defaultNormalBedNumber = defaultBedNumber(PartKind.NORMAL);
        int defaultEmergencyBedNumber = defaultBedNumber(PartKind.EMERGENCY);
        System.out.println("Price for " + PartKind.NORMAL + " : ");
        int normalRoomPrice = scanner.nextInt();
        System.out.println("Price for " + PartKind.EMERGENCY + " : ");
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

    public boolean check(int defaultBedNumber) {
        return defaultBedNumber <= 0 || defaultBedNumber > 6;
    }

    public void setBedsNumber(int bedsNumber) {
        this.bedsNumber = bedsNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getPrice() {
        return price;
    }
}