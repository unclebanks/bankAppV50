package bankProgram;

import java.util.HashMap;
import java.util.Scanner;


public class MainData {
	public HashMap<String, Users> userHash;
	public HashMap<String, MonetaryAccount> moneyAcc;//

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Welcome to Deadbeef Bank.\nOne moment while I check for the proper databases.\n---------------------------");
		if (jdbc.checkForDataBase() == true) {
			mainMenu();
		} else {
			System.out.println("Database not found, Proceeding to account creation.");
			accountCreation();
		}

	}
	
	static void mainMenu() {
		// TODO Auto-generated method stub	
		Scanner scan = new Scanner(System.in);	
		System.out.println("Welcome to Deadbeef Bank. How may we assist you today?\n1. Apply for a user account\n2. Login");
		String initial=scan.next();
		switch(initial) {
		case "1": accountCreation();
		break;
		case "2": login(scan);
		break;
		default: System.out.println("Please make a valid selection");
		break;
		};
		
	}

	private static void login(Scanner scan) {
		// TODO Auto-generated method stub
		String userName;
		String password2;
		String role="";
		System.out.println("Thank you for choosing DeadB33f bank as your financial institution of choice.\nPlease enter your username.");
		userName=scan.next();
		System.out.println(userName);
		System.out.println("Thank you, please enter your password.");
		password2=scan.next();
		System.out.println("Which account are you trying to access?\n1.Applicant\n2.Customer\n3.Employee\n4.Administrator");
		String initial=scan.next();
		switch(initial) {
		case "1": role="Users";
		break;
		case "2": role="Customers";
		break;
		case "3": role="Employees";
		break;
		case "4": role="Admin";
		break;
		default: System.out.println("Please make a valid selection");
				 login(scan);
		break;
		}
		jdbc.checkLogin(userName, password2, role, scan);
		
	}

	private static void accountCreation() {
		// TODO Auto-generated method stub
		System.out.println("Successfully started account creation.");
		System.out.println("Please enter your first and last name to continue this process.");
		Scanner scan= new Scanner(System.in);
		String userName;
		String password;
		int social;
		String first=scan.next();
		String last=scan.next();
		System.out.println("Please select which account type you would like to create\n1. Customer\n2. Employee\n3. Administrator");
		int accountType=scan.nextInt();
		switch(accountType) {
		case 1: accountType=1;
		break;
		case 2: accountType=2;
		break;
		case 3: accountType=3;
		break;
		default: System.out.println("Please make a valid selection");
		break;
		}
		System.out.println("Please select a username.");
		userName=scan.next();
		System.out.println("Please enter a password to protect your account.");
		password=scan.next();
		System.out.println("PLease enter your social security number.");
		social=scan.nextInt();
		scan.close();
		jdbc.accountCreation(accountType, first, last, password, userName, social);
	}


}
