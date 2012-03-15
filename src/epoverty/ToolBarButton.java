/**************************
 * Project: ePoverty
 * Filename: ToolBarButton.java
 * Description: Custom JButton Style
 * Name: Bunna Veth
 * Date: Mar 13, 2012
 **************************/
package epoverty;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;

public class ToolBarButton extends JButton
{
    //font
    private final Font ARIAL = new Font("Arial", Font.PLAIN, 13);
    private final Dimension SIZE = new Dimension(40, 33);

    //constructor
    public ToolBarButton(String label)
    {
        super(label);
        setFont(ARIAL);

        //set size multiple ways to ensure dimensions regardless of layout
        setPreferredSize(SIZE);
        setMinimumSize(SIZE);
        setMaximumSize(SIZE);
    }
}//end class

