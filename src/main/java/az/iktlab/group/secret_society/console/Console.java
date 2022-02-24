package az.iktlab.group.secret_society.console;


import az.iktlab.group.secret_society.controller.Controller;
import az.iktlab.group.secret_society.service.FlightService;
import az.iktlab.group.secret_society.service.TicketService;

import java.io.IOException;
import java.util.Scanner;

public class Console {
    public static Scanner SCANNER = new Scanner(System.in);
    public static Controller controller = new Controller();

    public void printCommand() {
        System.out.println();
        System.out.println(
                "LIST OF COMMANDS\n" +
                        "#1 > All Flights During 24 Hours\n" +
                        "#2 > Flight Information\n" +
                        "#3 > Searching and Booking process: \n" +
                        "#4 > Canceling the Booking: \n" +
                        "#5 > My Flights: \n" +
                        "#6 > Exit");
        System.out.println();
    }

    public void setDirection() throws IOException {
        printCommand();
        System.out.print("Enter index for Direction: ");
        int index = SCANNER.nextInt();
        while (true) {
            if (index < 1 || index > 6) {
                System.out.println("Wrong Data! Enter Index Again!");
                index = SCANNER.nextInt();
            } else {
                break;
            }
        }
        getDirection(index);
    }

    public void helper() throws IOException {
        System.out.println();
        setDirection();
    }

    public void getDirection(int index) throws IOException {
        System.out.println();
        if (index == 1) {
            System.out.println(">>> All Flights During 24 Hours:");
            controller.getNextDay();
            helper();
        } else if (index == 2) {
            System.out.println(">>> Flight Information:");
            String a = setKey();
            controller.getKeyAsId(
                    a
            );
            helper();
        } else if (index == 3) {
            System.out.println(">>> Searching and Booking process:");
            String[] a = setKeysForBooking();
            controller.getKeysForBooking(a);
            helper();
        } else if (index == 4) {
            System.out.println(">>> Canceling the Booking:");
            TicketService ticketService = new TicketService();
            ticketService.deleteTicket();
            helper();
        } else if (index == 5) {
            System.out.println(">>> My Flights:");
            FlightService flightService = new FlightService();
            flightService.getDATA();
            helper();
        } else {
            System.out.println(">>> Successfully Exited! <<<");
        }
    }

    public String setKey() {
        System.out.print("Enter Key for Searching: ");
        String key = SCANNER.next();
        while (key.length() < 3) {
            System.out.println("Wrong Data! Enter Key for Searching Again!");
            key = SCANNER.next();
        }
        return key;
    }

    public String[] setKeysForBooking() {
        String[] array = new String[3];
        System.out.print("Enter Destination for Booking       :\t ");
        String destinationKey = SCANNER.next();

        while (destinationKey.length() < 3) {
            System.out.println("Wrong Data! Enter Destination for Booking Again!");
            destinationKey = SCANNER.next();
        }

        array[0] = destinationKey;

        System.out.print("Enter Date for Booking              :\t ");
        String dateKey = SCANNER.next();

        while (!dateKey.matches("([0-9]{2}).([0-9]{2}).2022")) {
            System.out.println("Wrong Data! Enter Date for Booking Again!(dd.MM.yyyy)");
            dateKey = SCANNER.next();
        }

        array[1] = dateKey;

        System.out.print("Enter Number of Tickets for Booking :\t ");
        int numberKey = SCANNER.nextInt();

        while (numberKey > 3) {
            System.out.println("Wrong Data! Enter Number of Tickets for Booking Again!(Number of tickets should be less than 4)");
            numberKey = SCANNER.nextInt();
        }

        array[2] = String.valueOf(numberKey);

        return array;
    }

}

