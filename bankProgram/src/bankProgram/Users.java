package bankProgram;

import java.util.Scanner;

public class Users {
	
	public String first;
	public String last;
	public int social;
	public String accountHash;
	public static void login() {
		// TODO Auto-generated method stub
		Scanner scan=new Scanner(System.in);
		String userName;
		String password2;
		System.out.println("Thank you for choosing DeadB33f bank as your financial institution of choice.\nPlease enter your username.");
		userName=scan.next();
		System.out.println("Thank you, please enter your password.");
		password2=scan.next();
		jdbc.checkLogin(userName, password2);
		
		
	}

}
