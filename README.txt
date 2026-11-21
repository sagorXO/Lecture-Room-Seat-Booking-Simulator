Assignment 06 - Lecture Room Seat Booking Simulator

Team members:
MD SAIED SAGAR            ID: 1268062913
Nafiz Ahmed Chowdhury     ID: 1267299285
Md Hasan Wasif            ID: 1268091076
Ahmed Riaz                ID: 1264944404
Seemab Asghar Ranjha      ID: 1265979875


About the program

This is our seat booking program for a lecture room. The room has 4 rows and
5 seats in each row, so 20 seats in total. When you run it you get a menu:

1 - Display room
2 - Book a seat
3 - Cancel a booking
4 - Find first free seat
0 - Exit

Option 1 prints the room as a grid. X is a booked seat and F is a free seat.
Under the grid it shows how many seats are free in each row, how many are
booked in total and the occupancy percentage.

Option 2 and 3 ask for a row and a seat number. If you try to book a seat
that's already taken, or cancel one that's already free, it tells you and
nothing changes.

Option 4 checks row 1 first, from seat 1 to 5, then row 2 and so on, and
tells you the first free seat it finds. If every seat is booked it prints
"Full".


Files in the zip

SeatBooking.java - our program
README.txt - this file
TestLog.txt - our 12 test runs


How to run it

You need Java installed (we used JDK 25). Open a terminal in the folder and
type:

javac SeatBooking.java
java SeatBooking


How we made it

We stored the room in a 2D boolean array, boolean[4][5]. true means the seat
is booked and false means it's free. A new boolean array is all false in
Java, so the room starts empty without us doing anything.

The user types row 1-4 and seat 1-5 but the array starts at 0, so we take
away 1 every time we use the array (room[row - 1][seat - 1]) and add 1 back
when we print a seat number.

We split the program into these methods:

bookSeat - books a seat, returns true if it worked and false if not
cancelSeat - same as bookSeat but frees the seat instead
findFirstFree - looks for the first free seat
displayRoom - prints the grid
displayReport - prints the free seats per row, total booked and percentage
readInt - keeps asking for a number until it's in the right range

Some things we decided:

- In bookSeat and cancelSeat we do all the checks first and only change the
  array at the very end. So if something is wrong the method returns false
  before it touches anything and the room stays the same.

- The booking and cancel methods don't print anything themselves, they just
  return true or false. All the messages are printed in main. We did this so
  the methods only have one job.

- readInt checks that the number is in range, and bookSeat/cancelSeat check
  if the seat is free or taken. We kept these two kinds of checks apart like
  the brief said. bookSeat and cancelSeat still check the range as well, just
  to be safe.

- The brief said findFirstFree should give back one integer for both the
  row and the seat, and -1 if the room is full. We used row * 10 + seat, so
  for example 12 is row 1 seat 2 and 45 is row 4 seat 5. In main we get the
  row back with / 10 and the seat with % 10. This only works because there
  are less than 10 seats in a row. -1 can't be a real seat so it's safe to
  use for "full".

- findFirstFree only reads the array and never changes it, so searching
  doesn't book anything by accident. Test 11 in our test log checks this.

- For the percentage we cast to double before dividing. Without the cast,
  integer division would give 0 instead of 5.

- In readInt we use hasNextInt() before reading the number. If someone types
  letters or a decimal like 2.5, we throw it away with next() and ask again.
  Without that the program would crash or get stuck in a loop.


Testing

All our tests are in TestLog.txt. We did 12 runs and they all passed. We
included the two sample checks from the brief (empty room, and booking row 1
seat 1), plus booking the same seat twice, cancelling, cancelling a free
seat, wrong numbers, typing letters, filling the whole room, booking the
last seat and making sure the search doesn't change anything.


Extra

We added a small extra. If you type 777 at any prompt it prints "Welcome Back
Saied Sagar" and then asks the same question again. It doesn't change the
room.


Limitations

- The room size 4 x 5 is typed straight into the code in a few places, so if
  the room size changed we'd have to edit all of them.
- Bookings are only kept while the program is running. When you exit,
  everything is gone because nothing is saved to a file.
