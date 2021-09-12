package bankProgram;

import java.sql.*;
import java.util.Scanner;

	public class jdbc {
		
		static final String user="admin";
		static final String password="12345678";
		static final String db_url="jdbc:oracle:thin:@javadb1.cmkunmjaryn3.us-east-2.rds.amazonaws.com:1521:ORCL";

		public static boolean checkForDataBase() {
			// TODO Auto-generated method stub
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection conn= DriverManager.getConnection(db_url, user, password);
				Statement stmt=conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from Users");
				if (!rs.next()) {
					System.out.println("We hit false");
					return false;
				}
			} catch(ClassNotFoundException e) {
				System.out.println("Unable to load driver class");
			} catch (SQLException e) {
				System.out.println("SQL error"+e);
			}
			System.out.println("we Hit true");
			return true;
		}
		
		public static void accountCreation(int accountType, String first, String last, String password2,
				String userName, int social) {
			// TODO Auto-generated method stub
			System.out.println(accountType+" "+first);
			System.out.println("Preparing to create account.");
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection conn= DriverManager.getConnection(db_url, user, password);
				Statement stmt=conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from Users WHERE social = '"+social+"'");
				if (rs.next()) {
					System.out.println("Provisional account found, returning to main menu.");
					MainData.mainMenu();
					} else {
				stmt.executeUpdate("INSERT INTO Users VALUES ('"+first+"','"+last+"','"+social+"','"+userName+"','"+password2+"','"+accountType+"')");
				System.out.println("Provisional account created.");
					}
			} catch(ClassNotFoundException e) {
				System.out.println("Unable to load driver class");
			} catch (SQLException e) {
				System.out.println("SQL error"+e);
			}
		}

		public static void checkLogin(String userName, String password2, String role, Scanner scan) {
			// TODO Auto-generated method stub
			if(role.equals("Admin")) {
				role = "Employees";
			}
			try {	
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection conn= DriverManager.getConnection(db_url, user, password);
				Statement stmt=conn.createStatement();
				System.out.println("Accessing the "+role+" account.\n---------------------\n");
				ResultSet rs = stmt.executeQuery("Select * from "+role+" WHERE userName = '"+userName+"'");
				if(rs.next()) {
					if (rs.getString(5).equals(password2)) {
						switch(role) {
						case "Users": Users.userMenu(role, scan, userName);
						break;
						case "Employees": Employee.employeeMenu(role, scan);
						break;
						case "Admin": Administrator.adminMenu(role, scan);
						break;
						case "Customer": Customer.customerMenu(role, scan);
						}
					} else {System.out.println("nope"); System.out.println(rs.getString(5));}
				} else {System.out.println("Account not found, returning to main menu"); MainData.mainMenu();}
				
			} catch(ClassNotFoundException e) {
				
				System.out.println("Unable to load driver class");
				
			} catch (SQLException e) {
				System.out.println("SQL error"+e);
			}
		}
		public static void editAccountInfo(String info, String value, String username2, String role) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection conn= DriverManager.getConnection(db_url, user, password);
				Statement stmt=conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from "+role+" WHERE userName = '"+username2+"'");
				if (rs.next()) {
					System.out.println("User found, updating.");
					stmt.executeUpdate("UPDATE "+role+" SET "+info+" = '"+value+"' WHERE userName = '"+username2+"' ");
					System.out.println("Finished updating.\nReturning to main menu");
					MainData.mainMenu();
					} else {System.out.println("System failure, returning to main menu."); MainData.mainMenu();}
			} catch(ClassNotFoundException e) {
				System.out.println("Unable to load driver class");
			} catch (SQLException e) {
				System.out.println("SQL error"+e);
			}
			
		}
		
		public static void checkPendingApps(String role, Double social, String username) {
			try {	
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection conn= DriverManager.getConnection(db_url, user, password);
				Statement stmt=conn.createStatement();
				System.out.println(role);
				ResultSet rs = stmt.executeQuery("Select * from "+role+" WHERE social = '"+social+"' AND username = '"+username+"'");
				if(rs.next()) {
					int status = rs.getInt(6);
					switch(status) {
					case 1: System.out.println("Application For customer account found and still pending");
					break;
					case 2: System.out.println("Application For employee account found and still pending");
					break;
					case 3: System.out.println("Application For admin account found and still pending");
					break;
					case 4: System.out.println("Application For customer account found and still pending");
					break;
					}
				} else {System.out.println("Application not found.");}
				
			} catch(ClassNotFoundException e) {
				
				System.out.println("Unable to load driver class");
				
			} catch (SQLException e) {
				System.out.println("SQL error"+e);
			}
		}
		static void accountDeletion(String role, int social, String password2) {
			try {	
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection conn= DriverManager.getConnection(db_url, user, password);
				Statement stmt=conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from "+role+" WHERE social = '"+social+"' AND password = '"+password2+"'");
				if(rs.next()) {
					System.out.println("User found, deleting account.");
					stmt.executeUpdate("DELETE FROM "+role+" WHERE social = '"+social+"'");
					System.out.println("Finished updating.\nReturning to main menu");
					MainData.mainMenu();
				} else {System.out.println("Application not found.");}
				
			} catch(ClassNotFoundException e) {
				
				System.out.println("Unable to load driver class");
				
			} catch (SQLException e) {
				System.out.println("SQL error"+e);
			}
			
		}

}
