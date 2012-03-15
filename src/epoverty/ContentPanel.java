/**************************
 * Project: SqlPlayground
 * Filename: DisplayQueryResults.java
 * Description: //TODO Add Description
 * Name: Bunna Veth
 * Date: Mar 10, 2012
 **************************/
package epoverty;

/* NOTES
 *
 * SECTIONS
 * ui
 * database connections
 * tables
 * input methods (manual)
 * reporting
 *
 * FUNDRAISERS TABLE
 * SELECT firstName, lastName, emailAddress, phoneNumber, raiseGoal, raised
 * FROM persons
 * INNER JOIN fundraisers
 * ON persons.personId=fundraisers.personId
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.regex.PatternSyntaxException;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableModel;
import sun.font.Font2D;

public class ContentPanel extends JPanel
{
    static final String DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://50.63.244.60:3306/epoverty";
    static final String USERNAME = "epoverty";
    static final String PASSWORD = "Cis2770#";
    static final String DEFAULT_QUERY =
            "SELECT firstName, lastName, emailAddress, phoneNumber, raiseGoal, raised\n"
            + "FROM persons\n"
            + "INNER JOIN fundraisers\n"
            + "ON persons.personId=fundraisers.personId";
    private ResultSetTableModel tableModel;
    private TableRowSorter<TableModel> sorter;
    private JTextField filterText;

    public ContentPanel()
    {
        setLayout(new BorderLayout(1, 1));
        setBackground(Constants.BORDER_COLOR);

        try
        {
            //Filter
            Box toolBar = Box.createHorizontalBox();
            toolBar.setOpaque(true);
            toolBar.setBackground(Color.WHITE);
            filterText = new JTextField("Filter Results");
            filterText.setFont(new Font("Arial",Font.PLAIN,13));
            filterText.setForeground(new Color(150,150,150));

            InstantFilterHandler filterHandler = new InstantFilterHandler();
            filterText.addKeyListener(filterHandler);
            filterText.addMouseListener(filterHandler);

            ToolBarButton addButton = new ToolBarButton("Add");
            ToolBarButton editButton = new ToolBarButton("Edit");

            toolBar.add(addButton);
            toolBar.add(editButton);
            toolBar.add(filterText);

            add(toolBar, BorderLayout.NORTH);


            //Table
            tableModel = new ResultSetTableModel(DRIVER, DATABASE_URL, USERNAME, PASSWORD, DEFAULT_QUERY);
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

    //Query Database
    public void performQuery(String query) throws SQLException
    {
        try
        {
            tableModel.setQuery(query);
        }
        catch (SQLException e)
        {
            throw e;
        }
    }

    //Instant Table Filter (triggers after every keystroke)
    private class InstantFilterHandler extends KeyAdapter implements MouseListener
    {
        @Override
        public void keyReleased(KeyEvent event)
        {
            try
            {
                String text = filterText.getText();
                if (text.length() == 0)
                    sorter.setRowFilter(null);
                else
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text)); //(?i) = case-insensitive
            }
            //Clear field and remove filter if invalid expression is encountered
            catch (PatternSyntaxException e)
            {
                filterText.setText("");
                sorter.setRowFilter(null);
            }
        }

        @Override
        public void mouseClicked(MouseEvent me)
        {
            if(filterText.getText().equals("Filter Results"))
            {
                filterText.setText("");
                filterText.setForeground(Color.BLACK);
                filterText.setFont(new Font("Arial",Font.BOLD,13));
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