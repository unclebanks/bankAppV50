package bankProgram;

import java.util.Scanner;

public class Users {
	
	public String first;
	public String last;
	public int social;
	public String accountHash;
	public static void login() {
		
		
	}
	public static void menu(String role) {
		// TODO Auto-generated method stub
		Scanner scan= new Scanner(System.in);
		System.out.println("Main Menu. Please select one of the following.\n1. Check Application Status\n2. Begin A New Application\n3. Edit Account Details\n4. Delete Account");
		String menuChoice=scan.next();
		scan.close();
		switch (menuChoice) {
		case "1": checkAppStatus();
		break;
		case "2": userNewApp();
		break;
		case "3": userEditInfo(role);
		break;
		case "4": userDeleteAccount(role);
		break;
		default: System.out.println("Please enter a valid value");
		}
		
	}
	private static void userDeleteAccount(String role) {
		// TODO Auto-generated method stub
		
	}
	private static void userEditInfo(String role) {
		// TODO Auto-generated method stub
		
	}
	private static void userNewApp() {
		// TODO Auto-generated method stub
		
	}
	private static void checkAppStatus() {
		// TODO Auto-generated method stub
		
		
	}

}
