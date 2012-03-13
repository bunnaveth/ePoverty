/**************************
 * Project: ePov
 * Filename: SidebarButton.java
 * Description: //TODO Add Description
 * Name: Bunna Veth
 * Date: Mar 9, 2012
 **************************/
package epoverty;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.plaf.ButtonUI;

public class SideBarButton extends JButton
{
    private Font arial = new Font("Arial", Font.PLAIN, 15);
    private Font arialBold = new Font("Arial", Font.BOLD, 15);

    public SideBarButton(String label)
    {
        super(label + "  "); //extra space is for padding

        Dimension size = new Dimension(200, 30);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);

        setForeground(MainWindow.GRAY_TEXT);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, MainWindow.BORDER_COLOR));
        setHorizontalAlignment(JButton.RIGHT);
        deselect();
    }

    public void select()
    {
        setFont(arialBold);
        setOpaque(true);
        setBorderPainted(true);
    }

    public void deselect()
    {
        setFont(arial);
        setOpaque(false);
        setBorderPainted(false);
    }

}//end class

