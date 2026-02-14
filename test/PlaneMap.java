
import java.util.Scanner;

import utils.Validation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author 2004d
 */
public class PlaneMap {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        

            // Input: Total number of seats
            System.out.println("Total seats:");
            int totalSeats = sc.nextInt();

            // Calculate the number of rows and remaining seats
            int rows = totalSeats / 6;
            int remainder = totalSeats % 6;

            // Create the seating matrix
            boolean[][] seats = new boolean[rows][6];
            while (true) {
            System.out.println("Row:");
            int r = sc.nextInt();
            System.out.println("Column (A, B, C, D, E, F):");
            char column = sc.next().charAt(0);

            // Convert column character to index (0 to 5)
            int c = column - 'A';

            // Mark the selected seat
            seats[r - 1][c] = true;

            // Print the seating arrangement
            System.out.print("     A   B    C      D   E   F\n");
            for (int i = 0; i < rows; i++) {
                System.out.printf("%2d. ", i + 1); // Print row number
                for (int j = 0; j < 6; j++) {
                    if (seats[i][j]) {
                        System.out.print("(X) "); // Marked seat
                    } else {
                        System.out.print("(_) "); // Empty seat
                    }

                    if (j == 2) {
                        System.out.print("     "); // Add aisle spacing

                    }
                }
                System.out.println();
            }
            if (Validation.checkYesOrNo("Do you want to select another? \nChoose 'Y' for YES || Choose 'N for NO' :  ")) {
                continue;
            }
            break;

        }

    }

}
