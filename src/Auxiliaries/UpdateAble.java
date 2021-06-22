package Auxiliaries;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * checks if user input matches the type of input needs from user
 * in case than user has an option to avoid giving an answer
 */
public interface UpdateAble {
    /**
     * @param text message text that will be shown to the user
     * @param oldText a text before update which user can leave as a new input if choose "0";
     * @return user's input as a String
     */
    default String stringUpdate(String text, String oldText) {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.print(text + "(0 to leave the same): ");
            String choice = scan.next();
            if (choice.equals("0") || choice.equals("")) return oldText;
            else return choice;
        } catch (InputMismatchException e) {
            e.getMessage();
            return stringUpdate(text, oldText);
        }
    }

    /**
     * @param text message text that will be shown to the user
     * @param oldText a text before update which user can leave as a new input if choose "0";
     * @return user's input as a Sting Line
     */
    default String lineUpdate(String text, String oldText) {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.print(text + "(0 to leave the same): ");
            String choice = scan.nextLine();
            if (choice.equals("0") || choice.equals("")) return oldText;
            else return choice;
        } catch (InputMismatchException e) {
            e.getMessage();
            return lineUpdate(text, oldText);
        }
    }

    /**
     * @param text message text that will be shown to the user
     * @param oldNumber a number before update which user can leave as a new input if choose "0";
     * @return user's input as an integer
     */
    default int intUpdate(String text, int oldNumber) {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.print(text + "(0 to leave the same): ");
            int choice = scan.nextInt();
            if (choice == 0) return oldNumber;
            else return choice;
        } catch (InputMismatchException e) {
            e.getMessage();
            return intUpdate(text, oldNumber);
        }
    }

    /**
     * @param text message text that will be shown to the user
     * @param oldNumber a number before update which user can leave as a new input if choose "0";
     * @return user's input as a double
     */
    default double doubleUpdate(String text, double oldNumber) {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.print(text + "(0 to leave the same): ");
            double choice = scan.nextDouble();
            if (choice == 0) return oldNumber;
            else return choice;
        } catch (InputMismatchException e) {
            e.getMessage();
            return doubleUpdate(text, oldNumber);
        }
    }
}
