import java.util.Scanner;

public class Main {

    //Define booking method
    public static boolean bookSeat(boolean[][] room, int row, int seat) {
        if (row < 1 || row > 4 || seat < 1 || seat > 5) {
            return false;
        }
        //Array starts at 0 so take away 1
        if (room[row - 1][seat - 1]) {
            return false;
        }
        room[row - 1][seat - 1] = true;
        return true;
    }

    //Define cancel method
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

    //Define search method, goes row by row and returns the first free seat
    public static int[] findFirstFree(boolean[][] room) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (!room[i][j]) {
                    //Add 1 back so the numbers start at 1
                    int[] result = new int[2];
                    result[0] = i + 1;
                    result[1] = j + 1;
                    return result;
                }
            }
        }
        return null;
    }

    //Print the seating map
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

    //Print free seats for each row, total occupied and the percentage
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

        //Cast to double, otherwise integer division gives 0
        double percentage = (double) occupied / total * 100;
        System.out.printf("Occupancy: %.2f", percentage);
        System.out.println("%");
    }

    //Keep asking until the user types a number between min and max
    public static int readInt(Scanner scanner, String prompt, int min, int max) {
        int number = 0;
        boolean valid = false;

        while (!valid) {
            System.out.println(prompt);
            if (scanner.hasNextInt()) {
                number = scanner.nextInt();
                //Secret code, works at any prompt and then asks again
                if (number == 777) {
                    System.out.println("Welcome Back Saied Sagar");
                } else if (number >= min && number <= max) {
                    valid = true;
                } else {
                    System.out.printf("Invalid input. Please enter a number between %d and %d.\n", min, max);
                }
            } else {
                //Not a number, throw it away or the loop never ends
                scanner.next();
                System.out.printf("Invalid input. Please enter a number between %d and %d.\n", min, max);
            }
        }
        return number;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean[][] room = new boolean[4][5];

        //Start with -1 so the menu shows at least once
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
                int[] freeSeat = findFirstFree(room);
                if (freeSeat == null) {
                    System.out.println("Full");
                } else {
                    System.out.printf("First free seat is row %d, seat %d\n", freeSeat[0], freeSeat[1]);
                }
            }
        }

        scanner.close();
    }
}
