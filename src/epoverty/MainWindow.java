/**************************
 * Project: ePoverty
 * Filename: MainWindow.java
 * Description: Mother frame that houses all the components and handles the views.
 * Name: Bunna Veth
 * Date: Mar 9, 2012
 **************************/

// FUTURE CHANGES (feel free to add any ideas)
// =====================================================
// Perhaps move the details of a certain panel to another class.
// Make the status bar more useful by providing table metrics.
// Re-evaluate subcomponents of navbar and sidebar.
// Create a navBarButton class.
//
// CHANGELOG (include the most recent change at the top)
// =====================================================
// MINOR CHANGES TO SideBarHandler (Bunna, 3/19/2012)
// Now clears the filter text when buttons are pressed.
// This allows for the entire table to be displayed.
// Rather than have a previous, irrelevant filter narrow the results.
//
// GENERAL REFACTORING (Bunna, 3/16/2012)
// Changed toolBar to navBar to distinguish from the toolbar in the content panel (add/edit bar).
// Comments to clarify the structure of the entities.

package epoverty;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MainWindow extends JFrame
{
    //Style
    public static Color LIGHT_GRAY = new Color(240, 240, 240);
    public static Color DARK_GRAY = new Color(220, 220, 220);
    public static Color GRAY_TEXT = new Color(100, 100, 100);
    public static Color BORDER_COLOR = new Color(200, 200, 200);
    public static Font ARIAL = new Font("Arial", Font.PLAIN, 14);

    //Fields
    private Box navBar;
    private JButton backButton;
    private JButton forwardButton;
    private JTextField searchField;
    private Box sideBar;
    private SideBarButton fundraisersButton;
    private SideBarButton donorsButton;
    private SideBarButton donationsButton;
    private SideBarButton accountsButton;
    private SideBarButton expeditionsButton;
    public JLabel statusBar;
    ContentPanel content;

    //Constructor
    public MainWindow()
    {
        super("ePoverty"); //title
        setLayout(new BorderLayout(1, 1)); //cells are spaced 1px horizontally and 1px vertically
        setBackground(BORDER_COLOR); //I call it border color, because the cellspacing creates a border look

        //Navigation Bar
        navBar = Box.createHorizontalBox();
        navBar.setOpaque(true);
        navBar.setBackground(DARK_GRAY);

        backButton = new JButton("Back");
        Icon backButtonIcon = new ImageIcon(getClass().getResource("resources/LeftArrow.png"));
        backButton.setIcon(backButtonIcon);
        backButton.setFont(ARIAL);

        forwardButton = new JButton("Forward");
        Icon forwardButtonIcon = new ImageIcon(getClass().getResource("resources/RightArrow.png"));
        forwardButton.setIcon(forwardButtonIcon);
        forwardButton.setFont(ARIAL);

        searchField = new JTextField("Search", 12);
        searchField.setMinimumSize(searchField.getPreferredSize());
        searchField.setMaximumSize(searchField.getPreferredSize());

        navBar.add(backButton);
        navBar.add(forwardButton);
        navBar.add(Box.createHorizontalGlue());
        navBar.add(searchField);

        add(navBar, BorderLayout.NORTH);

        //Side Bar
        sideBar = Box.createVerticalBox();
        sideBar.setOpaque(true);
        sideBar.setBackground(LIGHT_GRAY);

        fundraisersButton = new SideBarButton("Fundraisers");
        donorsButton = new SideBarButton("Donors");
        donationsButton = new SideBarButton("Donations");
        accountsButton = new SideBarButton("Accounts");
        expeditionsButton = new SideBarButton("Expeditions");

        SideBarHandler handler = new SideBarHandler();
        fundraisersButton.addActionListener(handler);
        donorsButton.addActionListener(handler);
        donationsButton.addActionListener(handler);
        accountsButton.addActionListener(handler);
        expeditionsButton.addActionListener(handler);

        sideBar.add(Box.createVerticalStrut(20));
        sideBar.add(fundraisersButton);
        sideBar.add(donorsButton);
        sideBar.add(donationsButton);
        sideBar.add(accountsButton);
        sideBar.add(expeditionsButton);

        add(sideBar, BorderLayout.WEST);

        //Status Bar
        statusBar = new JLabel("Welcome to ePoverty.");
        statusBar.setHorizontalAlignment(JLabel.CENTER);
        statusBar.setOpaque(true);
        statusBar.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        statusBar.setBackground(DARK_GRAY);
        statusBar.setForeground(GRAY_TEXT);
        add(statusBar, BorderLayout.SOUTH);

        //Content Panel
        content = new ContentPanel();
        add(content);

    }

    //Sidebar Handler
    private SideBarButton selectedButton;

    private class SideBarHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            //deselects previous button
            if (selectedButton != null)
                selectedButton.deselect();

            //selects button user clicked and makes changes to the content panel
            selectedButton = (SideBarButton) event.getSource();
            selectedButton.select();
            content.performQuery(selectedButton.getQuery(),selectedButton.getLabel());
            content.clearFilter();
        }

    }

}//end class