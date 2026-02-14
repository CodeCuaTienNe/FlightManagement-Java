package view;

import utils.Validation;

/**
 *
 * @author DoanMinhTien
 */
public class Menu {

    public static final String[] MANAGEMET_MENU = {
        "Flight schedule management               |",
        "Passenger reservation and booking        |",
        "Passenger check-in and seat allocation   | ",
        "Assign Crew                              |",
        "Print all list                           |",
        "Save data to files                       |",
        "Close the application                    |"
    };
//Syste("==========================================");
    public static final String[] FLIGHT_MENU = {
        "Add a flight",
        "Update flight information",
        "Delete flight",
        "Show all flights",
        "Back to HOME"
    };

    public static final String[] RESERVATION_MENU = {
        "Booking flight and create reservation",
        "Back to HOME"
    };

    public static final String[] PRINT = {
        "Print all flights                   |",
        "Print flight by flight number       |",
        "Print reservations of flight        |",
        "Back to HOME                        |"
    };

    public static int getChoice(String[] menu) {
        for (int i = 0; i < menu.length; i++) {
            System.out.println("| " + (i + 1) + ". " + menu[i]);
        }

        if (menu == MANAGEMET_MENU) {
            System.out.println("+---------------------------------------------+");
        }
        if (menu == PRINT) {
            System.out.println("+----------------------------------------+");
        }
        return Validation.getInteger("Choose one option: ", "Your number is outside range, try again!!!", 1, menu.length);
    }

    public void excute() {

    }
}
