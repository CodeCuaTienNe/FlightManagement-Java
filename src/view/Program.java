/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import utils.Validation;

/**
 *
 * @author 2004d
 */
public class Program {

    Controller controller = new Controller();
    private int choice = 0;
    private boolean returnToMainMenu = false;

    public void execute() {
        controller.loadData();
        while (true) {
            System.out.println("+---------------------------------------------+");
            System.out.println("|           FLIGHT MANAGEMENT SYSTEM          |");
            System.out.println("+---------------------------------------------+");
            choice = Menu.getChoice(Menu.MANAGEMET_MENU);
            returnToMainMenu = false; // Đảm bảo mỗi khi vào MainMenu thì returnToMainMenu là false

            switch (choice) {
                case 1:
                    controller.addFlight();
                    break;
                case 2:
                    controller.bookingProgess();
                    break;
                case 3:
                    controller.checkInProgress();

                    break;
                case 4:
                    controller.assignCrew();

                    break;
                case 5:
                    do {
                        System.out.println();
                        System.out.println("+-------------PRINT-INFOMATION-----------+");
                        int c2 = Menu.getChoice(Menu.PRINT);
                        switch (c2) {
                            case 1:
                                controller.printAllFlights();

                                break;
                            case 2:
                                controller.printFlightByFlightNumber();
                                break;
                            case 3:
                                controller.printReservationOfFlight();
                                break;
                            case 4:
                                returnToMainMenu = true; // Thoát submenu và quay lại MainMenu
                                break;
                        }
                    } while (!returnToMainMenu);
                    //print lists
                    break;
                case 6:
                    controller.saveData();
                    break;
                case 7:
                    System.out.println("HAVE A NICE DAY, GOODBYE");
                    System.exit(0);
                    break;
            }

            System.out.println();
        }
    }

}
