/**************************
 * Project: SqlPlayground
 * Filename: FancyTable.java
 * Description: JTable with alternating row colors
 * Name: Bunna Veth
 * Date: Mar 12, 2012
 **************************/
package epoverty;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

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
    private final int GRID_HEIGHT = 1;

    //Constructor
    public FancyTable(TableModel model)
    {
        super(model);

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

}//end class

