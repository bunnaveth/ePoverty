/**************************
 * Project: ePoverty
 * Filename: EPoverty.java
 * Description: //TODO Add Description
 * Name: Bunna Veth
 * Date: Mar 10, 2012
 **************************/
package epoverty;

import java.awt.Dimension;
import javax.swing.JFrame;

public class EPoverty
{
    public static void main(String[] args)
    {
        MainWindow window = new MainWindow();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 500);
        window.setMinimumSize(new Dimension(400, 300));
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        window.connectToDatabase();
    }
}//end class

