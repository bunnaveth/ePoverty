/**************************
 * Project: ePoverty
 * Filename: SqlConnection.java
 * Description: Communicates with database
 * Name: Bunna Veth
 * Date: Mar 15, 2012
 **************************/
// FUTURE CHANGES (feel free to add any ideas)
// =====================================================
// Perhaps use a CachedRowSet to possibly speed things up.
// Handle exceptions more gracefully.
// Set up a connection pool rather than re-establishing the same connection.
// Make ResultSet -> Object[][] transformation more efficient.
//
// CHANGELOG (include the most recent change at the top)
// =====================================================
// (See OVERHAUL change in ResultSetTableModelNew.java)
//
// ROW NUMBERS (Bunna, 3/18/12)
// Added row numbers to the data to bind the general view
// to the detailed view. This allows the detailed view
// to reference the correct row when filtering the data.
// This also had the side effect of shifting the ResultSet
// by -1. Making it a net 0 shift.
package epoverty;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class SqlConnection
{
    //Database Parameters
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String URL = "jdbc:mysql://50.63.244.60:3306/epoverty";
    private final int CONNECTION_TEST_LENGTH = 2; //seconds
    private String username;
    private String password;
    private String[] headerNames;
    private String[] headerClasses;
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    ResultSetMetaData metaData;

    //Constructor
    public SqlConnection(String user, String pass)
    {
        username = user;
        password = pass;
        connect();
    }

    //Connect to database
    public void connect()
    {
        //intialize to null
        connection = null;
        statement = null;
        resultSet = null;
        metaData = null;

        //establish connection
        try
        {
            Class.forName(DRIVER); //loads the driver class (may be unnecessary, I read somewhere the JVM does this automatically)
            connection = DriverManager.getConnection(URL, username, password);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        }
        catch (Exception e)
        {
            System.err.println("Problem trying to connect.");
            closeConnections();
        }
    }

    //Executes one query
    public Object[][] execute(String query)
    {
        int numberOfRows = 0;
        int numberOfColumns = 0;

        try
        {
            //re-establish connection if the current one is invalid
            //an invalid connection can occur even if the connection is not closed
            //(i.e. the connection times out)
            if (!connection.isValid(CONNECTION_TEST_LENGTH))
            {
                System.out.println("Attempting to re-establish connection.");
                closeConnections();
                connect();
            }

            //perform query
            resultSet = statement.executeQuery(query);
            metaData = resultSet.getMetaData(); //metadata contains header information

            //get table dimensions (inefficient but gets the job done)
            numberOfColumns = metaData.getColumnCount() + 1;
            resultSet.last(); //go to the end to retrieve row number (in order to initialize array)
            numberOfRows = resultSet.getRow();
            Object[][] tableData = new Object[numberOfRows][numberOfColumns];
            headerNames = new String[numberOfColumns];
            headerClasses = new String[numberOfColumns];

            //header data
            headerNames[0] = ""; //leave blank
            headerClasses[0] = "java.lang.Integer";
            for (int i = 1; i < numberOfColumns; i++)
            {
                headerNames[i] = metaData.getColumnName(i);
                headerClasses[i] = metaData.getColumnClassName(i);
            }

            //populate table from result set
            int row = 0;
            resultSet.beforeFirst(); //move cursor back to the start
            while (resultSet.next())
            {
                tableData[row][0] = row + 1; //add row number (start counting from 1)
                for (int i = 1; i < numberOfColumns; i++)
                    tableData[row][i] = resultSet.getObject(i);
                row++;
            }

            return tableData;
        }
        catch (SQLException e)
        {
            System.err.println("Problem retrieving data from the database.");
            closeConnections();
        }

        //return null if exception occurred
        return null;
    }

    //Terminate all connections
    public void closeConnections()
    {
        try
        {
            //test for null case before attempting to close
            if (resultSet != null) resultSet.close();
            if (resultSet != null) statement.close();
            if (resultSet != null) connection.close();
        }
        catch (SQLException e)
        {
            System.err.println("Unable to terminate connection.");
        }
    }

    //Retrieve header names
    public String[] getHeaderNames()
    {
        return headerNames;
    }

    //Retrieve header classes (used to format columns appropriately)
    public String[] getHeaderClasses()
    {
        return headerClasses;
    }

}//end class

