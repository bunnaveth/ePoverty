/**************************
 * Project: ePoverty
 * Filename: ContentPanel.java
 * Description: This is the meat and potatoes of the application.
 * Contains the table view of the database, toolbar, add/edit panels
 * and details panel.
 * Name: Bunna Veth
 * Date: Mar 16, 2012
 **************************/

// FUTURE CHANGES (feel free to add any ideas)
// =====================================================
// Remove the username/password from the source code for obvious reasons.
// Move the filter handler to another class (as this class is concerned with structure, not functionality).
// Improve the filter by allowing AND, OR, and NOT filters.

package epoverty;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.regex.PatternSyntaxException;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableModel;

public class ContentPanel extends JPanel
{
    //Initialize fields
    static final String DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://50.63.244.60:3306/epoverty";
    static final String USERNAME = "epoverty";
    static final String PASSWORD = "Cis2770#";
    static final String DEFAULT_QUERY = "SELECT * FROM fundraisers_view";
    private ResultSetTableModelNew tableModel; //using the new model
    private TableRowSorter<TableModel> sorter;
    private JTextField filterTextField;

    //Constructor
    public ContentPanel()
    {
        setLayout(new BorderLayout(1, 1));
        setBackground(Constants.BORDER_COLOR);

        try
        {
            //Toolbar
            Box toolBar = Box.createHorizontalBox();
            toolBar.setOpaque(true);
            toolBar.setBackground(Color.WHITE);
            filterTextField = new JTextField("Filter Results");
            filterTextField.setFont(new Font("Arial", Font.PLAIN, 13));
            filterTextField.setForeground(new Color(150, 150, 150));

            InstantFilterHandler filterHandler = new InstantFilterHandler();
            filterTextField.addKeyListener(filterHandler);
            filterTextField.addMouseListener(filterHandler);

            ToolBarButton addButton = new ToolBarButton("Add");
            ToolBarButton editButton = new ToolBarButton("Edit");

            toolBar.add(addButton);
            toolBar.add(editButton);
            toolBar.add(filterTextField);

            add(toolBar, BorderLayout.NORTH);

            //Table
            tableModel = new ResultSetTableModelNew(USERNAME, PASSWORD, DEFAULT_QUERY);
            FancyTable resultTable = new FancyTable(tableModel);
            sorter = new TableRowSorter<TableModel>(tableModel);
            resultTable.setRowSorter(sorter);

            JScrollPane tablePane = new JScrollPane(resultTable);
            tablePane.setBorder(BorderFactory.createEmptyBorder());
            add(tablePane, BorderLayout.CENTER);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    //Query Database (made public so the sidebar has access to it)
    public void performQuery(String query)
    {
        tableModel.setQuery(query);
    }

    //Instant Table Filter (triggers after every keystroke)
    private class InstantFilterHandler extends KeyAdapter implements MouseListener
    {
        @Override
        public void keyReleased(KeyEvent event)
        {
            //apply filter
            try
            {
                String text = filterTextField.getText();
                if (text.length() == 0)
                    sorter.setRowFilter(null);
                else
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text)); //(?i) = case-insensitive
            }
            //clear field and remove filter if invalid expression is encountered
            catch (PatternSyntaxException e)
            {
                filterTextField.setText("");
                sorter.setRowFilter(null);
            }
        }

        //When the user initially clicks inside the field, it clears the help text.
        @Override
        public void mouseClicked(MouseEvent me)
        {
            if (filterTextField.getText().equals("Filter Results"))
            {
                filterTextField.setText("");
                filterTextField.setForeground(Color.BLACK);
                filterTextField.setFont(new Font("Arial", Font.BOLD, 13));
            }
        }

        @Override
        public void mousePressed(MouseEvent me)
        {
        }

        @Override
        public void mouseReleased(MouseEvent me)
        {
        }

        @Override
        public void mouseEntered(MouseEvent me)
        {
        }

        @Override
        public void mouseExited(MouseEvent me)
        {
        }

    }//end InstantFIlterHandlerClass

}//end class