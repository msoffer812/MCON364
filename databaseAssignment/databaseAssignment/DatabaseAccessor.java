package databaseAssignment;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * General class that gets and prints out database information
 */
public class DatabaseAccessor {
	protected Connection connection;
	protected HashMap<ResultSet, ResultSet> tableInfo;
	
	public DatabaseAccessor(String url, String user, String password) {
		try {
			//Get the database, assign database connection, map out data
			this.connection = DriverManager.getConnection(url, user, password);
			System.out.println("Connection to database achieved");
	        DatabaseMetaData metaData = connection.getMetaData();
	        ResultSet tables = getTables(metaData);
	        this.tableInfo =
	        		selectDataFromDatabase(metaData, tables, connection);
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	/**
	 * Print out the details from the database
	 * @param tableInfo
	 * @return database info
	 */
	public String dbToString() {
		StringBuilder str = new StringBuilder();
		for(ResultSet key : tableInfo.keySet()) {
			try {
				boolean printIntro = true;
				while(key.next()) {
					if(printIntro) {
						str.append(key.getString("TABLE_NAME") + "\n");
						printIntro = false;
					}
					str.append("Column " + key.getString("COLUMN_NAME") + "; DataType " + key.getString("TYPE_NAME") + "\n");
				}
				str.append(printOutRow(tableInfo.get(key)));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return str.toString();
	}
	
	/**
	 * 
	 * @param rows
	 * @return String of table data
	 */
	protected String printOutRow(ResultSet rows) {
		StringBuilder str = new StringBuilder();
		try {
			System.out.println("Looping through each row in the resultSet using iterator.");
	        while (rows.next()) {
	            ResultSetMetaData metaData = rows.getMetaData();
	            System.out.println("First we get the ResultSetMetaData for the rows, which has the column names and how many there are"
	            		+ " this will help us print out the data per column per row");
	            int columnCount = metaData.getColumnCount();
	            System.out.println("There are " + columnCount + " columns in this table. We'll now use that in a for loop to loop over each"
	            		+ " column and get a result");
	            for (int i = 1; i <= columnCount; i++) {
	            	System.out.println("Iteration " + i + ":");
	                String colName = metaData.getColumnName(i);
	                System.out.println("Column " + i + " name: " + colName);
	                str.append(colName + ": " + rows.getString(i) + "; ");
	                System.out.println("We will append to the string to return + '" + colName + ": " + rows.getString(i) + ";'");
	            }
	            str.append("\n");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return str.toString();
	}
	/**
	 * Get all the data in the table and format into a map of metadata->data
	 * @param tableInfo
	 * @param connection
	 * @return HashMap<ResultSet, ResultSet> 
	 */
	private HashMap<ResultSet, ResultSet> selectDataFromDatabase(DatabaseMetaData metaData, ResultSet tableInfo, Connection connection) {
		HashMap<ResultSet, ResultSet> results = new HashMap<>();
		try {
			System.out.println("Looping through the tables in the ResultSet");
			while (tableInfo.next()) {
			     Statement statement = connection.createStatement();
			     String tableName = tableInfo.getString("TABLE_NAME");
			     System.out.println("Current table: " + tableName);
			     ResultSet table = statement.executeQuery("SELECT * FROM " + tableName);
			     System.out.println("Selecting everything from " + tableName);
			     ResultSet columns = metaData.getColumns(null, null, tableName, "%");
			     System.out.println("Selecting column names from the table's metadata");
			     results.put(columns, table);
			     System.out.println("Putting a new entry into the HashMap of data - all the column names + all the table data. "
			     		+ "We'll loop through them when needed");
			 }
		} catch (SQLException e) {
		}
		return results;
	}
	
	/**
	 * gets the metadata for the structure of the whole database
	 * @param metaData
	 * @return
	 * @throws SQLException
	 */
	private ResultSet getTables(DatabaseMetaData metaData) throws SQLException {
		ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
		System.out.println("Getting table names from the database and returning");
		return tables;
	}

}
