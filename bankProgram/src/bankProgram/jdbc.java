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
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
				ResultSet rs = stmt.executeQuery("Select * from "+role+" WHERE USERNAME = '"+userName+"'");
				if(rs.next()) {
					if (rs.getString(5).equals(password2)) {
						switch(role) {
						case "Users": Users.userMenu(role, scan, userName);
						break;
						case "Employees": Employee.employeeMenu(role, scan);
						break;
						case "Admin": Administrator.adminMenu(role, scan);
						break;
						case "Customers": double social= rs.getInt(3);Customer.customerMenu(role, scan, social);
						break;
						default:System.out.println("Processing error.");
						}
					} else {System.out.println("nope"); System.out.println(rs.getString(5));}
				} else {System.out.println("Account not found, returning to main menu"); MainData.mainMenu();}
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		static void accountDeletion(String role, double social, String username) {
			try {	
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection conn= DriverManager.getConnection(db_url, user, password);
				Statement stmt=conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from "+role+" WHERE social = '"+social+"' AND username = '"+username+"'");
				if(rs.next()) {
					System.out.println("User found, deleting account.");
					stmt.executeUpdate("DELETE FROM "+role+" WHERE social = '"+social+"'");
					System.out.println("Finished updating.\nReturning to main menu");
					MainData.mainMenu();
				} else {System.out.println("Application not found.");}
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		public static void approveDenyApp(String role, Double social, String username, Scanner scan) {
			try {	
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection conn= DriverManager.getConnection(db_url, user, password);
				Statement stmt=conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from "+role+" WHERE social = '"+social+"' AND username = '"+username+"'");
				System.out.println(rs.next());
				if(rs !=null) {
						System.out.println("Would you like to approve or deny this account?\n1. Approve\n2. Deny");
						String aOrD=scan.next();
						switch(aOrD) {
							case "1": moveAccount(role,social,username);
							break;
							case "2": accountDeletion(role,social,username);
							break;
							default:System.out.println("Please select either number 1 or 2.");
							break;
						}
					}			
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public static boolean checkDeletePerms(String role, double social, String username, int perms) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection conn= DriverManager.getConnection(db_url, user, password);
				Statement stmt=conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from "+role+" WHERE social = '"+social+"' AND username = '"+username+"'");
				if (rs.next() && rs.getInt(6) < perms + 2) {
					System.out.println("Print true");
					return true;
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Return false");
			return false;
			
		}
		public static void moveAccount(String role, Double social, String username) {
			System.out.println("Made it to move");
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection conn= DriverManager.getConnection(db_url, user, password);
				Statement stmt=conn.createStatement();
				Connection conn2= DriverManager.getConnection(db_url, user, password);
				Statement stmt2=conn2.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from "+role+" WHERE social = '"+social+"' AND username = '"+username+"'");
				System.out.println(rs.next());
				boolean admin=false;
				String first = rs.getString(1);
				String last = rs.getString(2);
				String password2=rs.getString(5);
				int accountType=rs.getInt(6);
				String db="";
				switch(accountType) {
					case 1: db="Customers";
					break;
					case 2: db="Employees";
					break;
					case 3: db="Employees"; admin=true;
					break;
				}
					if (admin==true) {
						System.out.println("Moving account information.");
						stmt2.executeUpdate("INSERT INTO "+db+" VALUES ('"+first+"','"+last+"','"+social+"','"+username+"','"+password2+"', 1)");
					} else {
						stmt2.executeUpdate("INSERT INTO "+db+" VALUES ('"+first+"','"+last+"','"+social+"','"+username+"','"+password2+"', 0)");}
				accountDeletion(role,social,username);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		public static void bankAccountApplication(int accountType, double social) {
			// TODO Auto-generated method stub
			System.out.println("Preparing to create application.");
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection conn= DriverManager.getConnection(db_url, user, password);
				Statement stmt=conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from PENDINGACCOUNTS WHERE identifier = '"+social+"'");
				if (rs.next()) {
					System.out.println("Provisional account found, returning to main menu.");
					MainData.mainMenu();
					} else {
				stmt.executeUpdate("INSERT INTO PENDINGACCOUNTS VALUES ('"+social+"','"+accountType+"')");
				System.out.println("Provisional account created.");
					}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public static void checkPendingBankAccount(Double social) {
			try {	
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection conn= DriverManager.getConnection(db_url, user, password);
				Statement stmt=conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from PENDINGACCOUNTS WHERE identifier = '"+social+"'");
				if(rs.next()) {
					int status = rs.getInt(2);
					switch(status) {
					case 1: System.out.println("Application For checking account found and still pending");
					break;
					case 2: System.out.println("Application For savings account found and still pending");
					break;
					case 3: System.out.println("Application For investment account found and still pending");
					break;
					}
				} else {System.out.println("Application not found.");}
				MainData.mainMenu();
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public static void approveDenyBankApp(Double social, String username, Scanner scan) {
			try {	
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection conn= DriverManager.getConnection(db_url, user, password);
				Statement stmt=conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from PENDINGACCOUNTS WHERE social = '"+social+"'");
				System.out.println(rs.next());
				String role="null";
				if(rs !=null) {
						System.out.println("Would you like to approve or deny this application?\n1. Approve\n2. Deny");
						String aOrD=scan.next();
						switch(aOrD) {
							case "1":  System.out.println("Needs approve banks about");    //approveBankAccount(social,username);
							break;
							case "2": accountDeletion(role,social,username);//Need application deletion here.
							break;
							default:System.out.println("Please select either number 1 or 2.");
							break;
						}
					}			
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

}
