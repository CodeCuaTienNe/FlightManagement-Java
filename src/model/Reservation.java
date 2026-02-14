/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author 2004d
 */
public class Reservation implements Serializable {

    Flight flight;
    Map<String, Reservation> resevationsList;
    private static int reservationIDCount = 0;
    private String reservationID;
    private String name;
    private String address;
    private long phoneNumber;
    private long identityCardNumber;
    private String reservationFlightNumber;
    private String seatLocation;
    private boolean checkedIn;

    public Reservation(String name, String address, long phoneNumber, long identityCardNumber, String reservationFlightNumber) {
        this.reservationID = generateReservationCode();
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.identityCardNumber = identityCardNumber;
        this.reservationFlightNumber = reservationFlightNumber;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public static int getReservationIDCount() {
        return reservationIDCount;
    }

    public static void setReservationIDCount(int reservationIDCount) {
        Reservation.reservationIDCount = reservationIDCount;
    }

    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getIdentityCardNumber() {
        return identityCardNumber;
    }

    public void setIdentityCardNumber(long identityCardNumber) {
        this.identityCardNumber = identityCardNumber;
    }

    public String getReservationFlightNumber() {
        return reservationFlightNumber;
    }

    public void setReservationFlightNumber(String reservationFlightNumber) {
        this.reservationFlightNumber = reservationFlightNumber;
    }

    public String getSeatLocation() {
        return seatLocation;
    }

    public void setSeatLocation(String seatLocation) {
        this.seatLocation = seatLocation;
    }

    public void checkIn() {
        if (!isCheckedIn()) {
            // Nếu trạng thái check-in là `false`, thì cập nhật nó thành `true`
            setCheckedIn(true);
        }
    }

    public boolean isCheckedIn() {
        // Trả về trạng thái check-in
        return checkedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    private String generateReservationCode() {
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int digit = random.nextInt(10); // Sinh số ngẫu nhiên từ 0 đến 9
            codeBuilder.append(digit);
        }

        return codeBuilder.toString();
    }

    //ko xai
    public static String listFlightsReservationOfFlight(List<Reservation> listReservations) {
        StringBuilder sb = new StringBuilder();
        sb.append("+--------------+--------------------+--------------------+--------------------+--------------------+----------------------------------------+\n");
        sb.append("|ReservationID |        Name        |     Phone Number   |   ID Card Number   |    Seat Number     |                  Address                |\n");
        sb.append("+--------------+--------------------+--------------------+--------------------+--------------------+-----------------------------------------+\n");

        for (Reservation reservation : listReservations) {
            sb.append(String.format("| %-12s | %-18s | %-18s | %-18s | %-18s |%-36s|\n", reservation.getReservationID(),
                    reservation.getName(), reservation.getPhoneNumber(), reservation.identityCardNumber,
                    reservation.seatLocation, reservation.address));

            sb.append("+--------------+--------------------+--------------------+--------------------+--------------------+-----------------------------------------+\n");

        }
        return sb.toString();
    }

    public String boardingPassToString(Flight Dataflight) {
        StringBuilder sb = new StringBuilder();
        sb.append("+--------------------------------------------------------------------------------+\n");
        sb.append("|                                   BOARDING PASS                                |\n");
        sb.append("+--------------------------------------------------------------------------------+\n");
        sb.append("|         Client infomation                               Flight infomation      |\n");
        sb.append(String.format("| ReservationID : %-18s        Flight number   : %-18s |\n", reservationID, Dataflight.getFlightNumber()));
        sb.append(String.format("| Name          : %-18s        Departure city  : %-18s |\n", name, Dataflight.getDepartureCity()));
        sb.append(String.format("| Phone Number  : %-18s        Destination city: %-18s |\n", phoneNumber, Dataflight.getDestinationCity()));
        sb.append(String.format("| ID Card Number: %-18s        Departure time  : %-18s |\n", identityCardNumber, (new SimpleDateFormat("dd/MM/yyyy-HH:mm")).format(Dataflight.getDepartureTime())));
        sb.append(String.format("| Seat Number   : %-18s        Arrival time    : %-18s |\n", seatLocation, (new SimpleDateFormat("dd/MM/yyyy-HH:mm")).format(Dataflight.getArrivalTime())));
        sb.append("|                                                                                |\n");
        sb.append("|                THANKS FOR CHOOSING OUR SERVICE, HAVE A NICE DAY                |\n");
        sb.append("+--------------------------------------------------------------------------------+\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("+---------------------------------------------------------------+\n");
        sb.append("|                   Reservation Information                     |\n");
        sb.append("+---------------------------+-----------------------------------+\n");
        sb.append(String.format("| Reservation ID            | %-27s       |\n", reservationID));
        sb.append(String.format("| Name                      | %-27s       |\n", name));
        sb.append(String.format("| Address                   | %-27s       |\n", address));
        sb.append(String.format("| Phone Number              | %-27s       |\n", phoneNumber));
        sb.append(String.format("| ID Card Number            | %-27s       |\n", identityCardNumber));
        sb.append(String.format("| Reservation Flight Number | %-27s       |\n", reservationFlightNumber));
        sb.append("+---------------------------+-----------------------------------+\n");
        return sb.toString();
    }

}
