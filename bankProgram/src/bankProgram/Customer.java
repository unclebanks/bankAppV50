package bankProgram;

import java.util.Scanner;

public class Customer extends Users {

	public static void customerMenu(String role, Scanner scan, double social) {
		// TODO Auto-generated method stub
		System.out.println("Main Menu. Please select one of the following.\n1. Account Actions\n2. Apply For A New Account\n3. Edit Account Details\n4. Check Pending Applications.");
		String menuChoice=scan.next();
		switch (menuChoice) {
		case "1": monetaryManagement(scan, social);
		break;
		case "2": applyForNewAccount(scan);
		break;
		case "3": customerEditInfo(role);
		break;
		case "4": jdbc.checkPendingBankAccount(social);
		break;
		default: System.out.println("Please enter a valid value");
		}
		
	}

	private static void customerEditInfo(String role) {
		// TODO Auto-generated method stub
		
	}

	private static void applyForNewAccount(Scanner scan) {
		// TODO Auto-generated method stub
		System.out.println("Which type of account would you like to apply for?\n1. Checking\n2. Saving\n3. Investing");
		int accountType = scan.nextInt();
		System.out.println("Please confirm your social security number.");
		double social = scan.nextDouble();
		jdbc.bankAccountApplication(accountType, social);
	}

	private static void monetaryManagement(Scanner scan, double social) {
		// TODO Need to make account selector ex: which account you want to access? checking, sacing, investing, then what to do with it.
		System.out.println("Please select an account to view\n1. Checking\n2. Saving\n3. Investing");
		int account = scan.nextInt();
		if (jdbc.getBankAccDetails(social,account)>0) {
			System.out.println("What would you like to do with this account?\n1. Withdraw money\n2. Deposit money\n3. Transfer money");
			int whatToDo = scan.nextInt();
			switch (whatToDo) {
				case 1: withdrawMoney(scan);
				break;
				case 2: depositMoney(scan);
				break;
				case 3: transferMoney(scan);
				break;
				default:System.out.println("Please make a valid choice.");
			return;
			}
			
		} else {
			System.out.println("You do not own this type of account.");
		}
		
	}

	private static void transferMoney(Scanner scan) {
		// TODO Auto-generated method stub
		
	}

	private static void depositMoney(Scanner scan) {
		// TODO Auto-generated method stub
		
	}

	private static void withdrawMoney(Scanner scan) {
		System.out.println("Need to make withdraw money method");
	}

}
