package bankProgram;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class MainData {
	public HashMap<String, Users> userHash;
	public HashMap<String, MonetaryAccount> moneyAcc;//

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to Deadbeef Bank.\nOne moment while I check for the proper databases.\n---------------------------");
		jdbc.checkForDataBase();	
		if (jdbc.checkForDataBase() == true) {
			mainMenu();
		} else {
			System.out.println("Database not found, Proceeding to account creation.");
			accountCreation();
		}

	}
	
	private static void mainMenu() {
		// TODO Auto-generated method stub
		Scanner scan=new Scanner(System.in);		
		System.out.println("Welcome to Deadbeef Bank. How may we assist you today?\n1. Apply for a user account\n2. Login");
		String initial=scan.next();
		switch(initial) {
		case "1": accountCreation();
		break;
		case "2": login();
		break;
		default: System.out.println("Please make a valid selection");
		break;
		};
		scan.close();
		
	}

	private static void login() {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome back! Which account are you trying to access?\n1.Applicant\n2.Customer\n3.Employee\n4.Administrator");
		String initial=scan.next();
		switch(initial) {
		case "1": Users.login();
		break;
		case "2": Customer.login();
		break;
		case "3": Employee.login();
		break;
		case "4": Administrator.login();
		break;
		default: System.out.println("Please make a valid selection");
				 login();
		break;
		}
		
	}

	private static void accountCreation() {
		// TODO Auto-generated method stub
		System.out.println("Successfully started account creation.");
		System.out.println("Please enter your first and last name to continue this process.");
		Scanner scan= new Scanner(System.in);
		String userName;
		String password;
		String emailChoice;
		int social;
		String first=scan.next();
		String last=scan.next();
		System.out.println("Please select which account type you would like to create\n1. Customer\n2. Employee\n3. Administrator");
		int accountType=scan.nextInt();
		System.out.println("Please select a username.");
		userName=scan.next();
		System.out.println("Please enter a password to protect your account.");
		password=scan.next();
		System.out.println("PLease enter your social security number.");
		social=scan.nextInt();
		jdbc.accountCreation(accountType, first, last, password, userName, social);
	}


}
