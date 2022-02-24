package az.iktlab.group.secret_society.entity;

import java.util.Objects;

public class Flight {
    private String flightId;
    private String fromW;
    private String toW;
    private String flightDate;
    private String flightTime;
    private String duration;
    private String freeSeats;

    public Flight() {
    }

    public Flight(String flightID, String fromW, String toW, String flightDate, String flightTime, String duration, String freeSeats) {
        this.flightId = flightID;
        this.fromW = fromW;
        this.toW = toW;
        this.flightDate = flightDate;
        this.flightTime = flightTime;
        this.duration = duration;
        this.freeSeats = freeSeats;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getFromW() {
        return fromW;
    }

    public void setFromW(String fromW) {
        this.fromW = fromW;
    }

    public String getToW() {
        return toW;
    }

    public void setToW(String toW) {
        this.toW = toW;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public String getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(String flightTime) {
        this.flightTime = flightTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(String freeSeats) {
        this.freeSeats = freeSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flights = (Flight) o;
        return flightId.equals(flights.flightId) && fromW.equals(flights.fromW) && toW.equals(flights.toW) && flightDate.equals(flights.flightDate) && flightTime.equals(flights.flightTime) && duration.equals(flights.duration) && freeSeats.equals(flights.freeSeats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightId, fromW, toW, flightDate, flightTime, duration, freeSeats);
    }

    @Override
    public String toString() {
        return
                "\tFlight ID       :" + flightId +
                "\n\tSource        :" + fromW +
                "\n\tDestination   :" + toW +
                "\n\tFlight Date   :" + flightDate +
                "\n\tFlight Time   :" + flightTime +
                "\n\tDuration      :" + duration + "\n\n";
    }
}