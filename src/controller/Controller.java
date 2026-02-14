/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import model.Crew;
import model.Flight;
import model.Reservation;
import utils.LoadSaveData;
import utils.Validation;
import utils.LoadSaveData.Zip;

/**
 *
 * @author 2004d
 */
public class Controller implements Serializable {

    private final String DATE_TIME_FORMAT = "dd/MM/yyyy-HH:mm";
    private final String DATE_FORMAT = "dd/MM/yyyy";
    private final String FLIGHT_ID_FORMAT = "^F\\d{4}$";
    HashMap<String, Reservation> listReservations;
    HashMap<String, Flight> listFlights;
    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public Controller() {
        this.listReservations = new HashMap<>();
        this.listFlights = new HashMap<>();
    }

//=================================================================================================================================
    public void addFlight() {
        while (true) {
            Flight newFlight = inputFlight();
            newFlight.setFlightCapacity(newFlight.getAvailableSeat()); // Đặt flightCapacity bằng availableSeat cho planeMap
            listFlights.put(newFlight.getFlightNumber(), newFlight);
            System.out.println(newFlight.toString());
            if (Validation.checkYesOrNo("Do you want to add another flight? \nChoose 'Y' for YES || Choose 'N' for NO :  ")) {
                continue;
            }
            break;
        }
    }

    private Flight inputFlight() {
        String flightNumber;
        do {
            flightNumber = Validation.getID("Enter flight number (Must be Fxxxx and no space): ", Validation.StringFormatRexERR, FLIGHT_ID_FORMAT, false);
            if (listFlights.containsKey(flightNumber)) {
                System.out.println("Flight number already exists. Please enter a unique flight number.");
            }
        } while (listFlights.containsKey(flightNumber));
        String departureCity = Validation.getString("Enter departure city: ", Validation.StringERR, false);
        String destinationCity = Validation.getString("Enter destination city: ", Validation.EmptyERR, false);
        Date departureTime;
        Date arrivalTime;
        System.out.println("+------------------------------------------------------------------------------------------+");
        System.out.println("|<> Please enter departure and arrival times base on our instruction:                      |");
        System.out.println("|1. Time cannot be empty.                                                                  |");
        System.out.println("|2. Time format dd/mm/yyyy-HH:mm (day/month/year-Hour:minutes.                             |");
        System.out.println("|3. Times must be later than current time (initial flight time) 24 hours.                  |");
        System.out.println("|4. Duration of commercial flight must between 30 minutes and 24 hours.                    |");
        System.out.println("+------------------------------------------------------------------------------------------+");
        while (true) {
            departureTime = Validation.getDate("Enter departure time (format dd/MM/yyyy-HH:mm): ",
                    "Please follow our format and input reality times, try again", DATE_TIME_FORMAT, false);
            arrivalTime = Validation.getDate("Enter arrival time (format dd/MM/yyyy-HH:mm): ",
                    "Please follow our format and input reality times, try again", DATE_TIME_FORMAT, false);
            if ((!Validation.areValidDates(departureTime, arrivalTime))) {
                continue;
            } else {
                break;
            }
        }
        int availableSeat = Validation.getInteger("Enter availble seat for flight: ",
                "Available seats of commercial flight must between 36 and 853 and cannot be empty, try again!", 36, 853);
//        Chuyến bay dân dụng khai thác thương mại hiện nay có sức chứa hành khách lớn nhất là Airbus A380-800 
//        với sức chứa tối đa là 853 hành khách¹²³⁴. Trong khi đó, máy bay dân dụng ngắn nhất từng được sản xuất 
//        là Yakovlev Yak-40, chỉ có thể chở 32 hành khách⁸.
        System.out.println("Flight infomation: ");
        return new Flight(flightNumber, departureCity, destinationCity, departureTime, arrivalTime, availableSeat);
    }

    //-------------------SUPPORT--FLIGHT---------------------------------   
    //get flight by flightNumber
    public Flight getFlight(String flightNumber) {
        return listFlights.get(flightNumber);
    }

    public HashMap<String, Flight> searchFlight(String location, Date date) {
        HashMap<String, Flight> searchedFlights = new HashMap<>();

        for (Flight flight : listFlights.values()) {
            String flightDepartureCity = flight.getDepartureCity().toLowerCase();
            String flightDestinationCity = flight.getDestinationCity().toLowerCase();
            Date flightDepartureDate = flight.getDepartureTime();
            location = location.toLowerCase();

            if ((flightDepartureCity.contains(location) || flightDestinationCity.contains(location))
                    && dateFormat.format(flightDepartureDate).equals(dateFormat.format(date))) {
                searchedFlights.put(flight.getFlightNumber(), flight);
            }
        }
        return searchedFlights;
    }

    private Flight getSelectedFlight(HashMap<String, Flight> foundFlights) {
        int index = 1;
        System.out.println("+-----+--------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+");
        System.out.println("|Index|Flight number |   Departure City   | Destination City   |   Departure time   |    Arrival time    |    Available Seat  |   Flight Duration  |");
        System.out.println("+-----+--------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+");
        // Display flight choices
        for (Flight flight : foundFlights.values()) {
            System.out.printf("|%5d| %-12s | %-18s | %-18s | %-18s | %-18s | %-18d | %-18s |\n", index,
                    flight.getFlightNumber(), flight.getDepartureCity(),
                    flight.getDestinationCity(), dateFormat.format(flight.getDepartureTime()),
                    dateFormat.format(flight.getArrivalTime()), flight.getAvailableSeat(), flight.getDuration());
            System.out.print("+-----+--------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+\n");
            index++;
        }

        while (true) {
            int selectedFlightNumber = Validation.getInteger("Select a flight by entering the corresponding  number at the 'index' column of each flight: ",
                    "Invalid selection, please try again", 1, index);

            if (selectedFlightNumber >= 1 && selectedFlightNumber <= foundFlights.size()) {
                Flight[] foundFlightsArray = foundFlights.values().toArray(new Flight[foundFlights.size()]);
                return foundFlightsArray[selectedFlightNumber - 1];
            } else {
                System.out.println("Invalid selection. Please select a valid flight.");
            }
        }
    }
    //===============================================================================

    public void bookingProgess() {
        while (true) {
            String location = Validation.getString("Please input a location (departure or destination)): ", Validation.StringERR, false);
            Date date = Validation.getDate("Please enter date (departure or arrival) to find flight(dd/mm/yyyy): ", "Please follow our format and input reality times, try again", DATE_FORMAT, false);
            HashMap<String, Flight> foundFlights = searchFlight(location, date);

            if (foundFlights.isEmpty()) {
                System.out.println("No flight found");
                return;
            }

            System.out.println("Found Flight(s):");

            Flight selectedFlight = getSelectedFlight(foundFlights);

            if (getReservationsForFlight(selectedFlight.getFlightNumber()).size() >= selectedFlight.getAvailableSeat()) {
                System.out.println("Available slots for the flight are running out. Cannot add a reservation.");
                return;
            }

            Reservation reservation = inputReservation(selectedFlight.getFlightNumber());
            listReservations.put(reservation.getReservationID(), reservation);

            int remainingSeats = selectedFlight.getAvailableSeat();
            remainingSeats--;
            selectedFlight.setAvailableSeat(remainingSeats);

            System.out.println("Reservation ID: " + reservation.getReservationID() + " added successfully."
                    + "\nReservation ID is required for check-in progress, selecting a seat, and receiving a boarding pass\n");
            System.out.println(reservation.toString());
            System.out.println("When you go to the airport, please select the 'Flight check-in' option to choose your seat and receive your boarding pass.\n");

            if (Validation.checkYesOrNo("Do you want to create another reservation? \nChoose 'Y' for YES || Choose 'N' for NO :  ")) {
                continue;
            }

            break;
        }
    }

    //personal info
    private Reservation inputReservation(String flightNumber) {
        String name = Validation.getString("Enter name: ", Validation.StringERR, false);
        String address = Validation.getString("Enter address: ", Validation.StringERR, false);
        long phoneNumber = Validation.getLong("Enter phone number: ", Validation.IntergerErr, false);
        long identityCardNumber = Validation.getLong("Enter identity card number: ", Validation.IntergerErr, false);
        return new Reservation(name, address, phoneNumber, identityCardNumber, flightNumber);
    }

    private void selectSeat(Flight flight, Reservation reservation) {
        LinkedHashMap<String, Boolean> seats = flight.getSeatList();

        if (seats == null) {
            System.out.println("No seat information available for this flight.");
            return;
        }

        System.out.println("Available seats for flight " + flight.getFlightNumber() + ": ");
        showSeatsMap(seats);

        int maxAttempts = 3;
        int attempts = 0;

        while (attempts < maxAttempts) {
            String selectedSeat = Validation.getString("Enter the seat number you want to choose: ", "Seat number should not be blank, please try again", false);

            if (seats.containsKey(selectedSeat)) {
                if (seats.get(selectedSeat)) {
                    seats.put(selectedSeat,false); // Mark the selected seat as unavailable
                    reservation.setSeatLocation(selectedSeat); // Set the selected seat for the reservation
                    updateReservation(reservation.getReservationID(), reservation); // Update the reservation with the selected seat
                    System.out.println("Seat assigned successful!");
                    break; // Exit the loop after a successful seat assignment
                } else {
                    System.out.println("Selected seat is already taken. Please choose another seat.");
                }
            } else {
                System.out.println("Invalid seat number. Please choose a valid seat.");
            }

            attempts++;

            if (attempts >= maxAttempts) {
                System.out.println("Maximum attempts reached. Seat assignment canceled.");
                break;
            }
        }
    }

    private void showSeatsMap(LinkedHashMap<String, Boolean> seats) {

        int maxSeatsPerRow = 4; // Maximum seats per row
        int seatIndex = 0;
        int row = 1;
        System.out.println("=====================================================================");

        for (String seat : seats.keySet()) {
            if (seatIndex == 0) {
                System.out.printf("Row %-4d|", row);
            }

            if (seats.get(seat)) {
                System.out.printf(" %-6s |", seat);
            } else {
                System.out.printf(" %s(x) |", seat);
            }
            seatIndex++;

            if (seatIndex == 2) {
                System.out.print("                       |");
            }

            if (seatIndex >= maxSeatsPerRow) {
                System.out.println();
                seatIndex = 0;
                row++;
            }
        }
        System.out.println("=====================================================================");
    }



public void checkInProgress() {
        while (true) {
            String reservationID = Validation.getString("Please input reservation ID: ", Validation.StringERR, false);
            Reservation reservation = getReservation(reservationID);

            if (reservation == null) {
                System.out.println("No such reservation ID found!");
                return;
            }

            Flight flightOfReservation = getFlight(reservation.getReservationFlightNumber());

            if (flightOfReservation == null) {
                System.out.println("No such flight found for this reservation!");
                return;
            }

            if (reservation.getSeatLocation() == null || reservation.getSeatLocation().equals("   X")) {
                System.out.println("Please choose your seat on this journey: ");
                System.out.println();
                selectSeat(flightOfReservation, reservation);
            }

            System.out.println(reservation.boardingPassToString(flightOfReservation));

            if (reservation.isCheckedIn()) {
                System.out.println("This reservation has already been checked in. Please try with another reservation.");
                return;
            }

            reservation.checkIn();

            if (Validation.checkYesOrNo("Do you want to get another boarding pass? \nChoose 'Y' for YES || Choose 'N for NO' :  ")) {
                continue;
            }
            break;
        }
    }

    //--------------------------------------SUPPORT-RESERVATION-------------------------------------------
    public void updateReservation(String reservationID, Reservation updatedReservation) {
        listReservations.put(reservationID, updatedReservation);
    }

    public Reservation getReservation(String reservationID) {
        return listReservations.get(reservationID);
    }

    public HashMap<String, Reservation> getReservationsForFlight(String flightNumber) {
        HashMap<String, Reservation> flightReservations = new HashMap<>();
        for (Reservation reservation : listReservations.values()) {
            if (reservation.getReservationFlightNumber().equals(flightNumber)) {
                flightReservations.put(reservation.getReservationID(), reservation);
            }
        }
        return flightReservations;
    }

//======================================================================================================
    public void assignCrew() {
        while (true) {
            String flightNumber = Validation.getID("Enter flight number (Fxxxx and no space): ",
                    Validation.StringFormatRexERR, FLIGHT_ID_FORMAT, false);

            if (!listFlights.containsKey(flightNumber)) {
                System.out.println("Flight number does not exist. Cannot assign crew.");
                return;
            }

            Flight flight = getFlight(flightNumber);

            if (flight.getCrewMembers() != null && !flight.getCrewMembers().isEmpty()) {
                System.out.println("This flight already has a crew assigned.");
                return;
            }

            List<Crew> CrewOfFlight = inputCrew();

            if (CrewOfFlight.isEmpty()) {
                continue;
            }

            flight.setCrewMembers(CrewOfFlight);
            System.out.println("Crew of flight: " + flight.getFlightNumber() + " added successfully");
            System.out.println();
            System.out.println(Crew.crewListToString(CrewOfFlight));

            if (Validation.checkYesOrNo("Do you want to add crew for another flight? \nChoose 'Y' for YES || Choose 'N' for NO :  ")) {
                continue;
            } else {
                break;
            }
        }
    }

    private List<Crew> inputCrew() {
        List<Crew> crewList = new ArrayList<>();
        int pilotCount = 0;
        int attendantCount = 0;
        int groundStaffCount = 0;
        System.out.println("+-------------------------------------Assign-Crew------------------------------------------+");
        System.out.println("|<> Please assign crew base on our instruction:                                            |");
        System.out.println("|1. Must have at least one crew member for each position (Pilot, Attendant, Ground Staff). |");
        System.out.println("|2. Maximum 2 pilots allowed                                                               |");
        System.out.println("|3. Crew members must less or equal than initial quantity of crew members                  |");
        System.out.println("+------------------------------------------------------------------------------------------+");
        System.out.println();
        int maxCrewMember = Validation.getInteger("Please input quantity of crew members: ", Validation.IntergerErr, false);

        while (crewList.size() < maxCrewMember) {
            String name = Validation.getString("Please input name of crew member(Enter 'Q' if you want to stop input new crew members) ): ",
                    "Should not be blank, please try again!", false);

            if (name.equalsIgnoreCase("Q")) {
                break;                                //dung truoc khi du so luong
            }

            int choice = Validation.getInteger("Input their position (1. Pilot - 2. Attendant - 3. Ground Staff): ",
                    "Must be an integer and cannot be blank", 1, 3);

            String position = "";
            switch (choice) {
                case 1:
                    if (pilotCount >= 2) {
                        System.out.println("Maximum 2 pilots allowed.");
                        continue;
                    }
                    position = "Pilot";
                    pilotCount++;
                    break;
                case 2:
                    position = "Attendant";
                    attendantCount++;
                    break;
                case 3:
                    position = "Ground Staff";
                    groundStaffCount++;
                    break;
                default:
                    System.out.println("Invalid position");
                    continue;
            }

            crewList.add(new Crew(name, position));
        }

        if (pilotCount < 1 || attendantCount < 1 || groundStaffCount < 1) {
            System.out.println("You must have at least one crew member for each position (Pilot, Attendant, Ground Staff).");
        }

        return crewList;
    }

//===========================================================================================================================
    public void printAllFlights() { //by desc departure time
        List<Flight> flightList = new ArrayList<>(listFlights.values());
        Collections.sort(flightList, (flight1, flight2) -> flight2.getDepartureTime().compareTo(flight1.getDepartureTime()));
        System.out.println(Flight.listFlightsToString(flightList));
    }

    //lam them cho zui
    public void printFlightByFlightNumber() {
        while (true) {
            String flightNumber = Validation.getID("Enter flight number (Must be Fxxxx and no space): ", Validation.StringFormatRexERR, FLIGHT_ID_FORMAT, false);

            if (listFlights.containsKey(flightNumber)) {
                Flight flight = listFlights.get(flightNumber);
                System.out.println(flight);
            } else {
                System.out.println("Flight not found with Flight Number: " + flightNumber);
            }
            if (Validation.checkYesOrNo("Do you want to print another flight? \nChoose 'Y' for YES || Choose 'N' for NO :  ")) {
                continue;
            }
            break;
        }
    }

    public void printReservationOfFlight() {
        String flightNumber = Validation.getID("Enter flight number (Must be Fxxxx and no space): ", Validation.StringFormatRexERR, FLIGHT_ID_FORMAT, false);
        boolean hasReservations = false; // Biến kiểm tra xem có đặt chỗ nào cho chuyến bay hay không

        if (listFlights.containsKey(flightNumber)) {
            Flight selectedFlight = listFlights.get(flightNumber);
            System.out.println("Reservations for flight: " + flightNumber + ":");

            System.out.println("+--------------+--------------------+--------------------+--------------------+--------------------+------------------------------------+");
            System.out.println("|ReservationID |        Name        |     Phone Number   |   ID Card Number   |    Seat Number     |                  Address           |");
            System.out.println("+--------------+--------------------+--------------------+--------------------+--------------------+------------------------------------+");

            for (Reservation reservation : listReservations.values()) {
                if (reservation.getReservationFlightNumber().equals(flightNumber)) {
                    if (reservation.getSeatLocation() == null) {
                        reservation.setSeatLocation("   X");
                    }

                    System.out.printf("| %-12s | %-18s | %-18s | %-18s | %-18s |%-36s|\n", reservation.getReservationID(),
                            reservation.getName(), reservation.getPhoneNumber(), reservation.getIdentityCardNumber(),
                            reservation.getSeatLocation(), reservation.getAddress());
                    System.out.print("+--------------+--------------------+--------------------+--------------------+--------------------+------------------------------------+\n");

                    hasReservations = true; // Đặt hasReservations thành true nếu tìm thấy ít nhất một đặt chỗ
                }
            }

            if (!hasReservations) {
                System.out.println("No reservation in flight yet");
            }
        } else {
            System.out.println("Flight is not exist");
        }
    }

//=======================================================================================================
    public void loadData() {
        List<HashMap> temp = new ArrayList<>();
        temp.add(listReservations);
        temp.add(listFlights);
        Zip data = new Zip(temp);

        data = LoadSaveData.loadFromFile("product.dat", true);

        if (data != null) {
            temp = data.unZip().get(0);
            listReservations.putAll(temp.get(0));
            listFlights.putAll(temp.get(1));
        }
    }

    public void saveData() {
        List<HashMap> temp = new ArrayList<>();
        temp.add(listReservations);
        temp.add(listFlights);
        Zip data = new Zip(temp);

        LoadSaveData.saveToFile("product.dat", data, true);
    }

}
