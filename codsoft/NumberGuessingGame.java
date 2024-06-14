package codsoft;

import java.util.Random;
import java.util.Scanner;


public class NumberGuessingGame {



        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            Random random = new Random();
            int totalScore = 0;

            for (int round = 1; round <= 3; round++) {
                int answer = random.nextInt(100) + 1;
                System.out.println("Round " + round + ": Guess a number between 1 and 100");
                int roundScore = 0;

                for (int attempt = 1; attempt <= 5; attempt++) {
                    System.out.print("Attempt " + attempt + ": Enter your guess: ");
                    int guess = scanner.nextInt();

                    if (guess == answer) {
                        switch (attempt) {
                            case 1:
                                roundScore += 10;
                                break;
                            case 2:
                                roundScore += 9;
                                break;
                            case 3:
                                roundScore += 8;
                                break;
                            case 4:
                                roundScore += 6;
                                break;
                            case 5:
                                roundScore += 3;
                                break;
                        }
                        System.out.println("Correct guess! You earned " + roundScore + " points in this round.");
                        break;
                    } else {
                        if (guess > answer + 5) {
                            System.out.println("Your guess is Too high!");
                        } else if (guess > answer) {
                            System.out.println("Your guess is a Bit high!");
                        } else if (guess < answer - 5) {
                            System.out.println("Your guess is Too low!");
                        } else {
                            System.out.println("Your guesss is a Bit low!");
                        }

                        if (attempt == 5) {
                            System.out.println("Out of attempts. The correct number was: " + answer);
                        }
                    }
                }
                totalScore += roundScore;
            }

            System.out.println("YOUR FINAL SCORE: " + totalScore);
            scanner.close();
        }
}

