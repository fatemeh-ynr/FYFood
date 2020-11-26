package controller;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ScannerReaders {

    static Scanner scanner = new Scanner(System.in);

    public static int readInt(String message) {
        int input;
        while (true) {
            try {
                System.out.print(message);
                input = scanner.nextInt();
                break;
            } catch (InputMismatchException ex) {
                System.out.println("invalid input: enter a number");
                scanner = new Scanner(System.in);//prevent infinite loop!!!
            }
        }
        return input;
    }

    //=====================================================================
    public static int readInt(String message, int min, int max) {
        int input;
        while (true) {
            try {
                System.out.print(message);
                input = scanner.nextInt();
                if (input < min || input > max) {
                    if (max != Integer.MAX_VALUE)
                        System.out.println("valid range: " + min + "-" + max);
                    else
                        System.out.println("valid range: greater than " + min);
                    continue;
                }
                break;
            } catch (InputMismatchException ex) {
                System.out.println("invalid input: enter a number");
                scanner = new Scanner(System.in);
            }
        }
        return input;
    }

    //=====================================================================
    public static String readNumber(String message) {
        String input;
        while (true) {
            System.out.print(message);
            input = scanner.next();
            if (input.matches("[0-9]+"))
                break;
            System.out.println("invalid input...");
        }
        return input;
    }

    //=====================================================================
    public static String readStringWord(String message) {
        String input;
        while (true) {
            try {
                System.out.print(message);
                input = scanner.next();
                break;
            } catch (InputMismatchException ex) {
                System.out.println("invalid input!");
                scanner = new Scanner(System.in);
            }
        }
        return input;
    }
}
