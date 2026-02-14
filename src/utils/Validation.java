package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Validation {

    private static Scanner sc = new Scanner(System.in);
    public static String EmptyERR = "Cannot be blank, please enter again!!!";
    public static String StringERR = "Must enter string(s) and cannot be blank, please try again!!!";
    public static String StringFormatRexERR = " Must follow our instruction and cannot be blank, try again!!!";
    public static String IntergerErr = "Must input an interger number and cannot be blank, please input again!!!";
    public static String FloatErr = "Must input an float number and cannot be blank, please input again!!!";

    public static int getInteger(String inputMsg, String errorMsg, boolean canBeBlank) {
        int n;
        while (true) {
            System.out.print(inputMsg);
            String input = sc.nextLine().trim(); // Trim leading/trailing whitespace

            // Check if the input is blank and can be blank
            if (input.isEmpty() && canBeBlank) {
                // Return a sentinel value, e.g., -1, to indicate blank input
                return -1;
            }

            try {
                n = Integer.parseInt(input);
                return n;
            } catch (NumberFormatException e) {
                System.out.println(errorMsg);
            }
        }
    }

    public static int getInteger(String inputMsg, String errorMsg, int lowerBound, int upperBound) {
        int n, tmp;
        //nếu đầu vào lower > upper thì đổi chỗ
        if (lowerBound > upperBound) {
            tmp = lowerBound;
            lowerBound = upperBound;
            upperBound = tmp;
        }
        while (true) {
            try {
                System.out.print(inputMsg);
                n = Integer.parseInt(sc.nextLine());
                if (n < lowerBound || n > upperBound) {
                    throw new Exception();
                }
                return n;
            } catch (Exception e) {
                System.out.println(errorMsg);
            }
        }
    }

    //nhập vào 1 số thực, chặn hết các trường hợp cà chớn 
    public static double getDouble(String inputMsg, String errorMsg) {
        double n;
        while (true) {
            try {
                System.out.print(inputMsg);
                n = Double.parseDouble(sc.nextLine());
                return n;
            } catch (Exception e) {
                System.out.println(errorMsg);
            }
        }
    }

    public static double getDouble(String inputMsg, String errorMsg, double lowerBound, double upperBound) {
        double n, tmp;
        //nếu đầu vào lower > upper thì đổi chỗ
        if (lowerBound > upperBound) {
            tmp = lowerBound;
            lowerBound = upperBound;
            upperBound = tmp;
        }
        while (true) {
            try {
                System.out.print(inputMsg);
                n = Double.parseDouble(sc.nextLine());
                if (n < lowerBound || n > upperBound) {
                    throw new Exception();
                }
                return n;
            } catch (Exception e) {
                System.out.println(errorMsg);
            }
        }
    }

    public static long getLong(String inputMsg, String errorMsg, boolean canBeBlank) {
        long n;
        while (true) {
            System.out.print(inputMsg);
            String input = sc.nextLine().trim(); // Trim leading/trailing whitespace

            // Check if the input is blank and can be blank
            if (input.isEmpty() && canBeBlank) {
                // Return a sentinel value, e.g., -1, to indicate blank input
                return -1;
            }

            try {
                n = Long.parseLong(input);
                return n;
            } catch (NumberFormatException e) {
                System.out.println(errorMsg);
            }
        }
    }

    //nhập vào một chuỗi kí tự, theo định dạng đc đưa vào
    //định dạng xài Regular Expression
    public static String getID(String inputMsg, String errorMsg, String format, boolean canBeBlank) {
        String id;
        boolean match;

        while (true) {
            System.out.print(inputMsg);
            id = sc.nextLine().trim().toUpperCase();

            if (id.isEmpty() && canBeBlank) {
                return "";
            }

            match = id.matches(format);

            if (id.isEmpty() || !match) {
                System.out.println(errorMsg);
            } else {
                return id;
            }
        }
    }

    public static String getString(String inputMsg, String errorMsg, boolean canBeBlank) {
        String id;
        while (true) {
            System.out.print(inputMsg);
            id = sc.nextLine().trim();

            // Check if the input is blank and can be blank
            if (id.isEmpty() && canBeBlank) {
                // Return an empty string to indicate blank input
                return "";
            }

            if (id.isEmpty()) {
                System.out.println(errorMsg);
            } else {
                return id;
            }
        }
    }

    public static boolean isValidDate(Date date) {      //check single date
        if (date == null) {
            System.out.println("Time cannot be empty.");
            return false;
        }

        // Check if date is in the future and at least 24 hours ahead
        Date now = new Date();
        long diffInHours = (date.getTime() - now.getTime()) / (60 * 60 * 1000);
        if (diffInHours < 24) {
            System.out.println("Times must be later than current time (initial flight time) 24 hours");
            return false;
        }

        // Check if the date is realistic
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1; // Calendar months are 0-based
        int year = cal.get(Calendar.YEAR);

        if (day < 1 || day > 31 || month < 1 || month > 12) {
            System.out.println("Invalid day or month");
            return false; // Invalid day or month
        }

        if (month == 2) {
            if (day > 29) {
                System.out.println("Invalid day in February");
                return false; // Invalid day in February
            } else if (day == 29) {
                if (year % 4 != 0 || (year % 100 == 0 && year % 400 != 0)) {
                    System.out.println("Not a leap year");
                    return false; // Not a leap year
                }
            }
        }

        if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
            System.out.println("Invalid day in month: " + month);
            return false; // Invalid day in April, June, September, or November
        }

        return true;
    }

    public static boolean areValidDates(Date startDate, Date endDate) {
        boolean valid = true;

        if (startDate == null || endDate == null) {
            System.out.println("Departure time and arrival time cannot be empty.");
            valid = false;
        }

        // Check if startDate is before endDate
        if (startDate != null && endDate != null && startDate.compareTo(endDate) >= 0) {
            System.out.println("Arrival time must later than departure time, try again!");
            valid = false;
        }

        // Check if the time difference between startDate and endDate is between 30 minutes and 24 hours
        if (startDate != null && endDate != null) {
            long diffInMinutes = (endDate.getTime() - startDate.getTime()) / (60 * 1000);
            if (diffInMinutes <= 30 || diffInMinutes >= 24 * 60) {
                System.out.println("Duration of commercial flight must between 30 minutes and 24 hours");
                valid = false;
            }
        }

        return valid;
    }

    public static Date getDate(String message, String errorMessage, String pattern, boolean allowNull) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        dateFormat.setLenient(false); // Set to strict date parsing
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();

            if (allowNull && input.isEmpty()) {
                return null; // Return null for optional input
            }

            try {
                Date date = dateFormat.parse(input);
                if (isValidDate(date)) {
                    return date;
                }
            } catch (ParseException e) {
                System.out.println(errorMessage);
            }
        }
    }

    public static boolean isBlank(String str) {
        // Check if the string is null or consists only of whitespace characters
        return str == null || str.trim().isEmpty();
    }

    public static boolean areValidTime(Date departureDate, Date arrivalDate) {
        // Get the current time
        Date currentTime = new Date();

        // Calculate the time difference in milliseconds
        long timeDifferenceMillis = departureDate.getTime() - currentTime.getTime();

        // Check if the departure date is in the future
        if (timeDifferenceMillis <= 0) {
            System.out.println("Invalid departure date. Please enter a future date.");
            return false;
        }

        // Check if the departure time is at least 6 hours later than the current time
        long sixHoursInMillis = 24 * 60 * 60 * 1000;
        if (timeDifferenceMillis < sixHoursInMillis) {
            System.out.println("Invalid departure time. Departure time must be at least 24 hours later than initial commercial flight time.");
            return false;
        }

        // Calculate the time difference between departure and arrival in milliseconds
        long timeDifferenceMillisDepartureArrival = arrivalDate.getTime() - departureDate.getTime();

        // Check if the flight duration is longer than 24 hours
        if (timeDifferenceMillisDepartureArrival > 24 * 60 * 60 * 1000) {
            System.out.println("Unrealistic flight. The commercial flight duration cannot be longer than 24 hours, try again!!!");
            return false;
        }
        if (timeDifferenceMillisDepartureArrival < 30 * 60 * 1000) {
            System.out.println("Unrealistic flight. The commercial flight duration must be larger than 30 minutes, try again!!!");
            return false;
        }

        // Check if the departure time is earlier than the arrival time
        if (departureDate.after(arrivalDate)) {
            System.out.println("Invalid date. Departure time must be earlier than arrival time, try again!!!");
            return false;
        }

        return true;
    }

    public static char getColumnIndex(String input, boolean canBeBlank) {
        if (canBeBlank && isBlank(input)) {
            return '\0'; // Hoặc trả về một giá trị mặc định
        }
        char column = Character.toUpperCase(input.charAt(0));
        if (column < 'A' || column > 'F') {
            return '\0'; // Hoặc trả về một giá trị mặc định
        }
        return column;
    }

    public static boolean checkYesOrNo(String msg) {
        while (true) {
            String input = getString(msg, "Invalid input. Please enter Y or N.", false);
            if (input.equalsIgnoreCase("Y")) {
                return true;
            } else if (input.equalsIgnoreCase("N")) {
                return false;
            }
        }
    }
}
