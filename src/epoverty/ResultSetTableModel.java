/**************************
 * Project: ePoverty
 * Filename: ResultSetTableModel.java
 * Description: Binds a ResultSet object to a TableModel
 * Name: Bunna Veth
 * Date: Mar 15, 2012
 **************************/

//NOTICE!!! (Bunna, 3/16/2012)
//THIS CLASS HAS BEEN MADE OBSOLETE AND WILL BE REPLACED BY ResultSetTableModelNew
//DO NOT MAKE ANY FURTHER CHANGES TO THIS FILE, AS IT WILL BE DELETED IN THE NEXT COMMIT.

package epoverty;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.table.AbstractTableModel;

public class ResultSetTableModel extends AbstractTableModel
{
    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://50.63.244.60:3306/epoverty";
    private String username;
    private String password;
    private String query;

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private ResultSetMetaData metaData;
    private int numberOfRows;
    private boolean connectedToDatabase = false;

    public ResultSetTableModel(String user, String pass, String command)
            throws SQLException, ClassNotFoundException
    {
        username = user;
        password = pass;

        Class.forName(driver);
        connection = DriverManager.getConnection(url, username, password);
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        connectedToDatabase = true;

        setQuery(command);
    }

    //Column Class
    @Override
    public Class getColumnClass(int column) throws IllegalStateException
    {
        if (!connectedToDatabase)
            throw new IllegalStateException("Not Connected to Database");
        try
        {
            String className = metaData.getColumnClassName(column + 1);
            return Class.forName(className); //converts string to Class object
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return Object.class;
    }

    //Column Count
    @Override
    public int getColumnCount() throws IllegalStateException
    {
        if (!connectedToDatabase)
            throw new IllegalStateException("Not Connected to Database");
        try
        {
            return metaData.getColumnCount();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    //Maps database names to a more readable format
    HashMap<String, String> headers = new HashMap<String, String>()
    {

        {
            put("firstName", "First Name");
            put("lastName", "Last Name");
            put("emailAddress", "Email");
            put("phoneNumber", "Phone");
            put("raiseGoal", "Expedition Goal");
            put("raised", "Total Raised");
        }

    };

    //Column Name
    @Override
    public String getColumnName(int column) throws IllegalStateException
    {
        if (!connectedToDatabase)
            throw new IllegalStateException("Not Connected to Database");
        try
        {
            String columnName = metaData.getColumnName(column + 1);
            if (headers.containsKey(columnName))
                return headers.get(columnName);
            else
                return columnName;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return "";
    }

    //Row Count
    @Override
    public int getRowCount() throws IllegalStateException
    {
        if (!connectedToDatabase)
            throw new IllegalStateException("Not Connected to Database");

        return numberOfRows;
    }

    //Cell Value
    @Override
    public Object getValueAt(int row, int column) throws IllegalStateException
    {
        if (!connectedToDatabase)
            throw new IllegalStateException("Not Connected to Database");
        try
        {
            resultSet.absolute(row + 1); //moves cursor to specific row
            return resultSet.getObject(column + 1);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return "";
    }

    //Set Query
    public void setQuery(String command) throws SQLException, IllegalStateException
    {
        query = command;

        if (!connectedToDatabase)
            throw new IllegalStateException("Not Connected to Database");

        resultSet = statement.executeQuery(query);
        metaData = resultSet.getMetaData();

        //get row count by moving the cursor to the last row and retrieve its row number
        resultSet.last();
        numberOfRows = resultSet.getRow();

        fireTableStructureChanged(); //notify table that model has changed
    }

    public void reconnect() throws SQLException
    {
        connection = DriverManager.getConnection(url, username, password);
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    //Disconnect
    public void disconnectFromDatabase()
    {
        if (connectedToDatabase)
        {
            try
            {
                resultSet.close();
                statement.close();
                connection.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            finally
            {
                connectedToDatabase = false;
            }
        }
    }


}//end class

