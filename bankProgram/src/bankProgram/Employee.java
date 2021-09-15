package bankProgram;

import java.util.Scanner;

public class Employee extends Users {

	public static void employeeMenu(String role, Scanner scan) {
		System.out.println("Main Menu.\n--------------------------\nPlease select one of the following.\n1. Check Customers Application\n2. Edit Customer Account\n3. Delete Account\n4. Check customers account.");
		String menuChoice=scan.next();
		switch (menuChoice) {
			case "1": checkAppStatus(scan);
			break;
			case "2": employeeEditInfo(scan);
			break;
			case "3": employeeDeleteAccount(scan);
			break;
			case "4": checkCustomer(scan);
			break;
			default: System.out.println("Please enter a valid value");
		}		
	}
	private static void checkCustomer(Scanner scan) {
		System.out.println("Please enter the social security number of the customer");
		double social=scan.nextDouble();
		jdbc.viewAllHistory(social);	
		
	}

	private static void checkAppStatus(Scanner scan) {
		System.out.println("Would you like to\n 1. Check for a pending application\n2. Approve or deny a pending application\n3. Check account applications\n4. Approve or deny account applications.");
		int appChoice =scan.nextInt();
		String username="";
		if (appChoice < 4) {
			System.out.println("Please enter the username the application was placed under.");
			username = scan.next();
		}
		System.out.println("Please verify the social security number attached to the application.");
		double social = scan.nextDouble();
		String role = "Users";
		System.out.println("Thank you, one moment while we locate the application.");
		int perms=2;
		switch(appChoice) {
			case 1: jdbc.checkPendingApps(role, social, username);
			break;
			case 2: if(jdbc.checkDeletePerms(role, social, username, perms)==true) {
				jdbc.approveDenyApp(role, social, username, scan);
				}else{System.out.println("Insufficient permissions to execute command.\n Please contact an administrator.");};
			break;
			case 3: jdbc.checkPendingBankAccount(social);
			break;
			case 4: jdbc.approveDenyBankApp(social, username, scan);;
			break;
			default:System.out.println("Please select either 1 or 2."); employeeMenu(role, scan);
			break;
		
		}
		
	}
	
	private static void employeeEditInfo (Scanner scan) {
		String info="";
		System.out.println("Which type of account would you like to edit?\n1. User\n2. Customer");
		String role=scan.next();
		switch (role) {
		case "1": role = "Users";
		break;
		case"2":role = "Customers";
		break;
		default:System.out.println("Please select either number 1 or 2."); employeeMenu(role, scan);
		}
		System.out.println("Please enter the username of the account you would like to edit.");
		String username = scan.next();
		System.out.println("Please select which information you would like to change.\n1. Username\n2. Password\n3. First Name\n4. Last Name");
		String empChoice = scan.next();
		switch (empChoice) {
			case "1" : info = "username";
			break;
			case "2" : info = "password";
			break;
			case "3" : info = "firstname";
			break;
			case "4" : info = "lastname";
			break;
			default : System.out.println("Please enter a valid value"); role = "Employees"; employeeMenu(role, scan);
		}
		System.out.println("Please enter the new value.");
		String newValue = scan.next();
		System.out.println("One moment while we process this change.");
		jdbc.editAccountInfo(info, newValue, username, role);
	}
	
	private static void employeeDeleteAccount(Scanner scan) {
		System.out.println("Please select which account type you would like to delete\n1. User\n2. Customer");
		String role =scan.next();
		int perms=2;
		switch(role) {
			case "1": role="Users";
			break;
			case "2": role="Customers";
			break;
			default:System.out.println("Please enter either number 1 or 2.");role="Employees"; employeeMenu(role, scan);
		}
		System.out.println("Please verify the username of the account you are trying to delete.");
		String username=scan.next();
		System.out.println("Please verify the social security number tied to the account you are trying to delete.");
		double social=scan.nextDouble();
		System.out.println("One moment while we verify this information.");
		if (jdbc.checkDeletePerms(role, social, username, perms)==true) {
			System.out.println("Account found and information verified. Are you sure you would like to delete this account?\n1. Yes\n2. No");
			String deleteOrNot=scan.next();
			switch(deleteOrNot) {
				case "1": role="Users"; jdbc.accountDeletion(role, social, username);
				break;
				case"2": System.out.println("Operation cancelled, returning to main menu."); role="Employees"; employeeMenu(role,scan);
				break;
				default: System.out.println("Please enter either number 1 or 2.");
				break;
			}
		} else {System.out.println("Invalid permissions. Cancelling request."); employeeMenu(role, scan);}
		
		
		
	}
}
