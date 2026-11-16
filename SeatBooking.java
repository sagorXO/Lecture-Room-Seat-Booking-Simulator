import java.util.Scanner;

/*
 * Lecture Room Seat Booking Simulator
 * Assignment 06
 * Name:       MD SAIED SAGAR
 * Student ID: 1268062913
 *
 * This program simulates a small lecture room with 4 rows and 5 seats in each row.
 * The user picks options from a menu to:
 *   1. Display the room (seating map + occupancy report)
 *   2. Book a seat
 *   3. Cancel a booking
 *   4. Find the first free seat
 *   0. Exit
 *
 * How the room is stored:
 * The room is a 2D boolean array, room[row][seat].
 *   true  = the seat is occupied (shown as X)
 *   false = the seat is free     (shown as F)
 * The user types rows and seats starting from 1, but Java arrays start at 0,
 * so we always take away 1 before using the array and add 1 back when printing.
 */
public class SeatBooking {

    //Book a seat. Returns true if it worked, false if it did not
    public static boolean bookSeat(boolean[][] room, int row, int seat) {
        //Step 1: check the row and seat are inside the room (4 rows, 5 seats)
        if (row < 1 || row > 4 || seat < 1 || seat > 5) {
            return false;
        }
        //Step 2: if the seat is already true it is taken, so the booking fails
        //Array starts at 0 so take away 1
        if (room[row - 1][seat - 1]) {
            return false;
        }
        //Step 3: mark the seat as occupied
        room[row - 1][seat - 1] = true;
        return true;
    }

    //Cancel a booking. Works the same way as bookSeat but the other way round
    public static boolean cancelSeat(boolean[][] room, int row, int seat) {
        //Step 1: check the row and seat are inside the room
        if (row < 1 || row > 4 || seat < 1 || seat > 5) {
            return false;
        }
        //Step 2: you can't cancel a seat that is already free
        if (!room[row - 1][seat - 1]) {
            return false;
        }
        //Step 3: mark the seat as free again
        room[row - 1][seat - 1] = false;
        return true;
    }

    //Search the room row by row and return the first free seat as one number
    //The number is row * 10 + seat, so 12 means row 1 seat 2 and 45 means row 4 seat 5
    //This works because there are never more than 9 seats in a row
    //Returns -1 if every seat is taken
    //The search only reads the array, it never changes a seat
    public static int findFirstFree(boolean[][] room) {
        //Outer loop goes through the rows, inner loop goes through the seats in that row
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (!room[i][j]) {
                    //Found a free seat. Add 1 back so the numbers start at 1
                    //Return straight away so we only get the first one
                    return (i + 1) * 10 + (j + 1);
                }
            }
        }
        //Got through the whole room without finding a free seat, so the room is full
        return -1;
    }

    //Print the seating map, X for occupied and F for free
    public static void displayRoom(boolean[][] room) {
        //Print the header line with the seat numbers 1 to 5
        System.out.print("       Seat  ");
        for (int j = 0; j < 5; j++) {
            //Put spaces between the numbers (but not before the first one) so they line up
            if (j > 0) {
                System.out.print("   ");
            }
            System.out.print(j + 1);
        }
        System.out.println();

        //Print one line for each row
        for (int i = 0; i < 4; i++) {
            System.out.print("Row " + (i + 1) + "        ");
            for (int j = 0; j < 5; j++) {
                if (j > 0) {
                    System.out.print("   ");
                }
                //Check each seat and print the right letter
                if (room[i][j]) {
                    System.out.print("X");
                } else {
                    System.out.print("F");
                }
            }
            //Move to the next line after the row is finished
            System.out.println();
        }
    }

    //Print free seats for each row, total occupied and the percentage
    public static void displayReport(boolean[][] room) {
        //Counters for the whole room
        int occupied = 0;
        int total = 0;

        System.out.println();
        for (int i = 0; i < 4; i++) {
            //Reset the free counter for every new row
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

        //Work out the occupancy percentage
        //Cast to double, otherwise integer division gives 0
        double percentage = (double) occupied / total * 100;
        //%.2f shows 2 decimal places
        System.out.printf("Occupancy: %.2f", percentage);
        System.out.println("%");
    }

    //Keep asking until the user types a number between min and max
    //Every input in the program goes through here so the program never crashes on bad input
    public static int readInt(Scanner scanner, String prompt, int min, int max) {
        int number = 0;
        boolean valid = false;

        //Loop until we get a valid number
        while (!valid) {
            System.out.println(prompt);
            //hasNextInt checks if the next input is a whole number before we read it
            if (scanner.hasNextInt()) {
                number = scanner.nextInt();
                //Secret code, works at any prompt and then asks again
                if (number == 777) {
                    System.out.println("Welcome Back Saied Sagar");
                } else if (number >= min && number <= max) {
                    //Number is in range so we can stop the loop
                    valid = true;
                } else {
                    //It's a number but out of range
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
        //Set up the scanner for keyboard input
        Scanner scanner = new Scanner(System.in);
        //Create the room, 4 rows and 5 seats. Java fills a boolean array with false, so every seat starts free
        boolean[][] room = new boolean[4][5];

        //Main menu loop, keeps going until the user picks 0
        //Start with -1 so the menu shows at least once
        int choice = -1;
        while (choice != 0) {
            //Show the menu options
            System.out.println("Menu:");
            System.out.println("1. Display room");
            System.out.println("2. Book a seat");
            System.out.println("3. Cancel a booking");
            System.out.println("4. Find first free seat");
            System.out.println("0. Exit");
            choice = readInt(scanner, "Choose an option:", 0, 4);

            //Run the option the user picked
            if (choice == 1) {
                //Option 1: show the map and the report
                displayRoom(room);
                displayReport(room);
            } else if (choice == 2) {
                //Option 2: ask for a row and seat, then try to book it
                int row = readInt(scanner, "Enter row (1-4):", 1, 4);
                int seat = readInt(scanner, "Enter seat (1-5):", 1, 5);
                if (bookSeat(room, row, seat)) {
                    System.out.println("Seat booked.");
                } else {
                    System.out.println("Booking failed. That seat is already occupied.");
                }
            } else if (choice == 3) {
                //Option 3: ask for a row and seat, then try to cancel it
                int row = readInt(scanner, "Enter row (1-4):", 1, 4);
                int seat = readInt(scanner, "Enter seat (1-5):", 1, 5);
                if (cancelSeat(room, row, seat)) {
                    System.out.println("Booking cancelled.");
                } else {
                    System.out.println("Cancel failed. That seat is already free.");
                }
            } else if (choice == 4) {
                //Option 4: find the first free seat, -1 means there are none left
                int freeSeat = findFirstFree(room);
                if (freeSeat == -1) {
                    System.out.println("Full");
                } else {
                    //Split the number back into row and seat
                    //Dividing by 10 gives the row, the remainder (%) gives the seat
                    int freeRow = freeSeat / 10;
                    int freeCol = freeSeat % 10;
                    System.out.printf("First free seat is row %d, seat %d\n", freeRow, freeCol);
                }
            }
            //If choice is 0 the while loop ends here
        }

        //Close the scanner before the program ends
        scanner.close();
    }
}
