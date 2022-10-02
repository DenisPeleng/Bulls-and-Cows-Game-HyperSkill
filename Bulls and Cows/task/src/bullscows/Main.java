package bullscows;

import java.util.Scanner;

public class Main {
    private static String secretCode;
    private static boolean isWin = false;

    public static void main(String[] args) {
        int counterTurn = 1;
        Scanner scanner = new Scanner(System.in);
        generateAndSetSecretCode();
        while (!isWin) {
            System.out.printf("Turn %s:%n", counterTurn++);
            String currentTurn = scanner.nextLine();
            runCheckingOfGame(currentTurn);
        }
    }

    private static void generateAndSetSecretCode() {
        Scanner scanner = new Scanner(System.in);
        boolean isGenerated = false;
        while (!isGenerated) {
            System.out.println("Please, enter the secret code's length:");
            int amountDigits = scanner.nextInt();
            if (amountDigits > 10) {
                System.out.printf("Error: can't generate a secret number with a length of %s because there aren't enough unique digits.", amountDigits);
            } else {
                String generatedSecretCode = "0";
                while (generatedSecretCode.startsWith("0")) {
                    generatedSecretCode = generateSecretCode(amountDigits);
                }
                secretCode = generatedSecretCode;
                isGenerated = true;
            }
        }

    }

    private static String generateSecretCode(int amountDigits) {
        StringBuilder strSecretCodeRandom = new StringBuilder();
        while (strSecretCodeRandom.length() < amountDigits) {
            long pseudoRandomNumber = System.nanoTime();
            long newDigit = pseudoRandomNumber % 10;
            if (!strSecretCodeRandom.toString().contains(String.valueOf(newDigit))) {
                strSecretCodeRandom.append(pseudoRandomNumber % 10);
            }
        }
        return strSecretCodeRandom.toString();
    }

    private static void runCheckingOfGame(String answerNumber) {
        int amountCows = amountCows(answerNumber);
        int amountBulls = amountBulls(answerNumber);
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
            isWin = true;
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

    private static int amountCows(String answerNumber) {
        char[] secretCodeCharArr = secretCode.toCharArray();
        char[] answerNumberCharArr = answerNumber.toCharArray();
        int amountCows = 0;
        for (int i = 0; i < secretCodeCharArr.length; i++) {
            for (int j = 0; j < answerNumberCharArr.length; j++) {
                if (secretCodeCharArr[i] == answerNumberCharArr[j] && i != j) {
                    amountCows++;
                }
            }
        }
        return amountCows;
    }
}
