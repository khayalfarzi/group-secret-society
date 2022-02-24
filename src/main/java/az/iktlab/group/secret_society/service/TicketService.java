package az.iktlab.group.secret_society.service;

import az.iktlab.group.secret_society.DAO.TicketDAO;
import az.iktlab.group.secret_society.DB.SQL;
import az.iktlab.group.secret_society.entity.Flight;
import az.iktlab.group.secret_society.entity.Ticket;
import az.iktlab.group.secret_society.entity.User;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TicketService implements TicketDAO {
    public static Scanner scanner = new Scanner(System.in);
    public String planeID;
    public int number;
    public Flight flight;

    public TicketService() {
    }

    public TicketService(String planeID, int number, Flight flight) {
        this.planeID = planeID;
        this.number = number;
        this.flight = flight;
    }

    public String getAndCheckID() {
        return scanner.next();
    }

    public void createTicket() {

        System.out.print("Enter Plane ID  :\t");
        String ID = scanner.next();

        while (!(ID.equalsIgnoreCase(planeID))) {
            System.out.println(planeID);
            System.out.print("Enter Plane ID  :\t");
            ID = getAndCheckID();
        }

        for (int i = 0; i < number; i++) {
            User user = new User();
            Scanner SCANNER = new Scanner(System.in);
            System.out.print("Enter Name      :\t");
            user.setName(SCANNER.next());
            String nickN = user.getName();
            System.out.print("Enter Surname   :\t");
            user.setSurname(SCANNER.next());
            String nickS = user.getSurname();


            setTicketData(user, nickN, nickS);
        }
    }

    public void deleteTicket() {

        System.out.print("Enter Ticket ID :\t");
        String ID = scanner.next();

        String checker = String.format("DELETE FROM helper.data_list WHERE ticket_id = '%s'", ID);

        Statement statement = SQL.getStatement();

        try {
            statement.execute(checker);
            System.out.println("Ticket Deleted Successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Wrong Ticket ID! Please Try Again!");
            deleteTicket();
        }
    }


    @Override
    public void setTicketData(User user, String nickN, String nickS) {
        Ticket ticket = new Ticket();
        ticket.setUser(user);


        flight.setFlightId(planeID);
        ticket.setFlight(flight);

        System.out.println("\n************\n" + ticket + "\n************\n");

        String sql = String.format("INSERT INTO helper.data_list (name, surname, plane_id, ticket_id) VALUES ('%s', '%s', '%s', '%s')",
                nickN.toUpperCase(),
                nickS.toUpperCase(),
                planeID,
                ticket.ticketId);

        Statement statement = SQL.getStatement();

        try {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

