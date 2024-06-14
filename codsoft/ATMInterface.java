package codsoft;

import java.util.Scanner;

public class ATMInterface {


    //create ATMExample class to implement the ATM functionality

        public static void main(String[] args)
        {
            int balance = 100000, withdraw, deposit;

            //create scanner class object to get choice of user
            Scanner sc = new Scanner(System.in);

            while(true)
            {
                System.out.println("Automated Teller Machine ATM machine");
                System.out.println("Choose 1 for Withdrawal ");
                System.out.println("Choose 2 for Deposit funds");
                System.out.println("Choose 3 for Check Account Balance");
                System.out.println("Choose 4 for EXIT");
                System.out.print("Choose the action you want to perform:");

                //get choice from user
                int choice = sc.nextInt();
                switch(choice)
                {
                    case 1:
                        System.out.print("Enter amount of money to be withdrawn:");


                        withdraw = sc.nextInt();


                        if(balance >= withdraw)
                        {

                            balance = balance - withdraw;
                            System.out.println("Please collect your money\n");
                        }
                        else
                        {
                            //show custom error message
                            System.out.println("Insufficient Balance");
                        }
                        System.out.println(" ");
                        break;

                    case 2:

                        System.out.print("Enter money to be deposited:");

                        //get deposit amount from the user.
                        deposit = sc.nextInt();


                        balance = balance + deposit;
                        System.out.println("Money has been successfully depsited");
                        System.out.println(" ");
                        break;

                    case 3:

                        System.out.println("Balance : "+balance);
                        System.out.println(" ");
                        break;

                    case 4:
                        //exit from the menu
                        System.exit(0);
                }
            }
        }
}
