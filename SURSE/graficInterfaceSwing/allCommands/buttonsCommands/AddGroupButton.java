package graficInterfaceSwing.allCommands.buttonsCommands;

import appUsers.Assistant;
import graficInterfaceSwing.UserFrame;
import uniSystem.Group;
import uniSystem.SearchSystem;
import uniSystem.courseTypes.Course;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class AddGroupButton extends ButtonCommand {

    UserFrame userFrame;
    Course course;
    public AddGroupButton(UserFrame userFrame, Course course) {
        super(userFrame.getDisplayPannel(), null);
        this.course = course;
        this.userFrame = userFrame;
    }

    @Override
    public void execute() {
        JList<uniSystem.Group> groupJList = findJMyList(findJScrollPanne(userFrame.getListPannel()));
        JForm frame = new JForm(course, groupJList);

    }
    private class JForm extends JFrame implements ActionListener{
        JButton button;
        JTextField[] fields;
        Course course;
        JList<Group> jList;

        JForm(Course course, JList<Group> jList) {
            super();
            this.course = course;
            this.jList = jList;
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setLayout(null);
            this.setVisible(true);
            this.setResizable(false);
            this.setSize(400, 300);
            JLabel[] labels = new JLabel[3];
            for (int i = 0; i < 2; i++) {
                labels[i] = new JLabel();
                labels[i].setFont(new Font("Serif", Font.PLAIN, 20));
                labels[i].setBounds(0, 50 * i, 200, 50);
                labels[i].setVisible(true);
                this.add(labels[i]);
            }
            labels[0].setText("Set Group ID: ");
            labels[1].setText("Assistant Name: ");
            fields = new JTextField[3];
            for (int i = 0; i < 2; i++) {
                fields[i] = new JTextField();
                fields[i].setFont(new Font("Serif", Font.PLAIN, 20));
                fields[i].setBounds(200, 50 * i, 200, 50);
                fields[i].setVisible(true);
                this.add(fields[i]);
            }
            button = new JButton();
            button.setBounds(200, 100,100, 60);
            button.setText("Confirm");
            button.addActionListener(this);
            this.add(button);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource() == button) {
                String assitantName = fields[1].getText();
                StringTokenizer stringTokenizer = new StringTokenizer(assitantName);
                String firstName = stringTokenizer.nextToken();
                String lastName = stringTokenizer.nextToken();
                Assistant assistant = course.getAssistant(firstName,
                        lastName);
                if(assistant != null) {
                    JOptionPane.showMessageDialog(null, "An assistant could" +
                            "not teach two groups", "Error info", JOptionPane.ERROR_MESSAGE);
                } else {
                    ArrayList<String> arrayListCourses = new ArrayList<>();
                    arrayListCourses.add(course.getName());
                    assistant = new Assistant(firstName, lastName, arrayListCourses);
                    assistant.setGroup_Id(fields[0].getText());
                    course.addAssistant(fields[0].getText(), assistant);
                    Group newGroup = new Group(fields[0].getText(), assistant, null);
                    course.addGroup(newGroup);
                    ((DefaultListModel)jList.getModel()).addElement(newGroup);
                    this.dispose();
                }
            }
        }
    }

}
