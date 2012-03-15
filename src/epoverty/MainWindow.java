/**************************
 * Project: ePov
 * Filename: MainWindow.java
 * Description: //TODO Add Description
 * Name: Bunna Veth
 * Date: Mar 9, 2012
 **************************/
package epoverty;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class MainWindow extends JFrame
{
    private Box toolBar;
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

    private Box contentArea;
    private JLabel pageLabel;
    private JTable table;

    public static Color LIGHT_GRAY = new Color(240, 240, 240);
    public static Color DARK_GRAY = new Color(220, 220, 220);
    public static Color GRAY_TEXT = new Color(100, 100, 100);
    public static Color BORDER_COLOR = new Color(200, 200, 200);
    public static Font ARIAL = new Font("Arial", Font.PLAIN, 14);

    public MainWindow()
    {
        super("ePov"); //title
        setLayout(new BorderLayout(1, 1));
        setBackground(BORDER_COLOR);

        //toolbar
        toolBar = Box.createHorizontalBox();
        toolBar.setOpaque(true);
        toolBar.setBackground(DARK_GRAY);

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

        toolBar.add(backButton);
        toolBar.add(forwardButton);
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(searchField);

        add(toolBar, BorderLayout.NORTH);

        //sidebar
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

        //statusbar
        statusBar = new JLabel("Welcome to Eliminating Poverty.");
        statusBar.setHorizontalAlignment(JLabel.CENTER);
        statusBar.setOpaque(true);
        statusBar.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        statusBar.setBackground(DARK_GRAY);
        statusBar.setForeground(GRAY_TEXT);
        add(statusBar, BorderLayout.SOUTH);

        //content
        ContentPanel content = new ContentPanel();
        //contentArea = Box.createVerticalBox();
        //contentArea.setOpaque(true);
        //contentArea.setBackground(Color.WHITE);
        //pageLabel = new JLabel("Home Page");
        //table = new JTable();

        //contentArea.add(pageLabel);
        //contentArea.add(content);

        add(content);

    }

    private SideBarButton selectedButton;

    private class SideBarHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            if (selectedButton != null)
                selectedButton.deselect();

            selectedButton = (SideBarButton) event.getSource();
            selectedButton.select();
        }

    }

    private SqlDatabase database;
    private boolean isConnected = false;


}//end class

