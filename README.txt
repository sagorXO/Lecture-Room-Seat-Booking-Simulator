Assignment 06 - Lecture Room Seat Booking Simulator

Name:       MD SAIED SAGAR
Student ID: 1268062913


WHAT THE PROGRAM DOES
---------------------
It simulates a lecture room with 4 rows and 5 seats in each row (20 seats).
A menu lets the user:
  1. Display the room   - prints a map (X = occupied, F = free), the free seats
                          in each row, the total occupied seats and the
                          occupancy percentage
  2. Book a seat        - asks for a row and seat and books it if it is free
  3. Cancel a booking   - asks for a row and seat and frees it if it is booked
  4. Find first free    - shows the first free seat, scanning row 1 to row 4
                          and seat 1 to seat 5. Prints "Full" if there is none
  0. Exit


FILES
-----
SeatBooking.java  - the program
README.txt        - this file
TestLog.txt       - 12 test runs with inputs, expected and actual results,
                    and pass/fail


HOW TO COMPILE AND RUN
----------------------
Needs a Java JDK (tested with JDK 25). In the folder with SeatBooking.java:

  javac SeatBooking.java
  java SeatBooking


HOW IT WORKS
------------
The room is a 2D boolean array: boolean[4][5].
  true  = occupied
  false = free (Java fills a new boolean array with false, so the room starts
          empty)

The user types rows 1-4 and seats 1-5, but Java arrays start at 0. So the
program takes away 1 before using the array (room[row - 1][seat - 1]) and adds
1 back when it prints a seat.

Methods:
  bookSeat(room, row, seat)    returns true if booked, false if the seat is out
                               of range or already taken
  cancelSeat(room, row, seat)  returns true if cancelled, false if the seat is
                               out of range or already free
  findFirstFree(room)          returns one int that holds the row and seat, or
                               -1 if the room is full (see below)
  displayRoom(room)            prints the seating map
  displayReport(room)          prints free seats per row, total occupied and
                               the percentage
  readInt(scanner, prompt, min, max)
                               keeps asking until the user types a whole number
                               between min and max

Design decisions:
- Input validation is kept separate from occupancy checks. readInt makes sure
  the number is in range. bookSeat and cancelSeat then check whether the seat
  is free or taken. (bookSeat and cancelSeat also check the range again so they
  are safe on their own.)
- bookSeat and cancelSeat check first and change last. If a check fails they
  return false before touching the array, so a failed booking never changes
  the room.
- The methods return true/false instead of printing. main prints all the
  messages, so the methods could be reused or tested without the menu.
- findFirstFree encodes the result as row * 10 + seat. For example 12 means
  row 1 seat 2 and 45 means row 4 seat 5. main splits it back with
  result / 10 (row) and result % 10 (seat). This works because a row never has
  more than 9 seats. -1 is used for a full room because no real seat can give
  a negative number.
- findFirstFree only reads the array, it never assigns to it, so searching
  does not change any booking (Test 11 checks this).
- The percentage uses (double) occupied / total * 100. Without the cast, the
  integer division would give 0 for anything under 100%.
- readInt uses hasNextInt() before nextInt(). If the input is not a whole
  number (like "abc" or "2.5"), it is thrown away with next() so the program
  doesn't crash or get stuck in a loop.


TESTING
-------
See TestLog.txt. 12 tests, all passed. They cover both sample checks from the
brief (empty room and booking row 1 seat 1), double booking, cancelling,
cancelling a free seat, out of range input, text input, a full room, the last
seat, and checking that the search does not change the room.


EXTRA
-----
Typing 777 at any prompt prints "Welcome Back Saied Sagar" and then asks the
same question again. It does not change the room.


LIMITATIONS
-----------
- The room size (4 x 5) is written as plain numbers in the code, so changing
  the size means changing several places.
- Bookings are only kept while the program is running. Nothing is saved to a
  file.
