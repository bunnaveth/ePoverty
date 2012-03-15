/**************************
 * Project: SqlPlayground
 * Filename: ResultSetTableModel.java
 * Description: //TODO Add Description
 * Name: Bunna Veth
 * Date: Mar 10, 2012
 **************************/
package epoverty;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.AbstractTableModel;

public class ResultSetTableModel extends AbstractTableModel
{
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private ResultSetMetaData metaData;
    private int numberOfRows;
    private boolean connectedToDatabase = false;

    public ResultSetTableModel(String driver, String url, String username, String password, String query)
            throws SQLException, ClassNotFoundException
    {
        Class.forName(driver);
        connection = DriverManager.getConnection(url, username, password);
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        connectedToDatabase = true;
        setQuery(query);
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

    //Column Name
    @Override
    public String getColumnName(int column) throws IllegalStateException
    {
        if (!connectedToDatabase)
            throw new IllegalStateException("Not Connected to Database");
        try
        {
            return metaData.getColumnName(column + 1);
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
    public void setQuery(String query) throws SQLException, IllegalStateException
    {
        if (!connectedToDatabase)
            throw new IllegalStateException("Not Connected to Database");

        resultSet = statement.executeQuery(query);
        metaData = resultSet.getMetaData();

        //get row count by moving the cursor to the last row and retrieve its row number
        resultSet.last();
        numberOfRows = resultSet.getRow();

        fireTableStructureChanged(); //notify table that model has changed
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

