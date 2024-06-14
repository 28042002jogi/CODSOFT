package codsoft;
import java.util.Scanner;

public class StudentGradeCalculator {

        public static void main(String[] args) {
            Scanner input = new Scanner(System.in);

            System.out.print("Enter Student's Name: ");
            String studentName = input.nextLine();

            System.out.print("Enter the number of subjects for grade calculation: ");
            int numSubjects = input.nextInt();

            int totalMarks = 0;
            for (int i = 0; i < numSubjects; i++) {
                System.out.print("Enter the name of subject " + (i + 1) + ": ");
                String subjectName = input.next();
                System.out.print("Enter marks obtained in " + subjectName + ": ");
                int marks = input.nextInt();
                totalMarks += marks;
            }

            double averagePercentage = (totalMarks / (numSubjects * 100.0)) * 100;

            char grade;
            if (averagePercentage >= 97.0) {
                grade = 'O';
            } else if (averagePercentage >= 87.0) {
                grade = 'A';
            } else if (averagePercentage >= 77.0) {
                grade = 'B';
            } else if (averagePercentage >= 67.0) {
                grade = 'C';
            } else if (averagePercentage >= 57.0) {
                grade = 'D';
            } else if (averagePercentage >= 47.0) {
                grade = 'E';
            } else if (averagePercentage >= 37.0) {
                grade = 'P';
            } else {
                grade = 'F';
            }

            System.out.println("\nStudent's Name: " + studentName);
            System.out.println("Total Marks Obtained: " + totalMarks);
            System.out.println("Average Percentage: " + averagePercentage + "%");
            System.out.println("Final Grade: " + grade);
        }
}


