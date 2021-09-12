package bankProgram;

import java.util.Scanner;

public class Customer extends Users {

	public static void customerMenu(String role, Scanner scan) {
		// TODO Auto-generated method stub
		System.out.println("Main Menu. Please select one of the following.\n1. Account Actions\n2. Apply For A New Account\n3. Edit Account Details\n4. Check Pending Applications.");
		String menuChoice=scan.next();
		switch (menuChoice) {
		case "1": monetaryManagement();
		break;
		case "2": applyForNewAccount();
		break;
		case "3": customerEditInfo(role);
		break;
		case "4": System.out.println("Need jump for info here");;
		break;
		default: System.out.println("Please enter a valid value");
		}
		
	}

	private static void customerEditInfo(String role) {
		// TODO Auto-generated method stub
		
	}

	private static void applyForNewAccount() {
		// TODO Auto-generated method stub
		
	}

	private static void monetaryManagement() {
		// TODO Auto-generated method stub
		
	}

}
