/**************************
 * Project: ePoverty
 * Filename: SidebarButton.java
 * Description: Sidebar Button Style
 * Name: Bunna Veth
 * Date: Mar 15, 2012
 **************************/

package epoverty;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;

public class SideBarButton extends JButton
{
    private Font arial = new Font("Arial", Font.PLAIN, 15);
    private Font arialBold = new Font("Arial", Font.BOLD, 15);
    private String query;

    //Constructor
    public SideBarButton(String table)
    {
        super(table + "  "); //extra space is for padding

        //construct query
        query = "SELECT * from " + table.toLowerCase() + "_view";

        //restrict dimensions
        Dimension size = new Dimension(200, 30);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);

        //set color and style
        setForeground(MainWindow.GRAY_TEXT);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, MainWindow.BORDER_COLOR));
        setHorizontalAlignment(JButton.RIGHT);

        //default unselected state
        deselect();
    }

    //Retrieve Query
    public String getQuery()
    {
        return query;
    }

    //Selected Style
    public void select()
    {
        setFont(arialBold);
        setOpaque(true);        //allows background to be shown
        setBorderPainted(true); //allows border to be shown
    }

    //Unselected Style
    public void deselect()
    {
        setFont(arial);
        setOpaque(false);
        setBorderPainted(false);
    }

}//end class

