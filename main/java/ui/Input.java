package ui;

import java.util.Scanner;

public class Input {

    public static String getString(String question) {
        System.out.print(question);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static int getInt(String question) {
        while (true) {
            try {
                return Integer.parseInt(getString(question));
            } catch (Exception e) {
                System.out.println("Det må ikke være et tal ord");
            }
        }
    }

    public static int getTimeInMinutes(String question) {
        while (true) {
            System.out.print(question);
            Scanner scanner = new Scanner(System.in);
            String timeAsString = scanner.nextLine();
            String[] strings = timeAsString.split("\\.");
            try {
                int hoursInMinutes = Integer.parseInt(strings[0]) * 60;
                int minutes = Integer.parseInt(strings[1]);
                return hoursInMinutes + minutes;

            } catch (NumberFormatException e) {
                System.out.println("Fejl i tidsformat. Det skal være på formen hh.mm");
            }
        }
    }

    public static String getMinutesToTimeFormat(int minutes) {
        String hoursText = String.valueOf(minutes / 60);
        String minutesText = String.valueOf(minutes % 60);
        return hoursText + "." + minutesText;
    }


}
