/**************************
 * Project: ePoverty
 * Filename: ResultSetTableModel.java
 * Description: Default table model
 * Name: Bunna Veth
 * Date: Mar 15, 2012
 **************************/

// FUTURE CHANGES (feel free to add any ideas)
// =====================================================
// Rename class to something like TableViewModel.
// Resolve Bugs.
//
// CHANGELOG (include the most recent change at the top)
// =====================================================
// BUG FIX - ARRAY OUT OF BOUNDS (Bunna, 3/19/2012)
// Added an exception catch all to getColumnClass().
// This effectively returns an Object class if there is
// an error accessing the header array. Probably not the
// best solution, but it beats having it stall unexpectedly.
//
// OVERHAUL (Bunna, 3/16/2012)
// This version separates the Sql Connection from the actual table model.
// Instead of binding the table data to a ResultSet, the sequel connection
// produces Object arrays (Object[][]) that aren't tied to the connection.
//
// Benefits: When the connection times out, closes, or becomes
// invalid, the table is unaffected and still viewable and manipulable.
// If the connection is lost, it simply re-restablishes it.
//
// Drawbacks: The way it fetches data from the ResultSet is inefficient.
// First it needs to visit the last row to retrieve the row number.
// This is to establish the dimensions of the Object array.
// Then it backtracks to the beginning of the ResultSet to fetch the actual
// data row by row. Currently, this method is barely noticeable since
// it's working with a relatively small amount of data. Must see how
// it performs on a sample size of say 1000+ records. The reason I use
// arrays are for its fast read access. An ArrayList would have more desirable
// write qualities, but I'm unsure of how it performs in read situations from
// a table context.
//
// Bugs: Sometimes it throws an ArrayIndexOutOfBoundsException
// when accessing class names while switching between views.
// This is probably because it tries to access the previous array
// before the query finishes. One solution would be to have it
// return blanks until we know for certain the query is finished.

package epoverty;

import javax.swing.table.AbstractTableModel;

public class ResultSetTableModelNew extends AbstractTableModel
{
    SqlConnection tableConnection;
    private Object[][] tableData;
    private String[] headerNames;
    private String[] headerClasses;
    private int numberOfRows;
    private int numberOfColumns;

    public ResultSetTableModelNew(String username, String password, String query)
    {
        tableConnection = new SqlConnection(username, password);
        setQuery(query); //initial query
    }

    //Set Query
    public void setQuery(String query)
    {
        tableData = tableConnection.execute(query);
        headerNames = tableConnection.getHeaderNames();
        headerClasses = tableConnection.getHeaderClasses();

        //set dimensions
        numberOfRows = tableData.length;
        numberOfColumns = tableData[0].length;

        //notify table that model has changed
        fireTableStructureChanged();
    }

    //Get Row Data (used to retrieve data for the details view)
    public Object[] getRowData(int i)
    {
        return tableData[i];
    }

    //Row Count
    @Override
    public int getRowCount()
    {
        return numberOfRows;
    }

    //Column Count
    @Override
    public int getColumnCount()
    {
        return numberOfColumns;
    }

    //Column Name
    @Override
    public String getColumnName(int i)
    {
        return headerNames[i];
    }

    //Column Class
    @Override
    public Class getColumnClass(int i)
    {
        try
        {
            //load class from string name
            return Class.forName(headerClasses[i]);
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Class Not Found. Unable to load " + headerClasses[i]);
        }
        catch (Exception e) //catch-all to stop array access error
        {
            e.printStackTrace(System.err);
        }

        //if class not found, just represent the column as a generic object
        return Object.class;
    }

    //Cell Value
    @Override
    public Object getValueAt(int i, int j)
    {
        return tableData[i][j];
    }

}//end class