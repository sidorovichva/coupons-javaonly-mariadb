package Auxiliaries;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * checks if user input matches the type of input needs from user
 */
public interface InputAble {
    /**
     * @param text message text that will be shown to the user
     * @return user's input as a String
     */
    default String stringInput(String text) {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.print(text + ": ");
            String choice = scan.next();
            return choice;
        } catch (InputMismatchException e) {
            e.getMessage();
            return stringInput(text);
        }
    }

    /**
     * @param text message text that will be shown to the user
     * @return user's input as a String Line
     */
    default String lineInput(String text) {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.print(text + ": ");
            String choice = scan.nextLine();
            return choice;
        } catch (InputMismatchException e) {
            e.getMessage();
            return lineInput(text);
        }
    }

    /**
     * @param text message text that will be shown to the user
     * @return user's input as an integer
     */
    default int intInput(String text) {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.print(text + ": ");
            int choice = scan.nextInt();
            return choice;
        } catch (InputMismatchException e) {
            e.getMessage();
            return intInput(text);
        }
    }

    /**
     * @param text message text that will be shown to the user
     * @return user's input as a double
     */
    default double doubleInput(String text) {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.print(text + ": ");
            double choice = scan.nextDouble();
            return choice;
        } catch (InputMismatchException e) {
            e.getMessage();
            return doubleInput(text);
        }
    }
}
