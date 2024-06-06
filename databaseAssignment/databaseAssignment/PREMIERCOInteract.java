package databaseAssignment;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;

/**
 * Specific for interacting with this PremierCo database,
 * extends original DatabaseAccessor to reuse that code but with more 
 * flexibility
 */
public class PREMIERCOInteract extends DatabaseAccessor{
	private static String url = "jdbc:sqlserver://localhost:1433;databaseName=PREMIERECO;encrypt=false";
	private static String user = "sa";
	private static String password="root";
	
	public PREMIERCOInteract() {
		super(url, user, password);
		System.out.println("Initializing database connection specifically with PREMIERCO");
	}
	
	/**
	 * lookup user by id
	 * @param id
	 * @return user info
	 * @throws SQLException
	 */
	public String lookUpUser(int id) throws SQLException {
		System.out.println("Using a prepared statement to dynamically insert which id we want to lookup "
				+ "currently, we are looking up user " + id);
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM CUSTOMER WHERE CUST_NUM = ?");
		System.out.println("inserting param values for id");
		statement.setInt(1, id);
		System.out.println("Executing query and returning formatted user data as string");
		ResultSet user = statement.executeQuery();
		return printOutRow(user);
	}
	
	/**
	 * Allows you to change employee password - enter the old password, 
	 * then a new one.
	 * Changes it but checks to make sure the old one matches the one entered, 
	 * otherwise changes are rolled back
	 * @param empID
	 * @param oldPass
	 * @param newPass
	 * @return
	 */
	public String alterEmployeePassword(int empID, String oldPass, String newPass) {
		String resultString = "Error";
		try {
			//First get the original password - to later check 
			System.out.println("First get the original password - to later check ");
			String origPass = getUserPass(empID);
			System.out.println("Make sure it's not null - means user doesn't exist");
			//Make sure it's not null - means user doesn't exist
			if(!origPass.equals("noUser")) {
				//Set autocommit to false, so we can rollback transactions
				System.out.println("Set autocommit to false, so we can rollback transactions");
				connection.setAutoCommit(false);
				Savepoint savepoint1 = connection.setSavepoint("savepoint1");
				System.out.println("Call method to update password to new password");
				//Call method to update password to new password
				updatePass(empID, newPass);
				//Check if we should rollback changes
				System.out.println("Check if we should rollback changes");
		        if (origPass.equals("noPassSet") || origPass.equals(oldPass)) {
		        	System.out.println("Old pass confirmed");
		        	connection.commit();
			        resultString =  "Employee " + empID + " password changed";
			        System.out.println(resultString);
		        } else {
		        	resultString = "Failure to authenticate; Change rejected";
		        	System.out.println(resultString);
		            connection.rollback(savepoint1);
		        }
		        // End the transaction
		        System.out.println("End the transaction, set auto commit back to true");
		        connection.setAutoCommit(true);
			}else {
				resultString =  "Employee does not exist";
				System.out.println(resultString);
			}
		}catch(SQLException e) {
			resultString = "Error connecting to database";
		}
		return resultString;
	}
	
	/**
	 * Get user's password - for checking wiht transaction
	 * @param empID
	 * @return user password
	 * @throws SQLException
	 */
	private String getUserPass(int empID) throws SQLException {
		System.out.println("Getting user password from employee " + empID);
		PreparedStatement statement = connection.prepareStatement("SELECT userPassword FROM EMPLOYEE WHERE EMP_NUM = " + empID);
		ResultSet res = statement.executeQuery();
		System.out.println("Executing query:");
		if(res.next()) {
			System.out.println("User found");
			if(res.getString("userPassword") == null) {
				System.out.println("user has no password");
				return "noPassSet";
			}
			System.out.println("Password found, returning");
			return res.getString("userPassword");
		}
		return "noUser";
	}
	
	/**
	 * Update the password to new password
	 * @param empID
	 * @param newPass
	 * @throws SQLException
	 */
	private void updatePass(int empID, String newPass) throws SQLException {
		String sql = "UPDATE EMPLOYEE SET userPassword = ? WHERE EMP_NUM = ?";
		System.out.println("Updating user " + empID + "'s password");
		PreparedStatement statement = connection.prepareStatement(sql);
		System.out.println("Setting dyanmic parameters to values passed in of empID and newPass");
		statement.setString(1, newPass);
		statement.setInt(2, empID);
		statement.executeUpdate();
		System.out.println("Update executed");
	}
	
}
