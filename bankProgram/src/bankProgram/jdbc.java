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
			System.out.println("Database found, redirecting to main menu.\n");
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
				MainData.mainMenu();
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
						case "Customers": double social= rs.getInt(3);Customer.customerMenu(role, scan, social,userName);
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
					} else {System.out.println("Information could not be verified, returning to main menu."); MainData.mainMenu();}
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
					case 1: System.out.println("Application For customer account found and still pending. Redirecting to main menu.\n");
					break;
					case 2: System.out.println("Application For employee account found and still pending. Redirecting to main menu.\n");
					break;
					case 3: System.out.println("Application For admin account found and still pending. Redirecting to main menu.\n");
					break;
					case 4: System.out.println("Application For customer account found and still pending. Redirecting to main menu.\n");
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
				System.out.println("Provisional account created. Returning to main menu for security.\n");
				MainData.mainMenu();
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
				ResultSet rs = stmt.executeQuery("Select * from PENDINGACCOUNTS WHERE identifier = '"+social+"'");
				System.out.println(rs.next());
				String role="null";
				if(rs !=null) {
						System.out.println("Would you like to approve or deny this application?\n1. Approve\n2. Deny");
						String aOrD=scan.next();
						switch(aOrD) {
							case "1":  approveBankAccount(role, social, username);    //approveBankAccount(social,username);
							break;
							case "2": bankAccApplicationDeletion(social);//Need application deletion here.
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
		public static void approveBankAccount(String role, Double social, String username) {
			System.out.println("Made it to approveBankAccount");
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection conn= DriverManager.getConnection(db_url, user, password);
				Statement stmt=conn.createStatement();
				Connection conn2= DriverManager.getConnection(db_url, user, password);
				Statement stmt2=conn2.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from PENDINGACCOUNTS WHERE IDENTIFIER = '"+social+"'");
				System.out.println("got data from PENDINGACCOUNTS");
				System.out.println(rs.next());
				double identifier = rs.getDouble(1);
				int accountType = rs.getInt(2);
				String newBankAccount="";
				String db="";
				switch(accountType) {
					case 1: db="CheckingAccount"; newBankAccount = "INSERT INTO EXISTINGACCOUNTS VALUES ('"+identifier+"', 1, 0, 0, 0, 0, 0)";
					break;
					case 2: db="SavingAccount"; newBankAccount = "INSERT INTO EXISTINGACCOUNTS VALUES ('"+identifier+"', 0, 0, 2, 0, 0, 0)";
					break;
					case 3: db="InvestingAccount"; newBankAccount = "INSERT INTO EXISTINGACCOUNTS VALUES ('"+identifier+"', 0, 0, 0, 0, 3, 0)";
					break;
				}
				ResultSet rs2 = stmt2.executeQuery("Select * from EXISTINGACCOUNTS WHERE IDENTIFIER = '"+social+"'");
				if (rs2.next()) {
					System.out.println("Found account and updating information");
					stmt2.executeUpdate("UPDATE EXISTINGACCOUNTS SET "+db+" = 1 WHERE IDENTIFIER = '"+identifier+"'");
					//add account application deletion method
				} else {
					stmt2.executeUpdate(newBankAccount);
				}
				System.out.println("Going to account deletion");
				bankAccApplicationDeletion(identifier);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		private static void bankAccApplicationDeletion(double identifier) {
			try {	
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection conn= DriverManager.getConnection(db_url, user, password);
				Statement stmt=conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from PENDINGACCOUNTS WHERE identifier = '"+identifier+"'");
				if(rs.next()) {
					System.out.println("Application found, deleting account.");
					stmt.executeUpdate("DELETE FROM PENDINGACCOUNTS WHERE identifier = '"+identifier+"'");
					System.out.println("Finished deleting.\nReturning to main menu");
					MainData.mainMenu();
				} else {System.out.println("Application not found.");}
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		static double getBankAccDetails (double social, int account) {
			double active=0;
			try {	
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection conn= DriverManager.getConnection(db_url, user, password);
				Statement stmt=conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from EXISTINGACCOUNTS WHERE identifier = '"+social+"'");
				double balance=0;
				int accountType=0;
				if(rs.next()) {
					switch(account) {
						case 1: balance=rs.getDouble(3); accountType=2;
						break;
						case 2: balance=rs.getDouble(5); accountType=4;
						break;
						case 3: balance=rs.getDouble(7); accountType=6;
						break;
					}
					if (rs.getInt(accountType) > 0) {
						active=1;
						return balance;
					} else {
						System.out.println("You do not own this type of account.");
					}
				} else {System.out.println("account not found.");}
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return active;
		}
		static void withdrawMoney(int wAmount, double balance, String accountType, double social) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection conn= DriverManager.getConnection(db_url, user, password);
				Statement stmt=conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from EXISTINGACCOUNTS WHERE identifier = '"+social+"'");
				if (rs.next()) {
					System.out.println("User found, updating.");
					double newBal = balance-wAmount;
					stmt.executeUpdate("UPDATE EXISTINGACCOUNTS SET "+accountType+" = '"+newBal+"' WHERE identifier = '"+social+"' ");
					System.out.println("Finished updating.\nReturning to main menu");
					logWithdrawals(social,accountType,balance,wAmount,newBal);
					return;
					} else {System.out.println("System failure, returning to main menu."); MainData.mainMenu();}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		static void depositMoney(int dAmount, double balance, String accountType, double social) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection conn= DriverManager.getConnection(db_url, user, password);
				Statement stmt=conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from EXISTINGACCOUNTS WHERE identifier = '"+social+"'");
				if (rs.next()) {
					System.out.println("User found, updating.");
					double newBal = balance+dAmount;
					stmt.executeUpdate("UPDATE EXISTINGACCOUNTS SET "+accountType+" = '"+newBal+"' WHERE identifier = '"+social+"' ");
					System.out.println("Finished processing.\n Your new balance is"+newBal+"\nReturning to main menu");
					logDeposits(social,accountType,balance,dAmount,newBal);
					return;
					} else {System.out.println("System failure, returning to main menu."); MainData.mainMenu();}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		static void transferMoney(String accountType, String toAccount,double social, double amountToTransfer) {
			System.out.println("Made it to transfer monoey jdbc");
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection conn= DriverManager.getConnection(db_url, user, password);
				Statement stmt=conn.createStatement();
				Connection conn2= DriverManager.getConnection(db_url, user, password);
				Statement stmt2=conn2.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from EXISTINGACCOUNTS WHERE IDENTIFIER = '"+social+"'");
				System.out.println("got data from existing accounts");
				int originAccount=0;
				int recAccount=0;
				rs.next();
				switch(accountType) {
					case "CheckingBal": originAccount=3;
					break;
					case "SavingBal": originAccount=5;
					break;
					case "InvestingBal": originAccount=7;
					break;
				}
				switch(toAccount) {
				case "CheckingBal": recAccount=3;
				break;
				case "SavingBal": recAccount=5;
				break;
				case "InvestingBal": recAccount=7;
				break;
				}
				double oldBalanceOrigin = rs.getDouble(originAccount);
				double newBalanceOrigin = oldBalanceOrigin-amountToTransfer;
				double oldBalanceReceiver = rs.getDouble(recAccount);
				double newBalanceReceiver = oldBalanceReceiver+amountToTransfer;
				ResultSet rs2 = stmt2.executeQuery("Select * from EXISTINGACCOUNTS WHERE IDENTIFIER = '"+social+"'");
				stmt2.executeUpdate("UPDATE EXISTINGACCOUNTS SET "+toAccount+" = "+newBalanceReceiver+" WHERE IDENTIFIER = '"+social+"'");
				stmt2.executeUpdate("UPDATE EXISTINGACCOUNTS SET "+accountType+" = "+newBalanceOrigin+" WHERE IDENTIFIER = '"+social+"'");
				System.out.println("Finished transfer, new balance for origin account is "+newBalanceOrigin+" and the new balance for the receiving account it "+newBalanceReceiver);
				System.out.println("For security purposes returning to main menu");
				logTransfers(social,accountType,oldBalanceOrigin,newBalanceOrigin,oldBalanceReceiver,newBalanceReceiver,amountToTransfer, toAccount);
				MainData.mainMenu();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		private static void logDeposits(double social,String accountType,double balance, int dAmount, double newBal) {
			System.out.println("Need to log deposits here");
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection conn= DriverManager.getConnection(db_url, user, password);
				Statement stmt=conn.createStatement();
				stmt.executeUpdate("INSERT INTO TRANSACTIONS (IDENTIFIER,TYPE,ORIGIN,ORIGINAMOUNTPRE,ORIGINAMOUNTPOST,RECEIVE,RECEIVEAMOUNTPRE,RECEIVEAMOUNTPOST,AMOUNT) VALUES ('"+social+"','deposit','"+accountType+"','"+balance+"','"+newBal+"','none','0','0','"+dAmount+"')");
			} catch (SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		static void viewDepositHistory(double social) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection conn= DriverManager.getConnection(db_url, user, password);
				Statement stmt=conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from TRANSACTIONS WHERE IDENTIFIER = '"+social+"' AND TYPE = 'deposit'");
				while (rs.next()) {
				    String type = rs.getString(2);
				    String account = rs.getString(3);
				    double oldBal = rs.getDouble(4);
				    double newBal = rs.getDouble(5);
				    double amount = rs.getDouble(9);
				    System.out.println("Type of transaction: "+type + "\nDeposited account: " +account+ "\nPrevious Balance: " +oldBal+ "\nNew Balance: " +newBal +"\nAmount Deposited: +"+amount+"\n------------------------------------------");
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		private static void logWithdrawals(double social, String accountType, double balance, int wAmount, double newBal) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection conn= DriverManager.getConnection(db_url, user, password);
				Statement stmt=conn.createStatement();
				stmt.executeUpdate("INSERT INTO TRANSACTIONS (IDENTIFIER,TYPE,ORIGIN,ORIGINAMOUNTPRE,ORIGINAMOUNTPOST,RECEIVE,RECEIVEAMOUNTPRE,RECEIVEAMOUNTPOST,AMOUNT) VALUES ('"+social+"','withdrawal','"+accountType+"','"+balance+"','"+newBal+"','none','0','0','"+wAmount+"')");
			} catch (SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		static void viewWithdrawalsHistory(double social) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection conn= DriverManager.getConnection(db_url, user, password);
				Statement stmt=conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from TRANSACTIONS WHERE IDENTIFIER = '"+social+"' AND TYPE = 'withdrawal'");
				while (rs.next()) {
				    String type = rs.getString(2);
				    String account = rs.getString(3);
				    double oldBal = rs.getDouble(4);
				    double newBal = rs.getDouble(5);
				    double amount = rs.getDouble(9);
				    System.out.println("Type of transaction: "+type + "\nWithdrawn account: " +account+ "\nPrevious Balance: " +oldBal+ "\nNew Balance: " +newBal +"\nAmount Withdrawn: -"+amount+"\n-------------------------------------------------");
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		private static void logTransfers(double social, String accountType, double oldBalanceOrigin, double newBalanceOrigin, double oldBalanceReceiver, double newBalanceReceiver, double amountToTransfer,String toAccount) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection conn= DriverManager.getConnection(db_url, user, password);
				Statement stmt=conn.createStatement();
				stmt.executeUpdate("INSERT INTO TRANSACTIONS (IDENTIFIER,TYPE,ORIGIN,ORIGINAMOUNTPRE,ORIGINAMOUNTPOST,RECEIVE,RECEIVEAMOUNTPRE,RECEIVEAMOUNTPOST,AMOUNT) VALUES ('"+social+"','transfer','"+accountType+"','"+oldBalanceOrigin+"','"+newBalanceOrigin+"','"+toAccount+"','"+oldBalanceReceiver+"','"+newBalanceReceiver+"','"+amountToTransfer+"')");
			} catch (SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		static void viewTransferHistory(double social) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection conn= DriverManager.getConnection(db_url, user, password);
				Statement stmt=conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from TRANSACTIONS WHERE IDENTIFIER = '"+social+"' AND TYPE = 'transfer'");
				while (rs.next()) {
				    String type = rs.getString(2);
				    String account = rs.getString(3);
				    double oldBal = rs.getDouble(4);
				    double newBal = rs.getDouble(5);
				    String toType = rs.getString(6);
				    double toOld = rs.getDouble(7);
				    double toNew = rs.getDouble(8);
				    double amount = rs.getDouble(9);
				    System.out.println("Type of transaction: "+type + "\nWithdrawn account: " +account+ "\nPrevious Balance: " +oldBal+ "\nNew Balance: " +newBal +"\nAmount Transfered: -"+amount+"\nDeposited Account: "+toType+"\nReceiving account previous balance: "+toOld+"\nReceiving account new balance: "+toNew+"\n-------------------------------------");
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		static void viewAllHistory(double social) {
			viewTransferHistory(social);
			viewDepositHistory(social);
			viewWithdrawalsHistory(social);
		}

}
