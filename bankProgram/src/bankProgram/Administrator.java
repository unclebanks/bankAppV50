package bankProgram;

import java.util.Scanner;

public class Administrator extends Employee {

	public static void menu(String role) {
		// TODO Admins should ONLY have admin powers. Not customer to prevent credential overflow
		Scanner scan= new Scanner(System.in);
		System.out.println("Main Menu. Please select one of the following.\n1. Manage Account\n2. View Applications\n");
		String menuChoice=scan.next();
		scan.close();
		switch (menuChoice) {
		case "1": manageAccount(role);
		break;
		case "2": jdbc.checkPendingApps(role);
		break;
		default: System.out.println("Please enter a valid value");
		}
		
	}

	private static void manageAccount(String role) {
		// TODO Auto-generated method stub
		System.out.println("Menu to view, edit, or delete accounts. Those methods lead to submenus for what to do or account info to find them.");
		
	}

}
