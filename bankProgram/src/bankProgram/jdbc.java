package bankProgram;

import java.sql.*;

	public class jdbc {
		
		static final String user="admin";
		static final String password="12345678";
		static final String db_url="jdbc:oracle:thin:@javadb1.cmkunmjaryn3.us-east-2.rds.amazonaws.com:1521:ORCL";

		public static void main(String[] args) {

			try {
				
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
				Connection conn= DriverManager.getConnection(db_url, user, password);
				
				Statement stmt=conn.createStatement();
				
				ResultSet rs = stmt.executeQuery("Select * from Animals");
				
				while(rs.next()) {
					
					System.out.println("ID"+rs.getString(1));
					System.out.println("ID"+rs.getString(2));
					System.out.println("ID"+rs.getInt(3));
				}
				
			} catch(ClassNotFoundException e) {
				
				System.out.println("Unable to load driver class");
				
			} catch (SQLException e) {
				System.out.println("SQL error"+e);
			}

		}

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
				checkForDataBase();
				stmt.executeUpdate("INSERT INTO Users VALUES ('"+first+"','"+last+"','"+social+"','"+userName+"','"+password2+"','"+accountType+"')");
				System.out.println("Insert done, check out the peeps.");
				checkForDataBase();
				
			} catch(ClassNotFoundException e) {
				
				System.out.println("Unable to load driver class");
				
			} catch (SQLException e) {
				System.out.println("SQL error"+e);
			}
			
			
		}

		public static void checkLogin(String userName, String password2, String role) {
			// TODO Auto-generated method stub
			if(role.equals("Admin")) {
				role = "Employees";
			}

			try {
				
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
				Connection conn= DriverManager.getConnection(db_url, user, password);
				
				Statement stmt=conn.createStatement();
				System.out.println(role);
				
				ResultSet rs = stmt.executeQuery("Select * from "+role+" WHERE userName = '"+userName+"'");
				System.out.println(rs);
				
				while(rs.next()) {
					if (rs.getString(5).equals(password2)) {
						System.out.println("Perfect");
					} else {System.out.println("nope"); System.out.println(rs.getString(5));}
				}
				
			} catch(ClassNotFoundException e) {
				
				System.out.println("Unable to load driver class");
				
			} catch (SQLException e) {
				System.out.println("SQL error"+e);
			}
		}

}
