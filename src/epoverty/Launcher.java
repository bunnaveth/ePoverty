/**************************
 * Project: ePoverty
 * Filename: Launcher.java
 * Description: Launches the application
 * Name: Bunna Veth
 * Date: Mar 10, 2012
 **************************/
package epoverty;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Launcher
{
    public static void main(String[] args)
    {
        MainWindow window = new MainWindow();
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(800, 600);
        window.setMinimumSize(new Dimension(400, 300));
        window.setLocationRelativeTo(null); //centers window on screen
        window.setVisible(true);
    }
}//end class

