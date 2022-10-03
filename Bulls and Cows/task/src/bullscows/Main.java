package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Main {
    private static String secretCode;
    private static boolean isRunning = true;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int counterTurn = 1;
        generateAndSetSecretCode();
        while (isRunning) {
            System.out.println("Okay, let's start a game!");
            System.out.printf("Turn %s:%n", counterTurn++);
            String currentTurn = scanner.nextLine();
            runCheckingOfGame(currentTurn);
        }
    }


    private static void generateAndSetSecretCode() {
        Scanner scanner = new Scanner(System.in);
        boolean isGenerated = false;
        while (!isGenerated) {
            int amountDigits;
            int countPossibleSymbols;
            System.out.println("Please, enter the secret code's length:");
            String inputStrAmountDigits = scanner.nextLine();
            try {
                amountDigits = Integer.parseInt(inputStrAmountDigits);
            } catch (Exception e) {
                System.out.printf("Error: \"%s\" isn't a valid number.", inputStrAmountDigits);
                isRunning = false;
                return;
            }
            System.out.println("Input the number of possible symbols in the code:");
            String inputStrCountPossibleSymbols = scanner.nextLine();
            try {
                countPossibleSymbols = Integer.parseInt(inputStrCountPossibleSymbols);
            } catch (Exception e) {
                System.out.printf("Error: \"%s\" isn't a valid number.", inputStrCountPossibleSymbols);
                isRunning = false;
                return;
            }
            if (countPossibleSymbols > 36) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                isRunning = false;
                return;
            }
            if (countPossibleSymbols < amountDigits || amountDigits == 0) {
                System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", amountDigits, countPossibleSymbols);
                isRunning = false;
                return;
            }
            char[] possibleSymbols = fillArrayWithPossibleSymbols(countPossibleSymbols, amountDigits);

            secretCode = generateSecretCode(amountDigits, possibleSymbols);
            isGenerated = true;

        }

    }

    private static char[] fillArrayWithPossibleSymbols(int countPossibleSymbols, int amountDigits) {
        char[] possibleSymbols = new char[countPossibleSymbols];
        int currentFilledCells = 0;
        for (int i = 0; i < 10; i++) {
            if (currentFilledCells == countPossibleSymbols) {
                break;
            }
            possibleSymbols[i] = (char) (i + '0');
            currentFilledCells++;
        }
        if (currentFilledCells == countPossibleSymbols) {
            System.out.print("The secret is prepared: ");
            for (int i = 0; i < amountDigits; i++) {
                System.out.print("*");
            }
            System.out.printf(" (0-%c).%n", possibleSymbols[possibleSymbols.length - 1]);
        } else {
            char chrToFill = 'a';
            for (int i = 10; i < 36; i++) {
                if (currentFilledCells == countPossibleSymbols) {
                    break;
                }
                possibleSymbols[i] = chrToFill;
                chrToFill++;
                currentFilledCells++;
            }
            System.out.print("The secret is prepared: ");
            for (int i = 0; i < amountDigits; i++) {
                System.out.print("*");
            }
            System.out.printf(" (0-9, a-%c).%n", possibleSymbols[possibleSymbols.length - 1]);
        }
        return possibleSymbols;
    }

    private static String generateSecretCode(int amountDigits, char[] possibleSymbols) {
        int amountOfSymbols = possibleSymbols.length;
        StringBuilder strSecretCodeRandom = new StringBuilder();
        Random random = new Random();
        while (strSecretCodeRandom.length() < amountDigits) {
            int indexOfSymbol = random.nextInt(amountOfSymbols);
            char symbol = possibleSymbols[indexOfSymbol];
            if (!strSecretCodeRandom.toString().contains(String.valueOf(symbol))) {
                strSecretCodeRandom.append(symbol);
            }
        }
        return strSecretCodeRandom.toString();
    }

    private static void runCheckingOfGame(String answerNumber) {
        int amountBulls = amountBulls(answerNumber);
        int amountCows = amountCowsWithBulls(answerNumber) - amountBulls;
        if (amountBulls == 0 && amountCows == 0) {
            System.out.printf("Grade: None.%n");
        } else if (amountBulls > 0 && amountCows > 0) {
            System.out.printf("Grade: %s bull(s) and %s cow(s).%n", amountBulls, amountCows);
        } else if (amountBulls == 0) {
            System.out.printf("Grade: %s cow(s).%n", amountCows);
        } else {
            System.out.printf("Grade: %s bull(s).%n", amountBulls);
        }
        if (amountBulls == secretCode.length()) {
            isRunning = false;
            System.out.println("Congratulations! You guessed the secret code.");
        }
    }

    private static int amountBulls(String answerNumber) {
        int amountBulls = 0;
        char[] secretCodeCharArr = secretCode.toCharArray();
        char[] answerNumberCharArr = answerNumber.toCharArray();
        for (int i = 0; i < secretCodeCharArr.length; i++) {
            if (secretCodeCharArr[i] == answerNumberCharArr[i]) {
                amountBulls++;
            }
        }
        return amountBulls;
    }

    private static int amountCowsWithBulls(String answerNumber) {
        char[] secretCodeCharArr = secretCode.toCharArray();
        char[] answerNumberCharArr = answerNumber.toCharArray();
        int amountCows = 0;
        for (int i = 0; i < answerNumberCharArr.length; i++) {
            for (int j = 0; j < secretCodeCharArr.length; j++) {
                if (secretCodeCharArr[j] == answerNumberCharArr[i] && i != j) {
                    amountCows++;
                    break;
                }
            }
        }
        return amountCows;
    }
}
