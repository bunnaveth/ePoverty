/**************************
 * Project: ePoverty
 * Filename: FancyTable.java
 * Description: JTable with alternating row colors
 * Name: Bunna Veth
 * Date: Mar 15, 2012
 **************************/

// FUTURE CHANGES (feel free to add any ideas)
// =====================================================
// Separate the hiding of columns and the setting of column widths into 2 methods.
// Update the views in the MySQL Database.
// Change text color of date values for expedition (i.e. change color to red if expedition cutoff date has passed)
// Make changes to column order and width persistent.
// Perhaps move the sorter and filter inside this class (as opposed to separate entities in ContentPanel)
//
// CHANGELOG (include the most recent change at the top)
// =====================================================
// ADDED HIDECOLUMNS METHOD (Bunna, 3/19/2012)
// This method hides columns based on if the column name starts
// with a hyphen (-) or not. These hidden columns are meant to
// provide a detailed view of the current selection.
// This way the table doesn't become cluttered.
// Also has the capability to set widths.
// (changing the column name is done in MySQL through the use of aliases)

package epoverty;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class FancyTable extends JTable
{
    //Fonts and Colors
    private final Font REGULAR_FONT = new Font("Arial", Font.PLAIN, 12);
    private final Font SELECTED_FONT = new Font("Arial", Font.BOLD, 12);
    private final Color TEXT_COLOR = Color.WHITE;
    private final Color GRID_COLOR = Color.WHITE;
    private final Color EVEN_ROW_COLOR = Color.RED;
    private final Color ODD_ROW_COLOR = new Color(255, 100, 100);
    private final Color SELECTED_COLOR = Color.RED;
    private final Color SELECTED_BACKGROUND = Color.WHITE;

    //Focusable Behavior
    //true = allows scrolling through entries using the keyboard arrow keys
    //false = removes the blue selection box, but disables keyboard
    private final boolean FOCUSABLE = true;
    private final int GRID_HEIGHT = 0;

    private final int LARGE_COLUMN_WIDTH = 150;
    private final int ROW_NUMBER_COLUMN_WIDTH = 25;

    //Constructor
    public FancyTable(ResultSetTableModelNew model)
    {
        super(model);
        hideColumns();

        setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, EVEN_ROW_COLOR)); //makes end selections more visible

        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setRowHeight(20);
        setIntercellSpacing(new Dimension(0, GRID_HEIGHT));

        setFont(REGULAR_FONT);
        setForeground(TEXT_COLOR);
        setGridColor(GRID_COLOR);

        setSelectionForeground(SELECTED_COLOR);
        setSelectionBackground(SELECTED_BACKGROUND);

        setFocusable(FOCUSABLE);
    }

    //Renders the appearance of each row
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
    {
        Component cell = super.prepareRenderer(renderer, row, column);

        //Change font of selected row
        if (isCellSelected(row, column))
        {
            cell.setFont(SELECTED_FONT);
            return cell;
        }

        //Alternating row colors
        if (row % 2 == 0)
            cell.setBackground(EVEN_ROW_COLOR);
        else
            cell.setBackground(ODD_ROW_COLOR);

        return cell;
    }

    //Hide columns meant only for the program to interpret
    public void hideColumns()
    {
        //count from right-to-left because the index shifts
        //when a column is removed.
        for (int i = getColumnCount() - 1; i > 0; i--)
        {
            TableColumn column = columnModel.getColumn(i);
            String columnName = dataModel.getColumnName(i);

            //set email column bigger than others
            if (columnName.equals("Email"))
                column.setPreferredWidth(LARGE_COLUMN_WIDTH); //flexible

            //remove columns if they are marked with "-"
            if (columnName.startsWith("-"))
                removeColumn(column);
        }

        //set the row number column to a fixed-width
        TableColumn rowNumberColumn = columnModel.getColumn(0);
        rowNumberColumn.setMinWidth(ROW_NUMBER_COLUMN_WIDTH);
        rowNumberColumn.setMaxWidth(ROW_NUMBER_COLUMN_WIDTH);
    }

}//end class

