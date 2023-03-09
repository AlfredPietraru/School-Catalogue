package graficInterfaceSwing;

import appUsers.Parent;
import appUsers.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class ParentFrame extends UserFrame implements ActionListener {
    public ParentFrame(User myUser) {
        super(myUser);
        Parent parent = (Parent) myUser;
        setTextInPannels("Child name: " + parent.getStudent().toString(), "Child group: " +
                parent.getStudent().getGroupID(), null);
        super.setJListInListPannel(new Vector<>(parent.getNotificationInbox()), new Color(0xB08EA2),
                new Color(0x70CAD1));
        listPannel.setPreferredSize(new Dimension(400, 100));
        this.setPanel3(new Color(0x8EE3F5));
        updateDisplayPannel(new Color(0xEDDEA4));
        setColorForPannels(new Color(0xD9E5D6),
                new Color(0xFF9B42));
        this.getContentPane().setBackground(Color.blue);
        this.setVisible(true);
    }

    @Override
    public void updateDisplayPannel(Color color) {
        JLabel[] labels = new JLabel[6];
        for(int i = 0; i < 5; i++) {
            labels[i] = new JLabel();
            labels[i].setBounds((int)displayPannel.getAlignmentX(),
                    (int)displayPannel.getAlignmentY() + i * 50, 300, 50);
            labels[i].setFont(new Font("Serif", Font.PLAIN, 20));
            displayPannel.add(labels[i]);
        }
        labels[0].setText("Teachers Grade:");
        labels[1].setText("Assistant's Grade:");
        labels[2].setText("Total:");
        labels[3].setText("Teacher:");
        labels[4].setText("Assistant:");
        JTextArea textArea = new JTextArea();
        JTextField[] textFields = new JTextField[6];
        for(int i =0; i < 5; i++) {
            textFields[i] = new JTextField();
            textFields[i].setBounds((int)displayPannel.getAlignmentX() + 250,
                    (int)displayPannel.getAlignmentY() + i * 50, 300, 50);
            textFields[i].setFont(new Font("Serif", Font.PLAIN, 20));
            textFields[i].setVisible(true);
            textFields[i].setBackground(color);
            displayPannel.add(textFields[i]);
        }
        textArea.setBounds((int)displayPannel.getAlignmentX() + 250,
                (int)displayPannel.getAlignmentY() + 250, 300, 250);
        textArea.setFont(new Font("Serif", Font.PLAIN, 20));
        textArea.setLineWrap(true);
        textArea.setBackground(color);
        displayPannel.add(textArea);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
