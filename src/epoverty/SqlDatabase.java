/**************************
 * Project: ePov
 * Filename: SqlConnection.java
 * Description: //TODO Add Description
 * Name: Bunna Veth
 * Date: Mar 9, 2012
 **************************/
package epoverty;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


import java.util.logging.Logger;

public class SqlDatabase
{
    private final String url = "jdbc:mysql://50.63.244.60:3306/epoverty";
    private Connection connection;
    private Statement statement;
    private ResultSet results;

    public boolean connect(String login, String password)
    {
        try
        {
            connection = DriverManager.getConnection(url, login, password);
            statement = connection.createStatement();
        }
        catch (Exception e)
        {
            return false;
        }

        return true;
    }

    public String[][] query(String command, int columns)
    {
        ArrayList<Object> list = new ArrayList<Object>();
        String[][] returnedList;

        try
        {
            results = statement.executeQuery(command);
            while (results.next())
            {
                String[] row = new String[columns];
                for(int i = 0; i < columns; i++)
                    row[i] = results.getString(i+1);
                list.add(row);
            }
        }
        catch (Exception e)
        {
            return null;
        }

        returnedList = new String[list.size()][columns];
        for(int i = 0; i < list.size(); i++)
            returnedList[i] = (String[])list.get(i);

//        System.out.println(list.toArray());
        return returnedList;

    }

    public static void main(String[] args)
    {
        SqlDatabase con = new SqlDatabase();
        con.connect("epoverty", "Cis2770#");
        con.query("SELECT * FROM persons",12);
    }

}
