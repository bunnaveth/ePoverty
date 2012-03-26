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
// Include detailed panels of other views.
// Remove the username/password from the source code for obvious reasons.
// Move the filter handler to another class (as this class is concerned with structure, not functionality).
// Improve the filter by allowing AND, OR, and NOT filters.
//
// CHANGELOG (include the most recent change at the top)
// =====================================================
// PERSON PANEL (Bunna, 3/19/2012)
// This panel displays detailed information when the user selects
// a row. Currently, it only applies to the Fundraisers and Donors view.
//
// FILTER TRIGGERS (Bunna, 3/18/2012)
// Changed filter handler to trigger on focus events rather than mouse events.
// This approach is much cleaner and more logical.

package epoverty;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.PatternSyntaxException;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
    private FancyTable resultTable;
    private TableRowSorter<TableModel> sorter;
    private JTextField filterTextField;
    private PersonPanel personPanel;
    private String currentView;

    //Constructor
    public ContentPanel()
    {
        setLayout(new BorderLayout(1, 1));
        setBackground(Constants.BORDER_COLOR);
        currentView = "Fundraisers";

        try
        {
            //Toolbar
            /*
            Box toolBar = Box.createHorizontalBox();
            toolBar.setOpaque(true);
            toolBar.setBackground(Color.WHITE);
            filterTextField = new JTextField("Filter Results");
            filterTextField.setFont(new Font("Arial", Font.PLAIN, 13));
            filterTextField.setForeground(new Color(150, 150, 150));

            InstantFilterHandler filterHandler = new InstantFilterHandler();
            filterTextField.addKeyListener(filterHandler);
            filterTextField.addFocusListener(filterHandler);

            ToolBarButton addButton = new ToolBarButton("Add");
            ToolBarButton editButton = new ToolBarButton("Edit");

            toolBar.add(addButton);
            toolBar.add(editButton);
            toolBar.add(filterTextField);

            add(toolBar, BorderLayout.CENTER);
            */
            //Table
            tableModel = new ResultSetTableModelNew(USERNAME, PASSWORD, DEFAULT_QUERY);
            resultTable = new FancyTable(tableModel);
            sorter = new TableRowSorter<TableModel>(tableModel);
            resultTable.setRowSorter(sorter);
            ListSelectionModel listModel = resultTable.getSelectionModel();
            listModel.addListSelectionListener(new TableSelectionHandler());

            JScrollPane tablePane = new JScrollPane(resultTable);
            tablePane.setBorder(BorderFactory.createEmptyBorder());
            
            
            add(tablePane, BorderLayout.CENTER);

            //Person Panel
            personPanel = new PersonPanel();
            add(personPanel, BorderLayout.SOUTH);
        }
        catch (Exception e)
        {
            e.printStackTrace(System.err);
        }

    }

    //Query Database (made public so the sidebar has access to it)
    public void performQuery(String query, String buttonName)
    {
        currentView = buttonName;
        tableModel.setQuery(query);
        resultTable.hideColumns();
    }

    //Table Selection Handler
    private class TableSelectionHandler implements ListSelectionListener
    {
        @Override
        public void valueChanged(ListSelectionEvent event)
        {
            //System.out.println("Current View = " + currentView);
            int selectedRow = resultTable.getSelectedRow();

            //display person panel only if a row is selected and viewing fundraisers or donors
            if (selectedRow > -1 && (currentView.equals("Fundraisers") || currentView.equals("Donors")))
            {
                int rowNumber = Integer.parseInt(resultTable.getValueAt(selectedRow, 0).toString());
                personPanel.setDetails(tableModel.getRowData(rowNumber - 1));
                personPanel.setVisible(true);
            }
            //otherwise hide it
            else
            {
                personPanel.setVisible(false);
            }
        }

    }

    //Instant Table Filter (triggers after every keystroke)
    private class InstantFilterHandler extends KeyAdapter implements FocusListener
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

        //prepare field for input
        @Override
        public void focusGained(FocusEvent event)
        {
            if (filterTextField.getText().equals("Filter Results"))
            {
                filterTextField.setText("");
                filterTextField.setForeground(Color.BLACK);
                filterTextField.setFont(new Font("Arial", Font.BOLD, 13));
            }
        }

        //return to default state if the field is blank
        @Override
        public void focusLost(FocusEvent event)
        {
            if (filterTextField.getText().equals(""))
                clearFilter();
        }

    }//end InstantFIlterHandlerClass

    //Returns the filter to its default state
    public void clearFilter()
    {
        sorter.setRowFilter(null);
        filterTextField.setText("Filter Results");
        filterTextField.setForeground(new Color(150, 150, 150));
        filterTextField.setFont(new Font("Arial", Font.PLAIN, 13));
    }

}//end class