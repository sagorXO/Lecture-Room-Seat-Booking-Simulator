import java.util.Scanner;

/*
 * Lecture Room Seat Booking Simulator
 * Assignment 06
 * Name:       MD SAIED SAGAR
 * Student ID: 1268062913
 */
public class SeatBooking {

    //Books a seat if it is free. Returns true if booked, false if not
    public static boolean bookSeat(boolean[][] room, int row, int seat) {
        if (row < 1 || row > 4 || seat < 1 || seat > 5) {
            return false;
        }
        if (room[row - 1][seat - 1]) {
            return false;
        }
        room[row - 1][seat - 1] = true;
        return true;
    }

    //Cancels a booking if the seat is booked. Returns true if cancelled, false if not
    public static boolean cancelSeat(boolean[][] room, int row, int seat) {
        if (row < 1 || row > 4 || seat < 1 || seat > 5) {
            return false;
        }
        if (!room[row - 1][seat - 1]) {
            return false;
        }
        room[row - 1][seat - 1] = false;
        return true;
    }

    //Finds the first free seat and returns it as row * 10 + seat, or -1 if the room is full
    public static int findFirstFree(boolean[][] room) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (!room[i][j]) {
                    return (i + 1) * 10 + (j + 1);
                }
            }
        }
        return -1;
    }

    //Prints the seating map, X for occupied and F for free
    public static void displayRoom(boolean[][] room) {
        System.out.print("       Seat  ");
        for (int j = 0; j < 5; j++) {
            if (j > 0) {
                System.out.print("   ");
            }
            System.out.print(j + 1);
        }
        System.out.println();

        for (int i = 0; i < 4; i++) {
            System.out.print("Row " + (i + 1) + "        ");
            for (int j = 0; j < 5; j++) {
                if (j > 0) {
                    System.out.print("   ");
                }
                if (room[i][j]) {
                    System.out.print("X");
                } else {
                    System.out.print("F");
                }
            }
            System.out.println();
        }
    }

    //Prints the free seats in each row, the total occupied seats and the occupancy percentage
    public static void displayReport(boolean[][] room) {
        int occupied = 0;
        int total = 0;

        System.out.println();
        for (int i = 0; i < 4; i++) {
            int free = 0;
            for (int j = 0; j < 5; j++) {
                total++;
                if (room[i][j]) {
                    occupied++;
                } else {
                    free++;
                }
            }
            System.out.printf("Row %d free seats: %d\n", i + 1, free);
        }
        System.out.printf("Total occupied seats: %d\n", occupied);

        double percentage = (double) occupied / total * 100;
        System.out.printf("Occupancy: %.2f", percentage);
        System.out.println("%");
    }

    //Keeps asking until the user types a whole number between min and max, then returns it
    public static int readInt(Scanner scanner, String prompt, int min, int max) {
        int number = 0;
        boolean valid = false;

        while (!valid) {
            System.out.println(prompt);
            if (scanner.hasNextInt()) {
                number = scanner.nextInt();
                if (number == 777) {
                    System.out.println("Welcome Back Saied Sagar");
                } else if (number >= min && number <= max) {
                    valid = true;
                } else {
                    System.out.printf("Invalid input. Please enter a number between %d and %d.\n", min, max);
                }
            } else {
                scanner.next();
                System.out.printf("Invalid input. Please enter a number between %d and %d.\n", min, max);
            }
        }
        return number;
    }

    //Shows the menu and runs the option the user picks until they choose 0 to exit
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean[][] room = new boolean[4][5];

        int choice = -1;
        while (choice != 0) {
            System.out.println("Menu:");
            System.out.println("1. Display room");
            System.out.println("2. Book a seat");
            System.out.println("3. Cancel a booking");
            System.out.println("4. Find first free seat");
            System.out.println("0. Exit");
            choice = readInt(scanner, "Choose an option:", 0, 4);

            if (choice == 1) {
                displayRoom(room);
                displayReport(room);
            } else if (choice == 2) {
                int row = readInt(scanner, "Enter row (1-4):", 1, 4);
                int seat = readInt(scanner, "Enter seat (1-5):", 1, 5);
                if (bookSeat(room, row, seat)) {
                    System.out.println("Seat booked.");
                } else {
                    System.out.println("Booking failed. That seat is already occupied.");
                }
            } else if (choice == 3) {
                int row = readInt(scanner, "Enter row (1-4):", 1, 4);
                int seat = readInt(scanner, "Enter seat (1-5):", 1, 5);
                if (cancelSeat(room, row, seat)) {
                    System.out.println("Booking cancelled.");
                } else {
                    System.out.println("Cancel failed. That seat is already free.");
                }
            } else if (choice == 4) {
                int freeSeat = findFirstFree(room);
                if (freeSeat == -1) {
                    System.out.println("Full");
                } else {
                    int freeRow = freeSeat / 10;
                    int freeCol = freeSeat % 10;
                    System.out.printf("First free seat is row %d, seat %d\n", freeRow, freeCol);
                }
            }
        }

        scanner.close();
    }
}
