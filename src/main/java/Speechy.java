import java.util.Scanner;

/**
 * This is where the application execution begins.
 *
 * @author loren.ivanov
 */
public class Speechy {

    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new Speechy().start();
    }

    private void start() {
        boolean exit = false;
        while (!exit) {
            displayMenu();
            int userChoice = userChoice();
            switch (userChoice) {
                case 1:
                    exit = true;
                case 2:
                    exit = true;
                case 3:
                    exit = true;
                case 4:
                    exit = SpeechyTranslator.start();
                    break;
                case 0:
                    exit = true;
            }
        }
    }

    private int userChoice() {
        boolean validChoice = false;
        int userChoice = 100;

        while (!validChoice) {
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter number.");
                scanner.next();
            }
            userChoice = scanner.nextInt();

            if (userChoice >= 0 && userChoice <= 4) {
                validChoice = true;
            } else {
                System.out.println("Please press valid key.");
            }
        }
        return userChoice;
    }

    private void displayMenu() {
        System.out.println(
                "Speechy: What can I do for you?\n" +
                        "1. Ask for information. \n" +
                        "2. Translate. (Speech-to-speech) \n" +
                        "3. Translate. (Text-to-speech) \n" +
                        "4. Translate. (Text-to-text) \n" +
                        "0. Exit.");
    }

}




