/**************************
 * Project: ePoverty
 * Filename: PersonPanelLabel.java
 * Description: Standard label in personPanel that allows you to specify font size.
 * Name: Bunna Veth
 * Date: Mar 17, 2012
 **************************/
package epoverty;

import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class PersonPanelLabel extends JLabel
{
    private static final int DEFAULT_SIZE = 12;

    public PersonPanelLabel(String label, int fontSize)
    {
        super(label);
        setFont(new Font("Arial", Font.PLAIN, fontSize));
        setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0)); //left-padding
    }

    public PersonPanelLabel(String label)
    {
        this(label, DEFAULT_SIZE);
    }

}//end class

