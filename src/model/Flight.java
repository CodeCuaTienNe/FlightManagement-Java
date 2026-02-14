/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author 2004d
 */
public class Flight implements Serializable {
//chuyen bay phai lon hon tgian thuc 3h, o tuong lai

    private String flightNumber;
    private String departureCity;
    private String destinationCity;
    private Date departureTime;
    private Date arrivalTime;
    private int flightCapacity;         //capacity of plane no change
    private int availableSeat;          //decrease with each reservation is booked
    private List<Crew> crewMembers = new ArrayList();
    private LinkedHashMap<String, Boolean> seatList;

    public Flight(String flightNumber, String departureCity, String destinationCity, Date departureTime, Date arrivalTime, int availableSeat) {
        this.flightNumber = flightNumber;
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.flightCapacity = availableSeat; // Khởi tạo flightCapacity bằng availableSeat
        this.availableSeat = availableSeat;
        this.seatList = new LinkedHashMap<>();
        generateSeatList();
    }

    public int getFlightCapacity() {
        return flightCapacity;
    }

    public void setFlightCapacity(int flightCapacity) {
        this.flightCapacity = flightCapacity;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public LinkedHashMap<String, Boolean> getSeatList() {
        return seatList;
    }

    public void setSeatList(LinkedHashMap<String, Boolean> seatList) {
        this.seatList = seatList;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getAvailableSeat() {
        return availableSeat;
    }

    public void setAvailableSeat(int availableSeat) {
        this.availableSeat = availableSeat;
    }

    public List<Crew> getCrewMembers() {
        return crewMembers;
    }

    public void setCrewMembers(List<Crew> crewMembers) {
        this.crewMembers = crewMembers;
    }

    private void generateSeatList() {
        int row = 1;
        char[] seatLetters = {'A', 'B', 'C', 'D'};
        int seatIndex = 0;

        for (int i = 0; i < flightCapacity; i++) {
            String seatNumber = row + String.valueOf(seatLetters[seatIndex]);
            seatList.put(seatNumber, true);

            seatIndex++;
            if (seatIndex >= seatLetters.length) {
                seatIndex = 0;
                row++;
            }
        }
    }

   
    public void assignCrew(List<Crew> crewMembers) {
        this.crewMembers = crewMembers;
    }

    public void addCrewMember(Crew crew) {
        crewMembers.add(crew);
    }

    //list flight
    public static String listFlightsToString(List<Flight> listFlights) {
        StringBuilder sb = new StringBuilder();
        sb.append("+--------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+\n");
        sb.append("|Flight number |   Departure City   | Destination City   |   Departure time   |    Arrival Time    |    Available Seat  |   Flight Duration  |\n");
        sb.append("+--------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+\n");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm");

        for (Flight flight : listFlights) {
            sb.append(String.format("| %-12s | %-18s | %-18s | %-18s | %-18s | %-18d | %-18s |\n",
                    flight.getFlightNumber(), flight.getDepartureCity(),
                    flight.getDestinationCity(), dateFormat.format(flight.getDepartureTime()),
                    dateFormat.format(flight.getArrivalTime()), flight.getAvailableSeat(), flight.getDuration()));
            sb.append("+--------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+\n");
        }
        return sb.toString();
    }


    @Override
    public String toString() {      //in cho manager
        StringBuilder sb = new StringBuilder();
        sb.append("+---------------------------------------------------------------+\n");
        sb.append("|                      FLIGHT INFOMATION                        |\n");
        sb.append("+---------------------------+-----------------------------------+\n");
        sb.append(String.format("| Flight Number             | %-27s       |\n", flightNumber));
        sb.append(String.format("| Departure City            | %-27s       |\n", departureCity));
        sb.append(String.format("| Destination City          | %-27s       |\n", destinationCity));
        sb.append(String.format("| Departure Time            | %-27s       |\n", (new SimpleDateFormat("dd/MM/yyyy-HH:mm")).format(departureTime)));
        sb.append(String.format("| Arrival Time              | %-27s       |\n", (new SimpleDateFormat("dd/MM/yyyy-HH:mm")).format(arrivalTime)));
        sb.append(String.format("| Available Seat            | %-27d       |\n", availableSeat));
        sb.append(String.format("| Flight duration           | %-27s       |\n", getDuration()));
        sb.append("+---------------------------+-----------------------------------+\n");

        // Crew Information
        if (crewMembers != null && !crewMembers.isEmpty()) {
            sb.append("Crew Members of Flight: \n");
            sb.append("+-------------------+-------------------+\n");
            sb.append("|        Name       |     Position      |\n");
            sb.append("+-------------------+-------------------+\n");
            for (Crew crew : crewMembers) {
                sb.append(crew.toString());
            }
        } else {
            sb.append("Crew Members: No information\n");
        }

        return sb.toString();
    }

    public String getDuration() {
        Date currentTime = new Date();
        // Calculate the time difference in milliseconds
        long timeDifferenceMillis = arrivalTime.getTime() - departureTime.getTime();

        // Calculate hours and minutes
        long hours = timeDifferenceMillis / (60 * 60 * 1000);
        long minutes = (timeDifferenceMillis % (60 * 60 * 1000)) / (60 * 1000);

        // Format the duration as "hh:mm"
        return String.format("%02d:%02d", hours, minutes);
    }
}
