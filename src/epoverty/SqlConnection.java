/**************************
 * Project: ePoverty
 * Filename: SqlConnection.java
 * Description: Communicates with database
 * Name: Bunna Veth
 * Date: Mar 15, 2012
 **************************/

// FUTURE CHANGES (feel free to add any ideas)
// =====================================================
// Handle exceptions more gracefully.
// Set up a connection pool rather than re-establishing the same connection.
// Make ResultSet -> Object[][] transformation more efficient.
//
// CHANGELOG (include the most recent change at the top)
// =====================================================
// (See OVERHAUL change in ResultSetTableModelNew.java)

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
            Class.forName(DRIVER); //loads the driver class
            connection = DriverManager.getConnection(URL, username, password);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        }
        catch (Exception e)
        {
            System.out.println("Problem trying to connect. Exiting application.");
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
                connect();
            }

            //perform query
            resultSet = statement.executeQuery(query);
            metaData = resultSet.getMetaData();

            //get table dimensions (inefficient but gets the job done)
            numberOfColumns = metaData.getColumnCount();
            resultSet.last(); //go to the end to retrieve row number
            numberOfRows = resultSet.getRow();
            Object[][] tableData = new Object[numberOfRows][numberOfColumns];
            headerNames = new String[numberOfColumns];
            headerClasses = new String[numberOfColumns];

            //header data
            for (int i = 0; i < numberOfColumns; i++)
            {
                headerNames[i] = metaData.getColumnName(i + 1);
                headerClasses[i] = metaData.getColumnClassName(i + 1);
            }

            //populate table from result set
            int row = 0;
            resultSet.beforeFirst(); //move cursor back to the start
            while (resultSet.next())
            {
                for (int i = 0; i < numberOfColumns; i++)
                    tableData[row][i] = resultSet.getObject(i + 1);
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
            System.out.println("Unable to terminate connection.");
        }

        //Exit application for now
        System.exit(1);
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

