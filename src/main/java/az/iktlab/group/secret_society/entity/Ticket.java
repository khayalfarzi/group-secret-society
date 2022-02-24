package az.iktlab.group.secret_society.entity;

import java.util.Random;

public class Ticket {
    public User user;
    public Flight flight;
    public String ticketId;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }


    public String setTicketId() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 6;
        Random random = new Random();

        this.ticketId = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return ticketId;
    }

    @Override
    public String toString() {
        return ">>> Ticket Is Created:\n" +
                "\tTicket ID   :\t" + setTicketId() + '\n' +
                "\tBuyer       :\t" + user + '\n' +
                ">>>Travel Information:\n" + flight;
    }
}
