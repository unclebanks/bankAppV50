package bankProgram;

import java.util.Scanner;

public class Users {
	
	public String first;
	public String last;
	public int social;
	public String accountHash;
	
	public static void userMenu(String role, Scanner scan, String username) {
		// TODO Auto-generated method stub
		System.out.println("Main Menu.\n--------------------------\nPlease select one of the following.\n1. Check Application Status\n2. Edit Account Details\n3. Delete Account");
		String menuChoice=scan.next();
		switch (menuChoice) {
			case "1": checkAppStatus(role, scan, username);
			break;
			case "2": userEditInfo(scan, username, role);
			break;
			case "3": userDeleteAccount(role, scan);
			break;
			default: System.out.println("Please enter a valid value");
		}
		
	}
	private static void userDeleteAccount(String role, Scanner scan) {
		System.out.println("To delete your provisional account, please verify some information with our database.");
		System.out.println("Please enter your social security number.");
		int social =scan.nextInt();
		System.out.println("Please verify your username.");
		String username =scan.next();
		System.out.println("One moment while we verify this information.");
		jdbc.accountDeletion(role, social, username);
		
	}
	private static void userEditInfo(Scanner scan, String username, String role) {
		System.out.println("Please select which information you would like to edit.\n1. Username\n2. Password");
		int infoToEdit=scan.nextInt();
		String info;
		switch(infoToEdit) {
			case 1: 
				info = "username";
				System.out.println("Please enter the username you would like to change to.");
				String newUN=scan.next();
				jdbc.editAccountInfo(info, newUN, username, role);
				break;
			case 2:
				info = "password";
				System.out.println("Please enter the password you would like to change to.");
				String newPW=scan.next();
				jdbc.editAccountInfo(info, newPW, username, role);
				break;
			default:System.out.println("Please enter a valid option.");
		}
		
	}
	private static void checkAppStatus(String role, Scanner scan, String username) {
		// TODO Auto-generated method stub
		System.out.println("Please confirm your social security number.");
		double social=scan.nextDouble();
		jdbc.checkPendingApps(role, social, username);
		MainData.mainMenu();
	}

}
