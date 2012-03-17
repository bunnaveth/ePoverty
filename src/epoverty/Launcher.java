/**************************
 * Project: ePoverty
 * Filename: Launcher.java
 * Description: Launches the application
 * Name: Bunna Veth
 * Date: Mar 10, 2012
 **************************/

// FUTURE CHANGES (feel free to add any ideas)
// =====================================================
// Change class name back to EPoverty.
//
// CHANGELOG (include the most recent change at the top)
// =====================================================
// EXAMPLE OF NEW CHANGELOG ITEM (Bunna, 3/17/2012)
// The most recent change should be listed at the top.
// With a title, author, date, and description of the change.
//
// MAC MENUBAR (Bunna, 3/16/2012)
// Included stuff for the mac menubar. This moves the main menu to the menubar.
// And also sets the application name on the menubar.

package epoverty;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Launcher
{
    public static void main(String[] args)
    {
        //Mac MenuBar
        String OSName = System.getProperty("os.name").toLowerCase();
        if (OSName.startsWith("mac os x"))
        {
            System.setProperty("apple.laf.useScreenMenuBar", "true"); //moves main menu to menubar
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "ePoverty"); //application name
        }

        MainWindow window = new MainWindow();
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Dispose database connections
        window.setSize(800, 600);
        window.setMinimumSize(new Dimension(400, 300));
        window.setLocationRelativeTo(null); //centers window on screen
        window.setVisible(true);
    }
}//end class