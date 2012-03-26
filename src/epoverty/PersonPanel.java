/**************************
 * Project: ePoverty
 * Filename: PersonPanel.java
 * Description: Displays the details of a particular person.
 * Name: Bunna Veth
 * Date: Mar 17, 2012
 **************************/

// FUTURE CHANGES (feel free to add any ideas)
// =====================================================
// Implement the ability to read in blobs.
// Clean up readability.

package epoverty;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

public class PersonPanel extends JPanel
{
    JLabel photoLabel;
    JLabel nameLabel;
    JLabel addressLabel;
    JLabel phoneLabel;
    JLabel emailLabel;
    JLabel expeditionLabel;
    JProgressBar progressBar;

    public PersonPanel()
    {
        setLayout(new BorderLayout(1, 1));
        setBackground(Constants.BORDER_COLOR);
        setFont(new Font("Arial", Font.PLAIN, 12));

        //Photo
        Icon profileIcon = new ImageIcon(getClass().getResource("resources/Profile.png"));
        photoLabel = new JLabel("", profileIcon, SwingConstants.LEFT);
        add(photoLabel, BorderLayout.WEST);

        //Personal Info
        Box infoBox = Box.createVerticalBox();
        infoBox.setOpaque(true);
        infoBox.setBackground(Color.WHITE);
        nameLabel = new PersonPanelLabel("", 16);
        emailLabel = new PersonPanelLabel("");
        phoneLabel = new PersonPanelLabel("");
        addressLabel = new PersonPanelLabel("");

        //Expedition Info
        expeditionLabel = new PersonPanelLabel("", 14);

        progressBar = new JProgressBar(0, 6000);
        progressBar.setValue(0);
        progressBar.setString("");
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("Arial", Font.PLAIN, 11));
        progressBar.setAlignmentX(0); //makes it so the previous elements are left-aligned properly

        infoBox.add(nameLabel);
        infoBox.add(phoneLabel);
        infoBox.add(emailLabel);
        infoBox.add(addressLabel);
        infoBox.add(Box.createVerticalGlue());
        infoBox.add(expeditionLabel);
        infoBox.add(progressBar);

        add(infoBox, BorderLayout.CENTER);
    }

    //Change Details
    public void setDetails(Object[] data)
    {
        //Contact Info
        nameLabel.setText(data[3].toString());
        phoneLabel.setText(formatPhoneNumber(data[4].toString()));
        emailLabel.setText(data[5].toString());

        //Address
        String completeAddress = String.format("%s, %s, %s %s", data[6], data[7], data[8], data[9]);
        completeAddress = completeAddress.replaceAll("null,* *", "");
        addressLabel.setText(completeAddress);

        //clear expedition info if there is not enough data (i.e. donor view)
        //or if fundraiser isn't going on one.
        if (data.length < 15 || data[10] == null)
        {
            expeditionLabel.setText("");
            progressBar.setString("");
            progressBar.setValue(0);
            return;
        }

        //Expedition Info
        DateFormat df = new SimpleDateFormat("M/d/yy");
        String fromDate = df.format(data[11]);
        String toDate = df.format(data[12]);
        String expeditionText = String.format("%s (%s thru %s)", data[10], fromDate, toDate);
        expeditionLabel.setText(expeditionText);

        //Progress Made
        double raised = Double.parseDouble(data[13].toString());
        double goal = Double.parseDouble(data[14].toString());
        String deadline = df.format(data[15]);
        String money = String.format("$%.2f Raised / $%.2f Goal (Cutoff %s)", raised, goal, deadline);
        progressBar.setValue((int) raised);
        progressBar.setMaximum((int) goal);
        progressBar.setString(money);
    }

    //Phone Number Formatter
    private String formatPhoneNumber(String number)
    {
        switch (number.length())
        {
            case 10:
                String area = number.substring(0, 3);
                String office = number.substring(3, 6);
                String station = number.substring(6);
                return String.format("(%s) %s-%s", area, office, station);
            default:
                return number;
        }
    }

}//end class